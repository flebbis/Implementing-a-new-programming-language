package com.example.minilang.typechecker;

public class TypeError {
    String message;
    int line;
    int column;
    int endLine;
    int endColumn;
    String severity;

    TypeError(String message, int line, int column) {
        this.message = message;
        this.line = line;
        this.column = column;
        this.endLine = line;
        this.endColumn = column + 1;
        this.severity = "error";
    }

}
