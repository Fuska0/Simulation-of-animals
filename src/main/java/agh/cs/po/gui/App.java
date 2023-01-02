package agh.cs.po.gui;

import agh.cs.po.Classes.AbstractWorldMap;
import agh.cs.po.Classes.PlantsFieldForestedEquatoria;
import agh.cs.po.Classes.SimulationEngine;
import agh.cs.po.Classes.Vector2d;
import javafx.application.Application;
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
    private int HEIGHT = par.mapHeight;;

    public void xyLabel(){
        Label label = new Label("y/x");
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(WIDTH));
        gridPane.getRowConstraints().add(new RowConstraints(HEIGHT));
        gridPane.add(label, 0, 0);
    }

    public void columnsFunction(){
        for (int i = 0; i <= WIDTH; i++){
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(WIDTH));
            gridPane.add(label, i+1, 0);
        }
    }

    public void rowsFunction(){
        for (int i = 0; i < HEIGHT; i++) {
                Label label = new Label(Integer.toString(HEIGHT-i-1));
                gridPane.getRowConstraints().add(new RowConstraints(HEIGHT));
                GridPane.setHalignment(label, HPos.CENTER);
                gridPane.add(label, 0, i+1);
            }
    }




    public void addElements(){
        for (int x = 1; x <= WIDTH; x++) {
            for (int y = 1; y <= WIDTH; y++) {
                Vector2d position = new Vector2d(x, y);
                Object object = map.objectAt(position);
                if (object != null) {
                    Label label = new Label(object.toString());
                    gridPane.add(label, position.x, position.y);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            }
        }
    }

    public void prepareScene(){
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        this.xyLabel();
        this.columnsFunction();
        this.rowsFunction();
        this.addElements();
    }

    public void refreshMap(){

            gridPane.getChildren().clear();
            gridPane.setGridLinesVisible(false);
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            prepareScene();

    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        map = new PlantsFieldForestedEquatoria();
        map.createLife(map);
        engine = new SimulationEngine( map);
        prepareScene();
        Scene scene = new Scene(gridPane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        for(int i = 0; i < 10; i ++){
            System.out.println(map.toString());
            prepareScene();
            Scene scene1 = new Scene(gridPane, WIDTH, HEIGHT);
            primaryStage.setScene(scene1);
            primaryStage.show();
            Thread.sleep(300);
        }

    }


//        for(int c=0; c<=3;c++){
//            engine.evolution();
//            System.out.println(map.toString());
//            int height = par.mapHeight;
//            int width = par.mapWidth;
//            GridPane grid = new GridPane();
//            grid.setGridLinesVisible(true);
//            grid.getColumnConstraints().add(new ColumnConstraints(width));
//            Label labelxy = new Label("y/x");
//            grid.getRowConstraints().add(new RowConstraints(height));
//            GridPane.setHalignment(labelxy, HPos.CENTER);
//            grid.add(labelxy, 0, 0);
//
//            for (int i = 0; i < width; i++) {
//                Label label = new Label(Integer.toString(i));
//                grid.getColumnConstraints().add(new ColumnConstraints(width));
//                GridPane.setHalignment(label, HPos.CENTER);
//                grid.add(label, i+1, 0);
//            }
//            for (int i = 0; i < height; i++) {
//                Label label = new Label(Integer.toString(height-i-1));
//                grid.getRowConstraints().add(new RowConstraints(height));
//                GridPane.setHalignment(label, HPos.CENTER);
//                grid.add(label, 0, i+1);
//            }
//            for (int x = 1; x <= width; x++) {
//                for (int y = 1; y <= width; y++) {
//                    Vector2d position = new Vector2d(x, y);
//                    Object object = map.objectAt(position);
//                    if (object != null) {
//                        Label label = new Label(object.toString());
//                        grid.add(label, position.x, position.y);
//                        GridPane.setHalignment(label, HPos.CENTER);
//                    }
//                }
//            }
//            Scene scene = new Scene(grid, width, height);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//            Thread.sleep(300);
//        }
//    }


}
