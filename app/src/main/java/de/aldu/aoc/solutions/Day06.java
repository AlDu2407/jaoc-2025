package de.aldu.aoc.solutions;

import de.aldu.aoc.utils.FileUtils;
import de.aldu.aoc.utils.Grid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class Day06 extends AbstractDay {

  @Override
  protected void taskOne() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var lines = linesOpt.get();
    var size = lines.size();
    var operandLists = lines.subList(0, size - 1).stream()
        .map(line -> Arrays.stream(line.split(" "))
            .filter(Predicate.not(String::isBlank))
            .map(Long::parseLong)
            .toList())
        .toList();
    var operatorList = Arrays.stream(lines.get(size - 1).split(" "))
        .filter(Predicate.not(String::isBlank))
        .map(Operator::ofSign)
        .toList();
    var result = 0L;
    for (var i = 0; i < operatorList.size(); i++) {
      var j = i;
      var operator = operatorList.get(i);
      result += operandLists.stream()
          .map(line -> line.get(j))
          .reduce(operator.identity, operator.accumulator);
    }
    printResult(Task.ONE, result);
  }

  @Override
  protected void taskTwo() {
    var linesOpt = FileUtils.readInputAsList(getInputFileName());
    if (linesOpt.isEmpty()) {
      return;
    }
    var lines = linesOpt.get();
    var grid = Grid.parseGrid(lines, Function.identity());

    Operator op = null;
    var operands = new ArrayList<Long>();
    var result = 0L;
    for (var i = 0; i < grid.colCount; i++) {
      var col = grid.getColumn(i, ' ');
      var isEmpty = col.stream().allMatch(Character::isWhitespace);
      if (isEmpty) {
        result += calculate(operands, op);
        op = null;
        operands.clear();
        continue;
      }
      if (col.getLast().compareTo(' ') != 0) {
        op = Operator.ofSign(col.getLast());
      }
      var numberString = col.subList(0, col.size() - 1).stream().collect(
          Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
              StringBuilder::toString));
      operands.add(Long.parseLong(numberString.trim()));
    }
    if (op != null) {
      result += calculate(operands, op);
    }
    printResult(Task.TWO, result);
  }

  private long calculate(List<Long> operands, Operator operator) {
    return operands.stream().reduce(operator.identity, operator.accumulator);
  }

  record Operator(long identity, BinaryOperator<Long> accumulator) {

    static Operator ofSign(Character sign) {
      return switch (sign) {
        case '+' -> new Operator(0L, Long::sum);
        case '*' -> new Operator(1L, (lhs, rhs) -> lhs * rhs);
        default -> throw new IllegalStateException("Unexpected value: " + sign);
      };
    }

    static Operator ofSign(String sign) {
      return switch (sign) {
        case "+" -> new Operator(0L, Long::sum);
        case "*" -> new Operator(1L, (lhs, rhs) -> lhs * rhs);
        default -> throw new IllegalArgumentException(sign);
      };
    }
  }
}
