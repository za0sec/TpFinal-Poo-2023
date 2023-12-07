package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.w3c.dom.css.Rect;

public class DrawSquare extends DrawFigure{

    private Square square;

    public DrawSquare(Point topLeft, double size, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.square = new Square(topLeft, size);
    }

    @Override
    public void draw() {
        super.draw();
        gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
        gc.strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));

    }

    @Override
    public Square getFigure(){
        return square;
    }

    @Override
    public void move(double diffX, double diffY) {
        square.getTopLeft().x += diffX;
        square.getBottomRight().x += diffX;
        square.getTopLeft().y += diffY;
        square.getBottomRight().y += diffY;
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return eventPoint.getX() > square.getTopLeft().getX() && eventPoint.getX() < square.getBottomRight().getX() &&
                eventPoint.getY() > square.getTopLeft().getY() && eventPoint.getY() < square.getBottomRight().getY();
    }

    @Override
    public void updatePreview(Point eventPoint) {
        square.setBottomRight(eventPoint);
    }

    @Override
    public String toString(){
        return square.toString();
    }

    public boolean intersects(Rectangle other) {
        return square.getTopLeft().getX() < other.getBottomRight().getX() &&
                square.getBottomRight().getX() > other.getTopLeft().getX() &&
                square.getTopLeft().getY() < other.getBottomRight().getY() &&
                square.getBottomRight().getY() > other.getTopLeft().getY();
    }

    @Override
    public void setShadow(boolean value) {
        super.setShadow(value);
        if (isShadow){
            rectangleShadow(new Rectangle(square.getTopLeft(), square.getBottomRight()));
        }
    }

    @Override
    public void setGradient(boolean value, Color fillColor) {
        setRectangleGradient(value, fillColor);
    }

    @Override
    public void rotate() {

    }

    @Override
    public void enlarge() {
        Rectangle rectangle = new Rectangle(square.getTopLeft(), square.getBottomRight());
        resizeRectangle(rectangle, 1.125);
        this.square = new Square(rectangle.getTopLeft(), rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());
    }

    @Override
    public void reduce() {
        Rectangle rectangle = new Rectangle(square.getTopLeft(), square.getBottomRight());
        resizeRectangle(rectangle, 0.875);
        this.square = new Square(rectangle.getTopLeft(), rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());
    }

    @Override
    public void mirrorH() {
        Rectangle rectangle = new Rectangle(square.getTopLeft(), square.getBottomRight());
        mirrorRectangles(rectangle, true);
        this.square = new Square(rectangle.getTopLeft(), rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());
    }

    @Override
    public void mirrorV() {
        Rectangle rectangle = new Rectangle(square.getTopLeft(), square.getBottomRight());
        mirrorRectangles(rectangle, false);
        this.square = new Square(rectangle.getTopLeft(), rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof DrawSquare that && that.square.equals(square));
    }


}
