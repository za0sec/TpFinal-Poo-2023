package backend.model;

public interface Figure {
    @Override
    int hashCode();

    @Override
    String toString();

    @Override
    boolean equals(Object obj);

}