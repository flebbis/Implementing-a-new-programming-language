// Generated from com/example/minilang/Grammar.g4 by ANTLR 4.13.1
package com.example.minilang;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#separator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeparator(GrammarParser.SeparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DFunc}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDFunc(GrammarParser.DFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DStm}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDStm(GrammarParser.DStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncNoInference}
	 * labeled alternative in {@link GrammarParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncNoInference(GrammarParser.FuncNoInferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncInference}
	 * labeled alternative in {@link GrammarParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncInference(GrammarParser.FuncInferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParamDecl}
	 * labeled alternative in {@link GrammarParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamDecl(GrammarParser.ParamDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(GrammarParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#simpleStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStmt(GrammarParser.SimpleStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#blockStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(GrammarParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(GrammarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(GrammarParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(GrammarParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#doStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStmt(GrammarParser.DoStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfElseIf}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseIf(GrammarParser.IfElseIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfElse}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElse(GrammarParser.IfElseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code If}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(GrammarParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DeclNoInference}
	 * labeled alternative in {@link GrammarParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclNoInference(GrammarParser.DeclNoInferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DeclInference}
	 * labeled alternative in {@link GrammarParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclInference(GrammarParser.DeclInferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InitNoInference}
	 * labeled alternative in {@link GrammarParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitNoInference(GrammarParser.InitNoInferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InitInference}
	 * labeled alternative in {@link GrammarParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitInference(GrammarParser.InitInferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#terminator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminator(GrammarParser.TerminatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(GrammarParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#assignExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(GrammarParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(GrammarParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(GrammarParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(GrammarParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#relational}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational(GrammarParser.RelationalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(GrammarParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#mulExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(GrammarParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#powerExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExpr(GrammarParser.PowerExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(GrammarParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#postfixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(GrammarParser.PostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(GrammarParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expSeparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpSeparator(GrammarParser.ExpSeparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#paramSeparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamSeparator(GrammarParser.ParamSeparatorContext ctx);
}