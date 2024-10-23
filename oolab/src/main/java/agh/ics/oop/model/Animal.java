package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation;

    private Vector2d position;

    public Animal() {
        this(new Vector2d(2, 2));
    }

    public Animal(Vector2d position) {
        this.position = position;
        this.orientation = MapDirection.NORTH;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    @Override
    public String toString() {
        return String.format("pozycja: %s, orientacja: %s", position.toString(), orientation.toString());
    }
}
