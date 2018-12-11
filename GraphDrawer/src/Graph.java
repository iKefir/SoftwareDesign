import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.*;
import static java.lang.Math.PI;

public abstract class Graph {

    /**
     * Bridge to drawing api
     */
    protected DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    protected List<Vertex> getVertCoords(int vertexCount) {
        float centerX = (float)(drawingApi.getDrawingAreaWidth()) / 2, centerY = (float)(drawingApi.getDrawingAreaHeight()) / 2;
        float radius = min(centerX, centerY);
        float x = 0, y = 0, xnew, ynew;
        if (centerX < centerY) {
            x = -radius;
        } else {
            y = -radius + 20;
        }
        List<Vertex> ans = new ArrayList<>();
        ans.add(new Vertex(x + centerX, y + centerY));
        double s = sin(2*PI / vertexCount), c = cos(2*PI / vertexCount);
        for (int i = 1; i < vertexCount; ++i) {
            xnew = (float)(x * c - y * s);
            ynew = (float)(x * s + y * c);
            x = xnew;
            y = ynew;
            ans.add(new Vertex(x + centerX, y + centerY));
        }

        return ans;
    }

    public abstract void readGraph(Scanner in);
    public abstract void drawGraph();
}
