package com.example.minilang;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MiniLanguageMain {
    public static void main(String[] args) throws IOException {
        String path;
        String testFilePath = "src/test/resources/";

        if(args.length != 1) {
             path = testFilePath + "syntax/good/good-1.ml";

        } else if (args.length == 1){
             path = args[0];
        } else {
            System.err.println("Either provide a file path as an argument or no arguments to use the default file.");
            System.exit(1);
            return;
        }
        Compiler.parseFile(Path.of(path));
    }
}