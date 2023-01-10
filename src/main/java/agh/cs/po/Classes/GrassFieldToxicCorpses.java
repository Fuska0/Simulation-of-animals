package agh.cs.po.Classes;

public class GrassFieldToxicCorpses extends AbstractWorldMap {

    Parameters parameters;
    int which;
    int mapWidth;
    int mapHeight;
    public GrassFieldToxicCorpses(Parameters parameters) {
        super(parameters,0);
        this.mapHeight = parameters.mapHeight;
        this.mapWidth = parameters.mapWidth;
        deathsAmountArray = new int[mapWidth][mapHeight];
        initDeathsArray(parameters);
        generatePlants1(parameters.startingPlantCount);
    }


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