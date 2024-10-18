package agh.ics.oop.model;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Vector2dTest {

    private final Vector2d vec1 = new Vector2d(1, 1);
    private final Vector2d vec2 = new Vector2d(2, 2);
    private final Vector2d vec3 = new Vector2d(2, 3);

    @Test
    public void toStringTest() {
        Assertions.assertEquals(vec1.toString(), "(1,1)");
        Assertions.assertEquals(vec2.toString(), "(2,2)");
        Assertions.assertEquals(vec3.toString(), "(2,3)");
    }

    @Test
    public void precedesTest() {
        // vec 1
        Assertions.assertTrue(vec1.precedes(vec1));
        Assertions.assertTrue(vec1.precedes(vec2));
        Assertions.assertTrue(vec1.precedes(vec3));

        // vec 2
        Assertions.assertFalse(vec2.precedes(vec1));

        Assertions.assertTrue(vec2.precedes(vec2));
        Assertions.assertTrue(vec2.precedes(vec3));

        // vec 3
        Assertions.assertFalse(vec3.precedes(vec1));
        Assertions.assertFalse(vec3.precedes(vec2));

        Assertions.assertTrue(vec3.precedes(vec3));
    }

    @Test
    public void followsTest() {
        // vec 1
        Assertions.assertTrue(vec1.follows(vec1));

        Assertions.assertFalse(vec1.follows(vec2));
        Assertions.assertFalse(vec1.follows(vec3));

        // vec 2
        Assertions.assertTrue(vec2.follows(vec1));
        Assertions.assertTrue(vec2.follows(vec2));

        Assertions.assertFalse(vec2.follows(vec3));

        // vec 3
        Assertions.assertTrue(vec3.follows(vec1));
        Assertions.assertTrue(vec3.follows(vec2));
        Assertions.assertTrue(vec3.follows(vec3));
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(vec1.add(vec1), new Vector2d(2, 2));
        Assertions.assertEquals(vec1.add(vec1), vec2);
        Assertions.assertEquals(vec1.add(vec2), new Vector2d(3, 3));
        Assertions.assertEquals(vec1.add(vec3), new Vector2d(3, 4));
        Assertions.assertEquals(vec2.add(vec2), new Vector2d(4, 4));
        Assertions.assertEquals(vec2.add(vec3), new Vector2d(4, 5));
        Assertions.assertEquals(vec3.add(vec3), new Vector2d(4, 6));

        Assertions.assertEquals(vec1.add(vec3), vec3.add(vec1));
        Assertions.assertEquals(vec1.add(vec2), vec2.add(vec1));
        Assertions.assertEquals(vec2.add(vec3), vec3.add(vec2));
    }

    @Test
    public void subtractTest() {
        Assertions.assertEquals(vec1.subtract(vec1), new Vector2d(0, 0));
        Assertions.assertEquals(vec1.subtract(vec2), new Vector2d(-1, -1));
        Assertions.assertEquals(vec1.subtract(vec3), new Vector2d(-1, -2));

        Assertions.assertEquals(vec2.subtract(vec1), new Vector2d(1, 1));
        Assertions.assertEquals(vec2.subtract(vec2), new Vector2d(0, 0));
        Assertions.assertEquals(vec2.subtract(vec3), new Vector2d(0, -1));

        Assertions.assertEquals(vec3.subtract(vec1), new Vector2d(1, 2));
        Assertions.assertEquals(vec3.subtract(vec2), new Vector2d(0, 1));
        Assertions.assertEquals(vec3.subtract(vec3), new Vector2d(0, 0));
    }

    @Test
    public void upperRightTest() {
        final var vec4 = new Vector2d(0, 1);
        final var vec5 = new Vector2d(1, 0);

        Assertions.assertEquals(vec4.upperRight(vec5), new Vector2d(1, 1));

        Assertions.assertEquals(vec1.upperRight(vec2), vec2);
        Assertions.assertEquals(vec1.upperRight(vec3), vec3);
        Assertions.assertEquals(vec2.upperRight(vec3), vec3);

        Assertions.assertEquals(vec1.upperRight(vec2), vec2.upperRight(vec1));
    }

    @Test
    public void lowerLeftTest() {
        final var vec4 = new Vector2d(0, 1);
        final var vec5 = new Vector2d(1, 0);

        Assertions.assertEquals(vec4.lowerLeft(vec5), new Vector2d(0, 0));

        Assertions.assertEquals(vec1.lowerLeft(vec2), vec1);
        Assertions.assertEquals(vec1.lowerLeft(vec3), vec1);
        Assertions.assertEquals(vec2.lowerLeft(vec3), vec2);

        Assertions.assertEquals(vec1.lowerLeft(vec2), vec2.lowerLeft(vec1));
    }

    @Test
    public void oppositeTest() {
        Assertions.assertEquals(vec1.opposite(), new Vector2d(-1, -1));
        Assertions.assertEquals(vec2.opposite(), new Vector2d(-2, -2));
        Assertions.assertEquals(vec3.opposite(), new Vector2d(-2, -3));
    }

    @Test
    public void equalsTest() {
        // basic verification
        Assertions.assertTrue(vec1.equals(new Vector2d(1, 1)));
        Assertions.assertTrue(vec2.equals(new Vector2d(2, 2)));
        Assertions.assertTrue(vec3.equals(new Vector2d(2, 3)));

        // null
        Assertions.assertFalse(vec1.equals(null));
        Assertions.assertFalse(vec2.equals(null));
        Assertions.assertFalse(vec3.equals(null));

        // different types
        Assertions.assertFalse(vec1.equals("(1,1)"));
        Assertions.assertFalse(vec1.equals("(2,2)"));
        Assertions.assertFalse(vec1.equals("(1,3)"));
        Assertions.assertFalse(vec1.equals(1));
        Assertions.assertFalse(vec1.equals(1.0));
        Assertions.assertFalse(vec1.equals(1.0f));
        Assertions.assertFalse(vec1.equals(true));
    }
}