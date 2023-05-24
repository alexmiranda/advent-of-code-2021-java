package net.alexmiranda.adventofcode2021;

import java.io.Reader;
import java.util.LinkedList;
import java.util.Scanner;

public class Day10 {
    public static int calculateSyntaxErrorScore(Reader reader) {
        try (var scanner = new Scanner(reader)) {
            scanner.useDelimiter("");
            var stack = new LinkedList<Character>();
            int score = 0;
            while (scanner.hasNext()) {
                char bracket = scanner.next().charAt(0);
                switch (bracket) {
                    case '(', '[', '{', '<' -> stack.addLast(bracket);
                    case ')' -> {
                        assert !stack.isEmpty();
                        if (!stack.pollLast().equals('(')) {
                            score += 3;
                            scanner.nextLine();
                            stack.clear();
                        }
                    }
                    case ']' -> {
                        assert !stack.isEmpty();
                        if (!stack.pollLast().equals('[')) {
                            score += 57;
                            scanner.nextLine();
                            stack.clear();
                        }
                    }
                    case '}' -> {
                        assert !stack.isEmpty();
                        if (!stack.pollLast().equals('{')) {
                            score += 1197;
                            scanner.nextLine();
                            stack.clear();
                        }
                    }
                    case '>' -> {
                        assert !stack.isEmpty();
                        if (!stack.pollLast().equals('<')) {
                            score += 25137;
                            scanner.nextLine();
                            stack.clear();
                        }
                    }
                    case '\n' -> {
                        stack.clear();
                    }
                    default -> throw new RuntimeException("Unexpected character: " + bracket);
                }
            }
            return score;
        }
    }
}
