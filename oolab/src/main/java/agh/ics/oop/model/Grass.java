package agh.ics.oop.model;

public class Grass {
    public final static String GRASS_STRING = "*";
    private final Vector2d position;


    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return Grass.GRASS_STRING;
    }
}
