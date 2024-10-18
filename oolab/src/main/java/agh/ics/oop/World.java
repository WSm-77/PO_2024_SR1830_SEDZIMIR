package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

public class World {
    private final static String petName = "WSm";

    public static void main(String[] args) {
        // start
        System.out.println("system wystartował");

        MoveDirection[] directions = OptionsParser.parseStringArray(args);
        World.run(directions);

        // Vector2d verification
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        // MapDirections verification
        MapDirection direction = MapDirection.NORTH;
        System.out.println(direction.toString());
        System.out.println(direction.next().toString());
        System.out.println(direction.previous().toString());
        System.out.println(direction.toUnitVector().toString());

        // stop
        System.out.println("system zakończył działanie");
    }

    public static void run(MoveDirection[] directions) {
        // interpretacja argumentów
        for (var direction : directions) {
            String directionMessage = switch (direction) {
                case MoveDirection.FORWARD -> "idzie do przodu";
                case MoveDirection.BACKWARD -> "idzie do tyłu";
                case MoveDirection.LEFT -> "skręca w lewo";
                case MoveDirection.RIGHT -> "skręca w prawo";
                };

            System.out.println(World.petName + " " + directionMessage);
        }
    }
}
