package backend.model;

import java.util.Objects;

public class Rectangle implements Figure {

    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setTopLeft(Point topLeft){
        this.topLeft = topLeft;
    }

    public void setBottomRight(Point bottomRight){
        this.bottomRight = bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Rectangle that && that.topLeft.equals(topLeft) && that.bottomRight.equals(bottomRight));
    }

    @Override
    public int hashCode(){
        return Objects.hash(topLeft, bottomRight);
    }

}
