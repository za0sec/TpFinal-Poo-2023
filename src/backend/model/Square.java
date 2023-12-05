package backend.model;

public class Square implements Figure {

    private final Point topLeft;
    private Point bottomRight;

    public Square(Point topLeft, double size) {
        this.topLeft = topLeft;
        this.bottomRight = new Point(topLeft.x + size, topLeft.y + size);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", topLeft, bottomRight);
    }

    public void setBottomRight(Point bottomRight){
        double size = Math.abs(bottomRight.getX() - topLeft.getX());
        this.bottomRight = new Point(topLeft.x + size, topLeft.y + size);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Square that && that.topLeft.equals(topLeft) && that.bottomRight.equals(bottomRight));
    }

}
