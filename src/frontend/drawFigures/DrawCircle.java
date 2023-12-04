package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Ellipse;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawFigure{

    private final Circle circle;

    public DrawCircle(Point centerPoint, double radius, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.circle = new Circle(centerPoint, radius);
    }

    @Override
    public void draw() {
        double diameter = circle.getRadius() * 2;
        gc.fillOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
        gc.strokeOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
    }

    @Override
    public Circle getFigure(){
        return circle;
    }

    @Override
    public void move(double diffX, double diffY) {
        circle.getCenterPoint().x += diffX;
        circle.getCenterPoint().y += diffY;
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
    }

    @Override
    public void updatePreview(Point eventPoint) {
        circle.setRadius(Math.abs(eventPoint.getX() - circle.getCenterPoint().getX()));
    }


    @Override
    public String toString(){
        return circle.toString();
    }

}
