package net.alexmiranda.adventofcode2021;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day6 {
    static long part1(String input, int n) {
        long[] fish = new long[9];
        Stream.of(input.split(",")).mapToInt(Integer::valueOf).forEach(i -> {
            fish[i] += 1;
        });

        for (int day = 0; day < n; day++) {
            long fry = fish[0];
            fish[0] = 0;
            for (int i = 1; i < fish.length; i++) {
                fish[i - 1] += fish[i];
                fish[i] = 0;
            }
            fish[6] += fry;
            fish[fish.length - 1] += fry;
        }

        return LongStream.of(fish).sum();
    }
}
