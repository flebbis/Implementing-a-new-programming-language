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
	 * Enter a parse tree produced by the {@code SExp}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSExp(GrammarParser.SExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SExp}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSExp(GrammarParser.SExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SReturn}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSReturn(GrammarParser.SReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SReturn}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSReturn(GrammarParser.SReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SWhile}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSWhile(GrammarParser.SWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SWhile}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSWhile(GrammarParser.SWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SDo}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSDo(GrammarParser.SDoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SDo}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSDo(GrammarParser.SDoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SIfElse}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSIfElse(GrammarParser.SIfElseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SIfElse}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSIfElse(GrammarParser.SIfElseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SBlock}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSBlock(GrammarParser.SBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SBlock}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSBlock(GrammarParser.SBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SDecl}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSDecl(GrammarParser.SDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SDecl}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSDecl(GrammarParser.SDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SInit}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void enterSInit(GrammarParser.SInitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SInit}
	 * labeled alternative in {@link GrammarParser#stm}.
	 * @param ctx the parse tree
	 */
	void exitSInit(GrammarParser.SInitContext ctx);
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
	 * Enter a parse tree produced by the {@code EPlusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEPlusAssignExp(GrammarParser.EPlusAssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EPlusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEPlusAssignExp(GrammarParser.EPlusAssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EMulDiv}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEMulDiv(GrammarParser.EMulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EMulDiv}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEMulDiv(GrammarParser.EMulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EDivAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEDivAssignExp(GrammarParser.EDivAssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EDivAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEDivAssignExp(GrammarParser.EDivAssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DynamicArrayNoInferrence}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterDynamicArrayNoInferrence(GrammarParser.DynamicArrayNoInferrenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DynamicArrayNoInferrence}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitDynamicArrayNoInferrence(GrammarParser.DynamicArrayNoInferrenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EFunctionCall}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEFunctionCall(GrammarParser.EFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EFunctionCall}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEFunctionCall(GrammarParser.EFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EAssignmentExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEAssignmentExp(GrammarParser.EAssignmentExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EAssignmentExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEAssignmentExp(GrammarParser.EAssignmentExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EOr}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEOr(GrammarParser.EOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EOr}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEOr(GrammarParser.EOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EInt}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEInt(GrammarParser.EIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EInt}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEInt(GrammarParser.EIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EParensExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEParensExp(GrammarParser.EParensExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EParensExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEParensExp(GrammarParser.EParensExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EIdExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEIdExp(GrammarParser.EIdExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EIdExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEIdExp(GrammarParser.EIdExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EAddSub}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEAddSub(GrammarParser.EAddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EAddSub}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEAddSub(GrammarParser.EAddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EMinusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEMinusAssignExp(GrammarParser.EMinusAssignExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EMinusAssignExp}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEMinusAssignExp(GrammarParser.EMinusAssignExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DynamicArrayInference}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterDynamicArrayInference(GrammarParser.DynamicArrayInferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DynamicArrayInference}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitDynamicArrayInference(GrammarParser.DynamicArrayInferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EAnd}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEAnd(GrammarParser.EAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EAnd}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEAnd(GrammarParser.EAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EInc}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEInc(GrammarParser.EIncContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EInc}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEInc(GrammarParser.EIncContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EPower}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEPower(GrammarParser.EPowerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EPower}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEPower(GrammarParser.EPowerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EDouble}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEDouble(GrammarParser.EDoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EDouble}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEDouble(GrammarParser.EDoubleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EDec}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEDec(GrammarParser.EDecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EDec}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEDec(GrammarParser.EDecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EString}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEString(GrammarParser.EStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EString}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEString(GrammarParser.EStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EArrayIndexing}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEArrayIndexing(GrammarParser.EArrayIndexingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EArrayIndexing}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEArrayIndexing(GrammarParser.EArrayIndexingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ERelational}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterERelational(GrammarParser.ERelationalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ERelational}
	 * labeled alternative in {@link GrammarParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitERelational(GrammarParser.ERelationalContext ctx);
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