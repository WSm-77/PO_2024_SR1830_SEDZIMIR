package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.ConsoleMapDisplay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    public static final String NO_MOVES_PROVIDED_ALERT_TITLE = "No moves provided";
    public static final String NO_MOVES_PROVIDED_ALERT_MESSAGE = "Please enter animal moves";
    public static final String ILLEGAL_ARGUMENT_ALERT_TITLE = "Illegal argument alert";
    public static final Vector2d ANIMAL1_STARTING_POSITION = new Vector2d(2, 2);
    public static final Vector2d ANIMAL2_STARTING_POSITION = new Vector2d(3, 4);
    public static final int TOTAL_GRASS_ON_GRASS_FIELD_MAP = 10;
    public static final int CELL_WIDTH = 48;
    public static final int CELL_HEIGHT = 48;

    private WorldMap worldMap;

    @FXML
    private TextField movesTextEntry;

    @FXML
    private Label moveInfoLabel;

    @FXML
    private Button startButton;

    @FXML
    private GridPane mapGrid;

    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
    }

    private void buildMapGrid() {
        var boundaries = this.worldMap.getCurrentBounds();

        var upperLeft = new Vector2d(boundaries.lowerLeft().getX(), boundaries.upperRight().getY());

        int gridRowCnt = boundaries.upperRight().getY() - boundaries.lowerLeft().getY();
        int gridColumnCnt = boundaries.upperRight().getX() - boundaries.lowerLeft().getX();

        this.setGridCellsSize(gridRowCnt, gridColumnCnt);

        this.fillGridCells(gridRowCnt, gridColumnCnt, upperLeft);
    }

    private void setGridCellsSize(int rows, int columns) {
        for (int i = 0; i < rows; i++){
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        for (int i = 0; i < columns; i++){
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
    }

    private void fillGridCells(int gridRowCnt, int gridColumnCnt, Vector2d upperLeft) {
        for (int gridRow = 0; gridRow < gridRowCnt; gridRow++) {
            for (int gridColumn = 0; gridColumn < gridColumnCnt; gridColumn++) {
                var mapPosition = upperLeft.add(new Vector2d(gridColumn, -gridRow));
                Label field = new Label();
                field.setText(this.worldMap.isOccupied(mapPosition) ? this.worldMap.objectAt(mapPosition).toString() : " ");
                this.mapGrid.add(field,  gridColumn, gridRow, 1, 1);

                // Set horizontal and vertical alignment to center
                GridPane.setHalignment(field, HPos.CENTER);
                GridPane.setValignment(field, VPos.CENTER);
            }
        }
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void drawMap(String message) {
        moveInfoLabel.setText(message);
        this.clearGrid();
        this.buildMapGrid();
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
