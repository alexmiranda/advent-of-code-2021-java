package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {
    private static final String INPUT = "/2021/Day/11/input";
    private static final String TEST_SCRIPT = "/2021/Day/11/testscript.txt";

    private static final String EXAMPLE = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
            """;

    @Test
    public void testExamplePart1() {
        var octopodes = Day11.readInput(new StringReader(EXAMPLE));
        assertEquals(1656, Day11.simulate(octopodes, 100));
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day11Test.class.getResourceAsStream(INPUT)))) {
            var octopodes = Day11.readInput(reader);
            assertEquals(1667, Day11.simulate(octopodes, 100));
        }
    }

    @Test
    public void testExamplePart2() {
        var octopodes = Day11.readInput(new StringReader(EXAMPLE));
        assertEquals(195, Day11.simulate(octopodes));
    }

    @Test
    public void testPuzzleInputPart2() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day11Test.class.getResourceAsStream(INPUT)))) {
            var octopodes = Day11.readInput(reader);
            assertEquals(488, Day11.simulate(octopodes));
        }
    }

    @Test
    public void testExampleStepsPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day11Test.class.getResourceAsStream(TEST_SCRIPT)));
             var scanner = new Scanner(reader)) {
            scanner.useDelimiter("");
            scanner.nextLine();

            int step, prevStep = 0;
            var octopodes = Day11.readInput(scanner);
            while (scanner.hasNext()) {
                scanner.nextLine(); // discard empty line
                var line = scanner.nextLine(); // After step N...
                assert line.startsWith("After");
                step = Integer.parseInt(line.substring(line.lastIndexOf(' ') + 1, line.length() - 1));
                var expected = Day11.readInput(scanner);
                Day11.simulate(octopodes, step - prevStep);
                assertArrayEquals(expected, octopodes);
                prevStep = step;
            }
        }
    }

    @Test
    public void testAdjacent() {
        // corners
        assertEquals(3, Day11.adjacent(0).size());
        assertEquals(3, Day11.adjacent(9).size());
        assertEquals(3, Day11.adjacent(90).size());
        assertEquals(3, Day11.adjacent(99).size());

        // top and bottom edges
        assertEquals(5, Day11.adjacent(1).size());
        assertEquals(5, Day11.adjacent(8).size());
        assertEquals(5, Day11.adjacent(91).size());
        assertEquals(5, Day11.adjacent(98).size());

        // left and right edges
        assertEquals(5, Day11.adjacent(10).size());
        assertEquals(5, Day11.adjacent(19).size());
        assertEquals(5, Day11.adjacent(80).size());
        assertEquals(5, Day11.adjacent(89).size());

        // somewhere in the middle
        assertEquals(8, Day11.adjacent(45).size());
    }
}
