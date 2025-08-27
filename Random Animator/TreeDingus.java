import java.awt.Graphics;

/**
 * TreeDingus is an example of a slightly more advanced Dingus.
 * 
 * TreeDingus draws a "tree" using a rectangle as the trunk, and circle for a
 * crown.
 * 
 * @author Connor
 * @id 1791265
 */
class TreeDingus extends Dingus {
    private int crownRadius;
    private int trunkHeight;
    private int trunkWidth;
    private boolean filled; // true: filled; false: outline

    /**
     * Create and initialize a new TreeDingus.
     * 
     * @param maxX upper bound for the x coordinate of the position
     * @param maxY upper bound for the y coordinate of the position
     */
    public TreeDingus(int maxX, int maxY) {
        // initialize Dingus properties
        super(maxX, maxY);

        // initialize TreeDingus properties
        crownRadius = random.nextInt(maxY / (random.nextInt(20) + 4)) + 4;
        trunkHeight = random.nextInt(2 * crownRadius / (random.nextInt(2) + 1));
        trunkWidth = crownRadius / 3 + 1;
        filled = random.nextBoolean();
        width = crownRadius * 2;
        height = crownRadius * 2 + trunkHeight;

        x = random.nextInt(maxX - width);
        y = random.nextInt(maxY - height);
        
    }

    @Override
    void draw(Graphics g) {
        // draw crown
        g.setColor(colour);
        if (filled) {
            // more general way to draw an oval than with fillOval (hint :-)
            g.fillArc(x, y, 2 * crownRadius, 2 * crownRadius, 0, 360);
        } else {
            g.drawArc(x, y, 2 * crownRadius, 2 * crownRadius, 0, 360);
        }

        // draw trunk
        g.setColor(colour);
        int xx = x + crownRadius - trunkWidth / 2;
        int yy = y + 2 * crownRadius;

        if (filled) {
            g.fillRect(xx, yy, trunkWidth, trunkHeight);
        } else {
            g.fillRect(xx, yy, trunkWidth, trunkHeight);
        }
    }

    @Override
    public boolean containedInPanel(int var, int limit) {
        if (var == x) {
            return var >= 0 && var + width <= limit;
        }
        return var >= 0 && var + height <= limit;
    }
}