package com.example.minilang; // Builder Visitor

import com.example.minilang.GrammarParser.*;

/**
 * Transforms the raw Parse Tree (CST) into our nice, clean AST.
 */
public class AstBuilderVisitor extends GrammarBaseVisitor<Ast.Exp> {

    @Override
    public Ast.Exp visitProgram(ProgramContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public Ast.Exp visitInt(IntContext ctx) {
        // Convert the string "123" into the integer 123
        int value = Integer.parseInt(ctx.INT().getText());
        return new Ast.EInt(value);
    }

    @Override
    public Ast.Exp visitAddSub(AddSubContext ctx) {
        // Recursively build the left and right sides
        Ast.Exp left = visit(ctx.left);
        Ast.Exp right = visit(ctx.right);

        // Determine the operation
        Ast.Op op = (ctx.PLUS() != null) ? Ast.Op.ADD : Ast.Op.SUB;

        return new Ast.EOpp(left, right, op);
    }

    @Override
    public Ast.Exp visitMulDiv(MulDivContext ctx) {
        Ast.Exp left = visit(ctx.left);
        Ast.Exp right = visit(ctx.right);

        Ast.Op op = (ctx.MULTIPLY() != null) ? Ast.Op.MUL : Ast.Op.DIV;

        return new Ast.EOpp(left, right, op);
    }

    @Override
    public Ast.Exp visitParensExp(ParensExpContext ctx) {
        // Just unwrap it! The valid AST is inside.
        return visit(ctx.exp());
    }
}

