import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class JavaFXDrawingApi extends Application implements DrawingApi {

    static List<Circle> vertices = new ArrayList<>();
    static List<Line> edges = new ArrayList<>();
    static long width = 1000, height = 600;
    static private float circleRadius = 10;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.setTitle("Graph");
        Group root = new Group();

        root.getChildren().addAll(vertices);
        root.getChildren().addAll(edges);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public long getDrawingAreaWidth() {
        return width;
    }

    public long getDrawingAreaHeight() {
        return height - ((long) circleRadius * 2);
    }

    public void drawCircle(float x, float y) {
        vertices.add(new Circle(x+circleRadius, y+circleRadius, circleRadius));
    }

    public void drawLine(float x1, float y1, float x2, float y2) {
        edges.add(new Line(x1 + circleRadius, y1 + circleRadius, x2 + circleRadius, y2 + circleRadius));
    }

    public void drawFinally() {
        launch();
    }
}
