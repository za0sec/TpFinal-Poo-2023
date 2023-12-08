package frontend;

import frontend.drawFigures.DrawFigure;

import java.util.Set;

@FunctionalInterface
public interface Redraw {
    void redraw(Set<DrawFigure> figures);
}


