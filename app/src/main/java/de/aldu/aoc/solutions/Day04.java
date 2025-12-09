package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;
import de.aldu.aoc.utils.Grid;
import de.aldu.aoc.utils.Vec2;
import java.util.function.Function;

public class Day04 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsList("inputs/day04/input.in");
    if (linesOpt.isEmpty()) {
      return;
    }
    var grid = Grid.parseGrid(linesOpt.get(), Function.identity());
    var result = 0;
    for (var row = 0; row < grid.rowCount; ++row) {
      for (var col = 0; col < grid.colCount; ++col) {
        var pos = new Vec2(col, row);
        var curr = grid.at(pos);
        if (curr != '@') {
          continue;
        }
        var neighbours = grid.findAllNeighboursWith(pos, '@');
        if (neighbours.size() < 4) {
          result++;
        }
      }
    }
    printResult(Task.ONE, result);
  }

  @Override
  protected void taskTwo() {
    var linesOpt = FileUtils.readInputAsList("inputs/day04/input.in");
    if (linesOpt.isEmpty()) {
      return;
    }
    var grid = Grid.parseGrid(linesOpt.get(), Function.identity());
    var result = 0;
    var tempResult = 0;
    do {
      tempResult = 0;
      for (var row = 0; row < grid.rowCount; ++row) {
        for (var col = 0; col < grid.colCount; ++col) {
          var pos = new Vec2(col, row);
          var curr = grid.at(pos);
          if (curr != '@') {
            continue;
          }
          var neighbours = grid.findAllNeighboursWith(pos, '@');
          if (neighbours.size() < 4) {
            tempResult++;
            grid.setAt(pos, '.');
          }
        }
      }
      result += tempResult;
    } while (tempResult > 0);
    printResult(Task.TWO, result);
  }
}
