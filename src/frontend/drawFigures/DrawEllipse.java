package frontend.drawFigures;

import backend.model.Ellipse;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawEllipse extends DrawFigure{

    private Ellipse ellipse;
    private final Point startPoint;

    public DrawEllipse(Point startPoint ,Point centerPoint, double sMayorAxis, double sMinorAxis, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.startPoint = startPoint;
        this.ellipse = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public void draw() {
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

}