import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough arguments");
            return;
        }

        DrawingApi drawingApi = (args[0].equals("awt") ? new AwtDrawingApi() : new JavaFXDrawingApi());
        Graph graph = (args[1].equals("edgelist") ? new EdgeListGraph(drawingApi) : new AdjacencyMatrixGraph(drawingApi));

        Scanner in = new Scanner(System.in);
        graph.readGraph(in);
        graph.drawGraph();

        drawingApi.drawFinally();
    }
}
