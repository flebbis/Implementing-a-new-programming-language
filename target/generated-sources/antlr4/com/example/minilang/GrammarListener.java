// Generated from com/example/minilang/Grammar.g4 by ANTLR 4.13.1
package com.example.minilang;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
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
	 * Enter a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAndExp(GrammarParser.AndExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAndExp(GrammarParser.AndExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParensExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterParensExp(GrammarParser.ParensExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParensExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitParensExp(GrammarParser.ParensExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(GrammarParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(GrammarParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(GrammarParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(GrammarParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Relational}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterRelational(GrammarParser.RelationalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Relational}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitRelational(GrammarParser.RelationalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code String}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterString(GrammarParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code String}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitString(GrammarParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterDivAssignExp(GrammarParser.DivAssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitDivAssignExp(GrammarParser.DivAssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExp(GrammarParser.AssignmentExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExp(GrammarParser.AssignmentExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Double}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterDouble(GrammarParser.DoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Double}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitDouble(GrammarParser.DoubleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterInt(GrammarParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitInt(GrammarParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIdExp(GrammarParser.IdExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIdExp(GrammarParser.IdExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PlusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPlusAssignExp(GrammarParser.PlusAssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PlusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPlusAssignExp(GrammarParser.PlusAssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterOrExp(GrammarParser.OrExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitOrExp(GrammarParser.OrExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MinusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMinusAssignExp(GrammarParser.MinusAssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MinusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMinusAssignExp(GrammarParser.MinusAssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Power}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPower(GrammarParser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Power}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPower(GrammarParser.PowerContext ctx);
}