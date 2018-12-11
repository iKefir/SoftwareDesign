import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EdgeListGraph extends Graph {

    private List<Edge> edgeList;

    public EdgeListGraph(DrawingApi drawingApi) {
        super(drawingApi);
        edgeList = new ArrayList<>();
    }

    public void readGraph(Scanner in) {
        int edgesAmount = in.nextInt();
        for (int i = 0; i < edgesAmount; ++i) {
            edgeList.add(new Edge(in.nextInt(), in.nextInt()));
        }
    }

    public void drawGraph() {
        int vertAmount = 0;
        for (Edge e : edgeList) {
            if (vertAmount < e.a) {
                vertAmount = e.a;
            }
            if (vertAmount < e.b) {
                vertAmount = e.b;
            }
        }
        List<Vertex> vertices = getVertCoords(vertAmount);
        for (Vertex vertex : vertices) {
            drawingApi.drawCircle(vertex.x, vertex.y);
        }
        for (Edge edge : edgeList) {
            Vertex a = vertices.get(edge.a - 1);
            Vertex b = vertices.get(edge.b - 1);
            drawingApi.drawLine(a.x, a.y, b.x, b.y);
        }
    }
}
