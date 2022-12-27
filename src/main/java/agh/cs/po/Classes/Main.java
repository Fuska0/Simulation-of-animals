package agh.cs.po.Classes;

public class Main {


    public static void main(String[] args) {

        AbstractWorldMap map = new PlantsFieldForestedEquatoria();
        SimulationEngine engine = new SimulationEngine( map);
        System.out.println(map.toString());

    }



}