import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** 
 * Main class for the homework assignment Random Animator.
 * 
 *
 * @author Connor
 * @id 1791265
 */
public class RandomAnimator {
    JFrame frame;
    static JPanel buttonPanel;
    static JPanel mainPanel;
    FlowLayout buttonLayout;
    BoxLayout canvasLayout;
    static Painting painting; // panel that provides the random painting
    JButton regenerateButton;
    JButton shotButton;
    JButton recolourButton;
    JButton startAnimButton;
    JButton stopAnimButton;
    
    /**
     * Create a new instance of the RandomAnimator application.
     */
    RandomAnimator() {
        // invokeLater: preferred way to create components
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                //instanciate fields
                frame = new JFrame("Computer Assisted Random Animator");
                mainPanel = new JPanel();
                painting = new Painting();
                buttonPanel = new JPanel();
                canvasLayout = new BoxLayout(mainPanel,  BoxLayout.PAGE_AXIS);
                buttonLayout = new FlowLayout(FlowLayout.CENTER);
                
                buttonPanel.setLayout(buttonLayout);
                buttonPanel.setBackground(new Color(100, 230, 100));
                
                mainPanel.setLayout(canvasLayout);
                painting.setBackground(new Color(240, 240, 240));
                
                regenerateButton = new JButton("Regenerate");
                shotButton = new JButton("Screenshot");
                recolourButton = new JButton("Recolour");
                startAnimButton = new JButton("Start Animation");
                stopAnimButton = new JButton("Stop Animation");
                
                // painting provides reaction to buttonclick
                regenerateButton.addActionListener(painting); 
                shotButton.addActionListener(painting);
                recolourButton.addActionListener(painting);
                startAnimButton.addActionListener(painting);
                stopAnimButton.addActionListener(painting);
                
                buttonPanel.add(regenerateButton);
                buttonPanel.add(shotButton);
                buttonPanel.add(recolourButton);
                buttonPanel.add(startAnimButton);
                buttonPanel.add(stopAnimButton);
                
                buttonPanel.setMaximumSize(new Dimension(2000, 60));
                mainPanel.add(buttonPanel);
                mainPanel.add(painting);
                frame.add(mainPanel);
                frame.pack();
                frame.setVisible(true);
                painting.regenerate(); // can be done here since painting has a size!
            }
        });
    }

    public static void main(String[] arg) {
        new RandomAnimator();
    }
}