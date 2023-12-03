package frontend.drawFigures;

import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawFigure{

    private final Square square;

    public DrawSquare(Point topLeft, double size, GraphicsContext gc, Color fill, Color stroke) {
        super(gc, fill, stroke);
        this.square = new Square(topLeft, size);
    }

    @Override
    public void draw() {
        gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
        gc.strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
    }
}
