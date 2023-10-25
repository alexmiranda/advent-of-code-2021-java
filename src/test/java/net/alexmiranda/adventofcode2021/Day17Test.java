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
        var trajectory = Day17.calculateTrajectory(target);
        assertEquals(45, trajectory.highestY());
    }

    @Test
    public void testPuzzleInputPart1() throws Exception {
        try (var is = Objects.requireNonNull(Day17Test.class.getResourceAsStream(INPUT))) {
            var target = Day17.Target.from(new String(is.readAllBytes(), StandardCharsets.UTF_8).stripTrailing());
            var trajectory = Day17.calculateTrajectory(target);
            assertEquals(3916, trajectory.highestY());
        }
    }

    @Test
    public void testExamplePart2() {
        var target = Day17.Target.from(EXAMPLE);
        var trajectory = Day17.calculateTrajectory(target);
        assertEquals(112, trajectory.totalPossibleHits());
    }

    @Test
    public void testPuzzleInputPart2() throws Exception {
        try (var is = Objects.requireNonNull(Day17Test.class.getResourceAsStream(INPUT))) {
            var target = Day17.Target.from(new String(is.readAllBytes(), StandardCharsets.UTF_8).stripTrailing());
            var trajectory = Day17.calculateTrajectory(target);
            assertEquals(2986, trajectory.totalPossibleHits());
        }
    }
}
