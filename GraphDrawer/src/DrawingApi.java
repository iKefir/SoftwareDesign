public interface DrawingApi {
    long getDrawingAreaWidth();
    long getDrawingAreaHeight();
    void drawCircle(float x, float y);
    void drawLine(float x1, float y1, float x2, float y2);
    void drawFinally();
}
