package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SimulationIT {
    @Test
    public void noPositionsGiven() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>();
        ArrayList<MoveDirection> moves = new ArrayList<>(
                Collections.nCopies(4, MoveDirection.FORWARD)
        );

        // when
        Simulation simulation = new Simulation(positions, moves);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getAnimals().size(), 0);
        // no exceptions should be risen
    }

    @Test
    public void changeOrientationRight() {
        // given
        final Vector2d startPosition = new Vector2d(2, 2);
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                startPosition
        ));
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.RIGHT
        ));

        // when
        Simulation simulation = new Simulation(positions, moves);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getAnimals().getFirst().getOrientation(), MapDirection.EAST);
        Assertions.assertEquals(simulation.getAnimals().getFirst().getPosition(), startPosition);
    }

    @Test
    public void changeOrientationLeft() {
        // given
        final Vector2d startPosition = new Vector2d(2, 2);
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                startPosition
        ));
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.LEFT
        ));

        // when
        Simulation simulation = new Simulation(positions, moves);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getAnimals().getFirst().getOrientation(), MapDirection.WEST);
        Assertions.assertEquals(simulation.getAnimals().getFirst().getPosition(), startPosition);
    }

    @Test
    public void moveForward() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(2, 2)
        ));
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.FORWARD
        ));

        // when
        Simulation simulation = new Simulation(positions, moves);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getAnimals().getFirst().getOrientation(), Animal.DEFAULT_ORIENTATION);
        Assertions.assertEquals(simulation.getAnimals().getFirst().getPosition(), new Vector2d(2, 3));
    }

    @Test
    public void moveBackward() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(2, 2)
        ));
        ArrayList<MoveDirection> moves = new ArrayList<>(List.of(
                MoveDirection.BACKWARD
        ));

        // when
        Simulation simulation = new Simulation(positions, moves);
        simulation.run();

        // then
        Assertions.assertEquals(simulation.getAnimals().getFirst().getOrientation(), Animal.DEFAULT_ORIENTATION);
        Assertions.assertEquals(simulation.getAnimals().getFirst().getPosition(), new Vector2d(2, 1));
    }

    @Test
    public void generalMovementWithoutMovingOutOfBounds() {
        // given
        ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(0, 0),
                new Vector2d(1, 1),
                new Vector2d(2, 2)
        ));
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
        Simulation simulation = new Simulation(positions, moves);
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
            Assertions.assertEquals(simulation.getAnimals().get(i).getPosition(), expectedPositions.get(i));
            Assertions.assertEquals(simulation.getAnimals().get(i).getOrientation(), expectedOrientations.get(i));
        }
    }

    @Test
    public void canNotMoveOutOfBounds() {
        // given
        final ArrayList<Vector2d> positions = new ArrayList<>(List.of(
                new Vector2d(0, 0),
                new Vector2d(4, 4)
        ));
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
        Simulation simulation = new Simulation(positions, moves);
        simulation.run();

        // then
        // positions should not change
        ArrayList<MapDirection> expectedOrientations = new ArrayList<>(List.of(
                MapDirection.WEST,
                MapDirection.EAST
        ));
        for (int i = 0; i < positions.size(); i++) {
            Assertions.assertEquals(simulation.getAnimals().get(i).getPosition(), positions.get(i));
            Assertions.assertEquals(simulation.getAnimals().get(i).getOrientation(), expectedOrientations.get(i));
        }
    }
}