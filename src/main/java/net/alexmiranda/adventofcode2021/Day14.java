package net.alexmiranda.adventofcode2021;

import java.io.Reader;
import java.util.HashMap;
import java.util.Scanner;

public class Day14 {
    static class Polymer {
        private final char[] rules = new char[26 * 26];
        private final long[] frequency = new long[26];
        private final HashMap<String, Long> pairs = new HashMap<>((int) Math.ceil((26 * 26) * 0.75f), 0.75f);

        public Polymer(Reader reader) {
            try (var scanner = new Scanner(reader)) {
                scanner.useDelimiter("");

                // read the polymer template
                char prev = scanner.next().charAt(0);
                frequency[prev - 'A']++;

                char curr = scanner.next().charAt(0);
                do {
                    frequency[curr - 'A']++;
                    var s = "" + prev + curr;
                    pairs.compute(s, (k, v) -> v == null ? 1 : v + 1);
                    prev = curr;
                    curr = scanner.next().charAt(0);
                } while (curr != '\n');

                // read the rules
                scanner.useDelimiter("\\s");
                scanner.nextLine(); // skip empty line

                while (scanner.hasNext()) {
                    var pair = scanner.next();
                    scanner.next(); // skip ->
                    var insertion = scanner.next().charAt(0);
                    var left = pair.charAt(0);
                    var right = pair.charAt(1);
                    var pos = find(left, right);
                    rules[pos] = insertion;
                }
            }
        }

        public void polymerise(int repeat) {
            if (repeat == 0) return;

            var newPairs = new HashMap<String, Long>(Math.min(pairs.size() * 2, 26 * 26));
            for (var entry : pairs.entrySet()) {
                var key = entry.getKey();
                var value = entry.getValue();
                if (value.equals(0L)) {
                    continue;
                }

                var left = key.charAt(0);
                var right = key.charAt(1);
                var match = match(left, right);
                if (match != '\0') {
                    frequency[match - 'A'] = Math.addExact(frequency[match - 'A'], value);
                    newPairs.compute("" + left + match, (k, v) -> Math.addExact(v == null ? 0 : v, value));
                    newPairs.compute("" + match + right, (k, v) -> Math.addExact(v == null ? 0 : v, value));
                }
            }

            pairs.clear();
            pairs.putAll(newPairs);

            polymerise(repeat - 1);
        }

        public long computeFrequencyQuotients() {
            long min = frequency[0], max = frequency[0];
            for (int i = 1; i < 26; i++) {
                var val = frequency[i];
                if (val > 0) {
                    min = min == 0 ? val : Math.min(min, val);
                    max = Math.max(max, val);
                }
            }
            return max - min;
        }

        private char match(char left, char right) {
            var pos = find(left, right);
            return this.rules[pos];
        }

        private int find(char left, char right) {
            return ((left - 'A') * 26) + (right - 'A');
        }
    }
}
