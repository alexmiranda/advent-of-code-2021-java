package net.alexmiranda.adventofcode2021;

import java.io.PrintWriter;
import java.io.Reader;
import java.util.*;

public class Day11 {
    public static byte[] readInput(Reader reader) {
        try (var scanner = new Scanner(reader)) {
            scanner.useDelimiter("");
            var octopodes = readInput(scanner);
            assert !scanner.hasNext();
            return octopodes;
        }
    }

    static byte[] readInput(Scanner scanner) {
        var octopodes = new byte[100];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                octopodes[i * 10 + j] = (byte) scanner.nextInt();
            }
            scanner.nextLine();
        }
        return octopodes;
    }

    public static int simulate(byte[] octopodes, int turns) {
        var maxxed = new ArrayDeque<Integer>(100);
        return simulate(octopodes, maxxed, turns, 0);
    }

    private static int simulate(byte[] octopodes, Deque<Integer> maxxed, int turnsLeft, int counter) {
        if (turnsLeft == 0) return counter;
        var flashed = new boolean[100];

        maxxed.clear();
        for (int i = 0; i < 100; i++) {
            if (octopodes[i] == 9) {
                maxxed.offer(i);
                flashed[i] = true;
            }
            octopodes[i] = (byte) ((octopodes[i] + 1) % 10);
        }

        while (!maxxed.isEmpty()) {
            int pos = maxxed.poll();
            counter++;
            for (int adjacent : adjacent(pos)) {
                if (flashed[adjacent]) continue;
                if (octopodes[adjacent] == 9) {
                    maxxed.offer(adjacent);
                    flashed[adjacent] = true;
                }
                octopodes[adjacent] = (byte) ((octopodes[adjacent] + 1) % 10);
            }
        }

        return simulate(octopodes, maxxed, turnsLeft - 1, counter);
    }

    static Collection<Integer> adjacent(int pos) {
        var result = new ArrayList<Integer>(8);
        if (pos > 10 && pos % 10 != 0) { // top-left
            result.add(pos - 11);
        }
        if (pos >= 10) { // top
            result.add(pos - 10);
        }
        if (pos >= 10 && pos % 10 != 9) { // top-right
            result.add(pos - 9);
        }

        if (pos % 10 != 0) { // left
            result.add(pos - 1);
        }
        if (pos % 10 != 9) { // right
            result.add(pos + 1);
        }

        if (pos < 90 && pos % 10 != 0) { // bottom-left
            result.add(pos + 9);
        }
        if (pos < 90) { // bottom
            result.add(pos + 10);
        }
        if (pos < 90 && pos % 10 != 9) { // bottom-right
            result.add(pos + 11);
        }

        return result;
    }

    static void print(byte[] octopodes, PrintWriter w) {
        w.print((char) ('0' + octopodes[0]));
        for (int i = 1; i < 100; i++) {
            w.print((char) ('0' + octopodes[i]));
            if (i % 10 == 9) {
                w.println();
            }
        }
    }
}
