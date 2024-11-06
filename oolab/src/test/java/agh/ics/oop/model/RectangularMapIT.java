package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RectangularMapIT {
    private final WorldMap worldMap = new RectangularMap(4, 4);

    @Test
    void placeOnMap() {
        // given
        Vector2d positionToPlace = new Vector2d(2, 2);
        Animal animalToPlace = new Animal(positionToPlace);

        // when
        boolean isPlaced = this.worldMap.place(animalToPlace);

        // then
        Assertions.assertTrue(isPlaced);
    }

    @Test
    void placeOffMap() {
        // given
        Vector2d offMapPosition = new Vector2d(6, 6);
        Animal animalToPlace = new Animal(offMapPosition);

        // when
        boolean isPlaced = this.worldMap.place(animalToPlace);

        // then
        Assertions.assertFalse(isPlaced);
    }

    @Test
    void placeOnOccupiedPlace() {
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
    void emptyPositionIsNotOccupied() {
        // given
        Vector2d emptyPositionOnMap = new Vector2d(2, 2);

        // when
        boolean isEmptyPositionOnMapOccupied = this.worldMap.isOccupied(emptyPositionOnMap);

        // then
        Assertions.assertFalse(isEmptyPositionOnMapOccupied);
    }

    @Test
    void occupiedPosition() {
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
    void noObjectAtGivenPosition() {
        // given
        Vector2d positionWithoutAnimal = new Vector2d(2, 2);

        // when
        Animal noneAnimal = this.worldMap.objectAt(positionWithoutAnimal);

        // then
        Assertions.assertNull(noneAnimal);
    }

    @Test
    void objectAtGivenPosition() {
        // given
        Vector2d positionOccupiedByAnimal = new Vector2d(2, 2);
        Animal animal = new Animal(positionOccupiedByAnimal);

        // when
        boolean isPlaced = this.worldMap.place(animal);
        Animal animalAtGivenPosition = this.worldMap.objectAt(positionOccupiedByAnimal);

        // then
        Assertions.assertTrue(isPlaced);
        Assertions.assertEquals(animal, animalAtGivenPosition);
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
    void canNotMoveToOccupiedPosition() {
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

    @Test
    void canNotMoveOutOfBounds() {
        // given
        Vector2d outOfBoundsPosition = new Vector2d(2, 5);
        Vector2d animalToMoveStartingPosition = new Vector2d(2, 4);
        Animal animalToMove = new Animal(animalToMoveStartingPosition);

        // when
        boolean isAnimalToMovePlaced = this.worldMap.place(animalToMove);
        boolean canAnimalBeMoved = this.worldMap.canMoveTo(outOfBoundsPosition);

        // then
        Assertions.assertTrue(isAnimalToMovePlaced);
        Assertions.assertFalse(canAnimalBeMoved);
    }
}