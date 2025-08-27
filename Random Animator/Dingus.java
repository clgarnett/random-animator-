import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Dingus represents an arbitraty shape.
 * 
 * @author Connor
 * @id 1791265
 */
abstract class Dingus {
    /**
     * Random generator to be used by all subclasses of Dingus.
     * DON'T CHANGE
     */
    Random random = Painting.RANDOM;

    /** 
     * Postion of the shape (upper left corner).
     */
    protected int x;
    protected int y;

    /** 
     * Color used for drawing this shape.
     */
    protected Color colour;

    /** 
     * Maximal coordinates; drawing area is (0,0)- (maxX,maxY).
     */
    int maxX;
    int maxY;

    int xVelocity;
    int yVelocity;

    int width;
    int height;

    boolean leftToRight;
    boolean bottomToTop;

    String type;

    /**
     * Initialize color and position to random values.
     *
     * @param maxX upper bound for the x coordinate of the position
     * @param maxY upper bound for the y coordinate of the position
     */
    public Dingus(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        //velocity
        xVelocity = 0;
        yVelocity = 0;

        // Initialize to a random position
        x = random.nextInt(maxX + 1);
        y = random.nextInt(maxY + 1);

        // Initialize to a random color
        colour = new Color(random.nextInt(6) * 255 / 5,
        random.nextInt(6) * 255 / 5, random.nextInt(6) * 255 / 5);        
    }

    /**. */
    public void translateDingus(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public boolean containedInPanel(int var, int limit) {
        return var >= 0 && var + width <= limit;
    }

    abstract void draw(Graphics g);
}