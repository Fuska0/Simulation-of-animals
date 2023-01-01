package agh.cs.po.Classes;

import agh.cs.po.gui.App;
import javafx.application.Application;

public class Main {


    public static void main(String[] args) {

        AbstractWorldMap map = new PlantsFieldForestedEquatoria();
        //SimulationEngine engine = new SimulationEngine( map);
        //System.out.println(map.toString());
        Application.launch(App.class, args);

    }



}