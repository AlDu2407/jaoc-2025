package de.aldu.aoc.utils;

import java.util.EnumSet;
import java.util.List;

public enum Direction {
  DOWN(new Vec2(0, 1)),
  UP(new Vec2(0, -1)),
  LEFT(new Vec2(-1, 0)),
  RIGHT(new Vec2(1, 0)),
  DOWN_RIGHT(new Vec2(1, 1)),
  UP_RIGHT(new Vec2(1, -1)),
  DOWN_LEFT(new Vec2(-1, 1)),
  UP_LEFT(new Vec2(-1, -1));
  public final Vec2 vec;

  Direction(Vec2 vec) {
    this.vec = vec;
  }

  public Direction rotate90DegRight() {
    return switch (this) {
      case DOWN -> LEFT;
      case UP -> RIGHT;
      case LEFT -> UP;
      case RIGHT -> DOWN;
      case DOWN_RIGHT -> DOWN_LEFT;
      case UP_RIGHT -> DOWN_RIGHT;
      case DOWN_LEFT -> UP_LEFT;
      case UP_LEFT -> UP_RIGHT;
    };
  }

  public Vec2 add(Direction dir) {
    return vec.add(dir.vec);
  }

  public static EnumSet<Direction> compassDirections() {
    return EnumSet.of(UP, RIGHT, DOWN, LEFT);
  }

  public static List<Tuple<Direction>> cornerPairs() {
    return List.of(
        new Tuple<>(LEFT, UP),
        new Tuple<>(UP, RIGHT),
        new Tuple<>(RIGHT, DOWN),
        new Tuple<>(DOWN, LEFT));
  }
}