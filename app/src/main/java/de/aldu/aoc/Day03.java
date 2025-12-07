package de.aldu.aoc;

import java.util.List;
import java.util.Objects;

public class Day03 {
    public static void solve() {
        var linesOpt = Util.readFileAsLines("/inputs/day03/input.txt");
        if (linesOpt.isEmpty()) {
            return;
        }
        var lines = linesOpt.get();
        System.out.printf("Solution for first task: %d\n", solveFirst(lines));
        System.out.printf("Solution for second task: %d\n", solveSecond(lines));
    }

    // [9][8]7654321111111
    // [8]1111111111111[9]
    // 2342342342342[7][8]
    // 818181[9]1111[2]111
    static long solveFirst(List<String> lines) {
        return lines.stream()
                .map(line -> maxJoltage(line, 2))
                .filter(Objects::nonNull)
                .reduce(0L, Long::sum);
    }

    static long solveSecond(List<String> lines) {
        return lines.stream()
                .map(line -> maxJoltage(line, 12))
                .filter(Objects::nonNull)
                .reduce(0L, Long::sum);
    }

    static long maxJoltage(String line, int amount) {
        var length = line.length();
        if (length == amount) {
            return Integer.parseInt(line);
        }
        var val = new char[amount];
        for (var i = 0; i < amount; ++i) {
            val[i] = line.charAt(length - (amount - i));
        }

        for (var i = length - 1 - amount; i >= 0; --i) {
            var curr = line.charAt(i);
            if (curr >= val[0]) {
                var j = 1;
                var prev = val[j - 1];
                while (j < amount) {
                    if (val[j] > prev) {
                        break;
                    } else {
                        var temp = prev;
                        prev = val[j];
                        val[j] = temp;
                        j++;
                    }
                }

                val[0] = curr;
            }
        }

        var sb = new StringBuilder();
        for (var c : val) {
            sb.append(c);
        }
        var result = Long.parseLong(sb.toString());
        // System.out.println("%s => %d".formatted(line, result));
        return result;
    }

}
