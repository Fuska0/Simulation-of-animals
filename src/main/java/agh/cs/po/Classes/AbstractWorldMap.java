package agh.cs.po.Classes;
import agh.cs.po.Interfaces.IWorldMap;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap {
    Parameters parameters ;
    int which;
    protected HashMap<Vector2d,Plants> plantsHashMap = new HashMap<>();
    protected  HashMap<Vector2d, ArrayList<Animal>> animalsHashMap = new HashMap<>();
    protected int[][] deathsAmountArray;
    protected  ArrayList<Vector2d> plantsToAdd = new ArrayList<Vector2d>();
    protected int animalCount = 0, plantCount = 0,freeSpaces =0 ,avgEnergy=0,avdLivingDays=0, deathCount= 0,
    deathAimalLivingDays = 0, avgDeathAimalLivingDays = 0, day = 1;

    protected AbstractWorldMap(Parameters parameters, int which){
        this.parameters = parameters;
        this.which = which;
    }
    protected AbstractWorldMap() {}

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
        napis.append("Day number :").append(Integer.toString(day)).append("\r\n");
        napis.append("Animal count: ").append(Integer.toString(animalCount)).append("\r\n");
        napis.append("Plants count: ").append(Integer.toString(plantCount)).append("\r\n");
        napis.append("Free spaces: ").append(Integer.toString(freeSpaces)).append("\r\n");
        napis.append("Average energy: ").append(Integer.toString(avgEnergy)).append("\r\n");
        napis.append("Average living days: ").append(Integer.toString(avdLivingDays)).append("\r\n");
        napis.append("Death count: ").append(Integer.toString(deathCount)).append("\r\n");
        napis.append("Death animal living days :").append(Integer.toString(deathAimalLivingDays)).append("\r\n");
        napis.append("Average death animal living days :").append(Integer.toString(avgDeathAimalLivingDays)).append("\r\n");
        return String.valueOf(napis);}

    @Override
    public Object objectAt(Vector2d position) {
        if (animalsHashMap.get(position) != null && animalsHashMap.get(position).size() > 0) {
            return animalsHashMap.get(position).get(0);
        }
        return null;
    }

    public Parameters getParameters(){
        return parameters;
    }
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public void positionChanged(Vector2d newPosition, Animal animal) {
        animalsHashMap.computeIfAbsent(newPosition, k -> new ArrayList<Animal>());
        Vector2d oldPosition = animal.getPosition();
        if (oldPosition.getX() != newPosition.getX() || oldPosition.getY() != newPosition.getY())
        {animal.move();
            animalsHashMap.get(oldPosition).remove(animal);
            animalsHashMap.get(newPosition).add(animal);}
        else {animal.changeOrientatnion();}
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

                    Genes genes = new Genes(parameters);
                    Animal animal3 = new Animal(animal1.getPosition(), r.nextInt(8),
                            parameters.energyYield*2, r.nextInt(parameters.genomSize),
                            genes.genesSplicing(animal1,animal2), animal1.map ,parameters);

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
        int a = parameters.mapWidth;
        int b = parameters.mapHeight;
        for(int i = 0; i < a; i++){
            for(int j = 0; j < b ; j++){
                if (plantsHashMap.get(new Vector2d(i, j)) == null) {
                    if (j >= (int) b/3 + 1 && j < (int) 2*b/3 + 1) {
                        for (int k = 0; k < 3; k++) {
                            plantsToAdd.add(new Vector2d(i, j));
                        }
                    }
                    else{
                        plantsToAdd.add(new Vector2d(i, j));
                    }
                }
            }
        }
        Collections.shuffle(plantsToAdd);
        for(int i = 0; i< numberOf && i<plantsToAdd.size(); i++) {
            Vector2d position = plantsToAdd.get(i);
            addGrass(position);
            for (int j=0; j<plantsToAdd.size(); j++){
                if (plantsToAdd.get(j)==position){
                    plantsToAdd.remove(j);
                }
            }
        }
        plantsToAdd.clear();
    }

    public void generatePlants1(int numberOf) {
        int a = parameters.mapWidth;
        int b = parameters.mapHeight;
        for(int i = 0; i < a; i++){
            for(int j = 0; j < b ; j++){
                if (plantsHashMap.get(new Vector2d(i, j)) == null) {
                    for (int k=0; k<=deathsAmountArray[i][j];k++) {
                        plantsToAdd.add(new Vector2d(i, j));
                    }
                }
            }
        }
        Collections.shuffle(plantsToAdd);
        for(int i = 0; i< numberOf && i<plantsToAdd.size(); i++) {
            Vector2d position = plantsToAdd.get(i);
            addGrass(position);
            for (int j=0; j<plantsToAdd.size(); j++){
                if (plantsToAdd.get(j)==position){
                    plantsToAdd.remove(j);
                }
            }
        }
        plantsToAdd.clear();
    }

    public void addGrass(Vector2d position){
        plantsHashMap.put(position, new Plants(position, parameters));
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
                    Vector2d newPosition = animal.addWithModulo(how);
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
        int tmpAnimalsCount = 0, tmpEnergySum=0, tmpAliveDaysSum = 0;
        day++;
        for (Vector2d position : animalsHashMap.keySet()) {
            positionsList.add(position);
        }
        for (Vector2d position : positionsList) {
            if (animalsHashMap.get(position) != null) {
                for (int i = 0 ; i < animalsHashMap.get(position).size(); i++) {
                    Animal animal = animalsHashMap.get(position).get(i);
                    animal.addEnergy(-1);
                    if (animal.getEnergy() <= 0) {
                        animal.sorryYourDead();
                        deathCount++;
                        deathAimalLivingDays += animal.getAliveDays();
                        avgDeathAimalLivingDays = (int) (deathAimalLivingDays / deathCount);
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
        animalCount = tmpAnimalsCount;
        if (tmpAnimalsCount > 0) {avgEnergy = (int) tmpEnergySum / tmpAnimalsCount;
            avdLivingDays = tmpAliveDaysSum / tmpAnimalsCount;}
        else {avgEnergy = 0;
            avdLivingDays = 0;}
    }
    protected void initDeathsArray(Parameters parameters){
        for(int i = 0; i < parameters.mapWidth; i++) {
            for (int j = 0; j < parameters.mapHeight; j++) {
                deathsAmountArray[i][j] = 0;
            }
        }
    }

    public void eatGrass() {
        for (Vector2d position : animalsHashMap.keySet()) {
            if(animalsHashMap.get(position) != null && plantsHashMap.get(position) != null) {
                if(animalsHashMap.get(position).size() > 0) {
                    plantsHashMap.remove(position);
                    animalsHashMap.get(position).get(0).addEnergy(parameters.plantEnergy);
                    plantCount -= 1;
                }
            }
        }
    }
    public void addNewPlants(){
        if(which == 1){generatePlants(parameters.plantsGrowingNumber);}
        else {generatePlants1(parameters.plantsGrowingNumber);}
    }

    public void countFreeSpaces(){
        int tmp = 0;
        int tmp2 = 0;
        for(Vector2d position : animalsHashMap.keySet()){
            if (animalsHashMap.get(position).size() > 0 && plantsHashMap.get(position) == null){
                tmp++;
            }
        }
        for(Vector2d position : plantsHashMap.keySet()){
            if (plantsHashMap.get(position) != null){
                tmp++;
                tmp2++;
            }
        }
        freeSpaces = parameters.mapHeight*parameters.mapWidth-tmp;
        plantCount = tmp2;
    }
}