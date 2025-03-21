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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SimulationPresenter implements MapChangeListener {
    public static final String NO_MOVES_PROVIDED_ALERT_TITLE = "No moves provided";
    public static final String NO_MOVES_PROVIDED_ALERT_MESSAGE = "Please enter animal moves";
    public static final String ILLEGAL_ARGUMENT_ALERT_TITLE = "Illegal argument alert";
    public static final String MOVE_ARGUMENTS_SEPARATOR = " ";
    public static final String AXIS_DESCRIPTION_STRING = "y \\ x";
    public static final String EMPTY_CELL_STRING_REPRESENTATION = "";
    public static final Vector2d ANIMAL1_STARTING_POSITION = new Vector2d(2, 2);
    public static final Vector2d ANIMAL2_STARTING_POSITION = new Vector2d(3, 4);
    public static final int TOTAL_GRASS_ON_GRASS_FIELD_MAP = 10;

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

        int mapRowsCnt = boundaries.upperRight().getY() - boundaries.lowerLeft().getY() + 1;
        int mapColumnsCnt = boundaries.upperRight().getX() - boundaries.lowerLeft().getX() + 1;

        this.setGridCellsSize(mapRowsCnt + 1, mapColumnsCnt + 1);
        this.addRowsAndColumnsHeaders(mapRowsCnt, mapColumnsCnt, upperLeft);
        this.fillGridCells(mapRowsCnt, mapColumnsCnt, upperLeft);
    }


    private void setGridCellsSize(int rows, int columns) {
        double maxHeight = this.mapGrid.getMaxHeight();
        double maxWidth = this.mapGrid.getMaxWidth();

        double cellHeight = maxHeight / rows;
        double cellWidth = maxWidth / columns;

        var rowConstraint = new RowConstraints(cellHeight);
        var columnConstraint = new ColumnConstraints(cellWidth);

        for (int i = 0; i < rows; i++){
            mapGrid.getRowConstraints().add(rowConstraint);
        }

        for (int i = 0; i < columns; i++){
            mapGrid.getColumnConstraints().add(columnConstraint);
        }
    }

    private void addRowsAndColumnsHeaders(int mapRows, int mapColumns, Vector2d upperLeft) {
        var axisDescription = new Label(SimulationPresenter.AXIS_DESCRIPTION_STRING);
        this.mapGrid.add(axisDescription, 0, 0);
        GridPane.setHalignment(axisDescription, HPos.CENTER);
        GridPane.setValignment(axisDescription, VPos.CENTER);

        // fill x coordinate header
        for (int row = 0; row < mapRows; row++){
            var field = new Label(String.valueOf(upperLeft.getY() - row));
            this.mapGrid.add(field, 0, row + 1);
            GridPane.setHalignment(field, HPos.CENTER);
            GridPane.setValignment(field, VPos.CENTER);
        }

        // fill y coordinate header
        for (int column = 0; column < mapColumns; column++){
            var field = new Label(String.valueOf(upperLeft.getX() + column));
            this.mapGrid.add(field,column + 1, 0);
            GridPane.setHalignment(field, HPos.CENTER);
            GridPane.setValignment(field, VPos.CENTER);
        }
    }

    private void fillGridCells(int gridRowCnt, int gridColumnCnt, Vector2d upperLeft) {
        for (int gridRow = 0; gridRow < gridRowCnt; gridRow++) {
            for (int gridColumn = 0; gridColumn < gridColumnCnt; gridColumn++) {
                var mapPosition = upperLeft.add(new Vector2d(gridColumn, -gridRow));
                Optional<WorldElement> optionalWorldElement = this.worldMap.objectAt(mapPosition);
                if (optionalWorldElement.isPresent()) {
                    WorldElementBox worldElementBox = new WorldElementBox(optionalWorldElement.get());
                    var field = worldElementBox.getBox();

                    this.mapGrid.add(field,  gridColumn + 1, gridRow + 1, 1, 1);

                    GridPane.setHalignment(field, HPos.CENTER);
                    GridPane.setValignment(field, VPos.CENTER);
                }

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
            directions = OptionsParser.parseStringArray(movesString.split(SimulationPresenter.MOVE_ARGUMENTS_SEPARATOR));
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

        // create MapChangeListener with lambda expression
        MapChangeListener dateAndTimeMapLogger = (WorldMap worldMap, String message) -> {
            final String dateFormatTemplate = "yyyy-mm-dd hh:mm:ss";
            final String mapChangeWithDateAndTimeMessageTemplate = "%s %s";
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatTemplate);
            String formattedDate = dateFormat.format(currentDate);
            System.out.println(String.format(mapChangeWithDateAndTimeMessageTemplate, formattedDate, message));
        };

        // GrassField Simulation
        var grassField = new GrassField(SimulationPresenter.TOTAL_GRASS_ON_GRASS_FIELD_MAP);
        grassField.subscribe(dateAndTimeMapLogger);
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
