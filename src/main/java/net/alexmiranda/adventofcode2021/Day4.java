package net.alexmiranda.adventofcode2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day4 {
    private static final String INPUT = "/2021/Day/4/input";
    private static final int winFlag = 0b11111;

    static class Bingo {
        private final ArrayList<int[][]> boards = new ArrayList<>(100);
        private final int[] drawNumbers;
        private int[][] winner;
        private int drawIndex = 0;
        private int lastDrawn = 0;

        Bingo(Reader reader) throws IOException {
            var br = new BufferedReader(reader);

            String line = br.readLine();
            drawNumbers = Stream.of(line.split(","))
                    .mapToInt(Integer::valueOf)
                    .toArray();

            // skip line
            br.readLine();

            var prototype = new ArrayList<String>(5);
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    boards.add(createBoard(prototype));
                    prototype.clear();
                    continue;
                }
                prototype.add(line);
            }

            if (prototype.size() > 0) {
                boards.add(createBoard(prototype));
            }
        }

        int[][] winner() {
            return this.winner;
        }

        boolean draw() {
            boolean hasWinner = false;
            for (; drawIndex < drawNumbers.length && !hasWinner; drawIndex++) {
                lastDrawn = drawNumbers[drawIndex];
                var iter = boards.iterator();
                while (iter.hasNext()) {
                    var board = iter.next();
                    if (mark(board, drawNumbers[drawIndex])) {
                        if (!hasWinner) {
                            winner = board;
                            hasWinner = true;
                        }
                        iter.remove();
                    }
                }
            }
            return boards.size() > 0 && drawIndex < drawNumbers.length;
        }

        int score() {
            var board = winner;
            int sum = 0;
            for (int i = 0; i < board.length - 1; i++) {
                int last = board[i].length - 1;
                int flags = board[i][last];
                if (flags == winFlag) {
                    continue;
                }

                flags = ~(flags & winFlag);
                for (int pos = 0; pos < last; pos++) {
                    if ((flags & 1) > 0) {
                        sum += board[i][pos];
                    }
                    flags >>= 1;
                }
            }
            return sum * lastDrawn;
        }

        private boolean mark(int[][] board, int drawn) {
            for (int i = 0; i < board.length - 1; i++) {
                for (int j = 0; j < board[i].length - 1; j++) {
                    if (board[i][j] == drawn) {
                        board[i][5] |= 1 << j;
                        board[5][j] |= 1 << i;
                        if (board[i][5] == winFlag || board[5][j] == winFlag) {
                            return true;
                        }
                        return false;
                    }
                }
            }
            return false;
        }

        private static int[][] createBoard(List<String> prototype) {
            assert prototype.size() == 5 : "invalid number of row";
            var board = new int[6][6];

            for (int i = 0; i < prototype.size(); i++) {
                var line = prototype.get(i);
                for (int j = 0, pos = 2; j < board[i].length - 1; j++, pos += 3) {
                    var s = line.substring(pos - 2, pos).trim();
                    var n = Integer.parseInt(s);
                    board[i][j] = n;
                }
            }
            return board;
        }
    }

    public static int part1(Reader reader) throws IOException {
        var bingo = new Bingo(reader);
        bingo.draw();
        return bingo.score();
    }

    public static int part2(Reader reader) throws IOException {
        var bingo = new Bingo(reader);
        while (bingo.draw())
            ;
        return bingo.score();
    }

    public static int part1() throws IOException {
        try (
                var input = Day4.class.getResourceAsStream(INPUT);
                var reader = new InputStreamReader(input)) {
            return part1(reader);
        }
    }

    public static int part2() throws IOException {
        try (
                var input = Day4.class.getResourceAsStream(INPUT);
                var reader = new InputStreamReader(input)) {
            return part2(reader);
        }
    }
}
