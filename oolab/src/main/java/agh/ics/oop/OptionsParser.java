package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parseStringArray (String[] options) {
        MoveDirection[] directions = new MoveDirection[options.length];
        int directionsIdx = 0;

        // interpretacja opcji
        for (var option : options) {
            switch (option) {
                case "f" -> {
                    directions[directionsIdx] = MoveDirection.FORWARD;
                }
                case "b" -> {
                    directions[directionsIdx] = MoveDirection.BACKWARD;
                }
                case "l" -> {
                    directions[directionsIdx] = MoveDirection.LEFT;
                }
                case "r" -> {
                    directions[directionsIdx] = MoveDirection.RIGHT;
                }
                default -> {
                    directionsIdx -= 1;
                }
            }
            directionsIdx += 1;
        }

        return Arrays.copyOfRange(directions, 0, directionsIdx);
    }
}
