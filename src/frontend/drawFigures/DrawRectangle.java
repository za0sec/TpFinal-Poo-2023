package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRectangle extends DrawFigure {


    private Rectangle rectangle;


    public DrawRectangle(Point topLeft, Point bottomRight, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.rectangle = new Rectangle(topLeft, bottomRight);
    }

    @Override
    public void draw() {
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()), Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()));
    }
}
