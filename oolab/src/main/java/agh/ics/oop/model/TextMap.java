package agh.ics.oop.model;

import java.util.ArrayList;

public class TextMap implements WorldNumberPositionMap<String, Integer> {
    private final ArrayList<String> map = new ArrayList<>();

    @Override
    public boolean place(String object) {
        return this.map.add(object);
    }

    @Override
    public void move(String object, MoveDirection direction) {
        Integer currentPosition = this.map.indexOf(object);
        Integer nextPosition = switch (direction) {
            case FORWARD, RIGHT -> currentPosition + 1;
            case BACKWARD, LEFT -> currentPosition - 1;
        };

        if (this.isOccupied(currentPosition) && this.isOccupied(nextPosition)) {
            String neighbourObject = this.map.get(nextPosition);
            this.map.set(currentPosition, neighbourObject);
            this.map.set(nextPosition, object);
        }
    }

    @Override
    public boolean isOccupied(Integer position) {
        if (position == null) {
            return false;
        }
        return position >= 0 && position < this.map.size();
    }

    @Override
    public String objectAt(Integer position) {
        if (position == null) {
            return "";
        }
        return this.isOccupied(position) ? this.map.get(position) : "";
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return this.isOccupied(position);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
