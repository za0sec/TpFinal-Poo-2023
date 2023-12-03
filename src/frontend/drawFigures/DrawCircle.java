package frontend.drawFigures;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawCircle extends DrawFigure{

    Circle circle;

    public DrawCircle(Point centerPoint, double radius, GraphicsContext gc) {
        super(gc);
        this.circle = new Circle(centerPoint, radius);
    }

    @Override
    public void draw() {
        double diameter = circle.getRadius() * 2;
        gc.fillOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
        gc.strokeOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
    }
}
