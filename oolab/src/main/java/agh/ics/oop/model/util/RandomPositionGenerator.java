package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    public static final Vector2d DEFAULT_START_POSITION = new Vector2d(0, 0);
    private final List<Vector2d> randomPositions;

    public RandomPositionGenerator(int width, int height, int elementsCount){
        this.randomPositions = RandomPositionGenerator.generatePositions(
                RandomPositionGenerator.DEFAULT_START_POSITION, width, height, elementsCount
        );
    }

    public static List<Vector2d> generatePositions(Vector2d startPosition, int width, int height, int elementsCount) {
        var allPositions = new ArrayList<Vector2d>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var position = new Vector2d(startPosition.getX() + i, startPosition.getY() + j);
                allPositions.add(position);
            }
        }

        Collections.shuffle(allPositions);

        return allPositions.subList(0, elementsCount);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return this.randomPositions.iterator();
    }
}
