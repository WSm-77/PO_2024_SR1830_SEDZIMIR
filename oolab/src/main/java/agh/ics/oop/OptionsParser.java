package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static final String ILLEGAL_MOVE_SUBMESSAGE = "is not legal move specification";

    public static List<MoveDirection> parseStringArray (String[] options) {
        ArrayList<MoveDirection> directions = new ArrayList<>();

        for (var option : options) {
            switch (option) {
                case "f", "forward" -> directions.add(MoveDirection.FORWARD);
                case "b", "backward" -> directions.add(MoveDirection.BACKWARD);
                case "l", "left" -> directions.add(MoveDirection.LEFT);
                case "r", "right" -> directions.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException(String.format("%s %s", option, OptionsParser.ILLEGAL_MOVE_SUBMESSAGE));
            }
        }

        return directions;
    }
}
