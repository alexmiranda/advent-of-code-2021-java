package net.alexmiranda.adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Day15 {
    record Location(int x, int y) {}
    record Search(Location loc, int risk) {}

    public static int[][] readInput(Reader reader) throws IOException {
        try (var br = new BufferedReader(reader)) {
            var input = new ArrayList<int[]>();
            String line;
            while ((line = br.readLine()) != null) {
                var row = new int[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    row[i] = Character.digit(line.charAt(i), 10);
                }
                input.add(row);
            }
            return input.toArray(int[][]::new);
        }
    }

    public static int lowestTotalRisk(int[][] input) {
        int h = input.length - 1;
        int w = input[0].length - 1;
        var start = new Location(0, 0);
        var goal = new Location(w, h);

        var seen = new HashSet<Location>();
        var queue = new PriorityQueue<>(Comparator.comparingInt(Search::risk));
        queue.add(new Search(start, 0));
        while (!queue.isEmpty()) {
            var s = queue.poll();

            // we have reached our goal...
            if (s.loc.equals(goal)) {
                return s.risk;
            }

            seen.add(s.loc);
            for (var neighbour : neighbours(s.loc, w, h, seen)) {
                int risk = s.risk + input[neighbour.y][neighbour.x];
                queue.add(new Search(neighbour, risk));
            }
        }

        throw new IllegalStateException("Destination is not reachable");
    }

    private static List<Location> neighbours(Location loc, int w, int h, Set<Location> seen) {
        var neighbours = new ArrayList<Location>(4);

        // right
        if (loc.x < w) {
            var target = new Location(loc.x + 1, loc.y);
            if (!seen.contains(target)) {
                neighbours.add(target);
            }
        }

        // bottom
        if (loc.y < h) {
            var target = new Location(loc.x, loc.y + 1);
            if (!seen.contains(target)) {
                neighbours.add(target);
            }
        }

        // left
        if (loc.x > 0) {
            var target = new Location(loc.x - 1, loc.y);
            if (!seen.contains(target)) {
                neighbours.add(target);
            }
        }

        // top
        if (loc.y > 0) {
            var target = new Location(loc.x, loc.y - 1);
            if (!seen.contains(target)) {
                neighbours.add(target);
            }
        }

        return neighbours;
    }
}
