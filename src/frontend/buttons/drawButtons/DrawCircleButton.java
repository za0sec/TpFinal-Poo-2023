package frontend.buttons.drawButtons;

import backend.model.Point;
import frontend.drawFigures.DrawCircle;
import frontend.drawFigures.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircleButton implements Buttons{
    @Override
    public DrawFigure execute(Point startPoint, Point endPoint, GraphicsContext gc, Color fillColor, Color strokeColor) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawCircle(startPoint, circleRadius, gc, fillColor, strokeColor);
    }
}
