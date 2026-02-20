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
	 * Visit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExp(GrammarParser.AndExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParensExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParensExp(GrammarParser.ParensExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(GrammarParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(GrammarParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Relational}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational(GrammarParser.RelationalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code String}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(GrammarParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivAssignExp(GrammarParser.DivAssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExp(GrammarParser.AssignmentExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Double}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDouble(GrammarParser.DoubleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(GrammarParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExp(GrammarParser.IdExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PlusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusAssignExp(GrammarParser.PlusAssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExp(GrammarParser.OrExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MinusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusAssignExp(GrammarParser.MinusAssignExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Power}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(GrammarParser.PowerContext ctx);
}