package frontend.buttons.drawButtons;

import backend.model.Point;
import frontend.drawFigures.DrawFigure;
import frontend.drawFigures.DrawRectangle;
import frontend.drawFigures.DrawSelectionRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawMultiSelectionButton implements Buttons{
    @Override
    public DrawFigure execute(Point startPoint, Point endPoint, GraphicsContext gc, Color fillColor, Color strokeColor) {
        return new DrawSelectionRectangle(startPoint, endPoint, gc, Color.TRANSPARENT, strokeColor);
    }

    @Override
    public boolean isDrawable() {
        return false;
    }


}
