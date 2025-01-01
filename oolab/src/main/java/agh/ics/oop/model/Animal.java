package agh.ics.oop.model;

import com.sun.javafx.beans.event.AbstractNotifyListener;

import java.util.Map;

public class Animal implements WorldElement {
    public static final Vector2d DEFAULT_POSITION = new Vector2d(2, 2);
    public static final MapDirection DEFAULT_ORIENTATION = MapDirection.NORTH;
    public static final int NO_FIRST_CHARACTERS_OF_ORIENTATION_NAME = 1;
    public final static String ANIMAL_WITH_NORTH_ORIENTATION_RESOURCE_FILE_NAME = "up.png";
    public final static String ANIMAL_WITH_EAST_ORIENTATION_RESOURCE_FILE_NAME = "right.png";
    public final static String ANIMAL_WITH_SOUTH_ORIENTATION_RESOURCE_FILE_NAME = "down.png";
    public final static String ANIMAL_WITH_WEST_ORIENTATION_RESOURCE_FILE_NAME = "left.png";
    public final static String ANIMAL_LABEL_TEMPLATE = "Animal %s";

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

    public void move(MoveDirection direction, MoveValidator moveValidator) {
        switch (direction) {
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD -> {
                Vector2d nextPosition = this.position.add(this.orientation.toUnitVector());

                if (moveValidator.canMoveTo(nextPosition)) {
                    this.position = nextPosition;
                }
            }
            case BACKWARD -> {
                Vector2d nextPosition = this.position.subtract(this.orientation.toUnitVector());

                if (moveValidator.canMoveTo(nextPosition)) {
                    this.position = nextPosition;
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.orientation.name().substring(0, Animal.NO_FIRST_CHARACTERS_OF_ORIENTATION_NAME);
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getResourceFileName() {
        return switch (this.getOrientation()) {
            case MapDirection.NORTH -> Animal.ANIMAL_WITH_NORTH_ORIENTATION_RESOURCE_FILE_NAME;
            case MapDirection.EAST -> Animal.ANIMAL_WITH_EAST_ORIENTATION_RESOURCE_FILE_NAME;
            case MapDirection.SOUTH -> Animal.ANIMAL_WITH_SOUTH_ORIENTATION_RESOURCE_FILE_NAME;
            case MapDirection.WEST -> Animal.ANIMAL_WITH_WEST_ORIENTATION_RESOURCE_FILE_NAME;
        };
    }

    @Override
    public String getLabelString() {
        return String.format(Animal.ANIMAL_LABEL_TEMPLATE, this.getPosition());
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }
}
