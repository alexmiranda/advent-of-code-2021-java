package net.alexmiranda.adventofcode2021;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
    static int part1(String input, int n) {
        var fish = new ArrayList<Integer>();
        Stream.of(input.split(",")).mapToInt(Integer::valueOf).boxed().collect(Collectors.toCollection(() -> fish));
        for (int day = 0; day < n; day++) {
            var size = fish.size();
            for (int i = 0; i < size; i++) {
                var v = fish.get(i);
                var w = v.equals(0) ? 6 : v - 1;
                fish.set(i, w);
                if (v == 0) {
                    fish.add(8);
                }
            }
        }
        return fish.size();
    }
}
