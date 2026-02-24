package com.example.minilang;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class TestSpecificFile {

    @Test
    public void parseInputFileIfProvided() throws Exception {
        String input = System.getProperty("input");

        if (input == null) {
            return;
        }

        Path path = Paths.get(input);

        try {
            Compiler.parseFile(path);
        } catch (Exception e) {
            fail("Failed to parse file: " + path + "\n" + e.getMessage());
        }
    }
}
