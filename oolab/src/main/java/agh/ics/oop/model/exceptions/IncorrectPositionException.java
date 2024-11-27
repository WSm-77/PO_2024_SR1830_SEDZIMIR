package agh.ics.oop.model.exceptions;

import agh.ics.oop.model.Vector2d;

public class IncorrectPositionException extends Exception {
    public static final String EXCEPTION_MESSAGE_TEMPLATE = "Position %s is not correct";

    private static String createExceptionMessage(Vector2d incorrectPosition) {
        return String.format(IncorrectPositionException.EXCEPTION_MESSAGE_TEMPLATE, incorrectPosition.toString());
    }

    public IncorrectPositionException(Vector2d position) {
        super(IncorrectPositionException.createExceptionMessage(position));
    }
}
