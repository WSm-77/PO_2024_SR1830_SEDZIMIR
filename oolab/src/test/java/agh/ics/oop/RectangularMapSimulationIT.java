package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RectangularMapSimulationITSimulationIT {
    private final WorldMap<Animal, Vector2d> worldMap = new RectangularMap(4, 4);

    // helper function
    private List<Animal> initializeAnimalsList(List<Vector2d> positions) {
        ArrayList<Animal> animals = new ArrayList<>();

        for (var position : positions) {
            animals.add(new Animal(position));
        }

        return animals;
    }

    @Test
    public void noPositionsGiven() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>();
        var animals = this.initializeAnimalsList(positions);

        ArrayList<MoveDirection> moves = new ArrayList<>(
                Collections.nCopies(4, MoveDirection.FORWARD)
        );

        // when
        Simulation<Animal, Vector2d> simulation = new Simulation<>(animals, moves, this.worldMap);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getObjects().size(), 0);
        // no exceptions should be risen
    }

    @Test
    public void changeOrientationRight() {
        // given
        final Vector2d startPosition = new Vector2d(2, 2);
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                startPosition
        ));
        var animals = this.initializeAnimalsList(positions);

        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.RIGHT
        ));

        // when
        Simulation<Animal, Vector2d> simulation = new Simulation<>(animals, moves, this.worldMap);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getObjects().getFirst().getOrientation(), MapDirection.EAST);
        Assertions.assertEquals(simulation.getObjects().getFirst().getPosition(), startPosition);
    }

    @Test
    public void changeOrientationLeft() {
        // given
        final Vector2d startPosition = new Vector2d(2, 2);
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                startPosition
        ));
        var animals = this.initializeAnimalsList(positions);
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.LEFT
        ));

        // when
        Simulation<Animal, Vector2d> simulation = new Simulation<>(animals, moves, this.worldMap);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getObjects().getFirst().getOrientation(), MapDirection.WEST);
        Assertions.assertEquals(simulation.getObjects().getFirst().getPosition(), startPosition);
    }

    @Test
    public void moveForward() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(2, 2)
        ));
        var animals = this.initializeAnimalsList(positions);
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.FORWARD
        ));

        // when
        Simulation<Animal, Vector2d> simulation = new Simulation<>(animals, moves, this.worldMap);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getObjects().getFirst().getOrientation(), Animal.DEFAULT_ORIENTATION);
        Assertions.assertEquals(simulation.getObjects().getFirst().getPosition(), new Vector2d(2, 3));
    }

    @Test
    public void moveBackward() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(2, 2)
        ));
        var animals = this.initializeAnimalsList(positions);
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.BACKWARD
        ));

        // when
        Simulation<Animal, Vector2d> simulation = new Simulation<>(animals, moves, this.worldMap);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getObjects().getFirst().getOrientation(), Animal.DEFAULT_ORIENTATION);
        Assertions.assertEquals(simulation.getObjects().getFirst().getPosition(), new Vector2d(2, 1));
    }

    @Test
    public void generalMovementWithoutMovingOutOfBounds() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(0, 0),
                new Vector2d(1, 1),
                new Vector2d(2, 2)
        ));
        var animals = this.initializeAnimalsList(positions);
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT
        ));

        // when
        Simulation<Animal, Vector2d> simulation = new Simulation<>(animals, moves, this.worldMap);
        simulation.run();

        // then
        ArrayList<Vector2d> expectedPositions = new ArrayList<>(List.of(
            new Vector2d(0, 1),
            new Vector2d(1, 1),
            new Vector2d(3, 2)
        ));
        ArrayList<MapDirection> expectedOrientations = new ArrayList<>(List.of(
            MapDirection.NORTH,
            MapDirection.EAST,
            MapDirection.WEST
        ));
        for (int i = 0; i < positions.size(); i++) {
            Assertions.assertEquals(simulation.getObjects().get(i).getPosition(), expectedPositions.get(i));
            Assertions.assertEquals(simulation.getObjects().get(i).getOrientation(), expectedOrientations.get(i));
        }
    }

    @Test
    public void canNotMoveOutOfBounds() {
        // given
        final ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(0, 0),
                new Vector2d(4, 4)
        ));
        var animals = this.initializeAnimalsList(positions);
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.BACKWARD,     // out of bottom bounds
                MoveDirection.FORWARD,      // out of top bounds
                MoveDirection.BACKWARD,     // still out of bottom bounds
                MoveDirection.FORWARD,      // still out of top bounds
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,      // out of left bounds
                MoveDirection.FORWARD,      // out of right bounds
                MoveDirection.FORWARD,      // still out of left bounds
                MoveDirection.FORWARD       // still out of right bounds
        ));

        // when
        Simulation<Animal, Vector2d> simulation = new Simulation<>(animals, moves, this.worldMap);
        simulation.run();

        // then
        // positions should not change
        ArrayList<MapDirection> expectedOrientations = new ArrayList<>(List.of(
                MapDirection.WEST,
                MapDirection.EAST
        ));
        for (int i = 0; i < positions.size(); i++) {
            Assertions.assertEquals(simulation.getObjects().get(i).getPosition(), positions.get(i));
            Assertions.assertEquals(simulation.getObjects().get(i).getOrientation(), expectedOrientations.get(i));
        }
    }
}