package net.alexmiranda.adventofcode2021;

import java.io.Reader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
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
                    case ')', ']', '}', '>' -> {
                        assert !stack.isEmpty();
                        if (!stack.pollLast().equals(openingBracket(bracket))) {
                            score += syntaxErrorScore(bracket);
                            scanner.nextLine();
                            stack.clear();
                        }
                    }
                    case '\n' -> stack.clear();
                    default -> throw new RuntimeException("Unexpected character: " + bracket);
                }
            }
            return score;
        }
    }

    public static long calculateAutoCompleteScore(Reader reader) {
        try (var scanner = new Scanner(reader)) {
            scanner.useDelimiter("");
            var stack = new LinkedList<Character>();
            var minHeap = new PriorityQueue<Long>();
            var maxHeap = new PriorityQueue<Long>(Comparator.reverseOrder());
            while (scanner.hasNext()) {
                char bracket = scanner.next().charAt(0);
                switch (bracket) {
                    case '(', '[', '{', '<' -> stack.addLast(bracket);
                    case ')', ']', '}', '>' -> {
                        assert !stack.isEmpty();
                        if (!stack.pollLast().equals(openingBracket(bracket))) {
                            scanner.nextLine();
                            stack.clear();
                        }
                    }
                    case '\n' -> {
                        long score = computeAutoCompletionScore(stack);

                        // we use a min and max heap so that we can keep track of what the median score is
                        if (minHeap.size() == maxHeap.size()) {
                            maxHeap.offer(score);
                            minHeap.offer(maxHeap.poll());
                        } else {
                            minHeap.offer(score);
                            maxHeap.offer(minHeap.poll());
                        }
                        stack.clear();
                    }
                    default -> throw new RuntimeException("Unexpected character: " + bracket);
                }
            }

            assert minHeap.size() > 0;
            assert maxHeap.size() > 0;

            // get the median score
            if (minHeap.size() > maxHeap.size()) {
                return minHeap.peek();
            } else {
                return (minHeap.peek() + maxHeap.peek()) / 2;
            }
        }
    }

    private static long computeAutoCompletionScore(LinkedList<Character> stack) {
        long score = 0;
        while (!stack.isEmpty()) {
            score = score * 5 + autoCompletionScore(stack.pollLast());
        }
        return score;
    }

    private static char openingBracket(char closingBracket) {
        return switch (closingBracket) {
            case ')' -> '(';
            case ']' -> '[';
            case '}' -> '{';
            case '>' -> '<';
            default -> '\0';
        };
    }

    private static int syntaxErrorScore(char closingBracket) {
        return switch (closingBracket) {
            case ')' -> 3;
            case ']' -> 57;
            case '}' -> 1197;
            case '>' -> 25137;
            default -> 0;
        };
    }

    private static int autoCompletionScore(char closingBracket) {
        return switch (closingBracket) {
            case '(' -> 1;
            case '[' -> 2;
            case '{' -> 3;
            case '<' -> 4;
            default -> throw new IllegalStateException("Invalid character: " + closingBracket);
        };
    }
}
