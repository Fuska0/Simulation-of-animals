package agh.cs.po.Classes;

public class Parameters {

    public Parameters(int mapHeight, int mapWidth, int startingPlantCount, int plantEnergy,
                      int plantsGrowingNumber, int startingAnimalsCount, int animalStartEnergy, int readyToBreed, int energyYield,
                      int minMutationsNumber, int maxMutationsNumber, int genomSize)
    {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this. startingAnimalsCount = startingAnimalsCount;
        this. startingPlantCount = startingPlantCount;
        this.plantEnergy = plantEnergy;
        this.minMutationsNumber = minMutationsNumber;
        this.maxMutationsNumber = maxMutationsNumber;
        this.plantsGrowingNumber = plantsGrowingNumber;
        this.animalStartEnergy = animalStartEnergy;
        this.readyToBreed = readyToBreed;
        this.energyYield = energyYield;
        this.genomSize = genomSize;
    }

    public int mapHeight;
    public int mapWidth;
    int startingPlantCount;
    int plantEnergy ;
    int plantsGrowingNumber ;
    int startingAnimalsCount ;
    int animalStartEnergy ;
    int readyToBreed ;
    int energyYield ;
    int minMutationsNumber ;
    int maxMutationsNumber ;
    int genomSize ;

}