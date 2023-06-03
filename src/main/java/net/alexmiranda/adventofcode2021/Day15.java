package net.alexmiranda.adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

public class Day15 {
    record Location(int x, int y) {}
    record Search(Location loc, int risk) {}

    public static byte[][] readInput(Reader reader) throws IOException {
        try (var br = new BufferedReader(reader)) {
            var input = new ArrayList<byte[]>();
            String line;
            while ((line = br.readLine()) != null) {
                var row = new byte[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    row[i] = (byte) Character.digit(line.charAt(i), 10);
                }
                input.add(row);
            }
            return input.toArray(byte[][]::new);
        }
    }

    public static byte[][] readFullMap(Reader reader) throws IOException {
        var input = readInput(reader);
        int w = input[0].length;
        int h = input.length;
        var map = new byte[h * 5][w * 5];

        for (int i = 0; i < h; i++) {
            System.arraycopy(input[i], 0, map[i], 0, w);
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                for (byte k = 1; k <= 4; k++) {
                    byte v = (byte) (1 + ((map[i][j] + k) - 1) % 9);
                    map[i][j + k * w] = v;
                    map[i + k * h][j] = v;
                    for (byte l = 1; l <= 4; l++) {
                        byte vv = (byte) (1 + ((v + l) - 1) % 9);
                        map[i + k * h][l * w + j] = vv;
                    }
                }
            }
        }

        return map;
    }

    public static int lowestTotalRisk(byte[][] input) {
        int h = input.length - 1;
        int w = input[0].length - 1;
        var start = new Location(0, 0);
        var goal = new Location(w, h);

        var queue = new PriorityQueue<>(h + w, Comparator.comparingInt(Search::risk));
        queue.add(new Search(start, 0));
        while (!queue.isEmpty()) {
            var s = queue.poll();

            // we have reached our goal...
            if (s.loc.equals(goal)) {
                return s.risk;
            }

            // have we visited this node before?
            if (input[s.loc.y][s.loc.x] > 10) {
                continue;
            }

            // Because we know the range of each cell is from 1 to 9, then we can mark the node as visited by adding 10
            input[s.loc.y][s.loc.x] += 10;

            // plan neighbours for visiting later
            for (var neighbour : neighbours(s.loc, w, h, input)) {
                int risk = s.risk + input[neighbour.y][neighbour.x] % 10;
                queue.add(new Search(neighbour, risk));
            }
        }

        throw new IllegalStateException("Destination is not reachable");
    }

    private static List<Location> neighbours(Location loc, int w, int h, byte[][] input) {
        var neighbours = new ArrayList<Location>(4);

        // right
        if (loc.x < w) {
            var target = new Location(loc.x + 1, loc.y);
            if (input[target.y][target.x] < 10) { // check if target has been visited
                neighbours.add(target);
            }
        }

        // bottom
        if (loc.y < h) {
            var target = new Location(loc.x, loc.y + 1);
            if (input[target.y][target.x] < 10) { // check if target has been visited
                neighbours.add(target);
            }
        }

        // left
        if (loc.x > 0) {
            var target = new Location(loc.x - 1, loc.y);
            if (input[target.y][target.x] < 10) { // check if target has been visited
                neighbours.add(target);
            }
        }

        // top
        if (loc.y > 0) {
            var target = new Location(loc.x, loc.y - 1);
            if (input[target.y][target.x] < 10) { // check if target has been visited
                neighbours.add(target);
            }
        }

        return neighbours;
    }

    static void print(Writer w, byte[][] map) throws IOException {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                w.write(Character.forDigit(map[i][j], 10));
            }
            w.write('\n');
        }
    }
}
