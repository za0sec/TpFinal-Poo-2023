package frontend.drawFigures;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.ArrayList;
import java.util.List;

public abstract class DrawFigure implements Figure {

    protected final GraphicsContext gc;

    protected boolean isShadow, isGradient, isBelveled = false;

    protected Color fill;

    public DrawFigure(GraphicsContext gc, Color fill, Color stroke){
        this.gc = gc;
        this.fill = fill;
    }

    public void draw(){
        setShadow(isShadow);
        setGradient(isGradient, fill);
        gc.setLineDashes(null);
        gc.setFill(fill);
    };

    public abstract Figure getFigure();

    public abstract void move(double diffX, double diffY);

    public abstract boolean belongs(Point eventPoint);

    public abstract void updatePreview(Point eventPoint);

    public abstract boolean intersects(Rectangle other);

    public void setShadow(boolean value){
        isShadow = value;
    }

    public ArrayList<Boolean> getStatus(){
        return new ArrayList<>(List.of(isShadow, isGradient, isBelveled));
    }

    public void setStatus(boolean isShadow, boolean isGradient, boolean isBelveled){
        this.isShadow =  isShadow;
        this.isGradient = isGradient;
        this.isBelveled = isBelveled;
    }

    protected void rectangleShadow(Rectangle rectangle){
        Rectangle shadow = new Rectangle(rectangle.getTopLeft(), rectangle.getBottomRight());
        gc.setFill(Color.GRAY);
        gc.fillRect(shadow.getTopLeft().getX() + 10.0,
                shadow.getTopLeft().getY() + 10.0,
                Math.abs(shadow.getTopLeft().getX() - shadow.getBottomRight().getX()),
                Math.abs(shadow.getTopLeft().getY() - shadow.getBottomRight().getY()));
    }

    public void setGradient(boolean value, Color fillColor){
        isGradient = value;

    }

}
