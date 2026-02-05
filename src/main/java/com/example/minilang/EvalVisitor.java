package com.example.minilang;

import com.example.minilang.GrammarParser.*;

public class EvalVisitor extends GrammarBaseVisitor<Object> {

    @Override
    public Object visitInt(IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Object visitBool(BoolContext ctx) {
        return Boolean.valueOf(ctx.BOOL().getText());
    }

    @Override
    public Object visitAddSub(AddSubContext ctx) {
        int left = (int) visit(ctx.left);   // Casts work because TypeChecker proved they are Ints
        int right = (int) visit(ctx.right);

        if (ctx.PLUS() != null) return left + right;
        return left - right;
    }

    @Override
    public Object visitEq(EqContext ctx) {
        Object left = visit(ctx.left);
        Object right = visit(ctx.right);
        return left.equals(right);
    }

    @Override
    public Object visitProgram(ProgramContext ctx) { return visit(ctx.exp()); }
}
