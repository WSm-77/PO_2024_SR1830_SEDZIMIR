package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements WorldMap {
    private final Vector2d mapLowerLeftBoundary = new Vector2d(0, 0);
    private final Vector2d mapUpperRightBoundary;
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final MapVisualizer mapVisualizer  = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        this.mapUpperRightBoundary = new Vector2d(width, height);
    }

    public Vector2d getMapLowerLeftBoundary() {
        return this.mapLowerLeftBoundary;
    }

    public Vector2d getMapUpperRightBoundary() {
        return this.mapUpperRightBoundary;
    }

    @Override
    public boolean place(Animal animal) {
        final Vector2d positionToPlace = animal.getPosition();
        if (!this.isOccupied(positionToPlace)) {
            this.animals.put(positionToPlace, animal);
            return true;
        }
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.animals.containsKey(position) && this.animals.get(position) != null;
    }

    @Override
    public Animal objectAt(Vector2d position) {
        if (this.animals.containsKey(position)) {
            return this.animals.get(position);
        }
        return null;
    }

    private boolean isOnMap(Vector2d position) {
        return position.precedes(this.mapUpperRightBoundary) &&
                position.follows(this.mapLowerLeftBoundary);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return this.isOnMap(position) && !this.isOccupied(position);
    }
}
