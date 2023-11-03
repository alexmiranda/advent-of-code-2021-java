package net.alexmiranda.adventofcode2021;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class Day18 {
    sealed static abstract class SnailfishNumber permits Pair, Regular {
        Pair parent;
        boolean isLeft;

        private SnailfishNumber() {
        }

        private SnailfishNumber(Pair parent, boolean isLeft) {
            this.parent = parent;
            this.isLeft = isLeft;
        }

        static SnailfishNumber parse(String s) throws IOException {
            try (var reader = new StringReader(s)) {
                return parse(reader);
            }
        }

        static SnailfishNumber parse(Reader reader) throws IOException {
            class State {
                final Pair pair;
                boolean isLeft = true;

                State(Pair pair) {
                    this.pair = pair;
                }
            }

            Deque<State> stack = new ArrayDeque<>(4);
            State state = null;

            int read = 0;
            while (read != '\n') {
                assert stack.size() <= 4;

                read = reader.read();
                if (read == -1) {
                    break;
                }

                switch (read) {
                    case '[':
                        assert stack.size() <= 4;
                        if (state != null) {
                            stack.addLast(state);
                        }
                        var parent = state != null ? state.pair : null;
                        state = new State(new Pair(parent, state == null || state.isLeft));
                        break;
                    case ',':
                        assert state != null;
                        assert state.isLeft;
                        state.isLeft = false;
                        break;
                    case ']':
                        assert state == null || !state.isLeft;
                        if (stack.isEmpty()) {
                            continue;
                        }
                        var prev = stack.pollLast();
                        if (prev.isLeft) {
                            prev.pair.left = state.pair;
                        } else {
                            prev.pair.right = state.pair;
                        }
                        state = prev;
                        break;
                    case '\r', '\n':
                        continue;
                    default:
                        assert Character.isDigit(read) : "invalid character read: " + (char) read;
                        assert state != null;
                        var regular = new Regular(state.pair, state.isLeft, read - '0');
                        if (state.isLeft) {
                            state.pair.left = regular;
                        } else {
                            state.pair.right = regular;
                        }
                }
            }

            return state == null ? null : state.pair;
        }

        public SnailfishNumber add(SnailfishNumber right) {
            var pair = new Pair(this, right);
            this.parent = pair;
            this.isLeft = true;

            right.parent = pair;
            right.isLeft = false;

            return reduce(pair);
        }

        private static Pair reduce(Pair root) {
            record Node(SnailfishNumber elem, int depth) {
            }

            var stack = new ArrayDeque<Node>();
            stack.push(new Node(root, 0));

            Pair found = null;
            Regular nearestLeft = null, nearestRight = null;
            while (!stack.isEmpty()) {
                var node = stack.pop();
                switch (node.elem) {
                    case Pair pair -> {
                        // have we found a pair to explode?
                        if (found == null && node.depth >= 4 && pair.left instanceof Regular && pair.right instanceof Regular) {
                            found = pair;
                            continue;
                        }

                        // push right before left to ensure correct iteration order
                        stack.push(new Node(pair.right, node.depth + 1));
                        stack.push(new Node(pair.left, node.depth + 1));
                    }
                    case Regular regular -> {
                        if (found == null) {
                            // we update the nearest left node until a pair is found
                            nearestLeft = regular;
                        } else if (regular.parent != found) {
                            // the first regular node whose parent is not the found pair is the nearest right node
                            nearestRight = regular;
                            stack.clear();
                        }
                    }
                }
            }

            var needToSplit = false;
            if (found != null) {
                // explode the pair and determine if we need to split, i.e. nearest left or right have value >= 10
                needToSplit = explode(found, nearestLeft, nearestRight);
                root = reduce(root);
            }

            // if we know there is nothing to split, we simply return the root node
            if (!needToSplit) {
                return root;
            }

            // search for numbers to split
            stack.add(new Node(root, 0));
            while (!stack.isEmpty()) {
                var node = stack.pop();
                switch (node.elem) {
                    case Pair pair -> {
                        // push right before left to ensure correct iteration order
                        stack.push(new Node(pair.right, node.depth + 1));
                        stack.push(new Node(pair.left, node.depth + 1));
                    }
                    case Regular regular -> {
                        if (regular.value >= 10) {
                            split(regular);

                            // if split causes the pair to explode, we reduce it first
                            if (node.depth >= 4) {
                                root = reduce(root);
                            }

                            // start over at root
                            stack.clear();
                            stack.add(new Node(root, 0));
                        }
                    }
                }
            }

            // we're done: all explodes and splits are complete
            return root;
        }

        private static boolean explode(Pair pair, Regular nearestLeft, Regular nearestRight) {
            if (pair.isLeft) {
                pair.parent.left = new Regular(pair.parent, true, 0);
            } else {
                pair.parent.right = new Regular(pair.parent, false, 0);
            }

            var needToSplit = false;
            if (nearestLeft != null) {
                nearestLeft.value += ((Regular) pair.left).value;
                needToSplit |= nearestLeft.value >= 10;
            }

            if (nearestRight != null) {
                nearestRight.value += ((Regular) pair.right).value;
                needToSplit |= nearestRight.value >= 10;
            }

            return needToSplit;
        }

//        private static void add(Regular regular, int value) {
//            regular.value += value;
//            if (regular.value >= 10) {
//                split(regular);
//            }
//        }

        private static Pair split(Regular regular) {
            var leftVal = regular.value / 2;
            var rightVal = regular.value - leftVal;
            var pair = new Pair(regular.parent, regular.isLeft);
            pair.left = new Regular(pair, true, leftVal);
            pair.right = new Regular(pair, false, rightVal);
            if (regular.isLeft) {
                regular.parent.left = pair;
            } else {
                regular.parent.right = pair;
            }
            return pair;
        }

        private static Pair root(SnailfishNumber number) {
            return number.parent == null ? (Pair) number : root(number.parent);
        }

        abstract int magnitude();
    }

    static final class Pair extends SnailfishNumber {
        private SnailfishNumber left;
        private SnailfishNumber right;

        private Pair(Pair parent, boolean isLeft) {
            super(parent, isLeft);
        }

        private Pair(SnailfishNumber left, SnailfishNumber right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int magnitude() {
            return 3 * left.magnitude() + 2 * right.magnitude();
        }

        @Override
        public String toString() {
            return "[" + left + "," + right + "]";
        }
    }

    static final class Regular extends SnailfishNumber {
        private int value;

        private Regular(Pair parent, boolean isLeft, int value) {
            super(parent, isLeft);
            this.value = value;
        }

        @Override
        public int magnitude() {
            return this.value;
        }

        @Override
        public String toString() {
            return Integer.toString(this.value);
        }
    }

    public static void main(String[] args) {
        try (var reader = new StringReader("[[[[4,3],4],4],[7,[[8,4],9]]]\n[1,1]")) {
            try {
                var a = SnailfishNumber.parse(reader);
                var b = SnailfishNumber.parse(reader);
                System.out.println(a);
                System.out.println(b);
                System.out.println(a.add(b));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
