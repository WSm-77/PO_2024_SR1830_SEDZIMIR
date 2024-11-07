package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldIT {
    private final GrassField worldMap = new GrassField(10);
    private final List<WorldElement> grassList = this.worldMap.getGrassList();

    @Test
    void placeOnMap() {
        // given
        List<Vector2d> positionsToPlace = new ArrayList<>(List.of(
                new Vector2d(0, 0),
                new Vector2d(2, 5),
                new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE),
                new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE)
        ));

        // when
        List<Boolean> isPlaced = new ArrayList<>();
        List<Boolean> expectedIsPlace = new ArrayList<>(Collections.nCopies(positionsToPlace.size(), true));
        for (var positionToPlace : positionsToPlace) {
            Animal animalToPlace = new Animal(positionToPlace);
            isPlaced.add(this.worldMap.place(animalToPlace));
        }

        // then
        Assertions.assertEquals(isPlaced, expectedIsPlace);
    }

    @Test
    void cannotPlaceOnOtherAnimal() {
        // given
        Vector2d occupiedPosition = new Vector2d(2, 2);
        Animal animalOnOccupiedPosition = new Animal(occupiedPosition);
        Animal animalToPlace = new Animal(occupiedPosition);

        // when
        boolean isAnimalOnOccupiedPositionPlaced = this.worldMap.place(animalOnOccupiedPosition);
        boolean isAnimalToPlacePlaced = this.worldMap.place(animalToPlace);

        // then
        Assertions.assertTrue(isAnimalOnOccupiedPositionPlaced);
        Assertions.assertFalse(isAnimalToPlacePlaced);
    }

    @Test
    void positionWithGrassIsOccupied() {
        // given
        // grassList

        // when
        List<Boolean> isPositionWithGrassOccupied = new ArrayList<>();
        List<Boolean> expectedIsPositionWithGrassOccupied = new ArrayList<>(Collections.nCopies(grassList.size(), true));
        for (var grass : grassList) {
            isPositionWithGrassOccupied.add(this.worldMap.isOccupied(grass.getPosition()));
        }

        // then
        Assertions.assertEquals(isPositionWithGrassOccupied, expectedIsPositionWithGrassOccupied);
    }

    @Test
    void positionWithAnimalIsOccupied() {
        // given
        Vector2d occupiedPositionOnMap = new Vector2d(2, 2);
        Animal animal = new Animal(occupiedPositionOnMap);

        // when
        this.worldMap.place(animal);
        boolean isOccupiedPositionOnMapOccupied = this.worldMap.isOccupied(occupiedPositionOnMap);

        // then
        Assertions.assertTrue(isOccupiedPositionOnMapOccupied);
    }

    @Test
    void moveAnimal() {
        // given
        Vector2d position = new Vector2d(2, 2);
        Animal animal = new Animal(position);
        MoveDirection move = MoveDirection.FORWARD;

        // when
        Vector2d expectedPosition = new Vector2d(2, 3);
        MapDirection expectedOrientation = MapDirection.NORTH;
        this.worldMap.move(animal, move);

        // then
        Assertions.assertEquals(animal.getPosition(), expectedPosition);
        Assertions.assertEquals(animal.getOrientation(), expectedOrientation);
    }

    @Test
    void notOccupiedPosition() {
        // given
        var notOccupiedPosition = new Vector2d(0, 0);

        // when
        var nullObject = this.worldMap.objectAt(notOccupiedPosition);

        // then
        Assertions.assertNull(nullObject);
    }

    @Test
    void grassAtGivenPosition() {
        // given
        // grassList

        // when
        List<WorldElement> objectsList = new ArrayList<>();
        for (var grass : grassList) {
            objectsList.add(this.worldMap.objectAt(grass.getPosition()));
        }

        // then
        Assertions.assertEquals(this.grassList, objectsList);
    }

    @Test
    void animalAtGivenPosition() {
        // given
        Vector2d positionOccupiedByAnimal = new Vector2d(-1, -1);
        Animal animal = new Animal(positionOccupiedByAnimal);

        // when
        boolean isPlaced = this.worldMap.place(animal);
        var animalAtGivenPosition = this.worldMap.objectAt(positionOccupiedByAnimal);

        // then
        Assertions.assertTrue(isPlaced);
        Assertions.assertEquals(animal, animalAtGivenPosition);
    }

    @Test
    void animalCoversGrass() {
        // given
        WorldElement grass = this.grassList.getFirst();
        Vector2d positionOccupiedByAnimalAndGrass = grass.getPosition();
        Animal animal = new Animal(positionOccupiedByAnimalAndGrass);

        // when
        boolean isPlaced = this.worldMap.place(animal);
        var objectAtGivenPosition = this.worldMap.objectAt(positionOccupiedByAnimalAndGrass);

        // then
        Assertions.assertTrue(isPlaced);
        Assertions.assertEquals(animal, objectAtGivenPosition);
        Assertions.assertNotEquals(grass, objectAtGivenPosition);
    }

    @Test
    void canMoveToEmptyPosition() {
        // given
        Vector2d emptyPosition = new Vector2d(2, 2);
        Vector2d animalToMoveStartingPosition = new Vector2d(2, 1);
        Animal animalToMove = new Animal(animalToMoveStartingPosition);

        // when
        boolean isAnimalToMovePlaced = this.worldMap.place(animalToMove);
        boolean canAnimalBeMoved = this.worldMap.canMoveTo(emptyPosition);

        // then
        Assertions.assertTrue(isAnimalToMovePlaced);
        Assertions.assertTrue(canAnimalBeMoved);
    }

    @Test
    void canMoveToPositionOccupiedByGrass() {
        // given
        Vector2d positionOccupiedByGrass = this.grassList.getFirst().getPosition();
        Vector2d animalToMoveStartingPosition = positionOccupiedByGrass.add(MapDirection.SOUTH.toUnitVector());
        Animal animalToMove = new Animal(animalToMoveStartingPosition);

        // when
        boolean isAnimalToMovePlaced = this.worldMap.place(animalToMove);
        boolean canAnimalBeMoved = this.worldMap.canMoveTo(positionOccupiedByGrass);

        // then
        Assertions.assertTrue(isAnimalToMovePlaced);
        Assertions.assertTrue(canAnimalBeMoved);
    }

    @Test
    void canNotMoveToPositionOccupiedByAnimal() {
        // given
        Vector2d occupiedPosition = new Vector2d(2, 2);
        Animal animalAtOccupiedPosition = new Animal(occupiedPosition);
        Vector2d animalToMoveStartingPosition = new Vector2d(2, 1);
        Animal animalToMove = new Animal(animalToMoveStartingPosition);

        // when
        boolean isAnimalAtOccupiedPositionPlaced = this.worldMap.place(animalAtOccupiedPosition);
        boolean isAnimalToMovePlaced = this.worldMap.place(animalToMove);
        boolean canAnimalBeMoved = this.worldMap.canMoveTo(occupiedPosition);

        // then
        Assertions.assertTrue(isAnimalAtOccupiedPositionPlaced);
        Assertions.assertTrue(isAnimalToMovePlaced);
        Assertions.assertFalse(canAnimalBeMoved);
    }
}
