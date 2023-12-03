package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawCircle extends Circle implements DrawFigure{

    private GraphicsContext gc;
    public DrawCircle(Point centerPoint, double radius, GraphicsContext gc) {
        super(centerPoint, radius);
    }

    @Override
    public void draw() {

    }
}
