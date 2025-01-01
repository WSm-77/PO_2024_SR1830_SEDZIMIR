package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class WorldElementBox {
    public static double ELEMENT_WIDTH = 20.0;
    public static double ELEMENT_HEIGHT = 20.0;
    public static double BOX_MAX_WIDTH = 50.0;
    public static double BOX_MAX_HEIGHT = 50.0;
    final private WorldElement worldElement;
    final private ImageView elementImageView;
    final private Label label;
    final private VBox box;

    public WorldElementBox(WorldElement worldElement) {
        this.worldElement = worldElement;

        // create image
        Image elementImage = new Image(worldElement.getResourceFileName());
        this.elementImageView = new ImageView(elementImage);

        // set image size
        this.elementImageView.setFitWidth(WorldElementBox.ELEMENT_WIDTH);
        this.elementImageView.setFitHeight(WorldElementBox.ELEMENT_HEIGHT);

        // create label
        this.label = new Label(worldElement.getLabelString());

        this.box = new VBox();
        this.box.getChildren().addAll(this.elementImageView, this.label);
        this.box.setMaxHeight(WorldElementBox.BOX_MAX_HEIGHT);
        this.box.setMaxWidth(WorldElementBox.BOX_MAX_WIDTH);
        this.box.setAlignment(Pos.CENTER);
    }

    public VBox getBox() {
        return this.box;
    }
}
