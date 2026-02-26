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

    @Override
    public Ast.SDo visitDoStmt(GrammarParser.DoStmtContext ctx) {
        Ast.Exp times = astExpressionBuilder.visitExp(ctx.exp());
        Ast.Stmt body = visit(ctx.stmt());
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SDo(times, body, pos);
    }

    @Override
    public Ast.SDecl visitDecl(GrammarParser.DeclContext ctx) {
        String id = ctx.ID().getText();

        Ast.Type type = Ast.Type.TUnknown; // default to unknown if no type is specified
        if(ctx.TYPE() != null) {
            type = TypeConverter.mapType(ctx.TYPE().getText());
        }
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SDecl(type, id, pos);
    }

    @Override
    public Ast.SInit visitInit(GrammarParser.InitContext ctx) {
        String id = ctx.ID().getText();

        Ast.Type type = Ast.Type.TUnknown; // default to unknown if no type is specified
        if(ctx.TYPE() != null) {
            type = TypeConverter.mapType(ctx.TYPE().getText());
        }

        Ast.Exp exp = astExpressionBuilder.visitExp(ctx.exp());
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SInit(type, id, exp, pos);
    }

    @Override
    public Ast.SIf visitIfStmt(GrammarParser.IfStmtContext ctx) {
        Ast.Exp condition = astExpressionBuilder.visitExp(ctx.exp());
        Ast.Stmt body = visit(ctx.block(0));
        Ast.Stmt elseBranch = null;
//        if (ctx. != null) {
//            elseBranch = visit(ctx.elsePart);
//        }
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SIf(condition, body, elseBranch, pos);
    }



}
