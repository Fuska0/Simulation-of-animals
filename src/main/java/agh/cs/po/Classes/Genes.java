package agh.cs.po.Classes;
import java.util.Random;

public class Genes {

    private int[] genes;
    private int n;

    public Genes(int n) {
        genes = new int[n];
        this.n = n;
        Random r = new Random();
        for (int i = 0; i < n; i++){
            genes[i] = r.nextInt(8);
        }
    }

    public int[] getArrayGenes(){
        return genes;
    }

    public int[] genesSplicing(Animal father, Animal mother){
        Random r = new Random();
        int whichSide = r.nextInt(2);
        int sum = father.getEnergy() + mother.getEnergy();
        double proportion = father.getEnergy() / sum;
        int distribution;
        if (whichSide == 0) {distribution = (int) (n * proportion);}
        else {distribution =  (int) (n * (1 - proportion));}
        int[] genes = new int[n];
        for (int i = 0; i < distribution; i++){
            genes[i] = father.getGenes().getArrayGenes()[i];
        }
        for (int i = distribution; i < n ; i++){
            genes[i] = mother.getGenes().getArrayGenes()[i];
        }
        mutations(genes);
        return genes;
    }

    public void mutations(int [] genes){
        Random r = new Random();
        int size = r.nextInt(8);
        int[] selected = new int[n];
        int counter = 0;
        while (counter < size) {
            int x = r.nextInt(8);
            if (selected[x] != 1) {
                selected[x] = 1;
                genes[x] = r.nextInt(8);
                counter ++;
            }
        }
    }



}
