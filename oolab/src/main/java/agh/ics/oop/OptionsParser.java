package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parseStringArray (String[] options) {
        ArrayList<MoveDirection> directions = new ArrayList<>();

        for (var option : options) {
            switch (option) {
                case "f" -> directions.add(MoveDirection.FORWARD);
                case "b" -> directions.add(MoveDirection.BACKWARD);
                case "l" -> directions.add(MoveDirection.LEFT);
                case "r" -> directions.add(MoveDirection.RIGHT);
                default -> {}
            }
        }

        return directions;
    }
}
