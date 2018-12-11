import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class AwtDrawingApi extends Frame implements DrawingApi {
    private float circleRadius;

    public AwtDrawingApi() {
        circleRadius = 10.0f;
        setBackground(Color.white);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        setSize(1000, 600);
        setVisible(true);
    }

    public long getDrawingAreaWidth() {
        return getWidth() - (long) (2 * circleRadius);
    }

    public long getDrawingAreaHeight() {
        return getHeight();
    }

    public void drawCircle(float x, float y) {
        Graphics2D ga = (Graphics2D)getGraphics();
        ga.setPaint(Color.black);
        ga.fill(new Ellipse2D.Float(x, y, 2 * circleRadius, 2 * circleRadius));
    }

    public void drawLine(float x1, float y1, float x2, float y2) {
        Graphics2D ga = (Graphics2D)getGraphics();
        ga.setPaint(Color.black);
        ga.draw(new Line2D.Float(x1 + circleRadius, y1 + circleRadius, x2 + circleRadius, y2 + circleRadius));
    }

    public void drawFinally() {
    }
}
