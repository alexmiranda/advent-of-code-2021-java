package net.alexmiranda.adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 {
    private static final String INPUT = "/2021/Day/5/input";

    record Coordinate(int x, int y) {
        static Coordinate fromString(String s) {
            var components = s.split(",");
            assert components.length == 2;

            int x = Integer.valueOf(components[0]);
            int y = Integer.valueOf(components[1]);
            return new Coordinate(x, y);
        }
    };

    static class VentLine {
        private final Coordinate start, end;
        private Coordinate[] coordinates;

        static VentLine fromString(String s) {
            var parts = s.split(" -> ");
            assert parts.length == 2 : "invalid line: " + s;

            var start = Coordinate.fromString(parts[0]);
            var end = Coordinate.fromString(parts[1]);
            return new VentLine(start, end);
        }

        VentLine(Coordinate start, Coordinate end) {
            this.start = start;
            this.end = end;
        }

        Coordinate[] coordinates() {
            if (this.coordinates != null) {
                return this.coordinates;
            }
            if (this.start.equals(this.end)) {
                return singleCoordinate();
            }
            if (start.x == end.x) {
                return horizontalCoordinates();
            }
            if (start.y == end.y) {
                return verticalCoordinates();
            }
            return diagonalCoordinates();
        }

        private Coordinate[] singleCoordinate() {
            this.coordinates = new Coordinate[] { start };
            return this.coordinates;
        }

        private Coordinate[] verticalCoordinates() {
            int from = Math.min(start.x, end.x);
            int to = Math.max(start.x, end.x);
            this.coordinates = IntStream.rangeClosed(from, to)
                    .mapToObj(x -> new Coordinate(x, start.y))
                    .toArray(s -> new Coordinate[s]);
            return this.coordinates;
        }

        private Coordinate[] horizontalCoordinates() {
            int from = Math.min(start.y, end.y);
            int to = Math.max(start.y, end.y);
            this.coordinates = IntStream.rangeClosed(from, to)
                    .mapToObj(y -> new Coordinate(start.x, y))
                    .toArray(s -> new Coordinate[s]);
            return this.coordinates;
        }

        private Coordinate[] diagonalCoordinates() {
            var xstep = start.x < end.x ? 1 : -1;
            var ystep = start.y < end.y ? 1 : -1;
            this.coordinates = Stream.iterate(start, c -> !c.equals(end), prev -> {
                return new Coordinate(prev.x + xstep, prev.y + ystep);
            }).toArray(s -> new Coordinate[s + 1]);
            this.coordinates[this.coordinates.length - 1] = end;
            return this.coordinates;
        }

        boolean isDiagonal() {
            return start.x != end.x && start.y != end.y;
        }

        Coordinate start() {
            return this.start;
        }

        Coordinate end() {
            return this.end;
        }
    }

    public static List<VentLine> readInputFile() throws IOException {
        var reader = new InputStreamReader(Day5.class.getResourceAsStream(INPUT));
        return read(reader);
    }

    public static List<VentLine> read(Reader reader) throws IOException {
        var result = new ArrayList<VentLine>(500);
        try (var br = new BufferedReader(reader)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                result.add(VentLine.fromString(line));
            }
            return result;
        }
    }

    public static int countOverlappingLines(List<VentLine> lines) {
        int height = 0, width = 0;
        for (var line : lines) {
            height = Math.max(Math.max(height, line.start.x), line.end.x);
            width = Math.max(Math.max(width, line.start.y), line.end.y);
        }
        var grid = new int[height + 1][width + 1];
        int count = 0;
        for (var line : lines) {
            count += addToGrid(grid, line);
        }
        return count;
    }

    private static int addToGrid(int[][] grid, VentLine line) {
        var count = 0;
        for (var c : line.coordinates()) {
            if (++grid[c.x][c.y] == 2) {
                count++;
            }
        }
        return count;
    }
}
