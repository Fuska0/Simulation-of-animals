package agh.cs.po.Classes;
import agh.cs.po.Interfaces.IWorldMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
public abstract class AbstractWorldMap implements IWorldMap {

    Parameters parameters = new Parameters();
    protected HashMap<Vector2d,Plants> plantsHashMap = new HashMap<>();
    protected  HashMap<Vector2d, ArrayList<Animal>> animalsHashMap = new HashMap<>();
    protected int[][] deathsAmountArray=new int[parameters.mapWidth][parameters.mapHeight];
    protected  ArrayList<Vector2d> freePlacesOnTheGroove = new ArrayList<Vector2d>();
    protected  ArrayList<Vector2d> otherFreePlaces = new ArrayList<Vector2d>();
    protected int animalCount = 0, plantCount = 0,freeSpaces =0 ,avgEnergy=0,avdLivingDays=0;

    @Override
    public void place(Animal animal) {
        if (animalsHashMap.get(animal.getPosition()) == null) {
            animalsHashMap.put(animal.getPosition(),new ArrayList<>());
        }
        animalsHashMap.get(animal.getPosition()).add(animal);
        animalCount++;
    }

    public String getStatistic(){
        StringBuilder napis = new StringBuilder();
        napis.append("Animal count: ").append(Integer.toString(animalCount)).append("\r\n");
        napis.append("Plants count: ").append(Integer.toString(plantCount)).append("\r\n");
        napis.append("Free Spaces: ").append(Integer.toString(freeSpaces)).append("\r\n");
        napis.append("Average energy: ").append(Integer.toString(avgEnergy)).append("\r\n");
        napis.append("Average living days: ").append(Integer.toString(avdLivingDays)).append("\r\n");
        return String.valueOf(napis);}

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
            ArrayList<Animal> newAnimal = new ArrayList<>();
            for (int i = 0, j = 1; i < animalList.size() && j < animalList.size(); i+=2 , j+=2 ) {
                Animal animal1 = animalList.get(i);
                Animal animal2 = animalList.get(j);
                if (Math.min(animal2.getEnergy(), animal1.getEnergy()) >= parameters.readyToBreed){

                    Genes genes = new Genes();
                    Animal animal3 = new Animal(animal1.getPosition(), r.nextInt(8),
                            parameters.energyYield*2, r.nextInt(parameters.genomSize),
                            genes.genesSplicing(animal1,animal2), animal1.map );

                    animal1.takeEnergy(parameters.energyYield);
                    animal2.takeEnergy(parameters.energyYield);
                    newAnimal.add(animal3);
                }
            }
            for(Animal animal : newAnimal){
                place(animal);
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
        plantCount ++;
    }


    public void moveAnimals(){
        ArrayList<Vector2d> positionsList = new ArrayList<Vector2d>();
        for (Vector2d position : animalsHashMap.keySet()) {
            if(animalsHashMap.get(position) != null && animalsHashMap.get(position).size() >0){
            positionsList.add(position);}}
        for (Vector2d position : positionsList){
            if(animalsHashMap.get(position) != null && animalsHashMap.get(position).size()>0) {
                for(int i = 0; i < animalsHashMap.get(position).size(); i++ ) {
                    Animal animal = animalsHashMap.get(position).get(i);
                    Vector2d how = animal.unitToVector();
                    Vector2d newPosition = animal.getPosition().addWithModulo(how);
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
        int tmpAnimalsCount = 0, tmpOccupiedSpaces= 0, tmpEnergySum=0, tmpAliveDaysSum = 0;
        for (Vector2d position : animalsHashMap.keySet()) {
            positionsList.add(position);
        }
        for (Vector2d position : positionsList) {
            if (animalsHashMap.get(position) != null) {
                tmpOccupiedSpaces++;
                for (int i = 0 ; i < animalsHashMap.get(position).size(); i++) {
                    Animal animal = animalsHashMap.get(position).get(i);
                    animal.addEnergy(-1);
                    if (animal.getEnergy() <= 0) {
                        animal.sorryYourDead();
                        animalsHashMap.get(position).remove(animal);
                        deathsAmountArray[position.x][position.y]+=1;
                    }
                    else {
                        animal.oneMoreAliveDay();
                        tmpEnergySum+=animal.getEnergy();
                        tmpAnimalsCount += 1;
                        tmpAliveDaysSum += animal.getAliveDays();}
                }
            }
        }
        for (int i = 0 ; i < parameters.mapWidth; i++){
            for (int j = 0 ; j < parameters.mapHeight; j++){
                Vector2d pos = new Vector2d(i,j);
                if (animalsHashMap.get(pos)!=null || plantsHashMap.get(pos)!=null){
                    tmpOccupiedSpaces++;
                }
            }
        }
        freeSpaces = parameters.mapHeight * parameters.mapWidth - tmpOccupiedSpaces;
        animalCount = tmpAnimalsCount;
        if (tmpAnimalsCount > 0) {avgEnergy = (int) tmpEnergySum / tmpAnimalsCount;
            avdLivingDays = tmpAliveDaysSum / tmpAnimalsCount;}
        else {avgEnergy = 0;
            avdLivingDays = 0;}
    }

    public void eatGrass() {
        for (Vector2d position : animalsHashMap.keySet()) {
            if(animalsHashMap.get(position) != null && plantsHashMap.get(position) != null) {
                if(animalsHashMap.get(position).size() > 0) {
                    plantsHashMap.remove(position);
                    animalsHashMap.get(position).get(0).addEnergy(parameters.plantEnergy);
                    plantCount -= 1;
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
