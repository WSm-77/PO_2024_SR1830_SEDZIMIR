package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.ConsoleMapDisplay;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // start
        System.out.println("system wystartował");

        List<String> commandLineArgs = getParameters().getRaw();

        List<MoveDirection> directions = null;

        try {
            directions = OptionsParser.parseStringArray(commandLineArgs.toArray(String[]::new));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.exit(1);
        }

        // Vector2d verification
        Vector2d position1 = new Vector2d(1, 2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2, 1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        // MapDirections verification
        MapDirection direction = MapDirection.NORTH;
        System.out.println(direction.toString());
        System.out.println(direction.next().toString());
        System.out.println(direction.previous().toString());
        System.out.println(direction.toUnitVector().toString());

        // create animal
        Animal animalWSm = new Animal();
        System.out.println("status zwierzaka:");
        System.out.println(animalWSm.toString());

        // Simulation
        var repeatedPosition = new Vector2d(2, 2);
        List<Vector2d> positions = List.of(repeatedPosition, repeatedPosition, new Vector2d(3, 4));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        MapChangeListener consoleLog = new ConsoleMapDisplay();
        SimulationPresenter simulationPresenter = loader.getController();

        // 1. RectangularMap Simulation
        var rectangularMap = new RectangularMap(4, 4);

        rectangularMap.subscribe(consoleLog);
        rectangularMap.subscribe(simulationPresenter);
        simulationPresenter.setWorldMap(rectangularMap);

        var rectangularMapSimulation = new Simulation(positions, directions, rectangularMap);

        rectangularMapSimulation.run();

        // stop
        System.out.println("system zakończył działanie");

        this.configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
