package com.example.minilang.typechecker;

import com.example.minilang.Pos;

public class TypeException extends RuntimeException {
    private final String expected;
    private final String actual;
    private final Pos pos;
    private final String errorMessage;

    public TypeException(String message, Pos pos) {
        super(message);
        this.errorMessage = message;
        this.expected = null;
        this.actual = null;
        this.pos = pos;
    }

    public TypeException(String message, String expected, String actual, Pos pos) {
        super(message);
        this.errorMessage = message;
        this.expected = expected;
        this.actual = actual;
        this.pos = pos;
    }

    public TypeException(String message) {
        super(message);
        this.errorMessage = message;
        this.expected = null;
        this.actual = null;
        this.pos = null;
    }

    public String getExpected() {
        return expected;
    }
    public String getActual() {
        return actual;
    }
    public Pos getPos() {
        return pos;
    }

    @Override
    public String getMessage() {
        if (pos == null) {
            return "Type Error: " + errorMessage;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Type Error: "). append((errorMessage));

        if (expected != null && actual != null) {
            sb.append("\nExpected: ").append(expected);
            sb.append("\nActual: ").append(actual);
        }
        sb.append("\nAt line ").append(pos.line).append(", column ").append(pos.column);
        return sb.toString();
    }
}
