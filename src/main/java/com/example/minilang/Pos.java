package com.example.minilang;

public class Pos {
    private final int line;
    private final int column;

    public Pos(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine()  {
        return line;
    }

    public int getColumn() {
        return column;
    }
}

