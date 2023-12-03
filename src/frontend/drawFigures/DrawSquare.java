package frontend.drawFigures;

import backend.model.Point;
import backend.model.Square;

public class DrawSquare extends Square implements DrawFigure{
    public DrawSquare(Point topLeft, double size) {
        super(topLeft, size);
    }

    @Override
    public void draw() {

    }
}
