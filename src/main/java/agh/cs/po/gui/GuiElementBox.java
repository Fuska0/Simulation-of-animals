package agh.cs.po.gui;

import agh.cs.po.Classes.Animal;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiElementBox {

    private Image image;
    private ImageView imageView = new ImageView();
    private VBox vBox = new VBox();


    public GuiElementBox(Object element) {
        String tmpPNG, tmpLabel;

        if (element.getClass() == Animal.class) {
            tmpPNG = "rak";
            tmpLabel = ((Animal) element).label();}
        else {
            tmpPNG = "glon";
            tmpLabel = " ";}
        this.image = new Image("file:src/main/resources/" + tmpPNG + ".png");
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);


        Label label = new Label(tmpLabel);
        vBox.getChildren().addAll(imageView, label);
        vBox.setAlignment(Pos.CENTER);
    }

    public VBox getvBox() {
        return vBox;
    }
}