package agh.cs.po.Classes;

import java.util.Random;

public class PlantsFieldForestedEquatoria extends AbstractWorldMap{ // zmienic nazwa na PlantsFieldForestedEquatoria
    Parameters parameters = new Parameters();

    private int a = parameters.mapHeight;
    private int b = parameters.mapWidth;

    public PlantsFieldForestedEquatoria() {
        addFreePlaces();
        generatePlants(parameters.startingPlantCount);
    }

    public void addFreePlaces(){
        for(int i = 0; i < a+1; i++){
            for(int j = 0; j <= b ; j++){
                if (j >= (int) b/3 && j < (int) 2*b/3 + 1){
                freePlacesOnTheGroove.add(new Vector2d(i,j));}
                else {otherFreePlaces.add(new Vector2d(i,j));}
            }
        }
    }

    public void generatePlants(int numberOf) {
        for(int i = 0; i< numberOf; i++) {
            Random r = new Random();
            int propability = r.nextInt(10);
            if (propability < 8){
                if (freePlacesOnTheGroove.size() > 0 ){
                    int tmp = r .nextInt(0,freePlacesOnTheGroove.size());
                    Vector2d position = freePlacesOnTheGroove.get(tmp);
                    addGrass(position);
                    freePlacesOnTheGroove.remove(position);}

                else if (otherFreePlaces.size() > 0) {
                    int tmp = r .nextInt(0,otherFreePlaces.size());
                    Vector2d position = otherFreePlaces.get(tmp);
                    addGrass(position);
                    otherFreePlaces.remove(position); }
            }
            else {
                if (otherFreePlaces.size() > 0) {
                    int tmp = r .nextInt(0,otherFreePlaces.size());
                    Vector2d position = otherFreePlaces.get(tmp);
                    addGrass(position);
                    otherFreePlaces.remove(position); }

            }

        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {

        return objectAt(position) != null;
    }


    @Override
    public Object objectAt(Vector2d position) {
        Object objectTmp = super.objectAt(position);

        if(objectTmp != null) {return objectTmp;}

        if(plantsHashMap.get(position) != null) {return plantsHashMap.get(position);}

        return null;
    }


}
