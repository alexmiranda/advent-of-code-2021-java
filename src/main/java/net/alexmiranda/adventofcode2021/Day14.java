package net.alexmiranda.adventofcode2021;

import java.io.Reader;
import java.util.LinkedList;
import java.util.Scanner;

public class Day14 {
    static class Polymer {
        private final char[] rules = new char[26 * 26];
        private final int[] frequency = new int[26];
        private final LinkedList<Character> elems = new LinkedList<>();

        public Polymer(Reader reader) {
            try (var scanner = new Scanner(reader)) {
                scanner.useDelimiter("");

                // read the polymer template
                char c = scanner.next().charAt(0);
                do {
                    elems.offer(c);
                    frequency[c - 'A']++;
                    c = scanner.next().charAt(0);
                } while (c != '\n');

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

            var iter = this.elems.listIterator();
            var prev = iter.next();
            while (iter.hasNext()) {
                var next = iter.next();
                var match = match(prev, next);
                if (match != '\0') {
                    iter.previous();
                    iter.add(match);
                    frequency[match - 'A']++;
                    iter.next();
                }
                prev = next;
            }

            polymerise(repeat - 1);
        }

        public int computeFrequencyQuotients() {
            int min = frequency[0], max = frequency[0];
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
