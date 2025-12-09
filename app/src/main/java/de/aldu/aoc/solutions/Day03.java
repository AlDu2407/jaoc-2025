package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;

public class Day03 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var lines = linesOpt.get();
    var result = lines.stream()
        .map(line -> maxJoltage(line, 2))
        .reduce(0L, Long::sum);
    printResult(Task.ONE, result);
  }

  @Override
  protected void taskTwo() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var lines = linesOpt.get();
    var result = lines.stream()
        .map(line -> maxJoltage(line, 12))
        .reduce(0L, Long::sum);
    printResult(Task.TWO, result);
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
    return Long.parseLong(sb.toString());
  }

}
