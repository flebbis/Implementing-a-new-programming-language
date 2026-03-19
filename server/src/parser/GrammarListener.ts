// Generated from /Users/aishamohamed/Kandidatarbete/Implementing-a-new-programming-language/src/main/antlr4/com/example/minilang/Grammar.g4 by ANTLR 4.9.0-SNAPSHOT


import { ParseTreeListener } from "antlr4ts/tree/ParseTreeListener";

import { DFuncContext } from "./GrammarParser";
import { DStmContext } from "./GrammarParser";
import { SeparatorContext } from "./GrammarParser";
import { ProgramContext } from "./GrammarParser";
import { DefContext } from "./GrammarParser";
import { FuncContext } from "./GrammarParser";
import { ParamContext } from "./GrammarParser";
import { StmtContext } from "./GrammarParser";
import { SimpleStmtContext } from "./GrammarParser";
import { BlockStmtContext } from "./GrammarParser";
import { BlockContext } from "./GrammarParser";
import { ReturnStmtContext } from "./GrammarParser";
import { WhileStmtContext } from "./GrammarParser";
import { DoStmtContext } from "./GrammarParser";
import { IfStmtContext } from "./GrammarParser";
import { DeclContext } from "./GrammarParser";
import { InitContext } from "./GrammarParser";
import { TerminatorContext } from "./GrammarParser";
import { ExpContext } from "./GrammarParser";
import { AssignExprContext } from "./GrammarParser";
import { OrExprContext } from "./GrammarParser";
import { AndExprContext } from "./GrammarParser";
import { EqualityExprContext } from "./GrammarParser";
import { RelationalContext } from "./GrammarParser";
import { AddExprContext } from "./GrammarParser";
import { MulExprContext } from "./GrammarParser";
import { PowerExprContext } from "./GrammarParser";
import { UnaryExprContext } from "./GrammarParser";
import { PostfixExprContext } from "./GrammarParser";
import { PostFixOpContext } from "./GrammarParser";
import { PrimaryContext } from "./GrammarParser";
import { ExpSeparatorContext } from "./GrammarParser";
import { ParamSeparatorContext } from "./GrammarParser";


/**
 * This interface defines a complete listener for a parse tree produced by
 * `GrammarParser`.
 */
export interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the `DFunc`
	 * labeled alternative in `GrammarParser.def`.
	 * @param ctx the parse tree
	 */
	enterDFunc?: (ctx: DFuncContext) => void;
	/**
	 * Exit a parse tree produced by the `DFunc`
	 * labeled alternative in `GrammarParser.def`.
	 * @param ctx the parse tree
	 */
	exitDFunc?: (ctx: DFuncContext) => void;

	/**
	 * Enter a parse tree produced by the `DStm`
	 * labeled alternative in `GrammarParser.def`.
	 * @param ctx the parse tree
	 */
	enterDStm?: (ctx: DStmContext) => void;
	/**
	 * Exit a parse tree produced by the `DStm`
	 * labeled alternative in `GrammarParser.def`.
	 * @param ctx the parse tree
	 */
	exitDStm?: (ctx: DStmContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.separator`.
	 * @param ctx the parse tree
	 */
	enterSeparator?: (ctx: SeparatorContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.separator`.
	 * @param ctx the parse tree
	 */
	exitSeparator?: (ctx: SeparatorContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.program`.
	 * @param ctx the parse tree
	 */
	enterProgram?: (ctx: ProgramContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.program`.
	 * @param ctx the parse tree
	 */
	exitProgram?: (ctx: ProgramContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.def`.
	 * @param ctx the parse tree
	 */
	enterDef?: (ctx: DefContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.def`.
	 * @param ctx the parse tree
	 */
	exitDef?: (ctx: DefContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.func`.
	 * @param ctx the parse tree
	 */
	enterFunc?: (ctx: FuncContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.func`.
	 * @param ctx the parse tree
	 */
	exitFunc?: (ctx: FuncContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.param`.
	 * @param ctx the parse tree
	 */
	enterParam?: (ctx: ParamContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.param`.
	 * @param ctx the parse tree
	 */
	exitParam?: (ctx: ParamContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.stmt`.
	 * @param ctx the parse tree
	 */
	enterStmt?: (ctx: StmtContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.stmt`.
	 * @param ctx the parse tree
	 */
	exitStmt?: (ctx: StmtContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.simpleStmt`.
	 * @param ctx the parse tree
	 */
	enterSimpleStmt?: (ctx: SimpleStmtContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.simpleStmt`.
	 * @param ctx the parse tree
	 */
	exitSimpleStmt?: (ctx: SimpleStmtContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.blockStmt`.
	 * @param ctx the parse tree
	 */
	enterBlockStmt?: (ctx: BlockStmtContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.blockStmt`.
	 * @param ctx the parse tree
	 */
	exitBlockStmt?: (ctx: BlockStmtContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.block`.
	 * @param ctx the parse tree
	 */
	enterBlock?: (ctx: BlockContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.block`.
	 * @param ctx the parse tree
	 */
	exitBlock?: (ctx: BlockContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.returnStmt`.
	 * @param ctx the parse tree
	 */
	enterReturnStmt?: (ctx: ReturnStmtContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.returnStmt`.
	 * @param ctx the parse tree
	 */
	exitReturnStmt?: (ctx: ReturnStmtContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.whileStmt`.
	 * @param ctx the parse tree
	 */
	enterWhileStmt?: (ctx: WhileStmtContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.whileStmt`.
	 * @param ctx the parse tree
	 */
	exitWhileStmt?: (ctx: WhileStmtContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.doStmt`.
	 * @param ctx the parse tree
	 */
	enterDoStmt?: (ctx: DoStmtContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.doStmt`.
	 * @param ctx the parse tree
	 */
	exitDoStmt?: (ctx: DoStmtContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.ifStmt`.
	 * @param ctx the parse tree
	 */
	enterIfStmt?: (ctx: IfStmtContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.ifStmt`.
	 * @param ctx the parse tree
	 */
	exitIfStmt?: (ctx: IfStmtContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.decl`.
	 * @param ctx the parse tree
	 */
	enterDecl?: (ctx: DeclContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.decl`.
	 * @param ctx the parse tree
	 */
	exitDecl?: (ctx: DeclContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.init`.
	 * @param ctx the parse tree
	 */
	enterInit?: (ctx: InitContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.init`.
	 * @param ctx the parse tree
	 */
	exitInit?: (ctx: InitContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.terminator`.
	 * @param ctx the parse tree
	 */
	enterTerminator?: (ctx: TerminatorContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.terminator`.
	 * @param ctx the parse tree
	 */
	exitTerminator?: (ctx: TerminatorContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.exp`.
	 * @param ctx the parse tree
	 */
	enterExp?: (ctx: ExpContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.exp`.
	 * @param ctx the parse tree
	 */
	exitExp?: (ctx: ExpContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.assignExpr`.
	 * @param ctx the parse tree
	 */
	enterAssignExpr?: (ctx: AssignExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.assignExpr`.
	 * @param ctx the parse tree
	 */
	exitAssignExpr?: (ctx: AssignExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.orExpr`.
	 * @param ctx the parse tree
	 */
	enterOrExpr?: (ctx: OrExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.orExpr`.
	 * @param ctx the parse tree
	 */
	exitOrExpr?: (ctx: OrExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.andExpr`.
	 * @param ctx the parse tree
	 */
	enterAndExpr?: (ctx: AndExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.andExpr`.
	 * @param ctx the parse tree
	 */
	exitAndExpr?: (ctx: AndExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.equalityExpr`.
	 * @param ctx the parse tree
	 */
	enterEqualityExpr?: (ctx: EqualityExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.equalityExpr`.
	 * @param ctx the parse tree
	 */
	exitEqualityExpr?: (ctx: EqualityExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.relational`.
	 * @param ctx the parse tree
	 */
	enterRelational?: (ctx: RelationalContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.relational`.
	 * @param ctx the parse tree
	 */
	exitRelational?: (ctx: RelationalContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.addExpr`.
	 * @param ctx the parse tree
	 */
	enterAddExpr?: (ctx: AddExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.addExpr`.
	 * @param ctx the parse tree
	 */
	exitAddExpr?: (ctx: AddExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.mulExpr`.
	 * @param ctx the parse tree
	 */
	enterMulExpr?: (ctx: MulExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.mulExpr`.
	 * @param ctx the parse tree
	 */
	exitMulExpr?: (ctx: MulExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.powerExpr`.
	 * @param ctx the parse tree
	 */
	enterPowerExpr?: (ctx: PowerExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.powerExpr`.
	 * @param ctx the parse tree
	 */
	exitPowerExpr?: (ctx: PowerExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.unaryExpr`.
	 * @param ctx the parse tree
	 */
	enterUnaryExpr?: (ctx: UnaryExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.unaryExpr`.
	 * @param ctx the parse tree
	 */
	exitUnaryExpr?: (ctx: UnaryExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.postfixExpr`.
	 * @param ctx the parse tree
	 */
	enterPostfixExpr?: (ctx: PostfixExprContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.postfixExpr`.
	 * @param ctx the parse tree
	 */
	exitPostfixExpr?: (ctx: PostfixExprContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.postFixOp`.
	 * @param ctx the parse tree
	 */
	enterPostFixOp?: (ctx: PostFixOpContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.postFixOp`.
	 * @param ctx the parse tree
	 */
	exitPostFixOp?: (ctx: PostFixOpContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.primary`.
	 * @param ctx the parse tree
	 */
	enterPrimary?: (ctx: PrimaryContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.primary`.
	 * @param ctx the parse tree
	 */
	exitPrimary?: (ctx: PrimaryContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.expSeparator`.
	 * @param ctx the parse tree
	 */
	enterExpSeparator?: (ctx: ExpSeparatorContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.expSeparator`.
	 * @param ctx the parse tree
	 */
	exitExpSeparator?: (ctx: ExpSeparatorContext) => void;

	/**
	 * Enter a parse tree produced by `GrammarParser.paramSeparator`.
	 * @param ctx the parse tree
	 */
	enterParamSeparator?: (ctx: ParamSeparatorContext) => void;
	/**
	 * Exit a parse tree produced by `GrammarParser.paramSeparator`.
	 * @param ctx the parse tree
	 */
	exitParamSeparator?: (ctx: ParamSeparatorContext) => void;
}

