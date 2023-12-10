package backend;

import backend.model.Figure;

import java.util.*;

public class CanvasState<E extends Figure> {

    private final Map<E, Set<E>> figureGroups = new HashMap<>();

    private final List<E> list = new ArrayList<>();

    public void addFigure(E figure) {
        list.add(figure);
        groupSetter(figure);
    }

    public void deleteFigure(E figure) {
        list.remove(figure);
        figureGroups.remove(figure);
    }

    public Iterable<E> figures() {
        return new ArrayList<E>(list);
    }

    public Set<E> figuresSet() {
        return new HashSet<>(list);
    }

    public Set<E> getFigures(E figure){
        return figureGroups.getOrDefault(figure, new HashSet<>());
    }
    public Set<E> getFigures(Set<E> figures){
        Set<E> toReturn = new HashSet<>();
        for(E fig : figures){
            toReturn.addAll(getFigures(fig));
        }
        return toReturn;
    }

    public void gather(Set<E> figures){
        Set<E> mySet = getFigures(figures);
        for(E fig : figures){
            figureGroups.put(fig, mySet);
        }
    }

    public void unGather(Set<E> figures){
        for (E figure : figures){
            figureGroups.remove(figure);
            groupSetter(figure);
        }
    }

    private void groupSetter(E figure){
        Set<E> mySet = new HashSet<E>();
        mySet.add(figure);
        figureGroups.put(figure, mySet);
    }

}