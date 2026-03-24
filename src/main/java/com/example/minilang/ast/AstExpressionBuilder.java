package com.example.minilang.ast;

import com.example.minilang.GrammarBaseVisitor;
import com.example.minilang.GrammarParser;
import com.example.minilang.Pos;
import com.example.minilang.TypeConverter;

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
                    return new Ast.EAss(id.name(), right, Ast.AssOp.ASSIGN, type, pos);
                } else if (ctx.PLUS_ASSIGN() != null) {
                    return new Ast.EAss(id.name(), right, Ast.AssOp.PLUS_ASSIGN, type, pos);
                } else if (ctx.MINUS_ASSIGN() != null) {
                    return new Ast.EAss(id.name(), right, Ast.AssOp.MINUS_ASSIGN, type, pos);
                } else if (ctx.DIV_ASSIGN() != null) {
                    return new Ast.EAss(id.name(), right, Ast.AssOp.DIV_ASSIGN, type, pos);
                } else if (ctx.MULT_ASSIGN() != null) {
                    return new Ast.EAss(id.name(), right, Ast.AssOp.MULT_ASSIGN, type, pos);
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
            left = new Ast.ELogic(left, right, Ast.LogicOp.OR, new Ast.TBool(), pos);
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
                left = new Ast.ELogic(left, right, Ast.LogicOp.AND, new Ast.TBool(), pos);
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
                    left = new Ast.ECmp(left, right, Ast.CmpOp.EQ, new Ast.TBool(), pos);
                } else if (ctx.NE(i - 1) != null) {
                    left = new Ast.ECmp(left, right, Ast.CmpOp.NE, new Ast.TBool(), pos);
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
                    left = new Ast.ECmp(left, right, Ast.CmpOp.LT, new Ast.TBool(), pos);
                } else if (ctx.GT(i - 1) != null) {
                    left = new Ast.ECmp(left, right, Ast.CmpOp.GT, new Ast.TBool(), pos);
                } else if (ctx.LE(i - 1) != null) {
                    left = new Ast.ECmp(left, right, Ast.CmpOp.LE, new Ast.TBool(), pos);
                } else if (ctx.GE(i - 1) != null) {
                    left = new Ast.ECmp(left, right, Ast.CmpOp.GE, new Ast.TBool(), pos);
                }
            }
        }
        return left;
    }

    @Override
    public Ast.Exp visitAddExpr(GrammarParser.AddExprContext ctx) {
        Ast.Exp left = visit(ctx.mulExpr(0));

        if (ctx.mulExpr(1) != null) {
            for (int i = 1; i < ctx.mulExpr().size(); i++) {
                Ast.Exp right = visit(ctx.mulExpr(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

                if (ctx.PLUS(i - 1) != null) {
                    left = new Ast.EOpp(left, right, Ast.Op.ADD, new Ast.TInt(), pos);
                } else if (ctx.MINUS(i - 1) != null) {
                    left = new Ast.EOpp(left, right, Ast.Op.SUB, new Ast.TInt(), pos);
                }
            }
        }
        return left;
    }

    @Override
    public Ast.Exp visitMulExpr(GrammarParser.MulExprContext ctx) {
        Ast.Exp left = visit(ctx.powerExpr(0));

        if (ctx.powerExpr().size() > 1) {
            for (int i = 1; i < ctx.powerExpr().size(); i++) {
                Ast.Exp right = visit(ctx.powerExpr(i));
                Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

                Ast.Op op = Ast.Op.MUL;

                if (ctx.DIVIDE(i - 1) != null) {
                    op = Ast.Op.DIV;
                } else if (ctx.MODULO(i - 1) != null) {
                    op = Ast.Op.MOD;
                }

                left = new Ast.EOpp(left, right, op, new Ast.TInt(), pos);
            }
        }

        return left;
    }

    @Override
    public Ast.Exp visitPowerExpr(GrammarParser.PowerExprContext ctx) {
        Ast.Exp left = visit(ctx.unaryExpr());
        if (ctx.POWER() != null) {
            Ast.Exp right = visit(ctx.powerExpr());
            Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
            return new Ast.EPower(left, right, new Ast.TInt(), pos);
        }
        return left;
    }
    @Override
    public Ast.Exp visitUnaryExpr(GrammarParser.UnaryExprContext ctx) {
        if(ctx.postfixExpr() != null) { // if there is no unary operator, just return the postfix expression
            return visit(ctx.postfixExpr());
        }

        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        Ast.Exp exp = visit(ctx.unaryExpr()); // kod duplicering oops
        return new Ast.ENot(exp, exp.type() , pos);
    }


    @Override
    public Ast.Exp visitPostfixExpr(GrammarParser.PostfixExprContext ctx) {
        Ast.Exp expr = visit(ctx.primary());

        for(GrammarParser.PostFixOpContext op : ctx.postFixOp()) {
            if(op.INC() != null) {
                expr = new Ast.EUnary(expr, Ast.UnaryOp.INC, expr.type(), new Pos(op.getStart().getLine(), op.getStart().getCharPositionInLine()));
            } else if(op.DEC() != null) {
                expr = new Ast.EUnary(expr, Ast.UnaryOp.DEC, expr.type(), new Pos(op.getStart().getLine(), op.getStart().getCharPositionInLine()));
            } else if(op.DYNARR_START() != null) {
                Ast.Exp index = visit(op.exp());
                expr = new Ast.EArrayIndex(expr, index, expr.type(), new Pos(op.getStart().getLine(), op.getStart().getCharPositionInLine()));
            } else if (op.PARAM_START() != null && op.PARAM_END() != null) {
                // function call
                List<Ast.Exp> args = new ArrayList<>();
                for(GrammarParser.ExpContext expCtx : op.expSeparator().exp()) {
                    args.add(visit(expCtx));
                }
                // Should always be an EId if it's a function call, but we can check to be safe
                if (expr instanceof Ast.EId id) {
                    expr = new Ast.ECall(id.name(), args, expr.type(), new Pos(op.getStart().getLine(), op.getStart().getCharPositionInLine()));
                } else {
                    throw new IllegalArgumentException("Function call target must be an identifier");
                }
            } else {
                // Append array operation
                Ast.Exp exp = visit(op.exp());
                return new Ast.EAppend(expr, exp, expr.type(), new Pos(op.getStart().getLine(), op.getStart().getCharPositionInLine()));
            }
        }
        return expr;
    }



    // --- Primary expression visitors (one per labeled alternative in the grammar) ---

    @Override
    public Ast.Exp visitPrimaryInt(GrammarParser.PrimaryIntContext ctx) {
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.EInt(Integer.parseInt(ctx.INT().getText()), new Ast.TInt(), pos);
    }

    @Override
    public Ast.Exp visitPrimaryDouble(GrammarParser.PrimaryDoubleContext ctx) {
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.EDouble(Double.parseDouble(ctx.DOUBLE().getText()), new Ast.TDouble(), pos);
    }

    @Override
    public Ast.Exp visitPrimaryString(GrammarParser.PrimaryStringContext ctx) {
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.EString(ctx.STRING().getText(), new Ast.TString(), pos);
    }

    @Override
    public Ast.Exp visitPrimaryBool(GrammarParser.PrimaryBoolContext ctx) {
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.EBool(Boolean.parseBoolean(ctx.BOOL().getText()), new Ast.TBool(), pos);
    }

    @Override
    public Ast.Exp visitPrimaryId(GrammarParser.PrimaryIdContext ctx) {
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.EId(ctx.ID().getText(), new Ast.TUnknown(), pos);
    }

    @Override
    public Ast.Exp visitPrimaryParen(GrammarParser.PrimaryParenContext ctx) {
        // Unwrap the parentheses and return the inner expression
        return visit(ctx.exp());
    }

    @Override
    public Ast.Exp visitPrimaryArrayLiteral(GrammarParser.PrimaryArrayLiteralContext ctx) {
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());

        // Collect all element expressions from the comma-separated list
        List<Ast.Exp> elements = new ArrayList<>();
        int arraySize = 0;
        if (ctx.expSeparator() != null) {
            for (GrammarParser.ExpContext expCtx : ctx.expSeparator().exp()) {
                elements.add(visit(expCtx));
                arraySize++;
            }
        }

        // Element type is unknown until the typechecker resolves it
        return new Ast.EArray(elements, new Ast.TArray(new Ast.TUnknown(), arraySize), pos);
    }


}
