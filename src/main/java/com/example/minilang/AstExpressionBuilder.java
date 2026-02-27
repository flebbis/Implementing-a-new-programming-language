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
        System.out.println("assign expression");
        Ast.Exp left = visit(ctx.orExpr());

        // Right is also an assignExpr, check if present
        if (ctx.assignExpr() != null) {


            Ast.Exp right = visit(ctx.assignExpr());
            if (left instanceof Ast.EId id) {
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
    public Ast.Exp visitOrExpr(GrammarParser.OrExprContext ctx) {
        System.out.println("or expression");
        Ast.Exp left = visit(ctx.andExpr(0));
        if (ctx.andExpr(1) != null) {
            for (int i = 1; i < ctx.andExpr().size(); i++) {
            Ast.Exp right = visit(ctx.andExpr(i));
            Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
            left = new Ast.EOr(left, right, Ast.Type.TBool, pos);
            }
        }

        return left;
    }

    @Override
    public Ast.Exp visitAndExpr(GrammarParser.AndExprContext ctx) {
        System.out.println("and expression");
        Ast.Exp left = visit(ctx.equalityExpr(0));
        if (ctx.equalityExpr(1) != null) {
            for (int i = 1; i < ctx.equalityExpr().size(); i++) {
                Ast.Exp right = visit(ctx.equalityExpr(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
                left = new Ast.EOr(left, right, Ast.Type.TBool, pos);
            }
        }

        return left;
    }

    @Override
    public Ast.Exp visitEqualityExpr(GrammarParser.EqualityExprContext ctx) {
        System.out.println("equality expression");
        return visit(ctx.relational(0));
    }

    @Override
    public Ast.Exp visitRelational(GrammarParser.RelationalContext ctx) {
        System.out.println("relational expression");
        return visit(ctx.addExpr(0));
    }

    @Override
    public Ast.Exp visitAddExpr(GrammarParser.AddExprContext ctx) {
        System.out.println("add expression");
        return visit(ctx.mulExpr(0));
    }

    @Override
    public Ast.Exp visitMulExpr(GrammarParser.MulExprContext ctx) {
        System.out.println("mul expression");
        return visit(ctx.powerExpr(0));
    }

    @Override
    public Ast.Exp visitPowerExpr(GrammarParser.PowerExprContext ctx) {
        System.out.println("power expression");
        return visit(ctx.unaryExpr());
    }
    @Override
    public Ast.Exp visitUnaryExpr(GrammarParser.UnaryExprContext ctx) {
        System.out.println("unary expression");
        if (ctx.NOT() != null) {
            return visit(ctx.unaryExpr()); // Recursive call for NOT
        }
        if (ctx.PLUS() != null) {
            return visit(ctx.unaryExpr()); // Recursive call for PLUS
        }
        if (ctx.MINUS() != null) {
            return visit(ctx.unaryExpr()); // Recursive call for MINUS
        }
        return visit(ctx.postfixExpr());
    }
    @Override
    public Ast.Exp visitPostfixExpr(GrammarParser.PostfixExprContext ctx) {
        System.out.println("postfix expression");
        Ast.Exp left = visit(ctx.primary());
        if (ctx.INC() != null && !ctx.INC().isEmpty()) {
            System.out.println(ctx.INC());
            if (left instanceof Ast.EId id) {
                System.out.println("hej");
                return new Ast.EInc(id.name(), id.type(), id.pos());
            }
        }
        else if (ctx.DEC() != null && !ctx.DEC().isEmpty()) {
            if (left instanceof Ast.EId id) {
                return new Ast.EDec(id.name(), id.type(), id.pos());
            }
        }
        return left;
    }


    @Override
    public Ast.Exp visitPrimary(GrammarParser.PrimaryContext ctx) {
        System.out.println("primary expression");

        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        if (ctx.INT() != null) {
            return new Ast.EInt(Integer.parseInt(ctx.INT().getText()), Ast.Type.TInt, pos);
        } else if (ctx.ID() != null) {
            return new Ast.EId(ctx.ID().getText(), Ast.Type.TInt, pos);
        }
        else if (ctx.DOUBLE() != null) {
            System.out.println("ad");
            return new Ast.EDouble(Double.parseDouble(ctx.DOUBLE().getText()), Ast.Type.TDouble, pos);
        }
        return null;
    }

}