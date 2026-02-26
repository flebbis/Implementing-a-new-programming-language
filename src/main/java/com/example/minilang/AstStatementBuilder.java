package com.example.minilang;

import java.util.ArrayList;
import java.util.List;

public class AstStatementBuilder extends GrammarBaseVisitor<Ast.Stmt> {

    AstExpressionBuilder astExpressionBuilder = new AstExpressionBuilder();

    @Override
    public Ast.Stmt visitDStm(GrammarParser.DStmContext ctx) {
        return visit(ctx.stmt());
    }

    @Override
    public Ast.SBlock visitBlock(GrammarParser.BlockContext ctx) {
         List<Ast.Stmt> stmts = new ArrayList<>();
         for(GrammarParser.StmtContext stmt : ctx.stmt()) {
             stmts.add(visit(stmt));
         }
         Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
         return new Ast.SBlock(stmts, pos);
    }

    @Override
    public Ast.SReturn visitReturnStmt(GrammarParser.ReturnStmtContext ctx) {
        Ast.Exp exp = null;
        if(ctx.exp() != null) {
            exp = astExpressionBuilder.visitExp(ctx.exp());
        }
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SReturn(exp, pos);
    }

    @Override
    public Ast.SWhile visitWhileStmt(GrammarParser.WhileStmtContext ctx) {
        Ast.Exp condition = astExpressionBuilder.visitExp(ctx.exp());
        Ast.Stmt body = visit(ctx.stmt());
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SWhile(condition, body, pos);
    }

}
