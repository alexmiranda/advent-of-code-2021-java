package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {
    private static final String INPUT = "/2021/Day/13/input";
    private static final String EXAMPLE = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
                        
            fold along y=7
            fold along x=5
            """;

    private static final String EXAMPLE_EXPECTED_RESULT = """
            #####
            #...#
            #...#
            #...#
            #####
            .....
            .....
            """;

    private static final String PUZZLE_INPUT_EXPECTED_RESULT = """
            ....#.#..#..###.####..###.##....###..##.
            ....#.#..#.#..#....#.#..#.#....#..#.#..#
            ....#.#..#.#..#..###..###.#....#..#....#
            ....#.#..#..###....#.#..#.#.....###....#
            ....#.#..#..#.#....#.#..#.#..#....#.#..#
            .####..##..#..#.####..###..##.....#..##.
            """;

    @Test
    public void testExamplePart1() {
        var reader = new StringReader(EXAMPLE);
        var origami = new Day13.TransparentOrigami(reader);
        origami.fold();
        assertEquals(17, origami.countDots());
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day12.class.getResourceAsStream(INPUT)))) {
            var origami = new Day13.TransparentOrigami(reader);
            origami.fold();
            assertEquals(724, origami.countDots());
        }
    }

    @Test
    public void testExamplePart2() throws IOException {
        var reader = new StringReader(EXAMPLE);
        var origami = new Day13.TransparentOrigami(reader);
        while (origami.fold());
        var height = origami.coords.lastKey();
        var cap = height * (origami.width + 2);
        var sw = new StringWriter(cap);
        origami.print(sw);
        assertEquals(EXAMPLE_EXPECTED_RESULT, sw.toString());
    }

    @Test
    public void testPuzzleInputPart2() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day12.class.getResourceAsStream(INPUT)))) {
            var origami = new Day13.TransparentOrigami(reader);
            while (origami.fold()) ;
            var height = origami.coords.lastKey();
            var cap = height * (origami.width + 2);
            var sw = new StringWriter(cap);
            origami.print(sw);
            assertEquals(PUZZLE_INPUT_EXPECTED_RESULT, sw.toString());
        }
    }
}
