package frontend.drawFigures;

import backend.model.Ellipse;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class DrawEllipse extends DrawFigure{

    protected Ellipse ellipse;
    private final Point startPoint;

    public DrawEllipse(Point startPoint ,Point centerPoint, double sMayorAxis, double sMinorAxis, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.startPoint = startPoint;
        this.ellipse = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public void draw() {
        super.draw();
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

    @Override
    public Ellipse getFigure(){
        return ellipse;
    }

    @Override
    public void move(double diffX, double diffY) {
        ellipse.getCenterPoint().x += diffX;
        ellipse.getCenterPoint().y += diffY;
    }

    @Override
    public boolean belongs(Point eventPoint) {
        return ((Math.pow(eventPoint.getX() - ellipse.getCenterPoint().getX(), 2) / Math.pow(ellipse.getsMayorAxis(), 2)) +
                (Math.pow(eventPoint.getY() - ellipse.getCenterPoint().getY(), 2) / Math.pow(ellipse.getsMinorAxis(), 2))) <= 0.30;
    }

    @Override
    public void updatePreview(Point eventPoint) {
        ellipse.updateEllipse(startPoint ,eventPoint);
    }

    @Override
    public String toString(){
        return ellipse.toString();
    }

    @Override
    public boolean intersects(Rectangle selectionRectangle) {

        Point center = ellipse.getCenterPoint();
        if (center.getX() >= selectionRectangle.getTopLeft().getX() &&
                center.getX() <= selectionRectangle.getBottomRight().getX() &&
                center.getY() >= selectionRectangle.getTopLeft().getY() &&
                center.getY() <= selectionRectangle.getBottomRight().getY()) {
            return true;
        }

        Point[] corners = {
                selectionRectangle.getTopLeft(),
                new Point(selectionRectangle.getTopLeft().getX(), selectionRectangle.getBottomRight().getY()),
                selectionRectangle.getBottomRight(),
                new Point(selectionRectangle.getBottomRight().getX(), selectionRectangle.getTopLeft().getY())
        };

        double rx = ellipse.getsMayorAxis() / 2.0; // Radio en el eje X
        double ry = ellipse.getsMinorAxis() / 2.0; // Radio en el eje Y
        for (Point corner : corners) {
            double dx = (corner.getX() - center.getX()) / rx;
            double dy = (corner.getY() - center.getY()) / ry;
            if (dx * dx + dy * dy <= 1) {
                return true;
            }
        }


        return false;
    }

    @Override
    public void setShadow(boolean value) {
        super.setShadow(value);
        setOvalShadow(ellipse.getCenterPoint(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }


    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof DrawEllipse that && that.ellipse.equals(ellipse));
    }

    @Override
    public void setGradient(boolean value, Color fillColor){
        super.setGradient(value, fillColor);
    }

    @Override
    public void rotate() {
        Ellipse rotatedEllipse = new Ellipse(ellipse.getCenterPoint(), ellipse.getsMinorAxis(), ellipse.getsMayorAxis());
        this.ellipse = rotatedEllipse;
    }

    @Override
    public void enlarge() {
        super.resizeOvals(ellipse, 1.25);
    }

    @Override
    public void reduce() {
        super.resizeOvals(ellipse, 0.75);
    }

    @Override
    public void setBeveled(boolean value) {
        super.setBeveled(value);
        super.setOvalBeveled(ellipse.getCenterPoint(), ellipse.getsMayorAxis() / 2, ellipse.getsMinorAxis() / 2);
    }
}