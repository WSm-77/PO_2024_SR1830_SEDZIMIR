package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

import java.lang.Math;

public class GrassField implements WorldMap {
    public static final Vector2d DEFAULT_POSITION = new Vector2d(0, 0);
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grassFields = new HashMap<>();
    private final MapVisualizer mapVisualizer  = new MapVisualizer(this);
    private final int totalGrassFieldOnTheMap;
    private final int grassMaxYCoordinate;
    private final int grassMaxXCoordinate;

    private Vector2d mapLowerLeftBoundary;
    private Vector2d mapUpperRightBoundary;

    public GrassField(int totalGrassFieldOnTheMap) {
        this.mapLowerLeftBoundary = GrassField.DEFAULT_POSITION;
        this.mapUpperRightBoundary = GrassField.DEFAULT_POSITION;
        this.totalGrassFieldOnTheMap = totalGrassFieldOnTheMap;
        this.grassMaxXCoordinate = (int)Math.floor(Math.sqrt(10*this.totalGrassFieldOnTheMap));
        this.grassMaxYCoordinate = this.grassMaxXCoordinate;

        var randomGrassPositions = new RandomPositionGenerator(this.grassMaxXCoordinate, this.grassMaxYCoordinate, this.totalGrassFieldOnTheMap);
        for (var grassPosition : randomGrassPositions) {
            var grass = new Grass(grassPosition);
            this.grassFields.put(grassPosition, grass);

            // update map boundaries
            this.updateBoundaries(grassPosition);
        }
    }

    public int getTotalGrassFieldOnTheMap() {
        return this.totalGrassFieldOnTheMap;
    }

    public int getGrassMaxYCoordinate() {
        return this.grassMaxYCoordinate;
    }

    public int getGrassMaxXCoordinate() {
        return this.grassMaxXCoordinate;
    }

    public Vector2d getMapLowerLeftBoundary() {
        return this.mapLowerLeftBoundary;
    }

    public Vector2d getMapUpperRightBoundary() {
        return this.mapUpperRightBoundary;
    }

    private void updateUpperRightBoundary(Vector2d position) {
        this.mapUpperRightBoundary = this.mapUpperRightBoundary.upperRight(position);
    }

    private void updateLowerLeftBoundary(Vector2d position) {
        this.mapLowerLeftBoundary = this.mapLowerLeftBoundary.lowerLeft(position);
    }

    private void updateBoundaries(Vector2d position) {
        this.updateLowerLeftBoundary(position);
        this.updateUpperRightBoundary(position);
    }

    @Override
    public boolean place(Animal animal) {
        final Vector2d positionToPlace = animal.getPosition();
        if (this.canMoveTo(positionToPlace)) {
            this.animals.put(positionToPlace, animal);
            this.updateBoundaries(positionToPlace);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        var prevAnimalPosition = animal.getPosition();
        animal.move(direction, this);

        // check if animal changed it's position
        var currentAnimalPosition = animal.getPosition();
        if (!Objects.equals(prevAnimalPosition, currentAnimalPosition)) {
            this.animals.remove(prevAnimalPosition);
            this.place(animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.animals.containsKey(position) || this.grassFields.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement worldElement = this.grassFields.get(position);
        if (this.animals.containsKey(position)) {
            worldElement = this.animals.get(position);
        }
        return worldElement;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !this.animals.containsKey(position);
    }

    @Override
    public String toString() {
        return this.mapVisualizer.draw(this.mapLowerLeftBoundary, this.mapUpperRightBoundary);
    }
}
