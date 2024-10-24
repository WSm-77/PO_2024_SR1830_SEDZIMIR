package agh.ics.oop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class MapDirectionTest {
    private final MapDirection north = MapDirection.NORTH;
    private final MapDirection east = MapDirection.EAST;
    private final MapDirection south = MapDirection.SOUTH;
    private final MapDirection west = MapDirection.WEST;

    @Test
    public void nextTest() {
        Assertions.assertEquals(north.next(), east);
        Assertions.assertEquals(east.next(), south);
        Assertions.assertEquals(south.next(), west);
        Assertions.assertEquals(west.next(), north);
    }

    @Test
    public void previousTest() {
        Assertions.assertEquals(north.previous(), west);
        Assertions.assertEquals(east.previous(), north);
        Assertions.assertEquals(south.previous(), east);
        Assertions.assertEquals(west.previous(), south);
    }
}
