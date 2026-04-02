package com.example.minilang.typechecker;

import com.example.minilang.Pos;

public class TypeException extends RuntimeException {
    public TypeException(String message, Pos pos) {
        super(String.format(
                "Type error: %s (line %d, column %d)",
                message,
                pos.line,
                pos.column
        ));
    }

    public TypeException(String message) {
        super("Type error: " + message);
    }
}
