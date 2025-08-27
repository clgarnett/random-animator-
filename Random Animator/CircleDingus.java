import java.awt.Graphics;

/**
 * CircleDingus is an example of a very simple Dingus.
 * 
 * @author Connor
 * @id 1791265
 */
class CircleDingus extends Dingus {
    protected int radius;
    protected boolean filled; // true: filled, false: outline

    /**
     * Create and initialize a new CircleDingus.
     * 
     * @param maxX upper bound for the x coordinate of the position
     * @param maxY upper bound for the y coordinate of the position
     */
    public CircleDingus(int maxX, int maxY) {
        // intialize randomly the Dingus properties, i.e., position and color
        super(maxX, maxY);
        
        type = "circle";

        // initialize randomly the CircleDingus properties, i.e., radius and filledness
        radius = random.nextInt((int) Math.round(maxX / 4) + 1);
        filled = random.nextBoolean();
        
        x -= 2 * radius * (double) x / RandomAnimator.painting.getWidth() - radius;
        y -= 2 * radius * (double) y / RandomAnimator.painting.getHeight() - radius;

        width = radius;
        height = radius;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(colour);
        if (filled) {
            g.fillArc(x, y, radius, radius, 0, 360);
        } else {
            g.drawArc(x, y, radius, radius, 0, 360);
        }
    }
}
