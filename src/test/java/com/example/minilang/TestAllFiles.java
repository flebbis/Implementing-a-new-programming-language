package com.example.minilang;


import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestAllFiles {

    @Test
    void allGoodSyntaxFilesShouldParse() throws Exception {
        Path dir = Paths.get("src/test/resources/syntax/good");

        try (var files = Files.list(dir)) {
            files.forEach(p ->
                    {
                        System.out.println("Testing GOOD SYNTAX file: " + p);
                        assertDoesNotThrow(() -> Compiler.parseFile(p), "Parsing should not throw for file: " + p)  ;
                    }
            );
        }
    }

    @Test
    public void badSyntaxFilesShouldFail() throws Exception {
        Path dir = Paths.get("src/test/resources/syntax/bad");

        try (var files = Files.list(dir)) {
            files.forEach(p -> {
                System.out.println("Testing BAD SYNTAX file: " + p);
                assertThrows(
                        Exception.class,
                        () -> Compiler.parseFile(p)
                );
                    }
            );
        }
    }

    @Test
    void allGoodTypeFilesShouldParse() throws Exception {
        Path dir = Paths.get("src/test/resources/type/good");

        try (var files = Files.list(dir)) {
            files.forEach(p ->
                    {
                        System.out.println("Testing GOOD TYPE file: " + p);
                        assertDoesNotThrow(() -> Compiler.parseFile(p), "Parsing should not throw for file: " + p)  ;
                    }
            );
        }
    }

    @Test
    public void badTypeFilesShouldFail() throws Exception {
        Path dir = Paths.get("src/test/resources/type/bad");

        try (var files = Files.list(dir)) {
            files.forEach(p -> {
                        System.out.println("Testing BAD TYPE file: " + p);
                        assertThrows(
                                Exception.class,
                                () -> Compiler.parseFile(p)
                        );
                    }
            );
        }
    }
}
