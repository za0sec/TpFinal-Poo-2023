package frontend.buttons.drawButtons;

import backend.model.Point;
import frontend.drawFigures.DrawCircle;
import frontend.drawFigures.DrawFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircleButton extends DrawEllipseButton {
    @Override
    public DrawFigure execute(Point startPoint, Point endPoint, GraphicsContext gc, Color fillColor, Color strokeColor) {
        // Calcula el radio del círculo como la mitad de la distancia horizontal
        // entre startPoint y endPoint
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX()) / 2;

        // El centro del círculo está a 'radio' distancia del startPoint en la dirección x
        Point centerPoint = new Point(startPoint.getX() + circleRadius, startPoint.getY() + circleRadius);

        return new DrawCircle(centerPoint, circleRadius, gc, fillColor, strokeColor);
    }

}
