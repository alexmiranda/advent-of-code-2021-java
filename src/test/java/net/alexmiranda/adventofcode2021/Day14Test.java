package net.alexmiranda.adventofcode2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {
    private static final String INPUT = "/2021/Day/14/input";

    private static final String EXAMPLE = """
            NNCB
                        
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
            """;

    @Test
    public void testExamplePart1() {
        var reader = new StringReader(EXAMPLE);
        var polymer = new Day14.Polymer(reader);
        polymer.polymerise(10);
        assertEquals(1588, polymer.computeFrequencyQuotients());
    }

    @Test
    public void testPuzzleInputPart1() throws IOException {
        try (var reader = new InputStreamReader(Objects.requireNonNull(Day12.class.getResourceAsStream(INPUT)))) {
            var polymer = new Day14.Polymer(reader);
            polymer.polymerise(10);
            assertEquals(3342, polymer.computeFrequencyQuotients());
        }
    }
}
