package agh.cs.po.Classes;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GrassfieldForestedEquatoria extends AbstractWorldMap{ // zmienic nazwa na PlantsFieldForestedEquatoria
    Parameters parameters = new Parameters();
    protected HashMap<Vector2d,Plants> plantsHashMap = new HashMap<>();
    private int n = parameters.plantsGrowingNumber;
    private int a = parameters.mapHeight;
    private int b = parameters.mapWidth;

    public GrassfieldForestedEquatoria(int n){
        generatePlants(n);
    }
    private void generatePlants(int n) {
        for(int i = 0; i< this.n; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, a);
            int y = ThreadLocalRandom.current().nextInt((int) Math.floor(b/3), (int) Math.floor(2*b/3));
            if (isOccupied(new Vector2d(x, y))) {
                i--;
            }
            else {
                plantsHashMap.put(new Vector2d(x, y),new Plants(new Vector2d(x, y)));
            }
        }
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
