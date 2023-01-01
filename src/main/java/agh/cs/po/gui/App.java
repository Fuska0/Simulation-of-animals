package agh.cs.po.gui;

import agh.cs.po.Classes.AbstractWorldMap;
import agh.cs.po.Classes.SimulationEngine;
import agh.cs.po.Classes.PlantsFieldForestedEquatoria;
import agh.cs.po.Classes.Vector2d;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


import java.awt.*;

import static java.lang.Thread.sleep;

public class App extends Application {


    private agh.cs.po.Classes.Parameters par= new agh.cs.po.Classes.Parameters();

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        AbstractWorldMap map = new PlantsFieldForestedEquatoria();
        map.createLife(map);
        for(int c=0; c<=3;c++){
            SimulationEngine.evolve(map);
            System.out.println(map.toString());
            int height = par.mapHeight;
            int width = par.mapWidth;
            GridPane grid = new GridPane();
            grid.setGridLinesVisible(true);
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            Label labelxy = new Label("y/x");
            grid.getRowConstraints().add(new RowConstraints(height));
            GridPane.setHalignment(labelxy, HPos.CENTER);
            grid.add(labelxy, 0, 0);

            for (int i = 0; i < width; i++) {
                Label label = new Label(Integer.toString(i));
                grid.getColumnConstraints().add(new ColumnConstraints(width));
                GridPane.setHalignment(label, HPos.CENTER);
                grid.add(label, i+1, 0);
            }
            for (int i = 0; i < height; i++) {
                Label label = new Label(Integer.toString(height-i-1));
                grid.getRowConstraints().add(new RowConstraints(height));
                GridPane.setHalignment(label, HPos.CENTER);
                grid.add(label, 0, i+1);
            }
            for (int x = 1; x <= width; x++) {
                for (int y = 1; y <= width; y++) {
                    Vector2d position = new Vector2d(x, y);
                    Object object = map.objectAt(position);
                    if (object != null) {
                        Label label = new Label(object.toString());
                        grid.add(label, position.x, position.y);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                }
            }
            Scene scene = new Scene(grid, width, height);
            primaryStage.setScene(scene);
            primaryStage.show();
            Thread.sleep(300);
        }
    }
}
