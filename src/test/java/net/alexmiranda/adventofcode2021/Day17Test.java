package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {
    private static final String INPUT = "/2021/Day/17/input";
    private static final String EXAMPLE = "target area: x=20..30, y=-10..-5";

    @Test
    public void testExamplePart1() {
        var target = Day17.Target.from(EXAMPLE);
        assertEquals(45, Day17.calculateTrajectory(target));
    }

    @Test
    public void testPuzzleInputPart1() throws Exception {
        try (var is = Objects.requireNonNull(Day17Test.class.getResourceAsStream(INPUT))) {
            var target = Day17.Target.from(new String(is.readAllBytes(), StandardCharsets.UTF_8).stripTrailing());
            assertEquals(3916, Day17.calculateTrajectory(target));
        }
    }
}
