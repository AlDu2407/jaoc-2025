package de.aldu.aoc.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class FileUtils {

  public static Optional<List<String>> readInputAsList(String fileName) {
    try (var inputStream = FileUtils.class.getClassLoader().getResourceAsStream(fileName)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("File not found: %s".formatted(fileName));
      }
      try (var reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
        return Optional.of(reader.readAllLines());
      }
    } catch (IOException | IllegalArgumentException ex) {
      System.err.printf("Failed to load data: %s!%n", fileName);
    }

    return Optional.empty();
  }

  public static Optional<String> readInputAsLine(String fileName) {
    return readInputAsList(fileName).map(lines -> String.join("", lines));
  }
}
