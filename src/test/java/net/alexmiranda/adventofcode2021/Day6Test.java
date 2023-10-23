package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Day6Test {
    private static final String INPUT = "/2021/Day/6/input";

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
    public void testInputPart1() throws IOException {
        assertEquals(395627, Day6.part1(readInputFile(), 80));
    }

    @Test
    public void testInputPart2() throws IOException {
        assertEquals(1767323539209L, Day6.part1(readInputFile(), 256));
    }

    private static String readInputFile() throws IOException {
        try (var is = Day6Test.class.getResourceAsStream(INPUT)) {
            var bytes = Objects.requireNonNull(is).readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8).stripTrailing();
        }
    }
}
