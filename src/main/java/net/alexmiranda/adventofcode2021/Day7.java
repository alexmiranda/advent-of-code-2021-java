package net.alexmiranda.adventofcode2021;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day7 {
    private static final String INPUT = "2021/Day/7/input";

    static int[] crabs(String s) {
        return Stream.of(s.split(",")).mapToInt(Integer::valueOf).toArray();
    }

    static int calculateFuelCost(int[] crabs, int alignPosition) {
        return calculateFuelCost(crabs, alignPosition, Integer.MAX_VALUE);
    }

    static int calculateFuelCost(int[] crabs, int alignPosition, int min) {
        var totalCost = 0;
        for (int i = 0; i < crabs.length; i++) {
            totalCost += Math.abs(crabs[i] - alignPosition);
            if (totalCost > min) {
                return Integer.MAX_VALUE;
            }
        }
        return totalCost;
    }

    static int findBestAlignment(int[] crabs) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < crabs.length; i++) {
            var cost = calculateFuelCost(crabs, i, min);
            if (cost < min) {
                min = cost;
            }
        }
        return min;
    }

    static String readInputFile() throws URISyntaxException, IOException {
        var path = Path.of(ClassLoader.getSystemResource(INPUT).toURI());
        var s = Files.readString(path);
        return s.stripTrailing();
    }
}
