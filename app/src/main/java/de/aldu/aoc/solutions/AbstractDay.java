package de.aldu.aoc.solutions;

public abstract class AbstractDay {

  protected enum Task {
    ONE("task one"),
    TWO("task two");

    private final String value;

    Task(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  public void executeTasks() {
    System.out.println(
        "---------------------------------------------------------------------------------------------");
    System.out.printf("Executing task for %s:\n", getDay());
    taskOne();
    taskTwo();
  }

  private String getDay() {
    var number = getClass().getSimpleName().substring(3);
    if (number.length() == 1) {
      number = "0%s".formatted(number);
    }
    return "day%s".formatted(number);
  }

  protected abstract void taskOne();

  protected abstract void taskTwo();

  protected String getInputFileName() {
    return "inputs/%s/input.in".formatted(getDay());
  }

  protected String getExampleFileName() {
    return "inputs/%s/example.in".formatted(getDay());
  }

  protected String getExampleFileName(int no) {
    return "inputs/%s/example%s.in".formatted(getDay(), no);
  }

  protected <T> void printResult(Task task, T result) {
    System.out.printf("The result for %s is %s\n", task, result);
  }
}
