// Generated from /Users/aishamohamed/Kandidatarbete/Implementing-a-new-programming-language/src/main/antlr4/com/example/minilang/Grammar.g4 by ANTLR 4.13.1
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
}