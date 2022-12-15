package net.alexmiranda.adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class Day8 {
    private static final String INPUT = "/2021/Day/8/input";

    private static final Map<Integer, Integer> digitSegments = Map.of(
            0, 6,
            1, 2,
            2, 5,
            3, 5,
            4, 4,
            5, 5,
            6, 6,
            7, 3,
            8, 7,
            9, 6);

    static int countUniqueDigitsOccurrence(Reader reader) throws IOException {
        try (var br = new BufferedReader(reader)) {
            int counter = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                var parts = line.split(" \\| ");
                var output = parts[1].split(" ");
                for (var s : output) {
                    var l = s.length();
                    if (digitSegments.get(1).equals(l) ||
                            digitSegments.get(4).equals(l) ||
                            digitSegments.get(7).equals(l) ||
                            digitSegments.get(8).equals(l)) {
                        counter++;
                    }
                }
            }
            return counter;
        }
    }

    static int countUniqueDigitsOccurrence() throws IOException {
        try (var reader = new InputStreamReader(Day8.class.getResourceAsStream(INPUT))) {
            return countUniqueDigitsOccurrence(reader);
        }
    }
}
