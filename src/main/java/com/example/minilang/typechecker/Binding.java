package com.example.minilang.typechecker;

import com.example.minilang.Pos;
import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.List;

public class Binding {
    public enum Kind { VARIABLE, PARAMETER }

    public final String id;
    public final String name;
    public final Kind kind;
    public final Pos pos;
    public final int scopeLevel;
    public Ast.Type declaredType;
    public Ast.Type inferredType;
    public final boolean explicit;

    public final List<String> dependencies = new ArrayList<>();
    public final List<String> dependents = new ArrayList<>();

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
    @Override
    public String toString() {
        return "Binding[name=" + name + ", inferredType=" + inferredType
                + ", dependencies=" + dependencies + ", dependents=" + dependents + "]";
    }
}