package backend.model;

import frontend.drawFigures.DrawSquare;

public class Circle implements Figure {

    protected final Point centerPoint;
    protected double radius;

    public Circle(Point centerPoint, double radius) {
        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, radius);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double newRadius){
        this.radius = newRadius;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Circle that && that.centerPoint.equals(centerPoint) && that.radius == radius);
    }

}
