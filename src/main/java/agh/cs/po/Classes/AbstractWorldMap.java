package agh.cs.po.Classes;
import agh.cs.po.Interfaces.IWorldMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
public abstract class AbstractWorldMap implements IWorldMap {

    Parameters parameters = new Parameters();

    protected static HashMap<Vector2d, ArrayList<Animal>> animalsHashMap = new HashMap<>(); // to tymczasowe zmien ten static !!!
    protected HashMap<Vector2d, Plants> plantsHashMap = new HashMap<>();

    public static String toString(Vector2d position){
        return String.valueOf(animalsHashMap.get(position).size()); //tutaj ten static tez jest tylko na chwilke
    }
//
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

    public void animalsSort(ArrayList<Animal> animalList){
        animalList.sort(Comparator.comparing(Animal::getEnergy)
                .thenComparing(Animal::getAliveDays).thenComparing(Animal::getKidsNumber));
    }
    public void reproduction(ArrayList<Animal> animalList, IWorldMap map){
        if (animalList.size() > 1){
            animalsSort(animalList);
            Random r = new Random();
            for (int i = 0, j = 1; i < animalList.size() && j < animalList.size(); i+=2 , j+=2 ) {
                Animal animal1 = animalList.get(i);
                Animal animal2 = animalList.get(i);
                if (Math.min(animal2.getEnergy(), animal1.getEnergy()) >= parameters.readyToBreed){
                    Genes newGenes = new Genes();
                    Animal animal3 = new Animal(animal1.getPosition(), r.nextInt(8),
                            parameters.energyYield*2, r.nextInt(parameters.genomSize+1),
                            newGenes.genesSplicing(animal1,animal2), animal1.map );
                    map.place(animal3);
                    animal1.takeEnergy();
                    animal2.takeEnergy();
                }
            }
        }
    }
}
