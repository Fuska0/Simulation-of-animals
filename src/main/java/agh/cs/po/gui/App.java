package agh.cs.po.gui;

import agh.cs.po.Classes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {


    private agh.cs.po.Classes.Parameters par;

    private int WIDTH;
    private int HEIGHT;
    private int time;
    private int whcich;

    public App(agh.cs.po.Classes.Parameters parameters, int time, int which){
        this.par = parameters;
        this.HEIGHT = par.mapHeight;
        this.WIDTH = par.mapWidth;
        this.time = time;
        this.whcich = which;
    }
    private AbstractWorldMap map;
    private SimulationEngine engine;
    private GridPane gridPane;


    public void xyLabel(){
        Label label = new Label("y/x");
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(25));
        gridPane.getRowConstraints().add(new RowConstraints(25));
        gridPane.add(label, 0, 0);
    }

    public void columnsFunction(){
        for (int i = 1; i <= WIDTH; i++){
            Label label = new Label(Integer.toString(i-1));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(35));
            gridPane.add(label, i, 0);

        }
    }

    public void rowsFunction(){
        for (int i = 1; i <= HEIGHT; i++) {
            Label label = new Label(Integer.toString(i-1));
            gridPane.getRowConstraints().add(new RowConstraints(35));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, i );
        }
    }

    public void addElements(){
        for (int x = 1; x <= WIDTH; x++) {
            for (int y = 1; y <= HEIGHT; y++) {
                Vector2d position = new Vector2d(x-1, y-1);
                Object object = map.objectAt(position);
                if (object != null) {
                    GuiElementBox elementBox = new GuiElementBox(map.objectAt(position));
                    gridPane.add(elementBox.getvBox(),x,y);
                    GridPane.setHalignment(elementBox.getvBox(),HPos.CENTER);

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
    public Button stopButton(Stage primaryStage){
        Button stopButton = new Button("Stop");
        stopButton.setOnAction((action) -> {
            primaryStage.close();
            System.exit(0);
        });

        return stopButton;
    }

    public Button statisticsButton(Stage primaryStage){
        Button statisticsButton = new Button("statistics");
        Label label = new Label();
        statisticsButton.setOnAction((action) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(map.getStatistic());
            alert.showAndWait();
        });

        return statisticsButton;
    }
    public Button pauseButton() {
        Button pauseButton = new Button("Pause/Resume");
        pauseButton.setOnAction(
                (action) -> {engine.pause();});
        return pauseButton;
    }

    @Override
    public void init() throws Exception {

        engine = new SimulationEngine(map, this, par);
        engine.setMoveDelay(time);
        gridPane = new GridPane();

    }


    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        //refreshMap();
        if(whcich == 1){
        map = new PlantsFieldForestedEquatoria(par);}
        else{map = new GrassFieldToxicCorpses(par);}
        engine = new SimulationEngine(map, this, par);
        engine.setMoveDelay(time);
        gridPane = new GridPane();
        prepareScene();
        Button button = stopButton(primaryStage);
        Button pausebutton = pauseButton();
        Button statisticButton = statisticsButton(primaryStage);

        Thread thread = new Thread((Runnable) engine);
        thread.start();

        GridPane root = new GridPane();
        root.add(gridPane,1,0);
        root.add(button,1,1);
        root.add(pausebutton,1,2);
        root.add(statisticButton,0,0);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root,1500,800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

//

}