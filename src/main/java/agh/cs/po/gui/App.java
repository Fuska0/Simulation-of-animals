package agh.cs.po.gui;

import agh.cs.po.Classes.AbstractWorldMap;
import agh.cs.po.Classes.PlantsFieldForestedEquatoria;
import agh.cs.po.Classes.SimulationEngine;
import agh.cs.po.Classes.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {


    private agh.cs.po.Classes.Parameters par = new agh.cs.po.Classes.Parameters();
    private AbstractWorldMap map;
    private SimulationEngine engine;
    private GridPane gridPane;
    private int WIDTH = par.mapWidth;
    private int HEIGHT = par.mapHeight;

    public void xyLabel(){
        Label label = new Label("y/x");
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(WIDTH));
        gridPane.getRowConstraints().add(new RowConstraints(HEIGHT));
        gridPane.add(label, 0, 0);
    }

    public void columnsFunction(){
        for (int i = 1; i <= WIDTH; i++){
            Label label = new Label(Integer.toString(i-1));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(WIDTH));
            gridPane.add(label, i, 0);

        }
    }

    public void rowsFunction(){
        for (int i = 1; i <= HEIGHT; i++) {
            Label label = new Label(Integer.toString(i-1));
            gridPane.getRowConstraints().add(new RowConstraints(HEIGHT));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, i );
        }
    }

    public void addElements(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Vector2d position = new Vector2d(x, y);
                Object object = map.objectAt(position);
                if (object != null) {
                    Label label = new Label(object.toString());
                    gridPane.add(label, position.x+1, position.y+1);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }


    public void prepareScene(){
        this.xyLabel();
        this.columnsFunction();
        this.rowsFunction();
        this.addElements();
        gridPane.setGridLinesVisible(true);
    }

    public void refreshMap(){
        Platform.runLater(() ->{
            gridPane.getChildren().clear();
            gridPane.setGridLinesVisible(false);
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            prepareScene();
        });
    }
    @Override
    public void init() throws Exception {

        map = new PlantsFieldForestedEquatoria();
        engine = new SimulationEngine(map, this);
        engine.setMoveDelay(300);
        gridPane = new GridPane();

    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        refreshMap();
        Scene scene = new Scene(gridPane, 1500, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        Thread thread = new Thread((Runnable) engine);
        thread.start();

    }



}