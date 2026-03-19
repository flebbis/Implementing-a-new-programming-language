package com.example.minilang;

public record InferenceSuggestion(
        String name,
        String inferredType,
        int line,
        int column,
        int endLine,
        int endColumn,
        String replacement)

{}

