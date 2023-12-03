package frontend.drawFigures;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawEllipse extends DrawFigure{

    Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, GraphicsContext gc) {
        super(gc);
        this.ellipse = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public void draw() {
        gc.strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
}