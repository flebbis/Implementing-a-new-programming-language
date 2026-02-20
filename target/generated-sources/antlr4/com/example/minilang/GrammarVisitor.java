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
	 * Visit a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DStm}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDStm(GrammarParser.DStmContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DFunc}
	 * labeled alternative in {@link GrammarParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDFunc(GrammarParser.DFuncContext ctx);
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
	 * Visit a parse tree produced by the {@code SExp}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSExp(GrammarParser.SExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SReturn}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSReturn(GrammarParser.SReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SWhile}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSWhile(GrammarParser.SWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SDo}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSDo(GrammarParser.SDoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SIfElse}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSIfElse(GrammarParser.SIfElseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SBlock}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSBlock(GrammarParser.SBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SDecl}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSDecl(GrammarParser.SDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SInit}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSInit(GrammarParser.SInitContext ctx);
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
	 * Visit a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(GrammarParser.BlockContext ctx);
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
	 * Visit a parse tree produced by the {@code EPlusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEPlusAssignExp(GrammarParser.EPlusAssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EMulDiv}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEMulDiv(GrammarParser.EMulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EDivAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEDivAssignExp(GrammarParser.EDivAssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DynamicArrayNoInferrence}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDynamicArrayNoInferrence(GrammarParser.DynamicArrayNoInferrenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EFunctionCall}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEFunctionCall(GrammarParser.EFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EAssignmentExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEAssignmentExp(GrammarParser.EAssignmentExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EOr}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEOr(GrammarParser.EOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EInt}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEInt(GrammarParser.EIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EParensExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEParensExp(GrammarParser.EParensExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EIdExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEIdExp(GrammarParser.EIdExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EAddSub}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEAddSub(GrammarParser.EAddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EMinusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEMinusAssignExp(GrammarParser.EMinusAssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DynamicArrayInference}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDynamicArrayInference(GrammarParser.DynamicArrayInferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EAnd}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEAnd(GrammarParser.EAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EInc}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEInc(GrammarParser.EIncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EPower}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEPower(GrammarParser.EPowerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EDouble}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEDouble(GrammarParser.EDoubleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EDec}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEDec(GrammarParser.EDecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EString}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEString(GrammarParser.EStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EArrayIndexing}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEArrayIndexing(GrammarParser.EArrayIndexingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ERelational}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitERelational(GrammarParser.ERelationalContext ctx);
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