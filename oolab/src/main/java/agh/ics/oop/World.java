package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {
    private final static String petName = "WSm";

    public static void main(String[] args) {
        // start
        System.out.println("system wystartował");

        MoveDirection[] directions = OptionsParser.parseStringArray(args);
        World.run(directions);

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
