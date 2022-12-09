package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day5Test {
    private static final String example = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
            """;

    @Test
    public void testExamplePart1() throws IOException {
        var reader = new StringReader(example);
        var lines = Day5.read(reader);
        lines.removeIf(l -> l.isDiagonal());
        assertEquals(5, Day5.countOverlappingLines(lines));
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        var lines = Day5.readInputFile();
        lines.removeIf(l -> l.isDiagonal());
        assertEquals(6189, Day5.countOverlappingLines(lines));
    }

    @Test
    public void testExamplePart2() throws IOException {
        var reader = new StringReader(example);
        var lines = Day5.read(reader);
        assertEquals(12, Day5.countOverlappingLines(lines));
    }

    @Test
    public void testPuzzleInputPart2() throws IOException {
        var lines = Day5.readInputFile();
        assertEquals(19164, Day5.countOverlappingLines(lines));
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            '0,9', 0, 9
            '5,9', 5, 9
            '682,519', 682, 519
            """)
    public void testCoordinateFromString(String s, int x, int y) {
        var coord = Day5.Coordinate.fromString(s);
        assertEquals(x, coord.x());
        assertEquals(y, coord.y());
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            '0,9 -> 5,9', 0, 9, 5, 9
            '682,519 -> 682,729', 682, 519, 682, 729
            """)
    public void testVentLineFromString(String s, int x1, int y1, int x2, int y2) {
        var ventLine = Day5.VentLine.fromString(s);
        assertEquals(x1, ventLine.start().x());
        assertEquals(y1, ventLine.start().y());
        assertEquals(x2, ventLine.end().x());
        assertEquals(y2, ventLine.end().y());
    }

    @Test
    public void testVentLineCoordinates() {
        var ventLine = Day5.VentLine.fromString("0,9 -> 5,9");
        var coords = Arrays.asList(ventLine.coordinates());
        assertTrue(coords.contains(new Day5.Coordinate(0, 9)));
        assertTrue(coords.contains(new Day5.Coordinate(1, 9)));
        assertTrue(coords.contains(new Day5.Coordinate(2, 9)));
        assertTrue(coords.contains(new Day5.Coordinate(3, 9)));
        assertTrue(coords.contains(new Day5.Coordinate(4, 9)));
        assertTrue(coords.contains(new Day5.Coordinate(5, 9)));
        assertEquals(6, coords.size());

        ventLine = Day5.VentLine.fromString("2,2 -> 2,1");
        coords = Arrays.asList(ventLine.coordinates());
        assertTrue(coords.contains(new Day5.Coordinate(2, 2)));
        assertTrue(coords.contains(new Day5.Coordinate(2, 1)));
        assertEquals(2, coords.size());
    }
}
