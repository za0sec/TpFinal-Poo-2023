package frontend.buttons.drawButtons;

import backend.model.Point;
import frontend.drawFigures.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;

public interface Buttons{

    DrawFigure execute(Point startPoint, Point endPoint, GraphicsContext gc, Color fillColor, Color strokeColor);

    boolean isDrawable();

}
