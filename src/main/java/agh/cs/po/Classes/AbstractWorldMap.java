package agh.cs.po.Classes;
import agh.cs.po.Interfaces.IWorldMap;

import java.util.ArrayList;
import java.util.HashMap;
public abstract class AbstractWorldMap implements IWorldMap {

    protected static HashMap<Vector2d, ArrayList<Animal>> animalsHashMap = new HashMap<>(); // to tymczasowe zmien ten static !!!
    protected HashMap<Vector2d, Plants> plantsHashMap = new HashMap<>();

    public static String toString(Vector2d position){
        return String.valueOf(animalsHashMap.get(position).size()); //tutaj ten static tez jest tylko na chwilke
    }

    @Override
    public void place(Animal animal) {
        animalsHashMap.get(animal.getPosition()).add(animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animalsHashMap.get(position);
    }

    public void positionChanged(Vector2d newPosition, Animal animal) {
        Vector2d oldPosition = animal.getPosition();
        ArrayList<Animal> animalList = animalsHashMap.get(oldPosition);
        animalList.remove(animal);
        animalsHashMap.get(newPosition).add(animal);
    }
}
