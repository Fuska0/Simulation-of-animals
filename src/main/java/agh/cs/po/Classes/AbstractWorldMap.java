package agh.cs.po.Classes;
import agh.cs.po.Interfaces.IWorldMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
public abstract class AbstractWorldMap implements IWorldMap {

    Parameters parameters = new Parameters();
    protected HashMap<Vector2d,Plants> plantsHashMap = new HashMap<>();
    protected  HashMap<Vector2d, ArrayList<Animal>> animalsHashMap = new HashMap<>(); // to tymczasowe zmien ten static !!!
    protected ArrayList[][] deathsAmountArray = new ArrayList[parameters.mapHeight][parameters.mapWidth];
    protected  ArrayList<Vector2d> freePlacesOnTheGroove = new ArrayList<Vector2d>();
    protected  ArrayList<Vector2d> otherFreePlaces = new ArrayList<Vector2d>();

    @Override
    public void place(Animal animal) {
       if (animalsHashMap.get(animal.getPosition()) == null) {
           animalsHashMap.put(animal.getPosition(),new ArrayList<>());
       }
        animalsHashMap.get(animal.getPosition()).add(animal);
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (animalsHashMap.get(position) != null && animalsHashMap.get(position).size() > 0) {
        return animalsHashMap.get(position).get(0);
        }
        return null;
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public void positionChanged(Vector2d newPosition, Animal animal) {
        if(animalsHashMap.get(newPosition) == null) {animalsHashMap.put(newPosition,
                new ArrayList<Animal>());}
        Vector2d oldPosition = animal.getPosition();
        animal.move();
        animalsHashMap.get(oldPosition).remove(animal);
        animalsHashMap.get(newPosition).add(animal);
    }

    public void animalsSort(ArrayList<Animal> animalList){
        animalList.sort(Comparator.comparing(Animal::getEnergy)
                .thenComparing(Animal::getAliveDays).thenComparing(Animal::getKidsNumber));
    }
    public void reproduction(ArrayList<Animal> animalList,int minEnergy){
        if (animalList.size() > 1){
            Random r = new Random();
            for (int i = 0, j = 1; i < animalList.size() && j < animalList.size(); i+=2 , j+=2 ) {
                Animal animal1 = animalList.get(i);
                Animal animal2 = animalList.get(j);
                if (Math.min(animal2.getEnergy(), animal1.getEnergy()) >= parameters.readyToBreed){

                    Genes genes = new Genes();
                    Animal animal3 = new Animal(animal1.getPosition(), r.nextInt(8),
                            parameters.energyYield*2, r.nextInt(parameters.genomSize+1),
                            genes.genesSplicing(animal1,animal2), animal1.map );

                    animal1.takeEnergy(parameters.energyYield);
                    animal2.takeEnergy(parameters.energyYield);
                }
            }
        }
    }

    public void generatePlants(int numberOf) {
        for(int i = 0; i< numberOf; i++) {
            Random r = new Random();
            int propability = r.nextInt(10);
            if (freePlacesOnTheGroove.size() > 0 && propability < 8){
                int tmp = r .nextInt(0,freePlacesOnTheGroove.size());
                Vector2d position = freePlacesOnTheGroove.get(tmp);
                addGrass(position);
                freePlacesOnTheGroove.remove(position);

            } else if (otherFreePlaces.size() > 0) {
                int tmp = r .nextInt(0,otherFreePlaces.size());
                Vector2d position = otherFreePlaces.get(tmp);
                addGrass(position);
                otherFreePlaces.remove(position);
            }
        }
    }

    public void addGrass(Vector2d position){
        plantsHashMap.put(position, new Plants(position));
    }

    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(new Vector2d(0,0), new Vector2d(parameters.mapWidth, parameters.mapHeight));
    }

    public void moveAnimals(){
        ArrayList<Vector2d> positionsList = new ArrayList<Vector2d>();
        for (Vector2d position : animalsHashMap.keySet()) {
            positionsList.add(position);}
        for (Vector2d position : positionsList){
            if(animalsHashMap.get(position) != null) {
                for(int i = 0; i < animalsHashMap.get(position).size(); i++ ) {
                    Animal animal = animalsHashMap.get(position).get(i);
                    Vector2d newPosition = animal.getPosition().
                            addWithModulo(animal.unitToVector(animal.getGenes().getArrayGenes()[animal.getCurrentGen()]));
                    positionChanged(newPosition, animal);
                }
            }
        }
    }

    public void sortAnimalslists(){
        for (Vector2d position : animalsHashMap.keySet()) {
            if(animalsHashMap.get(position) != null) {
                animalsSort(animalsHashMap.get(position));
            }
        }
    }

    public void reproductingAnimals(){
        for (Vector2d position : animalsHashMap.keySet()) {
            if(animalsHashMap.get(position) != null) {
                if(animalsHashMap.get(position).size() >1){
                    reproduction(animalsHashMap.get(position), parameters.readyToBreed);
                }
            }
        }
    }

    public void cleanUpDeadAnimal(){
        ArrayList<Vector2d> positionsList = new ArrayList<Vector2d>();
        for (Vector2d position : animalsHashMap.keySet()) {
            positionsList.add(position);}
        for (Vector2d position : positionsList) {
            if (animalsHashMap.get(position) != null) {
                for (int i = 0 ; i < animalsHashMap.get(position).size(); i++) {
                    Animal animal = animalsHashMap.get(position).get(i);
                    animal.addEnergy(-1);
                    if (animal.getEnergy() <= 0) {
                        animal.sorryYourDead();
                        animalsHashMap.get(position).remove(animal);
                    }
                }
            }
        }

    }

    public void eatGrass() {
        for (Vector2d position : animalsHashMap.keySet()) {
            if(animalsHashMap.get(position) != null && plantsHashMap.get(position) != null) {
                if(animalsHashMap.get(position).size() > 0) {
                    plantsHashMap.remove(position);
                    animalsHashMap.get(position).get(0).addEnergy(parameters.plantEnergy);
                    if (position.getY() >= (int) parameters.mapHeight/3 + 1 && position.getY() < (int) 2*parameters.mapHeight/3 + 1){
                    freePlacesOnTheGroove.add(position);}
                    else {otherFreePlaces.add(position);}
                }
            }
        }
    }
     public void addNewPlants(){

        generatePlants(parameters.plantsGrowingNumber);

     }



}


