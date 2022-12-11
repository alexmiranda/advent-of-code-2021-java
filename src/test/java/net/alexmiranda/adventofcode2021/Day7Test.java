package net.alexmiranda.adventofcode2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day7Test {
    private static final String example = "16,1,2,0,4,2,7,1,2,14";
    
    @ParameterizedTest
    @CsvSource(textBlock = """
        2, 37
        1, 41
        3, 39
        10, 71
    """)
    public void testCalculateFuelCost(int alignPosition, int cost) {
        var crabs = Day7.crabs(example);
        assertEquals(cost, Day7.calculateFuelCost(crabs, alignPosition));
    }

    @Test
    public void testFindBestAlignment() {
        var crabs = Day7.crabs(example);
        assertEquals(37, Day7.findBestAlignment(crabs));
    }

    @Test
    public void testFindBestAlignmentInputFile() throws URISyntaxException, IOException {
        var s = Day7.readInputFile();
        var crabs = Day7.crabs(s);
        assertEquals(348996, Day7.findBestAlignment(crabs));
    }
}
