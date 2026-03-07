package com.example.minilang.typechecker;

import com.example.minilang.Pos;

public class TypeException extends RuntimeException {
    public TypeException(String message, Pos pos) {
        super("TypeException: " + message + " at line:" + pos.line + " column:" + pos.column);
    }

    public TypeException(String message) {
        super("TypeException: " + message);
    }
}
