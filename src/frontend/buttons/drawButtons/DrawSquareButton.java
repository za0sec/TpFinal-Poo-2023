package frontend.buttons.drawButtons;


import backend.model.Point;
import frontend.drawFigures.DrawFigure;
import frontend.drawFigures.DrawSquare;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquareButton implements Buttons {
    @Override
    public DrawFigure execute(Point startPoint, Point endPoint, GraphicsContext gc, Color fillColor, Color strokeColor) {
        double size = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawSquare(startPoint, size, gc, fillColor, strokeColor);
    }
}
