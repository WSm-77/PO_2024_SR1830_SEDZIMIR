package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class OptionsParserTest {
    @Test
    void parseStringArray() {
        // given
        final String[] options1 = {"f", "b", "l", "r"};
        final String[] options2 = {"f", "b", "l", "r", "invalid", "options", "l"};

        final List<MoveDirection> expected1 = Arrays.asList(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT
        );

        final List<MoveDirection> expected2 = Arrays.asList(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.LEFT
        );

        // when
        final var result1 = OptionsParser.parseStringArray(options1);
        final var result2 = OptionsParser.parseStringArray(options2);

        // then
        Assertions.assertEquals(result1, expected1);
        Assertions.assertEquals(result2, expected2);
    }
}