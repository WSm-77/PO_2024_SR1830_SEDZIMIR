package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.ConsoleMapDisplay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    public static final String NO_MOVES_PROVIDED_ALERT_TITLE = "No moves provided";
    public static final String NO_MOVES_PROVIDED_ALERT_MESSAGE = "Please enter animal moves";
    public static final String ILLEGAL_ARGUMENT_ALERT_TITLE = "Illegal argument alert";
    public static final Vector2d ANIMAL1_STARTING_POSITION = new Vector2d(2, 2);
    public static final Vector2d ANIMAL2_STARTING_POSITION = new Vector2d(3, 4);
    public static final int TOTAL_GRASS_ON_GRASS_FIELD_MAP = 10;

    @FXML
    private TextField movesTextEntry;

    @FXML
    private Label moveInfoLabel;

    @FXML
    private Button startButton;

    @FXML
    private Label infoLabel;

    private WorldMap worldMap;

    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
    }

    public void drawMap(String message) {
        moveInfoLabel.setText(message);
        infoLabel.setText(worldMap.toString());
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> this.drawMap(message));
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        List<MoveDirection> directions = null;

        String movesString = this.movesTextEntry.getText();

        if (movesString.isEmpty()) {
            this.showNoMovesProvidedAlert();
            return;
        }

        try {
            directions = OptionsParser.parseStringArray(movesString.split(" "));
        } catch (IllegalArgumentException exception) {
            this.showIllegalArgumentsAlert(exception.getMessage());
            return;
        }

        // Simulation
        List<Vector2d> positions = List.of(
                SimulationPresenter.ANIMAL1_STARTING_POSITION,
                SimulationPresenter.ANIMAL2_STARTING_POSITION
        );

        MapChangeListener consoleLog = new ConsoleMapDisplay();

        // 1. RectangularMap Simulation
        // var rectangularMap = new RectangularMap(4, 4);
        // rectangularMap.subscribe(consoleLog);
        // rectangularMap.subscribe(this);
        // this.setWorldMap(rectangularMap);
        // var rectangularMapSimulation = new Simulation(positions, directions, rectangularMap);

        // 2. GrassField Simulation
        var grassField = new GrassField(SimulationPresenter.TOTAL_GRASS_ON_GRASS_FIELD_MAP);
        grassField.subscribe(consoleLog);
        grassField.subscribe(this);
        this.setWorldMap(grassField);
        var grassFieldMapSimulation = new Simulation(positions, directions, grassField);

        var simulationEngine = new SimulationEngine(List.of(grassFieldMapSimulation));

        simulationEngine.runAsync();
    }

    private void showNoMovesProvidedAlert() {
        Alert illegalArgumentAlert = new Alert(Alert.AlertType.WARNING);
        illegalArgumentAlert.setTitle(SimulationPresenter.NO_MOVES_PROVIDED_ALERT_TITLE);
        illegalArgumentAlert.setHeaderText(SimulationPresenter.NO_MOVES_PROVIDED_ALERT_MESSAGE);
        illegalArgumentAlert.show();
    }

    private void showIllegalArgumentsAlert(String exceptionMessage) {
        Alert illegalArgumentAlert = new Alert(Alert.AlertType.ERROR);
        illegalArgumentAlert.setTitle(SimulationPresenter.ILLEGAL_ARGUMENT_ALERT_TITLE);
        illegalArgumentAlert.setHeaderText(exceptionMessage);
        illegalArgumentAlert.show();
    }
}
