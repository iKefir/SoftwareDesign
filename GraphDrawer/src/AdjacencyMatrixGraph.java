import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdjacencyMatrixGraph extends Graph {
    private List<List<Boolean>> adjecencyMatrix;

    public AdjacencyMatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    public void readGraph(Scanner in) {
        adjecencyMatrix = new ArrayList<>();
        int vertexAmount = in.nextInt();
        int tmp;
        for (int i = 0; i < vertexAmount; ++i) {
            adjecencyMatrix.add(new ArrayList<>());
            for (int j = 0; j < vertexAmount; ++j) {
                tmp = in.nextInt();
                adjecencyMatrix.get(i).add(tmp == 1);
            }
        }
    }


    public void drawGraph() {
        int vertAmount = adjecencyMatrix.size();
        List<Vertex> vertices = getVertCoords(vertAmount);
        for (Vertex vertex : vertices) {
            drawingApi.drawCircle(vertex.x, vertex.y);
        }
        for (int i = 0; i < adjecencyMatrix.size(); ++i) {
            for (int j = 0; j < adjecencyMatrix.get(i).size(); ++j) {
                if (adjecencyMatrix.get(i).get(j)) {
                    Vertex a = vertices.get(i);
                    Vertex b = vertices.get(j);
                    drawingApi.drawLine(a.x, a.y, b.x, b.y);
                }
            }
        }
    }
}
