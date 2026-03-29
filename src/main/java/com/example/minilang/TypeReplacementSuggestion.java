package com.example.minilang;

public record TypeReplacementSuggestion (
    String name,
    String currentType,
    int line,
    int column,
    int endLine,
    int endColumn,
    String newType)

{}
