// Generated from com/example/minilang/Grammar.g4 by ANTLR 4.13.1
package com.example.minilang;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#separator}.
	 * @param ctx the parse tree
	 */
	void enterSeparator(GrammarParser.SeparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#separator}.
	 * @param ctx the parse tree
	 */
	void exitSeparator(GrammarParser.SeparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DFunc}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDFunc(GrammarParser.DFuncContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DFunc}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDFunc(GrammarParser.DFuncContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DStm}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDStm(GrammarParser.DStmContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DStm}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDStm(GrammarParser.DStmContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncNoInference}
	 * labeled alternative in {@link GrammarParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFuncNoInference(GrammarParser.FuncNoInferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncNoInference}
	 * labeled alternative in {@link GrammarParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFuncNoInference(GrammarParser.FuncNoInferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncInference}
	 * labeled alternative in {@link GrammarParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFuncInference(GrammarParser.FuncInferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncInference}
	 * labeled alternative in {@link GrammarParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFuncInference(GrammarParser.FuncInferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParamDecl}
	 * labeled alternative in {@link GrammarParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParamDecl(GrammarParser.ParamDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParamDecl}
	 * labeled alternative in {@link GrammarParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParamDecl(GrammarParser.ParamDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(GrammarParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(GrammarParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#simpleStmt}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStmt(GrammarParser.SimpleStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#simpleStmt}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStmt(GrammarParser.SimpleStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(GrammarParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(GrammarParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(GrammarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(GrammarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(GrammarParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(GrammarParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(GrammarParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(GrammarParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#doStmt}.
	 * @param ctx the parse tree
	 */
	void enterDoStmt(GrammarParser.DoStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#doStmt}.
	 * @param ctx the parse tree
	 */
	void exitDoStmt(GrammarParser.DoStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfElseIf}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfElseIf(GrammarParser.IfElseIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfElseIf}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfElseIf(GrammarParser.IfElseIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfElse}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfElse(GrammarParser.IfElseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfElse}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfElse(GrammarParser.IfElseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code If}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIf(GrammarParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code If}
	 * labeled alternative in {@link GrammarParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIf(GrammarParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DeclNoInference}
	 * labeled alternative in {@link GrammarParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDeclNoInference(GrammarParser.DeclNoInferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DeclNoInference}
	 * labeled alternative in {@link GrammarParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDeclNoInference(GrammarParser.DeclNoInferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DeclInference}
	 * labeled alternative in {@link GrammarParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDeclInference(GrammarParser.DeclInferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DeclInference}
	 * labeled alternative in {@link GrammarParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDeclInference(GrammarParser.DeclInferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InitNoInference}
	 * labeled alternative in {@link GrammarParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInitNoInference(GrammarParser.InitNoInferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InitNoInference}
	 * labeled alternative in {@link GrammarParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInitNoInference(GrammarParser.InitNoInferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InitInference}
	 * labeled alternative in {@link GrammarParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInitInference(GrammarParser.InitInferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InitInference}
	 * labeled alternative in {@link GrammarParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInitInference(GrammarParser.InitInferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#terminator}.
	 * @param ctx the parse tree
	 */
	void enterTerminator(GrammarParser.TerminatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#terminator}.
	 * @param ctx the parse tree
	 */
	void exitTerminator(GrammarParser.TerminatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(GrammarParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(GrammarParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#assignExpr}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(GrammarParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#assignExpr}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(GrammarParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(GrammarParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(GrammarParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(GrammarParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(GrammarParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(GrammarParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(GrammarParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#relational}.
	 * @param ctx the parse tree
	 */
	void enterRelational(GrammarParser.RelationalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#relational}.
	 * @param ctx the parse tree
	 */
	void exitRelational(GrammarParser.RelationalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(GrammarParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(GrammarParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(GrammarParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(GrammarParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#powerExpr}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpr(GrammarParser.PowerExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#powerExpr}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpr(GrammarParser.PowerExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(GrammarParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(GrammarParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(GrammarParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(GrammarParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(GrammarParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(GrammarParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expSeparator}.
	 * @param ctx the parse tree
	 */
	void enterExpSeparator(GrammarParser.ExpSeparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expSeparator}.
	 * @param ctx the parse tree
	 */
	void exitExpSeparator(GrammarParser.ExpSeparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#paramSeparator}.
	 * @param ctx the parse tree
	 */
	void enterParamSeparator(GrammarParser.ParamSeparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#paramSeparator}.
	 * @param ctx the parse tree
	 */
	void exitParamSeparator(GrammarParser.ParamSeparatorContext ctx);
}