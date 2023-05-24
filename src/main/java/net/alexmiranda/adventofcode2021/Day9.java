package net.alexmiranda.adventofcode2021;

import java.io.Reader;
import java.util.Scanner;

public class Day9 {
    public static int findLowPoints(Reader reader) {
        try (var scanner = new Scanner(reader)) {
            String line = scanner.nextLine();
            int width = line.length();

            // area represents the 3 lines of the heightmap being looked at, at any given time
            char[] area = new char[3 * line.length()];

            // read the first line into the area array
            line.getChars(0, width, area, 0);
            int ans = 0, curr = 0, prev = 0;
            boolean isLast = false;
            while (!isLast) {
                // read the next line into the area array
                int next = (curr + width) % area.length;
                if (scanner.hasNext()) {
                    line = scanner.nextLine();
                    line.getChars(0, width, area, next);
                } else {
                    isLast = true;
                }

                int last = curr + width;
                for (int i = curr; i < last; i++) {
                    int v = area[i] - '0';

                    // continue if top location is smaller than or equal
                    if (prev != curr) {
                        int up = area[prev + (i - curr)] - '0';
                        if (up <= v) {
                            continue;
                        }
                    }

                    // continue if left location is smaller than or equal
                    if (i > curr) {
                        int left = area[i - 1] - '0';
                        if (left <= v) {
                            continue;
                        }
                    }

                    // continue if right location is smaller than or equal
                    if (i < last - 1) {
                        int right = area[i + 1] - '0';
                        if (right <= v) {
                            continue;
                        }
                    }

                    // continue if bottom location is smaller than or equal
                    if (!isLast) {
                        int down = area[next + (i - curr)] - '0';
                        if (down <= v) {
                            continue;
                        }
                    }

                    // if we reached this point, we know that it must be smaller than every adjacent location
                    ans += v + 1;
                }

                prev = curr;
                curr = next;
            }

            return ans;
        }
    }
}
