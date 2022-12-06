package net.alexmiranda.adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day1 {
    private static final String INPUT = "2021/Day/1/input";

    public static int part1() throws URISyntaxException, IOException {
        var path = Path.of(ClassLoader.getSystemResource(INPUT).toURI());
        try (var reader = Files.newBufferedReader(path)) {
            return part1(reader);
        }
    }

    public static int part2() throws URISyntaxException, IOException {
        var path = Path.of(ClassLoader.getSystemResource(INPUT).toURI());
        try (var reader = Files.newBufferedReader(path)) {
            return part2(reader);
        }
    }

    public static int part1(BufferedReader br) throws IOException {
        String line = null;
        int prev = Integer.parseInt(br.readLine());
        int counter = 0;
        while ((line = br.readLine()) != null) {
            int n = Integer.valueOf(line);
            if (n > prev)
                counter++;
            prev = n;
        }
        return counter;
    }

    public static int part2(BufferedReader br) throws IOException {
        String line = null;
        var window = new int[3];
        window[0] = Integer.parseInt(br.readLine());
        window[1] = Integer.parseInt(br.readLine());
        window[2] = Integer.parseInt(br.readLine());
        int prev = window[0] + window[1] + window[2];

        int counter = 0, i = 0;
        while ((line = br.readLine()) != null) {
            int n = Integer.valueOf(line);
            window[i++ % window.length] = n;
            int sum = window[0] + window[1] + window[2];
            if (sum > prev) {
                counter++;
            }
            prev = sum;
        }
        return counter;
    }
}