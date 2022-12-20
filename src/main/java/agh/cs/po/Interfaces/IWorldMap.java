package agh.cs.po.Interfaces;

import agh.cs.po.Classes.*;

public interface IWorldMap {

    void place(Animal animal);

    Object objectAt(Vector2d position);
//
    boolean isOccupied(Vector2d position);
}
