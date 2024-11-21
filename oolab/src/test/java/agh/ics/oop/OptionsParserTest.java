package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class OptionsParserTest {
    @Test
    void parseValidArguments() {
        // given
        final String[] validOptions = {"f", "b", "l", "r"};

        final List<MoveDirection> expected = Arrays.asList(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT
        );

        // when
        final var result = OptionsParser.parseStringArray(validOptions);

        // then
        Assertions.assertEquals(result, expected);
    }

    @Test
    void parseInvalidArguments() {
        final String[] invalidOptions = {"f", "b", "l", "r", "invalid", "options", "l"};

        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> OptionsParser.parseStringArray(invalidOptions));
    }
}
