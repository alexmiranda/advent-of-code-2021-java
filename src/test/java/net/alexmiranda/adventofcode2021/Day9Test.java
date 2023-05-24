package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {
    private static final String INPUT = "/2021/Day/9/input";
    private static final String EXAMPLE = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
            """;

    @Test
    public void testExamplePart1() {
        try (var reader = new StringReader(EXAMPLE)) {
            assertEquals(15, Day9.findLowPoints(reader));
        }
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day9Test.class.getResourceAsStream(INPUT)))) {
            assertEquals(572, Day9.findLowPoints(reader));
        }
    }
}
