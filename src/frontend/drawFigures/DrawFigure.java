package frontend.drawFigures;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class DrawFigure implements Figure {

    protected final GraphicsContext gc;
    public DrawFigure(GraphicsContext gc, Color fill, Color stroke){
        this.gc = gc;
        gc.setFill(fill);
        gc.setStroke(stroke);
    }

    public abstract void draw();

    public abstract Figure getFigure();

    public abstract void move(double diffX, double diffY);

    public abstract boolean belongs(Point eventPoint);

    public abstract void updatePreview(Point eventPoint);

    public abstract boolean intersects(Rectangle other);


}
