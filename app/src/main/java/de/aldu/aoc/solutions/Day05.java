package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;
import de.aldu.aoc.utils.Range;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Day05 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var ranges = linesOpt.get().stream()
        .filter(line -> line.contains("-"))
        .map(line -> line.split("-"))
        .map(parts -> new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])))
        .collect(Collectors.toSet());
    var inputs = linesOpt.get().stream()
        .filter(line -> !line.isBlank())
        .filter(line -> !line.contains("-"))
        .map(Long::parseLong)
        .collect(Collectors.toSet());

    var count = inputs.stream()
        .filter(input -> ranges.stream().anyMatch(range -> range.isInRange(input)))
        .count();
    printResult(Task.ONE, count);
  }

  @Override
  protected void taskTwo() {
    var linesOpt = FileUtils.readInputAsList(getExampleFileName(2));
    if (linesOpt.isEmpty()) {
      return;
    }

    var ranges = linesOpt.get().stream()
        .filter(line -> line.contains("-"))
        .map(line -> line.split("-"))
        .map(parts -> new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])))
        .sorted(Comparator.comparing(Range::start))
        .toList();
    var adjustedRanges = new HashSet<Range>();
    for (var range : ranges) {
      Range tempRange = null;
      for (var adjustedRange : adjustedRanges) {
        if (adjustedRange.containsRange(range)) break;
        if (adjustedRange.isStartIntersecting(range)) {
          tempRange = new Range(adjustedRange.end() + 1, range.end());
        } else if (adjustedRange.isEndIntersecting(range)) {
          tempRange = new Range(range.start(), adjustedRange.start() - 1);
        }
      }
      if (tempRange != null) {
        adjustedRanges.add(tempRange);
      }
    }
    var count = adjustedRanges.stream().map(Range::rangeSize).reduce(0L, Long::sum);
    printResult(Task.TWO, count);
  }
}
