package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {
    private static final String INPUT = "/2021/Day/11/input";

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

    private static final String EXAMPLE_AFTER_1_STEP = """
            6594254334
            3856965822
            6375667284
            7252447257
            7468496589
            5278635756
            3287952832
            7993992245
            5957959665
            6394862637
            """;

    private static final String EXAMPLE_AFTER_2_STEPS = """
            8807476555
            5089087054
            8597889608
            8485769600
            8700908800
            6600088989
            6800005943
            0000007456
            9000000876
            8700006848
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
    public void testExampleStepsPart1() {
        var octopodes = Day11.readInput(new StringReader(EXAMPLE));
        var after1 = Day11.readInput(new StringReader(EXAMPLE_AFTER_1_STEP));
        var after2 = Day11.readInput(new StringReader(EXAMPLE_AFTER_2_STEPS));

        assertEquals(0, Day11.simulate(octopodes, 1));
        assertArrayEquals(after1, octopodes);

        assertEquals(35, Day11.simulate(octopodes, 1));
        assertArrayEquals(after2, octopodes);
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
