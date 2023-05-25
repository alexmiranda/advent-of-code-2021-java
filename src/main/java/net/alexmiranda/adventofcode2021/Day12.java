package net.alexmiranda.adventofcode2021;

import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class Day12 {
    abstract static class Cave {
        protected final String id;
        protected final HashSet<Cave> links = new HashSet<>(6, 0.75f);

        static Cave fromId(String id) {
            if (id.equals("start")) {
                return new Start(id);
            } else if (id.equals("end")) {
                return new End(id);
            } else if (Character.isUpperCase(id.charAt(0))) {
                return new BigCave(id);
            } else if (Character.isLowerCase(id.charAt(0))) {
                return new SmallCave(id);
            }
            throw new IllegalStateException("Invalid cave: " + id);
        }

        Cave(String id) {
            this.id = id;
        }

        public int discover() {
            return discover(new HashSet<>());
        }

        protected int discover(HashSet<String> visited) {
            int paths = 0;
            visited = new HashSet<>(visited);
            visited.add(this.id);
            for (var cave : this.links) {
                paths += cave.discover(visited);
            }
            return paths;
        }

        public void connectTo(Cave other) {
            this.links.add(other);
            other.links.add(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cave cave = (Cave) o;
            return Objects.equals(id, cave.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class Start extends Cave {
        Start(String id) {
            super(id);
        }

        @Override
        protected int discover(HashSet<String> visited) {
            if (!visited.isEmpty()) return 0;
            return super.discover(visited);
        }
    }

    static class End extends Cave {
        End(String id) {
            super(id);
        }

        @Override
        protected int discover(HashSet<String> visited) {
            return 1;
        }
    }

    static class BigCave extends Cave {
        BigCave(String id) {
            super(id);
        }
    }

    static class SmallCave extends Cave {
        SmallCave(String id) {
            super(id);
        }

        @Override
        protected int discover(HashSet<String> visited) {
            if (visited.contains(this.id)) return 0;
            return super.discover(visited);
        }
    }

    public static Cave readInput(Reader reader) {
        var caves = new HashMap<String, Cave>();
        try (var scanner = new Scanner(reader)) {
            scanner.useDelimiter("([-\\n])");
            while (scanner.hasNext()) {
                var left = caves.computeIfAbsent(scanner.next(), Cave::fromId);
                var right = caves.computeIfAbsent(scanner.next(), Cave::fromId);
                left.connectTo(right);
            }
        }
        return caves.get("start");
    }
}
