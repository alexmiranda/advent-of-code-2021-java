package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class Day2Test {
    private static final String example = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
            """;

    @Test
    public void testExamplePart1() throws IOException {
        var reader = new StringReader(example);
        var result = Day2.part1(reader);
        assertEquals(150, result);
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        var result = Day2.part1();
        assertEquals(1690020, result);
    }

    @Test
    public void testExamplePart2() throws IOException {
        var reader = new StringReader(example);
        var result = Day2.part2(reader);
        assertEquals(900, result);
    }

    @Test
    public void testPuzzleInputPart2() throws IOException {
        var result = Day2.part2();
        assertEquals(1408487760, result);
    }
}