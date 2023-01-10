package agh.cs.po.Classes;

import agh.cs.po.gui.App;

import java.util.Random;


public class SimulationEngine extends AbstractWorldMap implements Runnable{

    public AbstractWorldMap map;
    public App app;
    public int moveDelay;
    public WriteToCSV writeToCSV = new WriteToCSV();
    public boolean stopped = false;

    public SimulationEngine(AbstractWorldMap map, App app, Parameters parameters){
        this.map = map;
        this.app = app;
        this.parameters= parameters;
        Random r = new Random();
        for(int i = 0 ; i < parameters.startingAnimalsCount; i++) {
            Genes genes = new Genes(parameters);
            Vector2d position = new Vector2d(r.nextInt(parameters.mapWidth),r.nextInt(parameters.mapHeight));
            Animal animal = new Animal(position,
                    r.nextInt(8),parameters.animalStartEnergy, r.nextInt(parameters.genomSize),
                    genes, map , parameters);
            map.place(animal);
        }
    }

    public void evolution() {
        this.map.moveAnimals();
        map.sortAnimalslists();
        map.eatGrass();
        map.reproductingAnimals();
        map.addNewPlants();
        map.cleanUpDeadAnimal();
        map.countFreeSpaces();
        writeToCSV.save(map.getStatistic());
    }

    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }

    public void pause(){
        stopped = !stopped;
    }

    public void run()  {
        while(true)
        {
            app.refreshMap();
            try{
                while(stopped){System.out.print("");}
                evolution();
                Thread.sleep(moveDelay);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}