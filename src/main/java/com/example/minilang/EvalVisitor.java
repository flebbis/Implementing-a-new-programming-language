package com.example.minilang;

public class EvalVisitor extends GrammarBaseVisitor<Integer> {

    @Override
    public Integer visitExp(GrammarParser.ExpContext ctx) {
        if (ctx.INT() != null) {
            return Integer.valueOf(ctx.INT().getText());
        }

        int left = visit(ctx.exp(0));
        int right = visit(ctx.exp(1));

        if (ctx.MULTIPLY() != null) return left * right;
        if (ctx.DIVIDE() != null) return left / right;
        if (ctx.PLUS() != null) return left + right;
        if (ctx.MINUS() != null) return left - right;

        return 0;
    }
}
