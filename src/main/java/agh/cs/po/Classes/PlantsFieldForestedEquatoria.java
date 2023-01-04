package agh.cs.po.Classes;

public class PlantsFieldForestedEquatoria extends AbstractWorldMap {
    Parameters parameters;

    public PlantsFieldForestedEquatoria(Parameters parameters) {
        super(parameters,1);
        deathsAmountArray = new int[parameters.mapWidth][parameters.mapHeight];
        initDeathsArray(parameters);
        generatePlants(parameters.startingPlantCount);
    }

    public PlantsFieldForestedEquatoria() {
        deathsAmountArray = new int[parameters.mapWidth][parameters.mapHeight];
        initDeathsArray(parameters);
        generatePlants(parameters.startingPlantCount);
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object objectTmp = super.objectAt(position);
        if (objectTmp != null) {
            return objectTmp;
        }
        if (plantsHashMap.get(position) != null) {
            return plantsHashMap.get(position);
        }
        return null;
    }
}