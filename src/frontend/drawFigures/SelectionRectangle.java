package frontend.drawFigures;

import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SelectionRectangle extends DrawRectangle{
    public SelectionRectangle(Point topLeft, Point bottomRight, GraphicsContext gc, Color fill, Color stroke) {
        super(topLeft, bottomRight, gc, fill, stroke);

    }
    @Override
    public void draw(){
        gc.setLineDashes(15d, 10d);
        gc.setFill(fill);
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }
}
