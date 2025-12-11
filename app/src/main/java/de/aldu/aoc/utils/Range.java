package de.aldu.aoc.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public record Range(Long start, Long end) {

  public boolean isInRange(Long value) {
    return value >= start && value <= end;
  }

  public long rangeSize() {
    return end - start + 1;
  }

  public Set<Long> valueSet() {
    return LongStream.rangeClosed(start, end).boxed().collect(Collectors.toSet());
  }

  public boolean containsRange(Range other) {
    return start <= other.start && end >= other.end;
  }

  public boolean isStartIntersecting(Range other) {
    return end >= other.start;
  }

  public boolean isEndIntersecting(Range other) {
    return start <= other.end;
  }
}
