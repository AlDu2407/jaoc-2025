package de.aldu.aoc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class Util {

    public static Optional<List<String>> readFileAsLines(String resource) {
        try (var file = new InputStreamReader(Util.class.getResourceAsStream(resource))) {
            return Optional.of(file.readAllLines());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<String> readFileAsString(String resource) {
        try (var file = new InputStreamReader(Util.class.getResourceAsStream(resource))) {
            return Optional.of(file.readAllAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Util() {
    }
}
