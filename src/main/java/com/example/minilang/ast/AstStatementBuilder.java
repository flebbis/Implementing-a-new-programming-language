package com.example.minilang.ast;

import com.example.minilang.GrammarBaseVisitor;
import com.example.minilang.GrammarParser;
import com.example.minilang.Pos;
import com.example.minilang.TypeConverter;

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
         if(ctx.stmt() != null) {
             for(GrammarParser.StmtContext stmt : ctx.stmt()) {
                 stmts.add(visit(stmt));
             }
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

        // Default to unknown if no type annotation is given
        Ast.Type type = new Ast.TUnknown();
        if (ctx.typeAnnotation() != null) {
            type = resolveTypeAnnotation(ctx.typeAnnotation());
        }
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SDecl(type, id, pos);
    }

    @Override
    public Ast.SInit visitInit(GrammarParser.InitContext ctx) {
        String id = ctx.ID().getText();

        // Default to unknown if no type annotation is given
        Ast.Type type = new Ast.TUnknown();
        if (ctx.typeAnnotation() != null) {
            type = resolveTypeAnnotation(ctx.typeAnnotation());
        }

        Ast.Exp exp = astExpressionBuilder.visitExp(ctx.exp());
        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SInit(type, id, exp, pos);
    }

    /**
     * Converts a typeAnnotation parse tree node into an Ast.Type.
     * Handles both simple types (int, double, ...) and array types ([int], [double], ...).
     */
    private Ast.Type resolveTypeAnnotation(GrammarParser.TypeAnnotationContext ctx) {
        if (ctx instanceof GrammarParser.SimpleTypeContext simple) {
            return TypeConverter.mapType(simple.TYPE().getText());
        } else if (ctx instanceof GrammarParser.ArrayTypeContext array) {
            Ast.Type elementType = TypeConverter.mapType(array.TYPE().getText());
            return new Ast.TArray(elementType);
        }
        return new Ast.TUnknown();
    }

    @Override
    public Ast.SIf visitIfStmt(GrammarParser.IfStmtContext ctx) {
        Ast.Exp condition = astExpressionBuilder.visitExp(ctx.exp());
        Ast.Stmt thenBranch = visit(ctx.block(0));

        Ast.Stmt elseBranch = null;

        // Check for 'else if'
        if (ctx.ifStmt() != null) {
            elseBranch = visit(ctx.ifStmt());
        }
        // Check for 'else' block
        // We only have a second block (index 1) if an 'else { ... }' part exists
        else if (ctx.block().size() > 1) {
            elseBranch = visit(ctx.block(1));
        }

        Pos pos = new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
        return new Ast.SIf(condition, thenBranch, elseBranch, pos);
    }

    @Override
    public Ast.SExp visitExp(GrammarParser.ExpContext ctx) {
        return new Ast.SExp(astExpressionBuilder.visitExp(ctx), new Pos(ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
    }

}
