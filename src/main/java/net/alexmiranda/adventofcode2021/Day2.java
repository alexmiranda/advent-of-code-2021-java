package net.alexmiranda.adventofcode2021;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day2 {
    private static final String INPUT = "/2021/Day/2/input";

    interface Submarine {
        void forward(int x);

        void up(int x);

        void down(int x);
    }

    static class SillySubmarine implements Submarine {
        private int x = 0, y = 0;

        @Override
        public void forward(int n) {
            x += n;
        }

        @Override
        public void down(int n) {
            y += n;
        }

        @Override
        public void up(int n) {
            down(-n);
        }

        int multiplyCoordinates() {
            return x * y;
        }
    }

    static class ImprovedSubmarine implements Submarine {
        private int x, y, aim;

        @Override
        public void forward(int n) {
            x += n;
            y += aim * n;
        }

        @Override
        public void up(int n) {
            aim -= n;
        }

        @Override
        public void down(int n) {
            aim += n;
        }

        int multiplyCoordinates() {
            return x * y;
        }
    }

    private static void loadInstructions(Submarine submarine, Reader reader) throws IOException {
        try (var scanner = new Scanner(reader)) {
            scanner.useDelimiter(Pattern.compile("[\\n\\r\\s]"));
            while (scanner.hasNext()) {
                var s = scanner.next();
                switch (s) {
                    case "forward" -> submarine.forward(scanner.nextInt());
                    case "down" -> submarine.down(scanner.nextInt());
                    case "up" -> submarine.up(scanner.nextInt());
                }
            }
        }
    }

    public static int part1(Reader reader) throws IOException {
        var submarine = new SillySubmarine();
        loadInstructions(submarine, reader);
        return submarine.multiplyCoordinates();
    }

    public static int part1() throws IOException {
        var submarine = new SillySubmarine();
        InputStream input = Day2.class.getResourceAsStream(INPUT);
        var reader = new InputStreamReader(input);
        loadInstructions(submarine, reader);
        return submarine.multiplyCoordinates();
    }

    public static int part2(Reader reader) throws IOException {
        var submarine = new ImprovedSubmarine();
        loadInstructions(submarine, reader);
        return submarine.multiplyCoordinates();
    }

    public static int part2() throws IOException {
        var submarine = new ImprovedSubmarine();
        InputStream input = Day2.class.getResourceAsStream(INPUT);
        var reader = new InputStreamReader(input);
        loadInstructions(submarine, reader);
        return submarine.multiplyCoordinates();
    }
}
