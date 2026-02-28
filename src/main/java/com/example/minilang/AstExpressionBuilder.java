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
        Ast.Exp left = visit(ctx.equalityExpr(0));
        if (ctx.equalityExpr(1) != null) {
            for (int i = 1; i < ctx.equalityExpr().size(); i++) {
                Ast.Exp right = visit(ctx.equalityExpr(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
                left = new Ast.EAnd(left, right, Ast.Type.TBool, pos);
            }
        }

        return left; //venne ens om detta e rätt return
    }

    @Override
    public Ast.Exp visitEqualityExpr(GrammarParser.EqualityExprContext ctx) {
        Ast.Exp left = visit(ctx.relational(0));
        if (ctx.relational(1) != null) {
            for (int i = 1; i < ctx.relational().size(); i++) {
                Ast.Exp right = visit(ctx.relational(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

                if (ctx.EQ(i - 1) != null) {
                    left = new Ast.EEq(left, right, Ast.Type.TBool, pos);
                } else if (ctx.NE(i - 1) != null) {
                    left = new Ast.ENe(left, right, Ast.Type.TBool, pos);
                }
            }
        }
        return left;
    }

    @Override
    public Ast.Exp visitRelational(GrammarParser.RelationalContext ctx) {
        Ast.Exp left = visit(ctx.addExpr(0));
        if (ctx.addExpr(1) != null) {
            for (int i = 1; i < ctx.addExpr().size(); i++) {
                Ast.Exp right = visit(ctx.addExpr(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

                if (ctx.LT(i - 1) != null) {
                    left = new Ast.ELt(left, right, Ast.Type.TBool, pos);
                } else if (ctx.GT(i - 1) != null) {
                    left = new Ast.EGt(left, right, Ast.Type.TBool, pos);
                } else if (ctx.LE(i - 1) != null) {
                    left = new Ast.ELe(left, right, Ast.Type.TBool, pos);
                } else if (ctx.GE(i - 1) != null) {
                    left = new Ast.EGe(left, right, Ast.Type.TBool, pos);
                }
            }
        }
        return left;
    }

    @Override
    public Ast.Exp visitAddExpr(GrammarParser.AddExprContext ctx) {
        Ast.Exp left = visit(ctx.mulExpr(0));

        if (ctx.mulExpr(1) != null) {
        // Iterate through the rest of the expressions
            for (int i = 1; i < ctx.mulExpr().size(); i++) {
                Ast.Exp right = visit(ctx.mulExpr(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

                // Check which operator was used at this position
                // The list of operators is at index i-1 relative to the right operand loop
                if (ctx.PLUS(i - 1) != null) {
                    left = new Ast.EOpp(left, right, Ast.Op.ADD, Ast.Type.TInt, pos);
                } else if (ctx.MINUS(i - 1) != null) {
                    left = new Ast.EOpp(left, right, Ast.Op.SUB, Ast.Type.TInt, pos);
                }
            }
        }
        return left;
    }

    @Override
    public Ast.Exp visitMulExpr(GrammarParser.MulExprContext ctx) {
        Ast.Exp left = visit(ctx.powerExpr(0));
        if (ctx.powerExpr(1) != null) {
            // Loop starts at 1
            for (int i = 1; i < ctx.powerExpr().size(); i++) {
                Ast.Exp right = visit(ctx.powerExpr(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

                // Check operator (index i-1)
                Ast.Op op = Ast.Op.MUL;
                if (ctx.DIVIDE(i - 1) != null) {
                    op = Ast.Op.DIV;
                }

                left = new Ast.EOpp(left, right, op, Ast.Type.TInt, pos);
            }
        }

        return left;
    }

    @Override
    public Ast.Exp visitPowerExpr(GrammarParser.PowerExprContext ctx) {
        return visit(ctx.unaryExpr());
    }
    @Override
    public Ast.Exp visitUnaryExpr(GrammarParser.UnaryExprContext ctx) {
        //okej lite tankar här!
        //1. istället för left.type kan vi gö en sån INT/DOUBLE wrapper grej
        //2. Venne om returnsen är helt rätt här egentligen, lite trött just nu lol
        Ast.Exp left = visit(ctx.postfixExpr());
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        if (ctx.NOT() != null) {
            Ast.Exp right = visit(ctx.unaryExpr()); // kod duplicering oops
            return new Ast.EOpp(left, right, Ast.Op.NOT ,left.type() , pos);
        }
        if (ctx.PLUS() != null) {
            Ast.Exp right = visit(ctx.unaryExpr());
            return new Ast.EOpp(left, right, Ast.Op.ADD,left.type() , pos);
        }
        if (ctx.MINUS() != null) {
            Ast.Exp right = visit(ctx.unaryExpr());
            return new Ast.EOpp(left, right, Ast.Op.SUB,left.type() , pos);
        }
        return left;
    }
    @Override
    public Ast.Exp visitPostfixExpr(GrammarParser.PostfixExprContext ctx) {
        Ast.Exp left = visit(ctx.primary());
        if (ctx.INC() != null && !ctx.INC().isEmpty()) {
            if (left instanceof Ast.EId id) {
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

        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

        if (ctx.INT() != null) {
            return new Ast.EInt(Integer.parseInt(ctx.INT().getText()), Ast.Type.TInt, pos);
        } else if (ctx.ID() != null) {
            // Check if this is an array assignment (ID ASSIGN ...) which also starts with ID
            // The grammar rule: ID ASSIGN DYNARR_START expSeparator DYNARR_END
            if (ctx.ASSIGN() != null) {
                // Requires implementing array logic later. For now, return null or throw.
                return null;
            }
            return new Ast.EId(ctx.ID().getText(), Ast.Type.TInt, pos);
        } else if (ctx.DOUBLE() != null) {
            return new Ast.EDouble(Double.parseDouble(ctx.DOUBLE().getText()), Ast.Type.TDouble, pos);
        } else if (ctx.STRING() != null) {
            return new Ast.EString(ctx.STRING().getText(), Ast.Type.TString, pos);
        } else if (ctx.BOOL() != null) {
            return new Ast.EBool(Boolean.parseBoolean(ctx.BOOL().getText()), Ast.Type.TBool, pos);
        } else if (ctx.exp() != null) {
            // Handle parenthesized expression: '(' exp ')'
            // Just return the inner expression
            return visit(ctx.exp());
        } else if (ctx.getStart().getText().equals("[")) {
            // Handle array declaration/init: '[' TYPE ']' ID ...
            // Pending implementation
        }

        return null;
    }

}