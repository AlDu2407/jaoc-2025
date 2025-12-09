package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;

public class Day02 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsLine(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var result = Arrays.stream(linesOpt.get().split(","))
        .map(ids -> {
          var idsSplit = ids.split("-");
          return new Pair<>(idsSplit[0], idsSplit[1]);
        })
        .map(Day02::getInvalidIdsFirst)
        .flatMap(Set::stream)
        .filter(Objects::nonNull)
        .reduce(0L, Long::sum);
    printResult(Task.ONE, result);
  }

  @Override
  protected void taskTwo() {
    var linesOpt = FileUtils.readInputAsLine(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var result = Arrays.stream(linesOpt.get().split(","))
        .map(ids -> {
          var idsSplit = ids.split("-");
          return new Pair<>(idsSplit[0], idsSplit[1]);
        })
        .map(Day02::getInvalidIdsSecond)
        .flatMap(Set::stream)
        .filter(Objects::nonNull)
        .reduce(0L, Long::sum);
    printResult(Task.TWO, result);
  }


  private static long solveFirst(String input) {
    return Arrays.stream(input.split(","))
        .map(ids -> {
          var idsSplit = ids.split("-");
          return new Pair<>(idsSplit[0], idsSplit[1]);
        })
        .map(Day02::getInvalidIdsFirst)
        .flatMap(Set::stream)
        .filter(Objects::nonNull)
        .reduce(0L, Long::sum);
  }

  // Numbers with odd length are always valid, so we can ignore them.
  // For numbers with even length the following has to be done:
  // let x be a number with even length 'n', then x is a sequence of characters
  // a_0, a_1, a_2, ... a_n
  // for a number to be invalid the following condition has to be met
  // x_invalid = sequence[a_0, a_1, ..., a_(n/2), a_0, a_1, ..., a_(n/2)]
  // Therefore we have to read only the first n/2 characters of the sequence,
  // duplicate it and check if this number is within the range of the pair
  private static Set<Long> getInvalidIdsFirst(Pair<String> idPair) {
    var range = idPair.asLongPair();
    var leftLength = idPair.left().length();
    var rightLength = idPair.right().length();
    var lowVal = range.left().toString();
    var highVal = range.right().toString();
    var result = new HashSet<Long>();

    if (leftLength % 2 == 1) {
      // If we have an odd number as low range, we increase the length to get the next
      // biggest even number
      leftLength++;
      lowVal = Long.toString(Math.powExact(10L, leftLength));
    }

    if (rightLength % 2 == 1) {
      // If we have an odd number as the high range, we take 10^length power and
      // subtract one
      highVal = Long.toString(Math.powExact(10L, rightLength) - 1);
      rightLength--;
    }

    var lowSeq = lowVal.substring(0, leftLength / 2);
    var highSeq = highVal.substring(0, rightLength / 2);
    var low = Long.parseLong(lowSeq);
    var high = Long.parseLong(highSeq);
    for (var curr = low; curr <= high; curr++) {
      var currLong = Long.parseLong("%d%d".formatted(curr, curr));
      if (range.isWithinRange(currLong)) {
        result.add(currLong);
      }
    }
    return result;
  }

  private static Set<Long> getInvalidIdsSecond(Pair<String> idPair) {
    var range = idPair.asLongPair();
    var result = new HashSet<Long>();

    for (var curr = range.left(); curr <= range.right(); curr++) {
      var currString = Long.toString(curr);
      if (isSequence(currString)) {
        result.add(curr);
      }
    }
    return result;
  }

  // 12341234 -> 1234 1234
  // 123123123 -> 123 123 123
  // 12121212 -> 12 12 12 12
  // 1111111 -> 1 1 1 1 1 1
  private static boolean isSequence(String val) {
    var range = Math.ceil(val.length() / 2);
    for (var i = 1; i <= range; i++) {
      var set = val.chars().mapToObj(c -> (char) c).gather(Gatherers.windowFixed(i))
          .collect(Collectors.toSet());
      if (set.size() == 1) {
        return true;
      }
    }
    return false;
  }

  private static record Pair<T>(T left, T right) {

    public Pair<Long> asLongPair() {
      if (left instanceof String leftString && right instanceof String rightString) {
        return new Pair<>(Long.parseLong(leftString), Long.parseLong(rightString));
      }

      throw new UnsupportedOperationException();
    }

    public boolean isWithinRange(long val) {
      if (left instanceof final Long leftLong && right instanceof final Long rightLong) {
        return leftLong <= val && val <= rightLong;
      }

      return false;
    }
  }
}
