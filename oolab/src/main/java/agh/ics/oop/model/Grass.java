package agh.ics.oop.model;

public class Grass implements WorldElement{
    public final static String GRASS_STRING = "*";
    public final static String GRASS_LABEL = "Grass";
    public final static String GRASS_RESOURCE_FILE_NAME = "grass.png";
    private final Vector2d position;


    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String getResourceFileName() {
        return Grass.GRASS_RESOURCE_FILE_NAME;
    }

    @Override
    public String getLabelString() {
        return Grass.GRASS_LABEL;
    }

    @Override
    public String toString() {
        return Grass.GRASS_STRING;
    }
}
