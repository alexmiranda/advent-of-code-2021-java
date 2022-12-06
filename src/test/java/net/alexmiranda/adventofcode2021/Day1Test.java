package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

public class Day1Test {
    private static final String example = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
            """;

    @Test
    public void testExamplePart1() throws IOException {
        try (var reader = new BufferedReader(new StringReader(example))) {
            assertEquals(7, Day1.part1(reader));
        }
    }

    @Test
    public void testInputFilePart1() throws URISyntaxException, IOException {
        assertEquals(1655, Day1.part1());
    }

    @Test
    public void testExamplePart2() throws IOException {
        try (var reader = new BufferedReader(new StringReader(example))) {
            assertEquals(5, Day1.part2(reader));
        }
    }

    @Test
    public void testInputFilePart2() throws URISyntaxException, IOException {
        assertEquals(1683, Day1.part2());
    }
}