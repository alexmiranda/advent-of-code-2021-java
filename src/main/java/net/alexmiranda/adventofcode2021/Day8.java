package net.alexmiranda.adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

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

    /*-
     *    0:      1:      2:      3:      4:
     *    aaaa    ....    aaaa    aaaa    ....
     *   b    c  .    c  .    c  .    c  b    c
     *   b    c  .    c  .    c  .    c  b    c
     *    ....    ....    dddd    dddd    dddd
     *   e    f  .    f  e    .  .    f  .    f
     *   e    f  .    f  e    .  .    f  .    f
     *    gggg    ....    gggg    gggg    ....
     *   
     *     5:      6:      7:      8:      9:
     *    aaaa    aaaa    aaaa    aaaa    aaaa
     *   b    .  b    .  .    c  b    c  b    c
     *   b    .  b    .  .    c  b    c  b    c
     *    dddd    dddd    ....    dddd    dddd
     *   .    f  e    f  .    f  e    f  .    f
     *   .    f  e    f  .    f  e    f  .    f
     *    gggg    gggg    ....    gggg    gggg
    */
    static int sumOutputValues(Reader reader) throws IOException {
        try (var br = new BufferedReader(reader)) {
            int sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += decodeOutput(line);
            }
            return sum;
        }
    }

    static int sumOutputValues() throws IOException {
        try (var reader = new InputStreamReader(Day8.class.getResourceAsStream(INPUT))) {
            return sumOutputValues(reader);
        }
    }

    static int decodeOutput(String line) {
        var parts = line.split(" \\| ");
        var input = parts[0].split(" ");
        var output = parts[1].split(" ");
        var one = findSingle(input, 1);
        var four = findSingle(input, 4);
        var seven = findSingle(input, 7);
        var eight = findSingle(input, 8);

        var zeroSixOrNine = find(input, 6).toArray(sz -> new String[sz]);
        var twoThreeOrFive = find(input, 5).toArray(sz -> new String[sz]);

        var nine = Stream.of(zeroSixOrNine)
                .filter(s -> containsAll(s, four, seven))
                .findFirst().get();

        var zeroOrSix = Stream.of(zeroSixOrNine)
                .filter(s -> !s.equals(nine))
                .toArray(sz -> new String[sz]);

        var zero = Stream.of(zeroOrSix)
                .filter(s -> containsAll(s, seven))
                .findFirst().get();

        var six = Stream.of(zeroOrSix)
                .filter(s -> !s.equals(zero))
                .findFirst().get();

        var five = Stream.of(twoThreeOrFive)
                .filter(s -> containsAll(six, s))
                .findFirst().get();

        var twoOrThree = Stream.of(twoThreeOrFive)
                .filter(s -> !s.equals(five))
                .toArray(sz -> new String[sz]);

        var three = Stream.of(twoOrThree)
                .filter(s -> containsAll(s, seven))
                .findFirst().get();

        var two = Stream.of(twoOrThree)
                .filter(s -> !s.equals(three))
                .findFirst().get();

        int factor = 1000;
        int num = 0;
        for (var s : output) {
            if (matches(s, zero)) {
                // pass
            } else if (matches(s, one)) {
                num += factor;
            } else if (matches(s, two)) {
                num += 2 * factor;
            } else if (matches(s, three)) {
                num += 3 * factor;
            } else if (matches(s, four)) {
                num += 4 * factor;
            } else if (matches(s, five)) {
                num += 5 * factor;
            } else if (matches(s, six)) {
                num += 6 * factor;
            } else if (matches(s, seven)) {
                num += 7 * factor;
            } else if (matches(s, eight)) {
                num += 8 * factor;
            } else if (matches(s, nine)) {
                num += 9 * factor;
            }
            factor /= 10;
        }
        return num;
    }

    static boolean matches(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        var c1 = s1.toCharArray();
        Arrays.sort(c1);
        s1 = new String(c1);
        var c2 = s2.toCharArray();
        Arrays.sort(c2);
        s2 = new String(c2);
        return s1.equals(s2);
    }

    static boolean containsChar(String s, char c) {
        return s.contains(String.valueOf(c));
    }

    static boolean containsAll(String s, String... ss) {
        for (var x : ss) {
            for (var c : x.toCharArray()) {
                if (!containsChar(s, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    static String findSingle(String[] input, int uniqueDigit) {
        return Stream.of(input)
                .filter(s -> s.length() == digitSegments.get(uniqueDigit))
                .findFirst().get();
    }

    static Stream<String> find(String[] input, int length) {
        return Stream.of(input)
                .filter(s -> s.length() == length);
    }
}
