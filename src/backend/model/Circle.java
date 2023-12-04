package backend.model;

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

}
