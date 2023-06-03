package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {
    private static final String INPUT = "/2021/Day/15/input";
    private static final String EXAMPLE = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
            """;

    @Test
    public void testExamplePart1() throws IOException {
        var reader = new StringReader(EXAMPLE);
        var input = Day15.readInput(reader);
        assertEquals(40, Day15.lowestTotalRisk(input));
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day15Test.class.getResourceAsStream(INPUT)))) {
            var input = Day15.readInput(reader);
            assertEquals(652, Day15.lowestTotalRisk(input));
        }
    }
}
