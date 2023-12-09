package frontend;

import frontend.drawFigures.DrawFigure;

@FunctionalInterface
public interface FigureAction {
    void apply(DrawFigure figure);
}
