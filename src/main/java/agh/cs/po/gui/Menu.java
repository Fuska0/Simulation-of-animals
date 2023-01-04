package agh.cs.po.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Menu extends Application {

    public App app;
    public int mapHeight ;
    public int mapWidth ;
    public int startingPlantCount ;
    public int plantEnergy;
    public int plantsGrowingNumber;
    public int startingAnimalsCount;
    public int animalStartEnergy;
    public int readyToBreed;
    public int energyYield;
    public int minMutationsNumber;
    public int maxMutationsNumber;
    public int genomSize;
    public int time;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {




        primaryStage.setTitle("Parameters");

        GridPane grid = new GridPane();

        // map height
        Label mapHeightLabel = new Label("Map height:");
        TextField mapHeightField = new TextField("10");
        grid.add(mapHeightLabel, 0, 0);
        grid.add(mapHeightField, 1, 0);

        // map width
        Label mapWidthLabel = new Label("Map width:");
        TextField mapWidthField = new TextField("15");
        grid.add(mapWidthLabel, 0, 1);
        grid.add(mapWidthField, 1, 1);

        // starting plant count
        Label startingPlantCountLabel = new Label("Starting plant count:");
        TextField startingPlantCountField = new TextField("20");
        grid.add(startingPlantCountLabel, 0, 2);
        grid.add(startingPlantCountField, 1, 2);

        // plant energy
        Label plantEnergyLabel = new Label("Plant energy:");
        TextField plantEnergyField = new TextField("7");
        grid.add(plantEnergyLabel, 0, 3);
        grid.add(plantEnergyField, 1, 3);

        // plants growing number
        Label plantsGrowingNumberLabel = new Label("Plants growing number:");
        TextField plantsGrowingNumberField = new TextField("3");
        grid.add(plantsGrowingNumberLabel, 0, 4);
        grid.add(plantsGrowingNumberField, 1, 4);

        // starting animals count
        Label startingAnimalsCountLabel = new Label("Starting animals count:");
        TextField startingAnimalsCountField = new TextField("10");
        grid.add(startingAnimalsCountLabel, 0, 5);
        grid.add(startingAnimalsCountField, 1, 5);

        // animal start energy
        Label animalStartEnergyLabel = new Label("Animal start energy:");
        TextField animalStartEnergyField = new TextField("15");
        grid.add(animalStartEnergyLabel, 0, 6);
        grid.add(animalStartEnergyField, 1, 6);

        // ready to breed
        Label readyToBreedLabel = new Label("Ready to breed:");
        TextField readyToBreedField = new TextField("5");
        grid.add(readyToBreedLabel, 0, 7);
        grid.add(readyToBreedField, 1, 7);

        // energy yield
        Label energyYieldLabel = new Label("Energy yield:");
        TextField energyYieldField = new TextField("5");
        grid.add(energyYieldLabel, 0, 8);
        grid.add(energyYieldField, 1, 8);
        // min mutations number
        Label minMutationsNumberLabel = new Label("Min mutations number:");
        TextField minMutationsNumberField = new TextField("1");
        grid.add(minMutationsNumberLabel, 0, 9);
        grid.add(minMutationsNumberField, 1, 9);

        // max mutations number
        Label maxMutationsNumberLabel = new Label("Max mutations number:");
        TextField maxMutationsNumberField = new TextField("6");
        grid.add(maxMutationsNumberLabel, 0, 10);
        grid.add(maxMutationsNumberField, 1, 10);

        // genom size
        Label genomSizeLabel = new Label("Genom size:");
        TextField genomSizeField = new TextField("7");
        grid.add(genomSizeLabel, 0, 11);
        grid.add(genomSizeField, 1, 11);

        //time

        Label timeLabel = new Label("Time :");
        TextField timeLabelField = new TextField("300");
        grid.add(timeLabel, 0, 12);
        grid.add(timeLabelField, 1, 12);

        javafx.scene.control.Button submitButton = new javafx.scene.control.Button("Submit");
        submitButton.setOnAction(event -> {
            try {
                // get values from text fields
                mapHeight = Integer.parseInt(mapHeightField.getText());
                if (mapHeight < 0) {
                    throw new IllegalArgumentException();
                }
                mapWidth = Integer.parseInt(mapWidthField.getText());
                if (mapWidth < 0 ) {
                    throw new IllegalArgumentException();
                }
                startingPlantCount = Integer.parseInt(startingPlantCountField.getText());
                if (startingPlantCount <= 0) {
                    throw new IllegalArgumentException();
                }
                plantEnergy = Integer.parseInt(plantEnergyField.getText());
                if(plantEnergy <= 0){
                    throw new IllegalArgumentException();
                }
                plantsGrowingNumber = Integer.parseInt(plantsGrowingNumberField.getText());
                if(plantsGrowingNumber <= 0){
                    throw new IllegalArgumentException();
                }
                startingAnimalsCount = Integer.parseInt(startingAnimalsCountField.getText());
                if(startingAnimalsCount <= 0){
                    throw new IllegalArgumentException();
                }
                animalStartEnergy = Integer.parseInt(animalStartEnergyField.getText());
                if(animalStartEnergy <=0) {
                    throw new IllegalArgumentException();
                }
                readyToBreed = Integer.parseInt(readyToBreedField.getText());
                if(readyToBreed <=0 ) {
                    throw new IllegalArgumentException();
                }
                energyYield = Integer.parseInt(energyYieldField.getText());
                if(energyYield <= 0) {throw new IllegalArgumentException();}
                minMutationsNumber = Integer.parseInt(minMutationsNumberField.getText());
                maxMutationsNumber = Integer.parseInt(maxMutationsNumberField.getText());
                genomSize = Integer.parseInt(genomSizeField.getText());
                if(minMutationsNumber >= maxMutationsNumber || maxMutationsNumber > genomSize || minMutationsNumber >= genomSize){
                    throw new IllegalArgumentException();
                }
                time = Integer.parseInt(timeLabelField.getText());
                if(time <= 0){throw new IllegalArgumentException();}
            }
            catch (IllegalArgumentException e) {
                System.out.println(e);
                System.exit(0);
            }

            try {
                startSimulation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(submitButton);
        grid.add(hbBtn, 1, 13);

        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startSimulation() throws InterruptedException {
        agh.cs.po.Classes.Parameters parameters = new agh.cs.po.Classes.Parameters(mapHeight, mapWidth, startingPlantCount, plantEnergy,
                plantsGrowingNumber,startingAnimalsCount, animalStartEnergy, readyToBreed, energyYield,
                minMutationsNumber, maxMutationsNumber, genomSize);
        this.app = new App(parameters,time);
        this.app.start(new Stage());

    }

}


