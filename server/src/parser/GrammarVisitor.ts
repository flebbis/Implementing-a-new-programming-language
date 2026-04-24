// Generated from Grammar.g4 by ANTLR 4.9.0-SNAPSHOT


import { ParseTreeVisitor } from "antlr4ts/tree/ParseTreeVisitor";

import { DFuncContext } from "./GrammarParser";
import { DStmContext } from "./GrammarParser";
import { SimpleTypeContext } from "./GrammarParser";
import { ArrayTypeContext } from "./GrammarParser";
import { PrimaryParenContext } from "./GrammarParser";
import { PrimaryIntContext } from "./GrammarParser";
import { PrimaryDoubleContext } from "./GrammarParser";
import { PrimaryStringContext } from "./GrammarParser";
import { PrimaryBoolContext } from "./GrammarParser";
import { PrimaryIdContext } from "./GrammarParser";
import { PrimaryArrayLiteralContext } from "./GrammarParser";
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
import { TypeAnnotationContext } from "./GrammarParser";
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
 * This interface defines a complete generic visitor for a parse tree produced
 * by `GrammarParser`.
 *
 * @param <Result> The return type of the visit operation. Use `void` for
 * operations with no return type.
 */
export interface GrammarVisitor<Result> extends ParseTreeVisitor<Result> {
	/**
	 * Visit a parse tree produced by the `DFunc`
	 * labeled alternative in `GrammarParser.def`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitDFunc?: (ctx: DFuncContext) => Result;

	/**
	 * Visit a parse tree produced by the `DStm`
	 * labeled alternative in `GrammarParser.def`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitDStm?: (ctx: DStmContext) => Result;

	/**
	 * Visit a parse tree produced by the `SimpleType`
	 * labeled alternative in `GrammarParser.typeAnnotation`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitSimpleType?: (ctx: SimpleTypeContext) => Result;

	/**
	 * Visit a parse tree produced by the `ArrayType`
	 * labeled alternative in `GrammarParser.typeAnnotation`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitArrayType?: (ctx: ArrayTypeContext) => Result;

	/**
	 * Visit a parse tree produced by the `PrimaryParen`
	 * labeled alternative in `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimaryParen?: (ctx: PrimaryParenContext) => Result;

	/**
	 * Visit a parse tree produced by the `PrimaryInt`
	 * labeled alternative in `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimaryInt?: (ctx: PrimaryIntContext) => Result;

	/**
	 * Visit a parse tree produced by the `PrimaryDouble`
	 * labeled alternative in `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimaryDouble?: (ctx: PrimaryDoubleContext) => Result;

	/**
	 * Visit a parse tree produced by the `PrimaryString`
	 * labeled alternative in `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimaryString?: (ctx: PrimaryStringContext) => Result;

	/**
	 * Visit a parse tree produced by the `PrimaryBool`
	 * labeled alternative in `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimaryBool?: (ctx: PrimaryBoolContext) => Result;

	/**
	 * Visit a parse tree produced by the `PrimaryId`
	 * labeled alternative in `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimaryId?: (ctx: PrimaryIdContext) => Result;

	/**
	 * Visit a parse tree produced by the `PrimaryArrayLiteral`
	 * labeled alternative in `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimaryArrayLiteral?: (ctx: PrimaryArrayLiteralContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.separator`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitSeparator?: (ctx: SeparatorContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.program`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitProgram?: (ctx: ProgramContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.def`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitDef?: (ctx: DefContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.func`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitFunc?: (ctx: FuncContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.param`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitParam?: (ctx: ParamContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.stmt`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitStmt?: (ctx: StmtContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.simpleStmt`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitSimpleStmt?: (ctx: SimpleStmtContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.blockStmt`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitBlockStmt?: (ctx: BlockStmtContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.block`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitBlock?: (ctx: BlockContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.returnStmt`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitReturnStmt?: (ctx: ReturnStmtContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.whileStmt`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitWhileStmt?: (ctx: WhileStmtContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.doStmt`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitDoStmt?: (ctx: DoStmtContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.ifStmt`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitIfStmt?: (ctx: IfStmtContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.typeAnnotation`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitTypeAnnotation?: (ctx: TypeAnnotationContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.decl`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitDecl?: (ctx: DeclContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.init`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitInit?: (ctx: InitContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.terminator`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitTerminator?: (ctx: TerminatorContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.exp`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitExp?: (ctx: ExpContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.assignExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitAssignExpr?: (ctx: AssignExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.orExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitOrExpr?: (ctx: OrExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.andExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitAndExpr?: (ctx: AndExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.equalityExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitEqualityExpr?: (ctx: EqualityExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.relational`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitRelational?: (ctx: RelationalContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.addExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitAddExpr?: (ctx: AddExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.mulExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitMulExpr?: (ctx: MulExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.powerExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPowerExpr?: (ctx: PowerExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.unaryExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitUnaryExpr?: (ctx: UnaryExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.postfixExpr`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPostfixExpr?: (ctx: PostfixExprContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.postFixOp`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPostFixOp?: (ctx: PostFixOpContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.primary`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitPrimary?: (ctx: PrimaryContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.expSeparator`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitExpSeparator?: (ctx: ExpSeparatorContext) => Result;

	/**
	 * Visit a parse tree produced by `GrammarParser.paramSeparator`.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	visitParamSeparator?: (ctx: ParamSeparatorContext) => Result;
}

