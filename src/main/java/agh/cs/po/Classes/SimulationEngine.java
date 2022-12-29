package agh.cs.po.Classes;

import java.util.Random;


public class SimulationEngine extends AbstractWorldMap{

    Parameters parameters= new Parameters();

    public AbstractWorldMap map;


    public SimulationEngine(AbstractWorldMap map){
        Random r = new Random();
        for(int i = 0 ; i < parameters.startingAnimalsCount; i++) {
            Genes genes = new Genes();
            Vector2d position = new Vector2d(r.nextInt(parameters.mapWidth),r.nextInt(parameters.mapHeight));
            Animal animal = new Animal(position,
                    r.nextInt(8),parameters.animalStartEnergy, r.nextInt(parameters.genomSize),
                    genes, map );
            map.place(animal);
        }
        Genes genes = new Genes();
        map.place(new Animal( new Vector2d(15,15),r.nextInt(8),parameters.animalStartEnergy, r.nextInt(parameters.genomSize),
                genes, map));

        for(int i = 0; i < 1; i ++){
        map.moveAnimals();
        map.sortAnimalslists();
        map.eatGrass();
        map.reproductingAnimals();
            }



    }

}
