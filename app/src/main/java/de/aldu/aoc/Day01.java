package de.aldu.aoc;

import java.util.List;

public class Day01 {
    public static void solve() {
        var linesOpt = Util.readFileAsLines("/inputs/day01/example.txt");
        if (linesOpt.isEmpty()) {
            return;
        }
        var lines = linesOpt.get();
        System.out.printf("Solution for first task: %d\n", solveFirst(lines));
        System.out.printf("Solution for second task: %d\n", solveSecond(lines));
    }

    static int solveFirst(List<String> lines) {
        var counts = 0;
        var val = 50;
        for (var line : lines) {
            var op = line.charAt(0);
            var rotation = Integer.parseInt(line.substring(1));
            switch (op) {
                case 'L' -> {
                    if (rotation >= val) {
                        val = ((val + 100) - (rotation % 100)) % 100;
                    } else {
                        val = (val - rotation);
                    }
                }
                case 'R' -> {
                    val = (val + rotation) % 100;
                }
                default -> {
                    System.err.println("Impossible!");
                }
            }
            if (val == 0)
                counts += 1;
        }
        return counts;
    }

    static int solveSecond(List<String> lines) {
        var counts = 0;
        var val = 50;
        for (var line : lines) {
            var opChar = line.charAt(0);
            var rotation = Integer.parseInt(line.substring(1));
            var op = switch (opChar) {
                case 'L' -> -1;
                case 'R' -> 1;
                default -> throw new IllegalArgumentException();
            };
            if (op < 0) {
                var x = rotation % 100;
                var y = rotation - x;
                var hits = 0;
                if (y > 0) {
                    hits += (y / 100);
                }
                if (x >= val) {
                    hits++;
                    val = (val + 100 - x) % 100;
                } else {
                    val = val - x;
                }
                counts += hits;
            } else {
                var x = (val + rotation) % 100;
                var y = (val + rotation) - x;
                var hits = 0;
                if (y > 100) {
                    hits += (y / 100);
                }
                val = x;
                counts += hits;
            }
        }
        return counts;
    }
}
