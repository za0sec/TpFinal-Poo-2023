package backend.model;

public class Ellipse implements Figure {

    protected Point centerPoint;
    protected double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    public void updateEllipse(Point startPoint, Point eventPoint){
        this.centerPoint = new Point(Math.abs(eventPoint.x + startPoint.x) / 2, (Math.abs((eventPoint.y + startPoint.y)) / 2));
        this.sMayorAxis = Math.abs(eventPoint.x - startPoint.x);
        this.sMinorAxis = Math.abs(eventPoint.y - startPoint.y);
    }

    public void setAxis(double newMayorAxis, double newMinorAxis){
        this.sMayorAxis = newMayorAxis;
        this.sMinorAxis = newMinorAxis;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Ellipse that && that.centerPoint.equals(centerPoint) && that.sMinorAxis == sMinorAxis && that.sMayorAxis == sMayorAxis);
    }

}
