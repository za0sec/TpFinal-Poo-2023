package frontend.drawFigures;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;

public abstract class DrawFigure implements Figure {

    protected final GraphicsContext gc;

    public DrawFigure(GraphicsContext gc){
        this.gc = gc;
    }

    public abstract void draw();
}
