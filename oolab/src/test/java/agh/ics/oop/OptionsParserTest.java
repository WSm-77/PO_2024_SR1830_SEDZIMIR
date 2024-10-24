package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    @Test
    void parseStringArray() {
        // given
        final String[] options1 = {"f", "b", "l", "r"};
        final String[] options2 = {"f", "b", "l", "r", "invalid", "options", "l"};

        final MoveDirection[] expected1 = {MoveDirection.FORWARD,
                                           MoveDirection.BACKWARD,
                                           MoveDirection.LEFT,
                                           MoveDirection.RIGHT};

        final MoveDirection[] expected2 = {MoveDirection.FORWARD,
                                           MoveDirection.BACKWARD,
                                           MoveDirection.LEFT,
                                           MoveDirection.RIGHT,
                                           MoveDirection.LEFT};

        // when
        final var result1 = OptionsParser.parseStringArray(options1);
        final var result2 = OptionsParser.parseStringArray(options2);

        // then
        Assertions.assertArrayEquals(result1, expected1);
        Assertions.assertArrayEquals(result2, expected2);
    }
}