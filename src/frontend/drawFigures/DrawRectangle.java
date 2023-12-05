package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
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

    @Override
    public Rectangle getFigure(){
        return rectangle;
    }

    @Override
    public void move(double diffX, double diffY) {
        rectangle.getTopLeft().x += diffX;
        rectangle.getBottomRight().x += diffX;
        rectangle.getTopLeft().y += diffY;
        rectangle.getBottomRight().y += diffY;
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return eventPoint.getX() > rectangle.getTopLeft().getX() && eventPoint.getX() < rectangle.getBottomRight().getX() &&
                eventPoint.getY() > rectangle.getTopLeft().getY() && eventPoint.getY() < rectangle.getBottomRight().getY();
    }

    @Override
    public void updatePreview(Point eventPoint) {
        rectangle.setBottomRight(eventPoint);
    }

    @Override
    public String toString(){
        return rectangle.toString();
    }

    @Override
    public boolean intersects(Rectangle other) {
        return rectangle.getTopLeft().getX() < other.getBottomRight().getX()
                && rectangle.getBottomRight().getX() > other.getTopLeft().getX()
                && rectangle.getTopLeft().getY() < other.getBottomRight().getY()
                && rectangle.getBottomRight().getY() > other.getTopLeft().getY();
    }
}
