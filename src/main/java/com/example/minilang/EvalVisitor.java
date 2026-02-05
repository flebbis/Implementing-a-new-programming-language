package com.example.minilang;

import com.example.minilang.GrammarParser.*;

public class EvalVisitor extends GrammarBaseVisitor<Integer> {

    /**
     * Corresponds to: INT #Int
     */
    @Override
    public Integer visitInt(IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /**
     * Corresponds to: left=exp (PLUS | MINUS) right=exp #AddSub
     */
    @Override
    public Integer visitAddSub(AddSubContext ctx) {
        int left = visit(ctx.left);   // Use the 'left' label
        int right = visit(ctx.right); // Use the 'right' label

        // Since 'op=' was not used in grammar, check existence of token
        if (ctx.PLUS() != null) {
            return left + right;
        } else {
            return left - right;
        }
    }

    /**
     * Corresponds to: left=exp (MULTIPLY | DIVIDE) right=exp #MulDiv
     */
    @Override
    public Integer visitMulDiv(MulDivContext ctx) {
        int left = visit(ctx.left);
        int right = visit(ctx.right);

        if (ctx.MULTIPLY() != null) {
            return left * right;
        } else {
            return left / right;
        }
    }

    /**
     * Entry point
     */
    @Override
    public Integer visitProgram(ProgramContext ctx) {
        return visit(ctx.exp());
    }
}
