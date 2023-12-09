package frontend;

import frontend.drawFigures.DrawFigure;

import java.util.Set;

@FunctionalInterface
public interface GroupAction {
    void apply(Set<DrawFigure> figures);
}
