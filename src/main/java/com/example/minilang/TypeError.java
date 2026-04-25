package com.example.minilang;

import java.util.Objects;

public class TypeError {
    private String message;
    private int line;
    private int column;
    private int endLine;
    private int endColumn;
    private String severity;

    public TypeError(String message, int line, int column) {
        this.message = message;
        this.line = line;
        this.column = column;
        this.endLine = line;
        this.endColumn = column + 1;
        this.severity = "error";
    }

    public String getMessage() { return message; }
    public int getLine() { return line; }
    public int getColumn() { return column; }
    public int getEndLine() { return endLine; }
    public int getEndColumn() { return endColumn; }
    public String getSeverity() { return severity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeError)) return false;
        TypeError that = (TypeError) o;
        return line == that.line &&
                column == that.column &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, line, column);
    }

    @Override
    public String toString() {
        return "TypeError{" +
                "message='" + message + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }

}
