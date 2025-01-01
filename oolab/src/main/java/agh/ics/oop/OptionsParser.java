package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsParser {
    public static final String ILLEGAL_MOVE_MESSAGE_TEMPLATE = "%s is not legal move specification";

    private static String createIllegalMoveMessage(String illegalOption) {
        return String.format(OptionsParser.ILLEGAL_MOVE_MESSAGE_TEMPLATE, illegalOption);
    }

    public static List<MoveDirection> parseStringArray (String[] options) {
        return Stream.of(options)
                .map(option -> switch (option) {
                    case "f", "forward" -> MoveDirection.FORWARD;
                    case "b", "backward" -> MoveDirection.BACKWARD;
                    case "l", "left" -> MoveDirection.LEFT;
                    case "r", "right" -> MoveDirection.RIGHT;
                    default -> throw new IllegalArgumentException(OptionsParser.createIllegalMoveMessage(option));
                })
                .collect(Collectors.toList());
    }
}
