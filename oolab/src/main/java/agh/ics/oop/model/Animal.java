package agh.ics.oop.model;

public class Animal {
    public static final Vector2d DEFAULT_POSITION = new Vector2d(2, 2);
    public static final Vector2d MAP_LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    public static final Vector2d MAP_UPPER_RIGHT_BOUNDARY = new Vector2d(4, 4);
    public static final MapDirection DEFAULT_ORIENTATION = MapDirection.NORTH;

    private MapDirection orientation;
    private Vector2d position;

    public Animal() {
        this(Animal.DEFAULT_POSITION);
    }

    public Animal(Vector2d position) {
        this.position = position;
        this.orientation = Animal.DEFAULT_ORIENTATION;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    private boolean isOnMap(Vector2d position) {
        return position.precedes(Animal.MAP_UPPER_RIGHT_BOUNDARY) &&
               position.follows(Animal.MAP_LOWER_LEFT_BOUNDARY);
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD -> {
                Vector2d nextPosition = this.position.add(this.orientation.toUnitVector());
                if (isOnMap(nextPosition)) {
                    this.position = nextPosition;
                }
            }
            case BACKWARD -> {
                Vector2d nextPosition = this.position.subtract(this.orientation.toUnitVector());
                if (isOnMap(nextPosition)) {
                    this.position = nextPosition;
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("pozycja: %s, orientacja: %s", position.toString(), orientation.toString());
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }
}
