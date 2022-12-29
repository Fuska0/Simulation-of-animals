package agh.cs.po.Classes;

public class PlantsFieldForestedEquatoria extends AbstractWorldMap{ // zmienic nazwa na PlantsFieldForestedEquatoria
    Parameters parameters = new Parameters();

    private int b = parameters.mapHeight;
    private int a = parameters.mapWidth;

    public PlantsFieldForestedEquatoria() {
        addFreePlaces();
        generatePlants(parameters.startingPlantCount);
    }

    public void addFreePlaces(){
        for(int i = 0; i < a+1; i++){
            for(int j = 0; j <= b ; j++){
                if (j >= (int) b/3 + 1 && j < (int) 2*b/3 + 1){
                freePlacesOnTheGroove.add(new Vector2d(i,j));}
                else {otherFreePlaces.add(new Vector2d(i,j));}
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
