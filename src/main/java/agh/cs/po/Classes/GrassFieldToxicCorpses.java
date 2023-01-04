package agh.cs.po.Classes;

import java.util.HashMap;

public class GrassFieldToxicCorpses extends AbstractWorldMap{
    Parameters parameters;

    public GrassFieldToxicCorpses(Parameters parameters){
        this.parameters = parameters;
    }

    public void GrassfieldForestedEquatoria(int n){
        generatePlants(n);
    }
    protected HashMap<Vector2d,Plants> plantsHashMap = new HashMap<>();
    private int n = parameters.plantsGrowingNumber;
    private int a = parameters.mapHeight;
    private int b = parameters.mapWidth;


    @Override
    public boolean isOccupied(Vector2d position) {
        return plantsHashMap.containsKey(position);
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (super.objectAt(position) == null) {
            return plantsHashMap.get(position);
        }
        return super.objectAt(position);
    }
}