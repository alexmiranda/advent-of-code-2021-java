package net.alexmiranda.adventofcode2021;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 {
    private static final String INPUT = "/2021/Day/3/input";

    public static int part1(Reader reader, int length) throws IOException {
        var buf = new char[length + 1];
        var counter = new int[length];
        while (reader.read(buf) >= 0) {
            for (int i = 0; i < counter.length; i++) {
                if (buf[i] == '1') {
                    counter[i]++;
                } else if (buf[i] == '0') {
                    counter[i]--;
                } else {
                    assert false: "unexpected char at " + i + ": " + buf.toString();
                }
            }
            assert buf[buf.length - 1] == '\n': "unexpected char at " + (buf.length - 1) + ": " + buf.toString();
        }

        int gamma = 0, epsilon = 0;
        for (int i = 0; i < counter.length; i++) {
            gamma <<= 1;
            epsilon <<= 1;
            if (counter[i] >= 0) {
                gamma++;
            } else {
                epsilon++;
            }
        }

        return gamma * epsilon;
    }

    public static int part1() throws IOException {
        try (
            var input = Day3.class.getResourceAsStream(INPUT);
            var reader = new InputStreamReader(input)) {
            return part1(reader, 12);
        }
    }

    public static int part2() throws IOException {
        try (
            var input = Day3.class.getResourceAsStream(INPUT);
            var reader = new InputStreamReader(input)) {
            return part2(reader, 12);
        }
    }

    public static int part2(Reader reader, int length) throws IOException {
        var buf = new char[length + 1];
        var counter = new int[length];
        var list = new ArrayList<char[]>();

        while (reader.read(buf) >= 0) {
            for (int i = 0; i < counter.length; i++) {
                if (buf[i] == '1') {
                    counter[i]++;
                } else if (buf[i] == '0') {
                    counter[i]--;
                } else {
                    assert false: "unexpected char at " + i + ": " + buf.toString();
                }
            }
            assert buf[buf.length - 1] == '\n': "unexpected char at " + (buf.length - 1) + ": " + buf.toString();
            list.add(Arrays.copyOf(buf, length));
        }

        var oxygenGeneratorRating = new ArrayList<>(list);
        for (int i = 0; i < length; i++) {
            int x = countOnes(oxygenGeneratorRating, i);
            removeIf(oxygenGeneratorRating, i, x >= 0 ? '0' : '1');
            if (oxygenGeneratorRating.size() == 1) {
                break;
            }
        }
        
        var co2scrubberRating = new ArrayList<>(list);
        for (int i = 0; i < length; i++) {
            int x = countOnes(co2scrubberRating, i);
            removeIf(co2scrubberRating, i, x >= 0 ? '1' : '0');
            if (co2scrubberRating.size() == 1) {
                break;
            }
        }

        assert oxygenGeneratorRating.size() == 1;
        assert co2scrubberRating.size() == 1;

        int x = Integer.parseUnsignedInt(new String(oxygenGeneratorRating.get(0)), 2);
        int y = Integer.parseUnsignedInt(new String(co2scrubberRating.get(0)), 2);

        return x * y;
    }

    private static int countOnes(List<char[]> list, int pos) {
        int counter = 0;
        for (var num : list) {
            if (num[pos] == '1') {
                counter++;
            } else {
                counter--;
            }
        }
        return counter;
    }

    private static void removeIf(List<char[]> list, int pos, char flag) {
        if (list.size() > 1) {
            list.removeIf(num -> num[pos] == flag);
        }
    }
}
