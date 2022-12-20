package agh.cs.po.Classes;
import agh.cs.po.Interfaces.*;
public class Animal {

    public IWorldMap map;
    private Vector2d position;
    private int orientation;
    private int energy;
    private Genes genes;
    private int currentGen;
    private int eatenPlants;
    private int kidsNumber;
    private int aliveDays;
    private int deathDay;

    public Animal(Vector2d position, int orientation, int energy, int currentGen, Genes genesn, IWorldMap map) {
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

    public Vector2d unitToVector(int direction) throws IllegalStateException{
        return(switch(direction) {
            case 0 -> new Vector2d(0,1);
            case 1 -> new Vector2d(1,1);
            case 2 -> new Vector2d(1,0);
            case 3 -> new Vector2d(1,-1);
            case 4 -> new Vector2d(0,-1);
            case 5 -> new Vector2d(-1,-1);
            case 6 -> new Vector2d(-1,0);
            case 7 -> new Vector2d(-1,1);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        });
    }

    public void move(int mapsize){
        int how = genes.getArrayGenes()[currentGen];
        Vector2d vector = unitToVector(how);
        this.position = this.position.addWithMod(vector,mapsize);
    }



}
