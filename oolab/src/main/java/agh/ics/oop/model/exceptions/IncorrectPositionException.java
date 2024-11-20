package agh.ics.oop.model.exceptions;

import agh.ics.oop.model.Vector2d;

public class IncorrectPositionException extends Exception {
    public static final String EXCEPTION_SUBMESSAGE_1 = "Position";
    public static final String EXCEPTION_SUBMESSAGE_2 = "is not correct";

    public IncorrectPositionException(Vector2d position) {
        super(String.format("%s %s %s", IncorrectPositionException.EXCEPTION_SUBMESSAGE_1,
                position.toString(), IncorrectPositionException.EXCEPTION_SUBMESSAGE_2));
    }
}
