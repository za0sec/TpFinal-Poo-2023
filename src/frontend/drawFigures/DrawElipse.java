package frontend.drawFigures;

import backend.model.Ellipse;
import backend.model.Point;

public class DrawElipse extends Ellipse implements DrawFigure{
    public DrawElipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public void draw() {

    }
}
