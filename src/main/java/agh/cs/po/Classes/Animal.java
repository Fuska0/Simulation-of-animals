package agh.cs.po.Classes;

import java.util.Random;

public class Animal {

    public AbstractWorldMap map;
    private Vector2d position;
    private int orientation,energy,currentGen,eatenPlants,kidsNumber,aliveDays,deathDay;
    private Genes genes;

    Parameters parameters;

    public Animal(Vector2d position, int orientation, int energy, int currentGen, Genes genes, AbstractWorldMap map, Parameters parameters) {
        this.position = position;
        this.orientation = orientation;
        this.energy = energy;
        this.currentGen = currentGen;
        this.genes = genes;
        this.eatenPlants = 0;
        this.kidsNumber = 0;
        this.aliveDays = 0;
        this.deathDay = 0;
        this.map = map;
        this.parameters = parameters;
    }

    public Vector2d getPosition() {return position;}
    public int getOrientation() {return orientation;}
    public int getEnergy() {return energy;}
    public Genes getGenes() {return genes;}
    public int getCurrentGen() {return currentGen;}
    public int getEatenPlants() {return eatenPlants;}
    public int getKidsNumber() {return kidsNumber;}
    public int getAliveDays() {return aliveDays;}
    public int getDeathDay() {return deathDay;}

    public Vector2d unitToVector( ) throws IllegalStateException{
        return(switch((genes.getArrayGenes()[currentGen] + orientation)%8) {
            case 0 -> new Vector2d(0,1);
            case 1 -> new Vector2d(1,1);
            case 2 -> new Vector2d(1,0);
            case 3 -> new Vector2d(1,-1);
            case 4 -> new Vector2d(0,-1);
            case 5 -> new Vector2d(-1,-1);
            case 6 -> new Vector2d(-1,0);
            case 7 -> new Vector2d(-1,1);
            default -> throw new IllegalStateException("Unexpected value: " + currentGen);
        });
    }
    public Vector2d addWithModulo(Vector2d other){
        int newX =(position.x + other.x)%( parameters.mapWidth );
        if (newX < 0) {newX = parameters.mapWidth-1;}
        int newY =(position.y + other.y);
        if(newY > parameters.mapHeight-1){newY = parameters.mapHeight-1;}
        if (newY < 0) {newY = 0;}
        return new Vector2d(newX,newY);
    }
    public void move(){
        Vector2d vector = unitToVector();
        this.position = addWithModulo(vector);

        Random r = new Random();
        if (r.nextInt() < 2){
            currentGen = r.nextInt(parameters.genomSize);
        }
        else currentGen = (currentGen + 1) % (parameters.genomSize);
    }

    public void takeEnergy(int howMany){
        energy -= howMany;
    }
    public void addEnergy(int howMany) {energy += howMany;}

    public String toString(){
        return String.valueOf(map.animalsHashMap.get(position).size());
    }

    public void oneMoreAliveDay(){
        aliveDays += 1;
    }

    public void sorryYourDead(){
        deathDay = aliveDays;
    }

    public String label(){
        StringBuilder napis = new StringBuilder();
        String prefix = "";
        for(Animal animal : map.animalsHashMap.get(position)){
            napis.append(prefix);
            prefix = ",";
            napis.append(Integer.toString(animal.getEnergy()));


        }

        return String.valueOf(napis);
    }

    public void changeOrientatnion(){
        orientation = (orientation + 4)%8;
    }
}