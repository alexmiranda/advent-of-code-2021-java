package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class Day3Test {
    private static final String example = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
            """;
    
    @Test
    public void testExamplePart1() throws IOException {
        var reader = new StringReader(example);
        assertEquals(198, Day3.part1(reader, 5));
    }
    
    @Test
    public void testPuzzleInputPart1() throws IOException {
        assertEquals(4138664, Day3.part1());
    }
    
    @Test
    public void testExamplePart2() throws IOException {
        var reader = new StringReader(example);
        assertEquals(230, Day3.part2(reader, 5));
    }
    
    @Test
    public void testPuzzleInputPart2() throws IOException {
        assertEquals(4273224, Day3.part2());
    }
}
