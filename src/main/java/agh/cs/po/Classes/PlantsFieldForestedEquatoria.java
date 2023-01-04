package agh.cs.po.Classes;

public class PlantsFieldForestedEquatoria extends AbstractWorldMap{ // zmienic nazwa na PlantsFieldForestedEquatoria
    Parameters parameters;

    public PlantsFieldForestedEquatoria(Parameters parameters){
        super(parameters);
        this.a = parameters.mapWidth;
        this.b = parameters.mapHeight;
        addFreePlaces();
        generatePlants(parameters.startingPlantCount);
    }

    private int b;
    private int a;

    public PlantsFieldForestedEquatoria() {
        addFreePlaces();
        generatePlants(parameters.startingPlantCount);
    }

    public void addFreePlaces(){
        for(int i = 0; i < a+1; i++){
            for(int j = 0; j <= b ; j++){
                if (plantsHashMap.get(new Vector2d(i, j)) == null) {
                    if (j >= (int) b/3 + 1 && j < (int) 2*b/3 + 1) {
                        freePlacesOnTheGroove.add(new Vector2d(i, j));
                    }
                    else{
                        otherFreePlaces.add(new Vector2d(i, j));
                    }
                }
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