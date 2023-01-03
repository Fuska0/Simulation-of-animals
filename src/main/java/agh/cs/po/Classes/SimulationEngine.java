package agh.cs.po.Classes;

import agh.cs.po.gui.App;

import java.util.Random;


public class SimulationEngine extends AbstractWorldMap implements Runnable{

    Parameters parameters= new Parameters();

    public AbstractWorldMap map;
    public App app;
    public int moveDelay;


    public SimulationEngine(AbstractWorldMap map, App app){
        this.map = map;
        this.app = app;
        Random r = new Random();
        for(int i = 0 ; i < parameters.startingAnimalsCount; i++) {
            Genes genes = new Genes();
            Vector2d position = new Vector2d(r.nextInt(parameters.mapWidth),r.nextInt(parameters.mapHeight));
            Animal animal = new Animal(position,
                    r.nextInt(8),parameters.animalStartEnergy, r.nextInt(parameters.genomSize),
                    genes, map );
            map.place(animal);
            System.out.println("Animal placed on map: " + animal);
        }



    }

    public void evolution() {
        this.map.moveAnimals();
        map.sortAnimalslists();
        map.eatGrass();
        map.reproductingAnimals();
        map.addNewPlants();
        map.cleanUpDeadAnimal();
    }

    public void setMoveDelay(int delay){
        this.moveDelay = delay;
    }
    public void run()  {
        for(int i = 0 ; i < 10 ; i++)
        {
            app.refreshMap();
            System.out.println(map);
            try{
                evolution();
                Thread.sleep(moveDelay);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}