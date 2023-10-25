package net.alexmiranda.adventofcode2021;

import java.util.regex.Pattern;

public class Day17 {
    record Target(int x1, int y1, int x2, int y2) {
        private static final Pattern inputPattern = Pattern.compile("^target area: x=(?<x1>\\d+)\\.\\.(?<x2>\\d+), y=(?<y1>-?\\d+)\\.\\.(?<y2>-?\\d+)$");

        static Target from(CharSequence s) {
            var matcher = inputPattern.matcher(s);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("s");
            }
            int x1 = Integer.parseInt(matcher.group("x1"));
            int x2 = Integer.parseInt(matcher.group("x2"));
            int y1 = Integer.parseInt(matcher.group("y1"));
            int y2 = Integer.parseInt(matcher.group("y2"));
            return new Target(x1, y1, x2, y2);
        }
    }

    static int calculateTrajectory(Target target) {
        final int minX = Math.min(target.x1, target.x2);
        final int maxX = Math.max(target.x1, target.x2);
        final int minY = Math.min(target.y1, target.y2);
        final int maxY = Math.max(target.y1, target.y2);

        // complete the square: x^2 + x - (targetArea.x1() * 2) >= 0
        // https://www.symbolab.com/solver/equation-calculator/%5Cfrac%7Bx%20%5Cleft(x%20%2B%201%5Cright)%7D%7B2%7D%3E%20%3D20?or=input
        // https://en.wikipedia.org/wiki/Quadratic_formula
        final int minVelocityX = (int) Math.ceil((Math.sqrt(minX * 8 + 1) - 1) / 2);

        // TODO: figure out the upper limit to x, considering that it's possible that for some y's, x might not be zero
        final int maxVelocityX = maxX;

        final int maxVelocityY = Math.abs(minY);
        int answer = 0;

        // TODO: is it always min initial velocity y = 1? This assumes maxY < 0
        for (int initialVelocityY = 1; initialVelocityY <= maxVelocityY; initialVelocityY++) {
            for (int initialVelocityX = minVelocityX; initialVelocityX <= maxVelocityX; initialVelocityX++) {
                boolean hasHitTarget = false;
                int x = 0, y = 0, vx = initialVelocityX, vy = initialVelocityY, hy = 0;

                while (x <= maxX && y >= minY) {
                    if (x >= minX && y <= maxY) {
                        hasHitTarget = true;
                    }

                    x += vx;
                    y += vy;
                    vx -= (vx > 0) ? 1 : 0;
                    vy--;
                    hy = Math.max(y, hy);
                }

                if (hasHitTarget && hy > answer) {
                    answer = hy;
                }
            }
        }

        return answer;
    }
}
