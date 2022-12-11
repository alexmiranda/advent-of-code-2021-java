package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day6Test {
    private static final String input = "1,1,1,2,1,1,2,1,1,1,5,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,4,1,1,1,1,3,1,1,3,1,1,1,4,1,5,1,3,1,1,1,1,1,5,1,1,1,1,1,5,5,2,5,1,1,2,1,1,1,1,3,4,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,5,4,1,1,1,1,1,5,1,2,4,1,1,1,1,1,3,3,2,1,1,4,1,1,5,5,1,1,1,1,1,2,5,1,4,1,1,1,1,1,1,2,1,1,5,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,3,1,1,3,1,3,1,4,1,5,4,1,1,2,1,1,5,1,1,1,1,1,5,1,1,1,1,1,1,1,1,1,4,1,1,4,1,1,1,1,1,1,1,5,4,1,2,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,4,1,1,1,2,1,4,1,1,1,1,1,1,1,1,1,4,2,1,2,1,1,4,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,3,2,1,4,1,5,1,1,1,4,5,1,1,1,1,1,1,5,1,1,5,1,2,1,1,2,4,1,1,2,1,5,5,3";

    @Test
    public void testExamplePart1() {
        assertEquals(5, Day6.part1("3,4,3,1,2", 1));
        assertEquals(6, Day6.part1("3,4,3,1,2", 2));
        assertEquals(7, Day6.part1("3,4,3,1,2", 3));
        assertEquals(26, Day6.part1("3,4,3,1,2", 18));
        assertEquals(5934, Day6.part1("3,4,3,1,2", 80));
        assertEquals(26984457539L, Day6.part1("3,4,3,1,2", 256));
    }

    @Test
    public void testInputPart1() {
        assertEquals(395627, Day6.part1(input, 80));
    }

    @Test
    public void testInputPart2() {
        assertEquals(1767323539209L, Day6.part1(input, 256));
    }

}
