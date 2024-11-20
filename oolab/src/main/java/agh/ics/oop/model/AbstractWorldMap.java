package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap {
    public static final Vector2d DEFAULT_POSITION = new Vector2d(0, 0);
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer mapVisualizer  = new MapVisualizer(this);
    protected Vector2d mapLowerLeftBoundary = DEFAULT_POSITION;
    protected Vector2d mapUpperRightBoundary = DEFAULT_POSITION;

    @Override
    public boolean place(Animal animal) {
        final Vector2d positionToPlace = animal.getPosition();
        if (this.canMoveTo(positionToPlace)) {
            this.animals.put(positionToPlace, animal);
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
        return this.animals.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return this.animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !this.animals.containsKey(position);
    }

    @Override
    public String toString() {
        return this.mapVisualizer.draw(this.mapLowerLeftBoundary, this.mapUpperRightBoundary);
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(this.animals.values());
    }
}
