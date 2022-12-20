package agh.cs.po.Interfaces;
import agh.cs.po.Classes.*;

public interface IMapElement {

    Vector2d getPosition();

    String toString();

    boolean isAt(Vector2d position);

    void addObserver();
//
    void removeObserver();
}

