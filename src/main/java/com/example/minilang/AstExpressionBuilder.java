package com.example.minilang;

public class AstExpressionBuilder extends GrammarBaseVisitor<Ast.Exp> {

    @Override
    public Ast.Exp visitExp(GrammarParser.ExpContext ctx) {
        return visit(ctx.assignExpr());
    }

    @Override
    public Ast.Exp visitAssignExpr(GrammarParser.AssignExprContext ctx) {
        // If there's an assignment, leave as unimplemented for now and return the RHS expression
        if (ctx.assignExpr() != null) {
            return visit(ctx.assignExpr());
        }
        return visit(ctx.orExpr());
    }

    @Override
    public Ast.Exp visitOrExpr(GrammarParser.OrExprContext ctx) {
        // flatten binary chains conservatively: return first child for now
        return visit(ctx.andExpr(0));
    }

    @Override
    public Ast.Exp visitAndExpr(GrammarParser.AndExprContext ctx) {
        return visit(ctx.equalityExpr(0));
    }

    @Override
    public Ast.Exp visitEqualityExpr(GrammarParser.EqualityExprContext ctx) {
        return visit(ctx.relational(0));
    }

    @Override
    public Ast.Exp visitRelational(GrammarParser.RelationalContext ctx) {
        return visit(ctx.addExpr(0));
    }

    @Override
    public Ast.Exp visitAddExpr(GrammarParser.AddExprContext ctx) {
        return visit(ctx.mulExpr(0));
    }

    @Override
    public Ast.Exp visitMulExpr(GrammarParser.MulExprContext ctx) {
        return visit(ctx.powerExpr(0));
    }

    @Override
    public Ast.Exp visitPowerExpr(GrammarParser.PowerExprContext ctx) {
        return visit(ctx.unaryExpr());
    }

    @Override
    public Ast.Exp visitUnaryExpr(GrammarParser.UnaryExprContext ctx) {
        if (ctx.postfixExpr() != null) return visit(ctx.postfixExpr());
        // unimplemented unary op
        return visitChildren(ctx);
    }

    @Override
    public Ast.Exp visitPostfixExpr(GrammarParser.PostfixExprContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public Ast.Exp visitPrimary(GrammarParser.PrimaryContext ctx) {
        if (ctx.INT() != null) {
            int v = Integer.parseInt(ctx.INT().getText());
            return new Ast.EInt(v, Ast.Type.TInt, new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
        }
        if (ctx.DOUBLE() != null) {
            double v = Double.parseDouble(ctx.DOUBLE().getText());
            return new Ast.EDouble(v, Ast.Type.TDouble, new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
        }
        if (ctx.STRING() != null) {
            String s = ctx.STRING().getText();
            return new Ast.EString(s, Ast.Type.TString, new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
        }
        if (ctx.BOOL() != null) {
            boolean b = Boolean.parseBoolean(ctx.BOOL().getText());
            return new Ast.EBool(b, Ast.Type.TBool, new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
        }
        if (ctx.ID() != null) {
            return new Ast.EId(ctx.ID().getText(), Ast.Type.TUnknown, new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
        }
        if (ctx.exp() != null) {
            return visit(ctx.exp());
        }
        return null;
    }
}
