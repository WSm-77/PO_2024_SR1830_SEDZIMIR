package agh.ics.oop.presenter;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private TextField movesTextEntry;

    @FXML
    private Button startButton;

    @FXML
    private Label infoLabel;

    private WorldMap worldMap;

    @FXML
    void initialize() {
        startButton.setOnAction(actionEvent -> {
            // TODO
        });
    }

    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
    }

    public void drawMap() {
        infoLabel.setText(worldMap.toString());
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        System.out.println("MapChange!!!!");
        this.drawMap();
    }
}
