package frontend.drawFigures;

import java.util.Set;

@FunctionalInterface
public interface GroupAction {
    void apply(Set<DrawFigure> figures);
}
