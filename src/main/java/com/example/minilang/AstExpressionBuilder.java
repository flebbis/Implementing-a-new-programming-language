package com.example.minilang;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class AstExpressionBuilder extends GrammarBaseVisitor<Ast.Exp> {

    @Override
    public Ast.Exp visitExp(GrammarParser.ExpContext ctx) {
        return visit(ctx.assignExpr());
    }

    @Override
    public Ast.Exp visitAssignExpr(GrammarParser.AssignExprContext ctx) {
        Ast.Exp left = visit(ctx.orExpr());

        // Right is also an assignExpr, check if present
        if (ctx.assignExpr() != null) {

            Ast.Exp right = visit(ctx.assignExpr());
            if(left instanceof Ast.EId id) {
                Ast.Type type = id.type();
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
                if (ctx.ASSIGN() != null) {
                    return new Ast.EAss(id.name(), right, type, pos);
                } else if (ctx.PLUS_ASSIGN() != null) {
                    return new Ast.EPlusAss(id.name(), right, type, pos);
                } else if (ctx.MINUS_ASSIGN() != null) {
                    return new Ast.EMinusAss(id.name(), right, type, pos);
                } else if (ctx.DIV_ASSIGN() != null) {
                    return new Ast.EDivAss(id.name(), right, type, pos);
                } else if (ctx.MULT_ASSIGN() != null) {
                    return new Ast.EMultAss(id.name(), right, type, pos);
                }
            }
        }
        return left;
    }

@Override
public Ast.Exp visitPrimary(GrammarParser.PrimaryContext ctx) {
    Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
    
    if (ctx.exp() != null) {
        return visit(ctx.exp());
    } else if (ctx.INT() != null) {
        return new Ast.EInt(Integer.parseInt(ctx.INT().getText()), Ast.Type.TInt, pos);
    } else if (ctx.ID() != null) {
        return new Ast.EId(ctx.ID().getText(), Ast.Type.TUnknown, pos);
    }
    
    return null;
}

    @Override
    public Ast.Exp aggregateResult(Ast.Exp aggregate, Ast.Exp nextResult) {
        return nextResult != null ? nextResult : aggregate;
    }

}
