package de.aldu.aoc;

import de.aldu.aoc.utils.FileUtils;
import de.aldu.aoc.utils.Grid;
import de.aldu.aoc.utils.Vec2;
import java.util.function.Function;

public class Day04 {

  public static void solve() {
    var linesOpt = FileUtils.readInputAsList("inputs/day04/input.txt");
    if (linesOpt.isEmpty()) {
      return;
    }
    var grid = Grid.parseGrid(linesOpt.get(), Function.identity());
    System.out.printf("Solution for first task: %d\n", solveFirst(grid));
    System.out.printf("Solution for second task: %d\n", solveSecond(grid));
  }

  static int solveFirst(final Grid<Character> characterGrid) {
    var result = 0;
    for (var row = 0; row < characterGrid.rowCount; ++row) {
      for (var col = 0; col < characterGrid.colCount; ++col) {
        var pos = new Vec2(col, row);
        var curr = characterGrid.at(pos);
        if (curr != '@') {
          continue;
        }
        var neighbours = characterGrid.findAllNeighboursWith(pos, '@');
        if (neighbours.size() < 4) {
          result++;
        }
      }
    }
    return result;
  }

  static int solveSecond(Grid<Character> characterGrid) {
    var result = 0;
    var tempResult = 0;
    do {
      tempResult = 0;
      for (var row = 0; row < characterGrid.rowCount; ++row) {
        for (var col = 0; col < characterGrid.colCount; ++col) {
          var pos = new Vec2(col, row);
          var curr = characterGrid.at(pos);
          if (curr != '@') {
            continue;
          }
          var neighbours = characterGrid.findAllNeighboursWith(pos, '@');
          if (neighbours.size() < 4) {
            tempResult++;
            characterGrid.setAt(pos, '.');
          }
        }
      }
      result += tempResult;
    } while (tempResult > 0);
    return result;
  }
}
