package net.alexmiranda.adventofcode2021;

import java.io.*;
import java.util.*;

public class Day9 {
    record Location(int x, int y) {
    }

    public static int calculateRiskLevel(Reader reader) throws IOException {
        var heightmap = heightmap(reader);
        int ans = 0;
        for (var loc : findLowPoints(heightmap)) {
            ans += heightmap[loc.y][loc.x] + 1;
        }
        return ans;
    }

    public static int findLargestBasins(Reader reader) throws IOException {
        var heightmap = heightmap(reader);
        int[] highestBasins = new int[3];
        for (var loc : findLowPoints(heightmap)) {
            int size = calculateBasinSize(heightmap, loc);
            if (size >= highestBasins[0]) {
                highestBasins[2] = highestBasins[1];
                highestBasins[1] = highestBasins[0];
                highestBasins[0] = size;
            } else if (size >= highestBasins[1]) {
                highestBasins[2] = highestBasins[1];
                highestBasins[1] = size;
            } else if (size > highestBasins[2]) {
                highestBasins[2] = size;
            }
        }
        return highestBasins[0] * highestBasins[1] * highestBasins[2];
    }

    private static int calculateBasinSize(int[][] heightmap, Location loc) {
        int w = heightmap[0].length;
        int h = heightmap.length;
        var seen = new HashSet<Location>();
        seen.add(loc);
        var queue = new LinkedList<Location>();
        queue.add(loc);
        int basinSize = 0;
        while (!queue.isEmpty()) {
            var curr = queue.pollFirst();
            if (heightmap[curr.y][curr.x] < 9) {
                basinSize++;
                for (var adjacent : adjacentLocations(curr.x, curr.y, w, h)) {
                    if (seen.add(adjacent)) {
                        queue.add(adjacent);
                    }
                }
            }
        }
        return basinSize;
    }

    private static List<Location> findLowPoints(int[][] heightmap) {
        var result = new ArrayList<Location>();
        int w = heightmap[0].length;
        int h = heightmap.length;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int value = heightmap[i][j];
                if (adjacentLocations(j, i, w, h).stream().allMatch(loc -> heightmap[loc.y][loc.x] > value)) {
                    result.add(new Location(j, i));
                }
            }
        }
        return result;
    }

    private static List<Location> adjacentLocations(int x, int y, int w, int h) {
        var locs = new ArrayList<Location>(4);
        if (y > 0) {
            locs.add(new Location(x, y - 1));
        }
        if (x > 0) {
            locs.add(new Location(x - 1, y));
        }
        if (x < w - 1) {
            locs.add(new Location(x + 1, y));
        }
        if (y < h - 1) {
            locs.add(new Location(x, y + 1));
        }
        return locs;
    }

    private static int[][] heightmap(Reader reader) throws IOException {
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines().map(line -> line.chars().map(num -> num - '0').toArray()).toArray(int[][]::new);
        }
    }
}
