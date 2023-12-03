package frontend.buttons.drawButtons;

import backend.model.Point;
import frontend.drawFigures.DrawFigure;
import frontend.drawFigures.DrawRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRectangleButton implements Buttons{
    @Override
    public DrawFigure execute(Point startPoint, Point endPoint, GraphicsContext gc, Color fillColor, Color strokeColor) {
        return new DrawRectangle(startPoint, endPoint, gc, fillColor, strokeColor);
    }
}
