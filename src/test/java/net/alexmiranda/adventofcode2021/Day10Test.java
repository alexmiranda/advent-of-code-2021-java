package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {
    private static final String INPUT = "/2021/Day/10/input";
    private static final String EXAMPLE = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
            """;

    @Test
    public void testExamplePart1() {
        try (var reader = new StringReader(EXAMPLE)) {
            assertEquals(26397, Day10.calculateSyntaxErrorScore(reader));
        }
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day9Test.class.getResourceAsStream(INPUT)))) {
            assertEquals(366027, Day10.calculateSyntaxErrorScore(reader));
        }
    }
}
