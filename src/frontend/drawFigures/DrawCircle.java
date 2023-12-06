package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawEllipse {

    public DrawCircle(Point centerPoint, double radius, GraphicsContext gc, Color fill, Color stroke) {
        super(centerPoint, centerPoint, radius, radius, gc, fill, stroke);
    }

    @Override
    public void draw() {
        super.draw(); //
    }

    @Override
    public Circle getFigure() {
        return (Circle) this.ellipse;
    }

    @Override
    public void updatePreview(Point eventPoint) {
        double radius = Math.abs(eventPoint.getX() - this.ellipse.getCenterPoint().getX());
        ((Circle) this.ellipse).setRadius(radius);
    }


}
