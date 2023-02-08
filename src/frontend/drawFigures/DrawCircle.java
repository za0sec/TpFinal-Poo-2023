package frontend.drawFigures;

import backend.model.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class DrawCircle extends DrawFigure{

    private Circle circle;

    public DrawCircle(Point centerPoint, double radius, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.circle = new Circle(centerPoint, radius);
    }

    @Override
    public void draw(boolean isSelected) {
        super.draw(isSelected);
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

    @Override
    public boolean intersects(Rectangle other) {
        Point center = circle.getCenterPoint();
        double radius = circle.getRadius();

        if (center.getX() >= other.getTopLeft().getX() && center.getX() <= other.getBottomRight().getX() &&
                center.getY() >= other.getTopLeft().getY() && center.getY() <= other.getBottomRight().getY()) {
            return true;
        }

        Point[] corners = {
                other.getTopLeft(),
                new Point(other.getTopLeft().getX(), other.getBottomRight().getY()),
                other.getBottomRight(),
                new Point(other.getBottomRight().getX(), other.getTopLeft().getY())
        };

        for (Point corner : corners) {
            if (Math.sqrt(Math.pow(corner.getX() - center.getX(), 2) + Math.pow(corner.getY() - center.getY(), 2)) <= radius) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setGradient(boolean value, Color fillColor) {
        super.setGradient(value, fillColor);
    }

    @Override
    public void rotate() {

    }

    @Override
    public void enlarge() {
        Ellipse ellipse = new Ellipse(circle.getCenterPoint(), circle.getRadius(), circle.getRadius());
        super.resizeOvals(ellipse, 1.25);
        this.circle = new Circle(ellipse.getCenterPoint(), ellipse.getsMayorAxis());
    }

    @Override
    public void reduce() {
        Ellipse ellipse = new Ellipse(circle.getCenterPoint(), circle.getRadius(), circle.getRadius());
        super.resizeOvals(ellipse, 0.75);
        this.circle = new Circle(ellipse.getCenterPoint(), ellipse.getsMayorAxis());
    }

    @Override
    public void mirrorH() {
        this.circle = new Circle(new Point (circle.getCenterPoint().getX()  + 2 * circle.getRadius(), circle.getCenterPoint().getY()), circle.getRadius());
    }

    @Override
    public void mirrorV() {
        this.circle = new Circle(new Point(circle.getCenterPoint().getX(), circle.getCenterPoint().getY() + 2 * circle.getRadius()) , circle.getRadius());
    }

    @Override
    public void setShadow(boolean value) {
        super.setShadow(value);
        setOvalShadow(circle.getCenterPoint(), circle.getRadius() * 2, circle.getRadius() * 2);
    }

    @Override
    public void setBeveled(boolean value) {
        super.setBeveled(value);
        setOvalBeveled(circle.getCenterPoint(), circle.getRadius(), circle.getRadius());
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof DrawCircle that && that.circle.equals(circle));
    }

}