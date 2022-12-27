package agh.cs.po.Classes;

import java.util.concurrent.ThreadLocalRandom;

public class PlantsFieldForestedEquatoria extends AbstractWorldMap{ // zmienic nazwa na PlantsFieldForestedEquatoria
    Parameters parameters = new Parameters();

    private int a = parameters.mapHeight;
    private int b = parameters.mapWidth;

    public PlantsFieldForestedEquatoria() {
        generatePlants(parameters.startingPlantCount);
    }
    public void generatePlants(int numberOf) {
        for(int i = 0; i< numberOf; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, a);
            int y = ThreadLocalRandom.current().nextInt((int) Math.floor(b/3), (int) Math.floor(2*b/3));
            if (isOccupied(new Vector2d(x, y))) {
                i--;
            }
            else {
                addGrass(new Vector2d(x, y));
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {

        return objectAt(position) != null;
    }

    public void addGrass(Vector2d position){
        plantsHashMap.put(position, new Plants(position));
    }
    @Override
    public Object objectAt(Vector2d position) {
        Object objectTmp = super.objectAt(position);

        if(objectTmp != null) {return objectTmp;}

        if(plantsHashMap.get(position) != null) {return plantsHashMap.get(position);}

        return null;
    }


}
