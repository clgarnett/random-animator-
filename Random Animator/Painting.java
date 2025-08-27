import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Painting.
 * 
 * Paint with Dinguses, i.e., generate a new painting by making a random
 * composition of Dingus shapes.
 *
 * @author Connor
 * @id 1791265
 */
public class Painting extends JPanel implements ActionListener {

    static final long SEED = 37;

    // ---- Random ----
    static final Random RANDOM = new Random(SEED);
    int numberOfRegenerates = 0;

    // ---- Screenshots ----
    char current = '0';
    String filename = "randomshot_"; // prefix

    ArrayList<Dingus> shapes = new ArrayList<Dingus>();
    String dingusType;
    boolean animationRunning;
    boolean leftToRight;
    boolean topToBottom;
    javax.swing.Timer timer;
    int speedLimit;

    /**
     * Create a new painting.
     */
    public Painting() {
        setPreferredSize(new Dimension(800, 450)); // make panel 800 by 450 pixels.
        timer = new javax.swing.Timer(20, this);
        timer.start();
        speedLimit = 10;
        animationRunning = false;
    }

    /**
     * Reaction to button press.
     * 
     * My defense to "deeply nested code" allegations from checkstyle: 
     *     - It is clearly simple and easy to read
     */
    public void actionPerformed(ActionEvent e) {
        if ("Regenerate".equals(e.getActionCommand())) {
            regenerate();

        } else if ("Screenshot".equals(e.getActionCommand())) { // screenshot
            saveScreenshot(this, filename + current++); // ++ to show off compact code :-)

        } else if ("Recolour".equals(e.getActionCommand())) {
            recolour();

        } else if ("Start Animation".equals(e.getActionCommand()) && !animationRunning) {
            //Creates a Set as opposed to an ArrayList to ensure different random indices of shapes
            Set<Integer> generated = new LinkedHashSet<Integer>();
            int numberOfAnimatedShapes = RANDOM.nextInt(6) + 5;

            //Randomizes which shapes get motion
            while (generated.size() < numberOfAnimatedShapes) {
                int next = RANDOM.nextInt(shapes.size());
                generated.add(next);
            }

            //Randomizes the speed and direction of aformentinoned motion
            for (int i = 0; i < numberOfAnimatedShapes; i++) {
                int xVel = RANDOM.nextInt(speedLimit) + 1;
                int yVel = RANDOM.nextInt(speedLimit) + 1;
                shapes.get((int) generated.toArray()[i]).xVelocity = xVel;
                shapes.get((int) generated.toArray()[i]).yVelocity = yVel;
                shapes.get((int) generated.toArray()[i]).leftToRight = RANDOM.nextBoolean();
                shapes.get((int) generated.toArray()[i]).bottomToTop = RANDOM.nextBoolean();
            }

            animationRunning = true;
        } else if ("Stop Animation".equals(e.getActionCommand()) && animationRunning) {
            for (Dingus dingus : shapes) {
                dingus.xVelocity = 0;
                dingus.yVelocity = 0;
            }
            animationRunning = false;
        }

        if (animationRunning) {
            runAnimation();
        }
    }

    /**
     * Handles the motion and colisions of the dinguses.
     */
    public void runAnimation() {
        for (Dingus dingus : shapes) {
            if (dingus.xVelocity != 0 || dingus.yVelocity != 0) {
                /*
                 * Move dingus speed amount of pixels horizontally
                 * and bounce off border if necessary.
                 */
                for (int i = 0; i < dingus.xVelocity; i++) {
                    if (!dingus.containedInPanel(dingus.x, getWidth())) {
                        dingus.leftToRight = !dingus.leftToRight;
                    }
                    if (dingus.leftToRight) {
                        dingus.translateDingus(1, 0);
                    } else {
                        dingus.translateDingus(-1, 0);
                    }
                }

                /*
                 * Move dingus speed amount of pixels vertically
                 * and bounce off border if necessary.
                 */
                for (int i = 0; i < dingus.yVelocity; i++) {
                    if (!dingus.containedInPanel(dingus.y, getHeight())) {
                        dingus.bottomToTop = !dingus.bottomToTop;
                    }
                    if (dingus.bottomToTop) {
                        dingus.translateDingus(0, 1);
                    } else {
                        dingus.translateDingus(0, -1);
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) { // draw all your shapes
        super.paintComponent(g); // clears the panel

        if (shapes.size() == 0) {
            for (int i = 0; i < 10 + RANDOM.nextInt(21); i++) {
                int shapeNumber = RANDOM.nextInt(6);
                if (shapeNumber == 0) {
                    CircleDingus dingus = new CircleDingus(
                            RANDOM.nextInt(getWidth()), RANDOM.nextInt(getHeight()));
                    shapes.add(dingus);
                    dingus.draw(g);
                } else if (shapeNumber == 1) {
                    TreeDingus dingus = new TreeDingus(getWidth(), getHeight());
                    shapes.add(dingus);
                    dingus.draw(g);
                } else {
                    PolygonDingus dingus = new PolygonDingus(
                            getWidth(), getHeight(), 50, shapeNumber + 1);
                    shapes.add(dingus);
                    dingus.draw(g);
                }
            }
        } else {
            for (Dingus dingus : shapes) {
                dingus.draw(g);
                repaint();
            }
        }

    }

    /**
     * Regenerate this painting.
     */
    void regenerate() {
        numberOfRegenerates++; // do not change

        // create random shapes
        if (shapes.size() != 0) {
            shapes.clear();
        }

        revalidate();
        repaint();
        paintComponent(getGraphics());
        animationRunning = false;
    }

    /**
     * Saves a screenshot of this painting to disk.
     * 
     * Note. This action will override existing files!
     *
     * @param component Component to be saved
     * @param name      filename of the screenshot, followed by a sequence number
     * 
     */
    void saveScreenshot(Component component, String name) {
        // minus 1 because the initial picture should not count
        String randomInfo = "" + SEED + "+" + (numberOfRegenerates - 1);
        System.out.println(SwingUtilities.isEventDispatchThread());
        BufferedImage image = new BufferedImage(
                component.getWidth(),
                component.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // call the Component's paint method, using
        // the Graphics object of the image.
        Graphics graphics = image.getGraphics();
        component.paint(graphics); // alternately use .printAll(..)
        graphics.drawString(randomInfo, 0, component.getHeight());

        try {
            ImageIO.write(image, "PNG", new File(name + ".png"));
            System.out.println("Saved screenshot as " + name);
        } catch (IOException e) {
            System.out.println("Saving screenshot failed: " + e);
        }
    }

    /** . */
    public void recolour() {
        for (Dingus dingus : shapes) {
            dingus.colour = new Color(RANDOM.nextInt(6) * 255 / 5,
                    RANDOM.nextInt(6) * 255 / 5, RANDOM.nextInt(6) * 255 / 5);
            dingus.draw(getGraphics());
        }
    }
}