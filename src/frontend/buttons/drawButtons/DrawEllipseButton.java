package frontend.buttons.drawButtons;

import backend.model.Point;
import frontend.drawFigures.DrawEllipse;
import frontend.drawFigures.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawEllipseButton implements Buttons{
    @Override
    public DrawFigure execute(Point startPoint, Point endPoint, GraphicsContext gc, Color fillColor, Color strokeColor) {
        Point centerPoint = new Point(Math.abs(endPoint.x + startPoint.x) / 2, (Math.abs((endPoint.y + startPoint.y)) / 2));
        double sMayorAxis = Math.abs(endPoint.x - startPoint.x);
        double sMinorAxis = Math.abs(endPoint.y - startPoint.y);
        return new DrawEllipse(startPoint, centerPoint, sMayorAxis, sMinorAxis, gc, fillColor, strokeColor);
    }
}
