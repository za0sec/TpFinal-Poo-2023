package frontend.drawFigures;

import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class DrawRectangle extends Rectangle implements DrawFigure {

    private final GraphicsContext gc;


    public DrawRectangle(Point topLeft, Point bottomRight, GraphicsContext gc) {
        super(topLeft, bottomRight);
        this.gc = gc;
    }

    @Override
    public void draw() {
        gc.fillRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
        gc.strokeRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
    }
}
