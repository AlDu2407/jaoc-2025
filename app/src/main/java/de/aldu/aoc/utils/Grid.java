package de.aldu.aoc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Grid<T> {

  public final int rowCount, colCount;
  private final List<List<T>> grid = new ArrayList<>();

  private Grid(int rowCount, int colCount) {
    this.rowCount = rowCount;
    this.colCount = colCount;
  }

  @SuppressWarnings("unused")
  public Set<Vec2> getCorners() {
    return Set.of(new Vec2(0, 0), new Vec2(colCount, 0), new Vec2(0, rowCount),
        new Vec2(colCount, rowCount));
  }

  public T at(Vec2 pos) {
    return grid.get(pos.y()).get(pos.x());
  }

  public void setAt(Vec2 pos, T value) {
    grid.get(pos.y()).set(pos.x(), value);
  }

  @SuppressWarnings("unused")
  public List<Vec2> findNeighbours(Vec2 point) {
    return Direction.compassDirections().stream()
        .map(point::calculate)
        .filter(p -> p.withinBoundaries(rowCount, colCount))
        .toList();
  }

  @SuppressWarnings("unused")
  public List<Vec2> findAllNeighbours(Vec2 point) {
    return Arrays.stream(Direction.values())
        .map(point::calculate)
        .filter(p -> p.withinBoundaries(rowCount, colCount))
        .toList();
  }

  public List<Vec2> findAllNeighboursWith(Vec2 point, T val) {
    return Arrays.stream(Direction.values())
        .map(point::calculate)
        .filter(p -> p.withinBoundaries(rowCount, colCount))
        .filter(p -> at(p).equals(val))
        .toList();
  }

  public List<T> getColumn(int col, T defaultValue) {
    if (col < 0 || col >= colCount) {
      return null;
    }
    var result = new ArrayList<T>();
    for (var y = 0; y < rowCount; y++) {
      if (col >= grid.get(y).size()) {
        result.add(defaultValue);
      } else {
        result.add(grid.get(y).get(col));
      }
    }
    return result;
  }

  public static <T> Grid<T> parseGrid(List<String> lines, Function<Character, T> mapping) {
    var rowCount = lines.size();
    var colCount = lines.getFirst().length();
    var result = new Grid<T>(rowCount, colCount);
    for (var y = 0; y < rowCount; y++) {
      var line = new ArrayList<T>();
      for (var x = 0; x < colCount; x++) {
        if (x >= lines.get(y).length()) {
          break;
        }
        line.add(x, mapping.apply(lines.get(y).charAt(x)));
      }

      result.grid.add(y, line);
    }
    return result;
  }
}