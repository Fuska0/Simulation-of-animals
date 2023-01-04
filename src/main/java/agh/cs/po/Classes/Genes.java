package agh.cs.po.Classes;
import java.util.Random;

public class Genes {


    Parameters parameters;
    private int[] genes;
    private int n;

    public Genes(Parameters parameters) {
        this.parameters = parameters;
        this.n = parameters.genomSize;
        genes = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++){
            genes[i] = r.nextInt(8);
        }

    }


    public int[] getArrayGenes(){
        return genes;
    }

    public Genes genesSplicing(Animal father, Animal mother){
        Random r = new Random();
        int whichSide = r.nextInt(2);
        int sum = father.getEnergy() + mother.getEnergy();
        double proportion = father.getEnergy() / sum;
        int distribution;
        if (whichSide == 0) {distribution = (int) (n * proportion);}
        else {distribution =  (int) (n * (1 - proportion));}
        Genes genes = new Genes(parameters);
        for (int i = 0; i < distribution; i++){
            genes.getArrayGenes()[i] = father.getGenes().getArrayGenes()[i];
        }
        for (int i = distribution; i < n ; i++){
            genes.getArrayGenes()[i] = mother.getGenes().getArrayGenes()[i];
        }
        mutations(genes.getArrayGenes());
        return genes;
    }

    public void mutations(int [] genes){
        Random r = new Random();
        int size = r.nextInt(parameters.minMutationsNumber, parameters.maxMutationsNumber+1);
        int[] selected = new int[n];
        int counter = 0;
        while (counter < size) {
            int x = r.nextInt(parameters.genomSize);
            if (selected[x] != 1) {
                selected[x] = 1;
                genes[x] = r.nextInt(8);
                counter ++;
            }
        }
    }



}