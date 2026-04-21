package com.example.minilang;
// Java side - TypeError.java


public record TypeError(
    String message,
    int line,
    int column,
    int endLine,
    int endColumn,
    String severity  // "error" or "warning"
) {}