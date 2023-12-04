package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawFigure{

    private final Square square;

    public DrawSquare(Point topLeft, double size, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.square = new Square(topLeft, size);
    }

    @Override
    public void draw() {
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
    public String toString(){
        return square.toString();
    }

}
