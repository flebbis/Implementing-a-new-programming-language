package com.example.minilang.typechecker;

import com.example.minilang.ast.Ast;

import java.util.List;

public class Signature {
    public final String name;
    public final Ast.Type returnType;
    public final List<Ast.Type> paramTypes;

    public Signature(String name, Ast.Type returnType, List<Ast.Type> paramTypes) {
        this.name = name;
        this.returnType = returnType;
        this.paramTypes = paramTypes;
    }
}
