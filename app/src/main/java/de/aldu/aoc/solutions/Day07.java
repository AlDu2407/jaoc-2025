package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;
import de.aldu.aoc.utils.Grid;
import de.aldu.aoc.utils.Vec2;
import java.util.TreeSet;
import java.util.function.Function;

public class Day07 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var grid = Grid.parseGrid(linesOpt.get(), Function.identity());
    var columnOpt = grid.findInRow('S', 0);
    if (columnOpt.isEmpty()) {
      return;
    }
    var splits = 0;
    var columnList = new TreeSet<Integer>();
    columnList.add(columnOpt.get());
    for (var row = 1; row < grid.rowCount; ++row) {
      var newList = new TreeSet<Integer>();
      for (var col : columnList) {
        if (grid.at(new Vec2(col, row)).equals('^')) {
          splits++;
          newList.add(col - 1);
          newList.add(col + 1);
        } else {
          newList.add(col);
        }
      }
      columnList = newList;
    }
    printResult(Task.ONE, splits);
  }

  @Override
  protected void taskTwo() {}
}
