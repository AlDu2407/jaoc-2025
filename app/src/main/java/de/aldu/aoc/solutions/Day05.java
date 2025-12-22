package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;
import de.aldu.aoc.utils.Range;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var ranges =
        linesOpt.get().stream()
            .filter(line -> line.contains("-"))
            .map(line -> line.split("-"))
            .map(parts -> new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])))
            .collect(Collectors.toSet());
    var inputs =
        linesOpt.get().stream()
            .filter(line -> !line.isBlank())
            .filter(line -> !line.contains("-"))
            .map(Long::parseLong)
            .collect(Collectors.toSet());

    var count =
        inputs.stream()
            .filter(input -> ranges.stream().anyMatch(range -> range.isInRange(input)))
            .count();
    printResult(Task.ONE, count);
  }

  @Override
  protected void taskTwo() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }

    Deque<Range> ranges =
        linesOpt.get().stream()
            .filter(line -> line.contains("-"))
            .map(line -> line.split("-"))
            .map(parts -> new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])))
            .sorted(Comparator.comparing(Range::start))
            .collect(LinkedList::new, List::add, List::addAll);
    var adjustedRanges = new HashSet<Range>();
    var temp = ranges.pop();
    while (!ranges.isEmpty()) {
      var curr = ranges.pop();
      if (temp.isDisjunct(curr)) {
        adjustedRanges.add(temp);
        temp = curr;
        continue;
      }
      if (temp.containsRange(curr)) {
        continue;
      } else if (curr.containsRange(temp)) {
        temp = curr;
        continue;
      }

      if (temp.isStartIntersecting(curr)) {
        temp = new Range(temp.start(), curr.end());
      } else if (temp.isEndIntersecting(curr)) {
        temp = new Range(curr.start(), temp.end());
      }
    }
    if (temp != null) {
      adjustedRanges.add(temp);
    }
    var count = adjustedRanges.stream().map(Range::rangeSize).reduce(0L, Long::sum);
    printResult(Task.TWO, count);
  }
}
