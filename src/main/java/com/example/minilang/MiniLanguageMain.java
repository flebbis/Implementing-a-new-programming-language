package com.example.minilang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MiniLanguageMain {
    public static void main(String[] args) throws IOException {
    String path;

    if (args.length == 0) {
        path = "src/test/resources/type/good/changeType.fika";
    } else if (args.length == 1) {
        path = args[0];
    } else {
        System.err.println("Either provide a file path as an argument or no arguments to use the default file.");
        System.exit(1);
        return;
    }
    Path p = Path.of(path);
    Compiler.parseFile(p, Files.readString(p), "0");
}
}