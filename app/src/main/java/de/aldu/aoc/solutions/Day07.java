package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;
import de.aldu.aoc.utils.Grid;
import java.util.function.Function;

public class Day07 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsList(getExampleFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var grid = Grid.parseGrid(linesOpt.get(), Function.identity());
    var columnOpt = grid.findInRow('S', 0);
  }

  @Override
  protected void taskTwo() {}
}
