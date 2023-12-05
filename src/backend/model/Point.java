package backend.model;

public class Point {

    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Point that && that.x == x && that.y == y);
    }

}
