import java.awt.Graphics;
import java.awt.Polygon;

/**. */
public class PolygonDingus extends Dingus {
    Polygon polygon;
    int sides;
    int size;
    boolean filled;

    /**
     * Instanciates polygon dingus with it's necessary fields and randomness.
     */
    public PolygonDingus(int maxX, int maxY, int maxSize, int polygonSides) {
        super(maxX, maxY);
        type = "polygon";
        polygon = new Polygon();  
        sides = polygonSides;
        size = random.nextInt(maxSize);
        filled = random.nextBoolean();


        double theta = 2 * Math.PI / sides;

        x -= 2 * size * (double) x / RandomAnimator.painting.getWidth() - size;
        y -= 2 * size * (double) y / RandomAnimator.painting.getHeight() - size;

        for (int i = 0; i < sides; ++i) {
            polygon.addPoint((int) Math.round(Math.cos(theta * i) * size + x),
                (int) Math.round(Math.sin(theta * i) * size) + y);
        } 

        width = polygon.getBounds().width / 2;
        height = polygon.getBounds().height / 2;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(colour);
        if (filled) {
            g.fillPolygon(polygon);
        } else {
            g.drawPolygon(polygon);
        }
    }
    
    @Override
    public void translateDingus(int x, int y) {
        polygon.translate(x, y);
        this.x += x;
        this.y += y;
    }

    @Override
    public boolean containedInPanel(int var, int limit) {
        return var >= width && var + width <= limit;
    }
}
