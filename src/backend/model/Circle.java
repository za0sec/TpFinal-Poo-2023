package backend.model;

import java.util.Objects;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, radius, radius);
    }

    public double getRadius() {
        return getsMayorAxis();
    }

    public void setRadius(double newRadius) {
        this.sMayorAxis = newRadius;
        this.sMinorAxis = newRadius;
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, getRadius());
    }

}
