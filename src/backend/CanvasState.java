package backend;

import backend.model.Figure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState<E extends Figure> {

    private final List<E> list = new ArrayList<>();

    public void addFigure(E figure) {
        list.add(figure);
    }

    public void deleteFigure(E figure) {
        list.remove(figure);
    }

    public Iterable<E> figures() {
        return new ArrayList<E>(list);
    }

}
