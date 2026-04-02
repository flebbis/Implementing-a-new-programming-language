package com.example.minilang.typechecker;

import com.example.minilang.Pos;
import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.List;

public class Binding {
    public enum Kind {
        VARIABLE,
        PARAMETER,
        FUNCTION_RETURN,
        CALL_RESULT,
        ARRAY_LITERAL,
        ARRAY_ELEMENT
    }

    public final String id;       // unique symbol id
    public final String name;     // x, y, a, hello, etc.
    public final Kind kind;
    public final Pos pos;
    public final int scopeLevel;

    public Ast.Type declaredType; // what user wrote, if any
    public Ast.Type inferredType; // current computed type
    public boolean explicit;      // true if user wrote a type

    public final List<String> dependencies = new ArrayList<>(); // what this binding depends on
    public final List<String> dependents = new ArrayList<>();    // who depends on this binding

    public Binding(String id, String name, Kind kind, Pos pos, int scopeLevel,
                   Ast.Type declaredType, Ast.Type inferredType, boolean explicit) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.pos = pos;
        this.scopeLevel = scopeLevel;
        this.declaredType = declaredType;
        this.inferredType = inferredType;
        this.explicit = explicit;
    }
}
