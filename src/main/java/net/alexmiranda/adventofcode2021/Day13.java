package net.alexmiranda.adventofcode2021;

import java.io.Reader;
import java.util.*;

public class Day13 {
    static class TransparentOrigami {
        sealed interface Fold permits FoldUp, FoldLeft {
            int position();
        }

        record FoldUp(int position) implements Fold {}
        record FoldLeft(int position) implements Fold {}

        private final ArrayDeque<Fold> instructions;
        private NavigableMap<Integer, NavigableSet<Integer>> coords;
        private int width;

        public TransparentOrigami(Reader reader) {
            this.coords = new TreeMap<>();
            this.instructions = new ArrayDeque<>();

            var scanner = new Scanner(reader);
            scanner.useDelimiter("");
            char prev = '\0';
            int x = 0, y = 0;
            boolean comma = false;
            int maxWidth = 0;

            loop:
            while (scanner.hasNext()) {
                char curr = scanner.next().charAt(0);
                switch (curr) {
                    case ',' -> comma = true;
                    case '\n' -> {
                        if (prev == '\n') {
                            break loop;
                        }
                        coords.computeIfAbsent(y, k -> new TreeSet<>()).add(x);
                        maxWidth = Math.max(maxWidth, x);
                        x = y = 0;
                        comma = false;
                    }
                    default -> {
                        assert Character.isDigit(curr) : "expected digit, but found: " + curr;
                        if (!comma) {
                            x *= 10;
                            x += Character.digit(curr, 10);
                        } else {
                            y *= 10;
                            y += Character.digit(curr, 10);
                        }
                    }
                }
                prev = curr;
            }

            scanner.useDelimiter("\\s");
            while (scanner.hasNext()) {
                scanner.next();
                scanner.next();

                var str = scanner.next().stripLeading();
                char axis = str.charAt(0);
                int val = Integer.parseInt(str.substring(2));
                switch (axis) {
                    case 'x' -> instructions.add(new FoldLeft(val));
                    case 'y' -> instructions.add(new FoldUp(val));
                    default -> throw new IllegalStateException("expected fold along x or y axis, but got " + axis);
                };
            }

            this.width = maxWidth;
        }

        public boolean fold() {
            var instruction = instructions.pollFirst();
            if (instruction instanceof FoldUp fold) {
                foldUp(fold.position());
            } else if (instruction instanceof FoldLeft fold) {
                foldLeft(fold.position());
            }
            return !instructions.isEmpty();
        }

        public int countDots() {
            int sum = 0;
            for (var row : coords.values()) {
                sum += row.size();
            }
            return sum;
        }

        private void foldUp(int y) {
            var result = new TreeMap<Integer, NavigableSet<Integer>>();
            int height = coords.lastKey();
            int newHeight = height - y - 1;

            // the upper half of the origami needs to be at most the same size of the bottom half, thus the reason why we need to "cut" it
            var beginUpperHalf = y - (height - y);
            var upperHalf = coords.subMap(beginUpperHalf, true, y, false);

            // the bottom part are all the rows below the folding line (non-inclusive)
            var bottomHalf = coords.tailMap(y, false);

            // first we copy all the dots from the bottom half and transpose them 180 degrees
            for (var k : bottomHalf.keySet()) {
                result.put(height - k, bottomHalf.get(k));
            }

            // then we copy all the dots from the upper half to their corresponding positions
            for (var k : upperHalf.keySet()) {
                result.computeIfAbsent(k - beginUpperHalf, _k -> new TreeSet<>()).addAll(upperHalf.get(k));
            }

            // because of empty lines, we need to make sure to have the index of the last line present
            result.putIfAbsent(newHeight, Collections.emptyNavigableSet());

            this.coords = result;
        }

        private void foldLeft(int x) {
            var result = new TreeMap<Integer, NavigableSet<Integer>>();
            int newWidth = this.width - x - 1;

            for (var entry : this.coords.entrySet()) {
                var lhs = entry.getValue().headSet(x, false);
                var rhs = entry.getValue().tailSet(x, false);
                var line = new TreeSet<Integer>();

                // first we transpose all the dots from the right hand side by shifting them left
                for (var pos : rhs) {
                    line.add(pos - x - 1);
                }

                // then we transpose all the dots from the left hand side by "mirroring"
                for (var pos : lhs) {
                    line.add(newWidth - pos);
                }

                result.put(entry.getKey(), line);
            }

            this.coords = result;
            this.width = newWidth;
        }
    }
}
