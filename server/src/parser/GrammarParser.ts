// Generated from /Users/aishamohamed/Kandidatarbete/Implementing-a-new-programming-language/src/main/antlr4/com/example/minilang/Grammar.g4 by ANTLR 4.9.0-SNAPSHOT


import { ATN } from "antlr4ts/atn/ATN";
import { ATNDeserializer } from "antlr4ts/atn/ATNDeserializer";
import { FailedPredicateException } from "antlr4ts/FailedPredicateException";
import { NotNull } from "antlr4ts/Decorators";
import { NoViableAltException } from "antlr4ts/NoViableAltException";
import { Override } from "antlr4ts/Decorators";
import { Parser } from "antlr4ts/Parser";
import { ParserRuleContext } from "antlr4ts/ParserRuleContext";
import { ParserATNSimulator } from "antlr4ts/atn/ParserATNSimulator";
import { ParseTreeListener } from "antlr4ts/tree/ParseTreeListener";
import { ParseTreeVisitor } from "antlr4ts/tree/ParseTreeVisitor";
import { RecognitionException } from "antlr4ts/RecognitionException";
import { RuleContext } from "antlr4ts/RuleContext";
//import { RuleVersion } from "antlr4ts/RuleVersion";
import { TerminalNode } from "antlr4ts/tree/TerminalNode";
import { Token } from "antlr4ts/Token";
import { TokenStream } from "antlr4ts/TokenStream";
import { Vocabulary } from "antlr4ts/Vocabulary";
import { VocabularyImpl } from "antlr4ts/VocabularyImpl";

import * as Utils from "antlr4ts/misc/Utils";

import { GrammarListener } from "./GrammarListener";

export class GrammarParser extends Parser {
	public static readonly T__0 = 1;
	public static readonly T__1 = 2;
	public static readonly T__2 = 3;
	public static readonly T__3 = 4;
	public static readonly T__4 = 5;
	public static readonly T__5 = 6;
	public static readonly T__6 = 7;
	public static readonly T__7 = 8;
	public static readonly T__8 = 9;
	public static readonly T__9 = 10;
	public static readonly T__10 = 11;
	public static readonly TYPE = 12;
	public static readonly STRING = 13;
	public static readonly INT = 14;
	public static readonly DOUBLE = 15;
	public static readonly BOOL = 16;
	public static readonly PLUS_ASSIGN = 17;
	public static readonly MINUS_ASSIGN = 18;
	public static readonly MULT_ASSIGN = 19;
	public static readonly DIV_ASSIGN = 20;
	public static readonly PLUS = 21;
	public static readonly MINUS = 22;
	public static readonly MULTIPLY = 23;
	public static readonly DIVIDE = 24;
	public static readonly POWER = 25;
	public static readonly MODULO = 26;
	public static readonly GT = 27;
	public static readonly LT = 28;
	public static readonly LE = 29;
	public static readonly GE = 30;
	public static readonly EQ = 31;
	public static readonly NE = 32;
	public static readonly AND = 33;
	public static readonly OR = 34;
	public static readonly NOT = 35;
	public static readonly ID = 36;
	public static readonly ASSIGN = 37;
	public static readonly DYNARR_START = 38;
	public static readonly DYNARR_END = 39;
	public static readonly INC = 40;
	public static readonly DEC = 41;
	public static readonly BOM = 42;
	public static readonly WS = 43;
	public static readonly NL = 44;
	public static readonly SEMI = 45;
	public static readonly LINE_COMMENT = 46;
	public static readonly BLOCK_COMMENT = 47;
	public static readonly RULE_separator = 0;
	public static readonly RULE_program = 1;
	public static readonly RULE_def = 2;
	public static readonly RULE_func = 3;
	public static readonly RULE_param = 4;
	public static readonly RULE_stmt = 5;
	public static readonly RULE_simpleStmt = 6;
	public static readonly RULE_blockStmt = 7;
	public static readonly RULE_block = 8;
	public static readonly RULE_returnStmt = 9;
	public static readonly RULE_whileStmt = 10;
	public static readonly RULE_doStmt = 11;
	public static readonly RULE_ifStmt = 12;
	public static readonly RULE_decl = 13;
	public static readonly RULE_init = 14;
	public static readonly RULE_terminator = 15;
	public static readonly RULE_exp = 16;
	public static readonly RULE_assignExpr = 17;
	public static readonly RULE_orExpr = 18;
	public static readonly RULE_andExpr = 19;
	public static readonly RULE_equalityExpr = 20;
	public static readonly RULE_relational = 21;
	public static readonly RULE_addExpr = 22;
	public static readonly RULE_mulExpr = 23;
	public static readonly RULE_powerExpr = 24;
	public static readonly RULE_unaryExpr = 25;
	public static readonly RULE_postfixExpr = 26;
	public static readonly RULE_postFixOp = 27;
	public static readonly RULE_primary = 28;
	public static readonly RULE_expSeparator = 29;
	public static readonly RULE_paramSeparator = 30;
	// tslint:disable:no-trailing-whitespace
	public static readonly ruleNames: string[] = [
		"separator", "program", "def", "func", "param", "stmt", "simpleStmt", 
		"blockStmt", "block", "returnStmt", "whileStmt", "doStmt", "ifStmt", "decl", 
		"init", "terminator", "exp", "assignExpr", "orExpr", "andExpr", "equalityExpr", 
		"relational", "addExpr", "mulExpr", "powerExpr", "unaryExpr", "postfixExpr", 
		"postFixOp", "primary", "expSeparator", "paramSeparator",
	];

	private static readonly _LITERAL_NAMES: Array<string | undefined> = [
		undefined, "'func'", "'('", "')'", "'{'", "'}'", "'return'", "'while'", 
		"'do'", "'if'", "'else'", "','", undefined, undefined, undefined, undefined, 
		undefined, "'+='", "'-='", "'*='", "'/='", "'+'", "'-'", "'*'", "'/'", 
		"'**'", "'%'", "'>'", "'<'", "'<='", "'>='", "'=='", "'!='", "'and'", 
		"'or'", "'not'", undefined, "'='", "'['", "']'", "'++'", "'--'", "'\uFEFF'", 
		undefined, undefined, "';'",
	];
	private static readonly _SYMBOLIC_NAMES: Array<string | undefined> = [
		undefined, undefined, undefined, undefined, undefined, undefined, undefined, 
		undefined, undefined, undefined, undefined, undefined, "TYPE", "STRING", 
		"INT", "DOUBLE", "BOOL", "PLUS_ASSIGN", "MINUS_ASSIGN", "MULT_ASSIGN", 
		"DIV_ASSIGN", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "POWER", "MODULO", 
		"GT", "LT", "LE", "GE", "EQ", "NE", "AND", "OR", "NOT", "ID", "ASSIGN", 
		"DYNARR_START", "DYNARR_END", "INC", "DEC", "BOM", "WS", "NL", "SEMI", 
		"LINE_COMMENT", "BLOCK_COMMENT",
	];
	public static readonly VOCABULARY: Vocabulary = new VocabularyImpl(GrammarParser._LITERAL_NAMES, GrammarParser._SYMBOLIC_NAMES, []);

	// @Override
	// @NotNull
	public get vocabulary(): Vocabulary {
		return GrammarParser.VOCABULARY;
	}
	// tslint:enable:no-trailing-whitespace

	// @Override
	public get grammarFileName(): string { return "Grammar.g4"; }

	// @Override
	public get ruleNames(): string[] { return GrammarParser.ruleNames; }

	// @Override
	public get serializedATN(): string { return GrammarParser._serializedATN; }

	protected createFailedPredicateException(predicate?: string, message?: string): FailedPredicateException {
		return new FailedPredicateException(this, predicate, message);
	}

	constructor(input: TokenStream) {
		super(input);
		this._interp = new ParserATNSimulator(GrammarParser._ATN, this);
	}
	// @RuleVersion(0)
	public separator(): SeparatorContext {
		let _localctx: SeparatorContext = new SeparatorContext(this._ctx, this.state);
		this.enterRule(_localctx, 0, GrammarParser.RULE_separator);
		let _la: number;
		try {
			let _alt: number;
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 63;
			this._errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					this.state = 62;
					_la = this._input.LA(1);
					if (!(_la === GrammarParser.NL || _la === GrammarParser.SEMI)) {
					this._errHandler.recoverInline(this);
					} else {
						if (this._input.LA(1) === Token.EOF) {
							this.matchedEOF = true;
						}

						this._errHandler.reportMatch(this);
						this.consume();
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				this.state = 65;
				this._errHandler.sync(this);
				_alt = this.interpreter.adaptivePredict(this._input, 0, this._ctx);
			} while (_alt !== 2 && _alt !== ATN.INVALID_ALT_NUMBER);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public program(): ProgramContext {
		let _localctx: ProgramContext = new ProgramContext(this._ctx, this.state);
		this.enterRule(_localctx, 2, GrammarParser.RULE_program);
		let _la: number;
		try {
			let _alt: number;
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 70;
			this._errHandler.sync(this);
			_alt = this.interpreter.adaptivePredict(this._input, 1, this._ctx);
			while (_alt !== 2 && _alt !== ATN.INVALID_ALT_NUMBER) {
				if (_alt === 1) {
					{
					{
					this.state = 67;
					this.separator();
					}
					}
				}
				this.state = 72;
				this._errHandler.sync(this);
				_alt = this.interpreter.adaptivePredict(this._input, 1, this._ctx);
			}
			this.state = 86;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if ((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.T__0) | (1 << GrammarParser.T__1) | (1 << GrammarParser.T__3) | (1 << GrammarParser.T__5) | (1 << GrammarParser.T__6) | (1 << GrammarParser.T__7) | (1 << GrammarParser.T__8) | (1 << GrammarParser.TYPE) | (1 << GrammarParser.STRING) | (1 << GrammarParser.INT) | (1 << GrammarParser.DOUBLE) | (1 << GrammarParser.BOOL))) !== 0) || ((((_la - 35)) & ~0x1F) === 0 && ((1 << (_la - 35)) & ((1 << (GrammarParser.NOT - 35)) | (1 << (GrammarParser.ID - 35)) | (1 << (GrammarParser.DYNARR_START - 35)))) !== 0)) {
				{
				this.state = 73;
				this.def();
				this.state = 83;
				this._errHandler.sync(this);
				_alt = this.interpreter.adaptivePredict(this._input, 3, this._ctx);
				while (_alt !== 2 && _alt !== ATN.INVALID_ALT_NUMBER) {
					if (_alt === 1) {
						{
						{
						this.state = 75;
						this._errHandler.sync(this);
						_la = this._input.LA(1);
						do {
							{
							{
							this.state = 74;
							this.separator();
							}
							}
							this.state = 77;
							this._errHandler.sync(this);
							_la = this._input.LA(1);
						} while (_la === GrammarParser.NL || _la === GrammarParser.SEMI);
						this.state = 79;
						this.def();
						}
						}
					}
					this.state = 85;
					this._errHandler.sync(this);
					_alt = this.interpreter.adaptivePredict(this._input, 3, this._ctx);
				}
				}
			}

			this.state = 91;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while (_la === GrammarParser.NL || _la === GrammarParser.SEMI) {
				{
				{
				this.state = 88;
				this.separator();
				}
				}
				this.state = 93;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			this.state = 94;
			this.match(GrammarParser.EOF);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public def(): DefContext {
		let _localctx: DefContext = new DefContext(this._ctx, this.state);
		this.enterRule(_localctx, 4, GrammarParser.RULE_def);
		try {
			this.state = 98;
			this._errHandler.sync(this);
			switch ( this.interpreter.adaptivePredict(this._input, 6, this._ctx) ) {
			case 1:
				_localctx = new DFuncContext(_localctx);
				this.enterOuterAlt(_localctx, 1);
				{
				this.state = 96;
				this.func();
				}
				break;

			case 2:
				_localctx = new DStmContext(_localctx);
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 97;
				this.stmt();
				}
				break;
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public func(): FuncContext {
		let _localctx: FuncContext = new FuncContext(this._ctx, this.state);
		this.enterRule(_localctx, 6, GrammarParser.RULE_func);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 101;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if (_la === GrammarParser.TYPE) {
				{
				this.state = 100;
				this.match(GrammarParser.TYPE);
				}
			}

			this.state = 103;
			this.match(GrammarParser.T__0);
			this.state = 104;
			this.match(GrammarParser.ID);
			this.state = 105;
			this.match(GrammarParser.T__1);
			this.state = 106;
			this.paramSeparator();
			this.state = 107;
			this.match(GrammarParser.T__2);
			this.state = 108;
			this.block();
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public param(): ParamContext {
		let _localctx: ParamContext = new ParamContext(this._ctx, this.state);
		this.enterRule(_localctx, 8, GrammarParser.RULE_param);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 111;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if (_la === GrammarParser.TYPE) {
				{
				this.state = 110;
				this.match(GrammarParser.TYPE);
				}
			}

			this.state = 113;
			this.match(GrammarParser.ID);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public stmt(): StmtContext {
		let _localctx: StmtContext = new StmtContext(this._ctx, this.state);
		this.enterRule(_localctx, 10, GrammarParser.RULE_stmt);
		try {
			this.state = 117;
			this._errHandler.sync(this);
			switch (this._input.LA(1)) {
			case GrammarParser.T__1:
			case GrammarParser.T__5:
			case GrammarParser.TYPE:
			case GrammarParser.STRING:
			case GrammarParser.INT:
			case GrammarParser.DOUBLE:
			case GrammarParser.BOOL:
			case GrammarParser.NOT:
			case GrammarParser.ID:
			case GrammarParser.DYNARR_START:
				this.enterOuterAlt(_localctx, 1);
				{
				this.state = 115;
				this.simpleStmt();
				}
				break;
			case GrammarParser.T__3:
			case GrammarParser.T__6:
			case GrammarParser.T__7:
			case GrammarParser.T__8:
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 116;
				this.blockStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public simpleStmt(): SimpleStmtContext {
		let _localctx: SimpleStmtContext = new SimpleStmtContext(this._ctx, this.state);
		this.enterRule(_localctx, 12, GrammarParser.RULE_simpleStmt);
		try {
			this.state = 123;
			this._errHandler.sync(this);
			switch ( this.interpreter.adaptivePredict(this._input, 10, this._ctx) ) {
			case 1:
				this.enterOuterAlt(_localctx, 1);
				{
				this.state = 119;
				this.decl();
				}
				break;

			case 2:
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 120;
				this.init();
				}
				break;

			case 3:
				this.enterOuterAlt(_localctx, 3);
				{
				this.state = 121;
				this.returnStmt();
				}
				break;

			case 4:
				this.enterOuterAlt(_localctx, 4);
				{
				this.state = 122;
				this.exp();
				}
				break;
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public blockStmt(): BlockStmtContext {
		let _localctx: BlockStmtContext = new BlockStmtContext(this._ctx, this.state);
		this.enterRule(_localctx, 14, GrammarParser.RULE_blockStmt);
		try {
			this.state = 129;
			this._errHandler.sync(this);
			switch (this._input.LA(1)) {
			case GrammarParser.T__6:
				this.enterOuterAlt(_localctx, 1);
				{
				this.state = 125;
				this.whileStmt();
				}
				break;
			case GrammarParser.T__7:
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 126;
				this.doStmt();
				}
				break;
			case GrammarParser.T__8:
				this.enterOuterAlt(_localctx, 3);
				{
				this.state = 127;
				this.ifStmt();
				}
				break;
			case GrammarParser.T__3:
				this.enterOuterAlt(_localctx, 4);
				{
				this.state = 128;
				this.block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public block(): BlockContext {
		let _localctx: BlockContext = new BlockContext(this._ctx, this.state);
		this.enterRule(_localctx, 16, GrammarParser.RULE_block);
		let _la: number;
		try {
			let _alt: number;
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 131;
			this.match(GrammarParser.T__3);
			this.state = 135;
			this._errHandler.sync(this);
			_alt = this.interpreter.adaptivePredict(this._input, 12, this._ctx);
			while (_alt !== 2 && _alt !== ATN.INVALID_ALT_NUMBER) {
				if (_alt === 1) {
					{
					{
					this.state = 132;
					this.separator();
					}
					}
				}
				this.state = 137;
				this._errHandler.sync(this);
				_alt = this.interpreter.adaptivePredict(this._input, 12, this._ctx);
			}
			this.state = 151;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if ((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.T__1) | (1 << GrammarParser.T__3) | (1 << GrammarParser.T__5) | (1 << GrammarParser.T__6) | (1 << GrammarParser.T__7) | (1 << GrammarParser.T__8) | (1 << GrammarParser.TYPE) | (1 << GrammarParser.STRING) | (1 << GrammarParser.INT) | (1 << GrammarParser.DOUBLE) | (1 << GrammarParser.BOOL))) !== 0) || ((((_la - 35)) & ~0x1F) === 0 && ((1 << (_la - 35)) & ((1 << (GrammarParser.NOT - 35)) | (1 << (GrammarParser.ID - 35)) | (1 << (GrammarParser.DYNARR_START - 35)))) !== 0)) {
				{
				this.state = 138;
				this.stmt();
				this.state = 148;
				this._errHandler.sync(this);
				_alt = this.interpreter.adaptivePredict(this._input, 14, this._ctx);
				while (_alt !== 2 && _alt !== ATN.INVALID_ALT_NUMBER) {
					if (_alt === 1) {
						{
						{
						this.state = 140;
						this._errHandler.sync(this);
						_la = this._input.LA(1);
						do {
							{
							{
							this.state = 139;
							this.separator();
							}
							}
							this.state = 142;
							this._errHandler.sync(this);
							_la = this._input.LA(1);
						} while (_la === GrammarParser.NL || _la === GrammarParser.SEMI);
						this.state = 144;
						this.stmt();
						}
						}
					}
					this.state = 150;
					this._errHandler.sync(this);
					_alt = this.interpreter.adaptivePredict(this._input, 14, this._ctx);
				}
				}
			}

			this.state = 156;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while (_la === GrammarParser.NL || _la === GrammarParser.SEMI) {
				{
				{
				this.state = 153;
				this.separator();
				}
				}
				this.state = 158;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			this.state = 159;
			this.match(GrammarParser.T__4);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public returnStmt(): ReturnStmtContext {
		let _localctx: ReturnStmtContext = new ReturnStmtContext(this._ctx, this.state);
		this.enterRule(_localctx, 18, GrammarParser.RULE_returnStmt);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 161;
			this.match(GrammarParser.T__5);
			this.state = 163;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if ((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.T__1) | (1 << GrammarParser.STRING) | (1 << GrammarParser.INT) | (1 << GrammarParser.DOUBLE) | (1 << GrammarParser.BOOL))) !== 0) || ((((_la - 35)) & ~0x1F) === 0 && ((1 << (_la - 35)) & ((1 << (GrammarParser.NOT - 35)) | (1 << (GrammarParser.ID - 35)) | (1 << (GrammarParser.DYNARR_START - 35)))) !== 0)) {
				{
				this.state = 162;
				this.exp();
				}
			}

			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public whileStmt(): WhileStmtContext {
		let _localctx: WhileStmtContext = new WhileStmtContext(this._ctx, this.state);
		this.enterRule(_localctx, 20, GrammarParser.RULE_whileStmt);
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 165;
			this.match(GrammarParser.T__6);
			this.state = 166;
			this.exp();
			this.state = 167;
			this.stmt();
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public doStmt(): DoStmtContext {
		let _localctx: DoStmtContext = new DoStmtContext(this._ctx, this.state);
		this.enterRule(_localctx, 22, GrammarParser.RULE_doStmt);
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 169;
			this.match(GrammarParser.T__7);
			this.state = 170;
			this.exp();
			this.state = 171;
			this.stmt();
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public ifStmt(): IfStmtContext {
		let _localctx: IfStmtContext = new IfStmtContext(this._ctx, this.state);
		this.enterRule(_localctx, 24, GrammarParser.RULE_ifStmt);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 173;
			this.match(GrammarParser.T__8);
			this.state = 174;
			this.exp();
			this.state = 175;
			this.block();
			this.state = 187;
			this._errHandler.sync(this);
			switch ( this.interpreter.adaptivePredict(this._input, 20, this._ctx) ) {
			case 1:
				{
				this.state = 179;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
				while (_la === GrammarParser.NL || _la === GrammarParser.SEMI) {
					{
					{
					this.state = 176;
					this.separator();
					}
					}
					this.state = 181;
					this._errHandler.sync(this);
					_la = this._input.LA(1);
				}
				this.state = 182;
				this.match(GrammarParser.T__9);
				this.state = 185;
				this._errHandler.sync(this);
				switch (this._input.LA(1)) {
				case GrammarParser.T__8:
					{
					this.state = 183;
					this.ifStmt();
					}
					break;
				case GrammarParser.T__3:
					{
					this.state = 184;
					this.block();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public decl(): DeclContext {
		let _localctx: DeclContext = new DeclContext(this._ctx, this.state);
		this.enterRule(_localctx, 26, GrammarParser.RULE_decl);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 190;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if (_la === GrammarParser.TYPE) {
				{
				this.state = 189;
				this.match(GrammarParser.TYPE);
				}
			}

			this.state = 192;
			this.match(GrammarParser.ID);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public init(): InitContext {
		let _localctx: InitContext = new InitContext(this._ctx, this.state);
		this.enterRule(_localctx, 28, GrammarParser.RULE_init);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 195;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if (_la === GrammarParser.TYPE) {
				{
				this.state = 194;
				this.match(GrammarParser.TYPE);
				}
			}

			this.state = 197;
			this.match(GrammarParser.ID);
			this.state = 198;
			this.match(GrammarParser.ASSIGN);
			this.state = 199;
			this.exp();
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public terminator(): TerminatorContext {
		let _localctx: TerminatorContext = new TerminatorContext(this._ctx, this.state);
		this.enterRule(_localctx, 30, GrammarParser.RULE_terminator);
		let _la: number;
		try {
			this.state = 208;
			this._errHandler.sync(this);
			switch (this._input.LA(1)) {
			case GrammarParser.SEMI:
				this.enterOuterAlt(_localctx, 1);
				{
				this.state = 201;
				this.match(GrammarParser.SEMI);
				}
				break;
			case GrammarParser.NL:
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 203;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
				do {
					{
					{
					this.state = 202;
					this.match(GrammarParser.NL);
					}
					}
					this.state = 205;
					this._errHandler.sync(this);
					_la = this._input.LA(1);
				} while (_la === GrammarParser.NL);
				}
				break;
			case GrammarParser.EOF:
				this.enterOuterAlt(_localctx, 3);
				{
				this.state = 207;
				this.match(GrammarParser.EOF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public exp(): ExpContext {
		let _localctx: ExpContext = new ExpContext(this._ctx, this.state);
		this.enterRule(_localctx, 32, GrammarParser.RULE_exp);
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 210;
			this.assignExpr();
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public assignExpr(): AssignExprContext {
		let _localctx: AssignExprContext = new AssignExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 34, GrammarParser.RULE_assignExpr);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 212;
			this.orExpr();
			this.state = 215;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if (((((_la - 17)) & ~0x1F) === 0 && ((1 << (_la - 17)) & ((1 << (GrammarParser.PLUS_ASSIGN - 17)) | (1 << (GrammarParser.MINUS_ASSIGN - 17)) | (1 << (GrammarParser.MULT_ASSIGN - 17)) | (1 << (GrammarParser.DIV_ASSIGN - 17)) | (1 << (GrammarParser.ASSIGN - 17)))) !== 0)) {
				{
				this.state = 213;
				_la = this._input.LA(1);
				if (!(((((_la - 17)) & ~0x1F) === 0 && ((1 << (_la - 17)) & ((1 << (GrammarParser.PLUS_ASSIGN - 17)) | (1 << (GrammarParser.MINUS_ASSIGN - 17)) | (1 << (GrammarParser.MULT_ASSIGN - 17)) | (1 << (GrammarParser.DIV_ASSIGN - 17)) | (1 << (GrammarParser.ASSIGN - 17)))) !== 0))) {
				this._errHandler.recoverInline(this);
				} else {
					if (this._input.LA(1) === Token.EOF) {
						this.matchedEOF = true;
					}

					this._errHandler.reportMatch(this);
					this.consume();
				}
				this.state = 214;
				this.assignExpr();
				}
			}

			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public orExpr(): OrExprContext {
		let _localctx: OrExprContext = new OrExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 36, GrammarParser.RULE_orExpr);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 217;
			this.andExpr();
			this.state = 222;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while (_la === GrammarParser.OR) {
				{
				{
				this.state = 218;
				this.match(GrammarParser.OR);
				this.state = 219;
				this.andExpr();
				}
				}
				this.state = 224;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public andExpr(): AndExprContext {
		let _localctx: AndExprContext = new AndExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 38, GrammarParser.RULE_andExpr);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 225;
			this.equalityExpr();
			this.state = 230;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while (_la === GrammarParser.AND) {
				{
				{
				this.state = 226;
				this.match(GrammarParser.AND);
				this.state = 227;
				this.equalityExpr();
				}
				}
				this.state = 232;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public equalityExpr(): EqualityExprContext {
		let _localctx: EqualityExprContext = new EqualityExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 40, GrammarParser.RULE_equalityExpr);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 233;
			this.relational();
			this.state = 238;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while (_la === GrammarParser.EQ || _la === GrammarParser.NE) {
				{
				{
				this.state = 234;
				_la = this._input.LA(1);
				if (!(_la === GrammarParser.EQ || _la === GrammarParser.NE)) {
				this._errHandler.recoverInline(this);
				} else {
					if (this._input.LA(1) === Token.EOF) {
						this.matchedEOF = true;
					}

					this._errHandler.reportMatch(this);
					this.consume();
				}
				this.state = 235;
				this.relational();
				}
				}
				this.state = 240;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public relational(): RelationalContext {
		let _localctx: RelationalContext = new RelationalContext(this._ctx, this.state);
		this.enterRule(_localctx, 42, GrammarParser.RULE_relational);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 241;
			this.addExpr();
			this.state = 246;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while ((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.GT) | (1 << GrammarParser.LT) | (1 << GrammarParser.LE) | (1 << GrammarParser.GE))) !== 0)) {
				{
				{
				this.state = 242;
				_la = this._input.LA(1);
				if (!((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.GT) | (1 << GrammarParser.LT) | (1 << GrammarParser.LE) | (1 << GrammarParser.GE))) !== 0))) {
				this._errHandler.recoverInline(this);
				} else {
					if (this._input.LA(1) === Token.EOF) {
						this.matchedEOF = true;
					}

					this._errHandler.reportMatch(this);
					this.consume();
				}
				this.state = 243;
				this.addExpr();
				}
				}
				this.state = 248;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public addExpr(): AddExprContext {
		let _localctx: AddExprContext = new AddExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 44, GrammarParser.RULE_addExpr);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 249;
			this.mulExpr();
			this.state = 254;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while (_la === GrammarParser.PLUS || _la === GrammarParser.MINUS) {
				{
				{
				this.state = 250;
				_la = this._input.LA(1);
				if (!(_la === GrammarParser.PLUS || _la === GrammarParser.MINUS)) {
				this._errHandler.recoverInline(this);
				} else {
					if (this._input.LA(1) === Token.EOF) {
						this.matchedEOF = true;
					}

					this._errHandler.reportMatch(this);
					this.consume();
				}
				this.state = 251;
				this.mulExpr();
				}
				}
				this.state = 256;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public mulExpr(): MulExprContext {
		let _localctx: MulExprContext = new MulExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 46, GrammarParser.RULE_mulExpr);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 257;
			this.powerExpr();
			this.state = 262;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			while ((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.MULTIPLY) | (1 << GrammarParser.DIVIDE) | (1 << GrammarParser.MODULO))) !== 0)) {
				{
				{
				this.state = 258;
				_la = this._input.LA(1);
				if (!((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.MULTIPLY) | (1 << GrammarParser.DIVIDE) | (1 << GrammarParser.MODULO))) !== 0))) {
				this._errHandler.recoverInline(this);
				} else {
					if (this._input.LA(1) === Token.EOF) {
						this.matchedEOF = true;
					}

					this._errHandler.reportMatch(this);
					this.consume();
				}
				this.state = 259;
				this.powerExpr();
				}
				}
				this.state = 264;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public powerExpr(): PowerExprContext {
		let _localctx: PowerExprContext = new PowerExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 48, GrammarParser.RULE_powerExpr);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 265;
			this.unaryExpr();
			this.state = 268;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if (_la === GrammarParser.POWER) {
				{
				this.state = 266;
				this.match(GrammarParser.POWER);
				this.state = 267;
				this.powerExpr();
				}
			}

			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public unaryExpr(): UnaryExprContext {
		let _localctx: UnaryExprContext = new UnaryExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 50, GrammarParser.RULE_unaryExpr);
		try {
			this.state = 273;
			this._errHandler.sync(this);
			switch (this._input.LA(1)) {
			case GrammarParser.NOT:
				this.enterOuterAlt(_localctx, 1);
				{
				{
				this.state = 270;
				this.match(GrammarParser.NOT);
				}
				this.state = 271;
				this.unaryExpr();
				}
				break;
			case GrammarParser.T__1:
			case GrammarParser.STRING:
			case GrammarParser.INT:
			case GrammarParser.DOUBLE:
			case GrammarParser.BOOL:
			case GrammarParser.ID:
			case GrammarParser.DYNARR_START:
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 272;
				this.postfixExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public postfixExpr(): PostfixExprContext {
		let _localctx: PostfixExprContext = new PostfixExprContext(this._ctx, this.state);
		this.enterRule(_localctx, 52, GrammarParser.RULE_postfixExpr);
		try {
			let _alt: number;
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 275;
			this.primary();
			this.state = 279;
			this._errHandler.sync(this);
			_alt = this.interpreter.adaptivePredict(this._input, 34, this._ctx);
			while (_alt !== 2 && _alt !== ATN.INVALID_ALT_NUMBER) {
				if (_alt === 1) {
					{
					{
					this.state = 276;
					this.postFixOp();
					}
					}
				}
				this.state = 281;
				this._errHandler.sync(this);
				_alt = this.interpreter.adaptivePredict(this._input, 34, this._ctx);
			}
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public postFixOp(): PostFixOpContext {
		let _localctx: PostFixOpContext = new PostFixOpContext(this._ctx, this.state);
		this.enterRule(_localctx, 54, GrammarParser.RULE_postFixOp);
		try {
			this.state = 292;
			this._errHandler.sync(this);
			switch (this._input.LA(1)) {
			case GrammarParser.INC:
				this.enterOuterAlt(_localctx, 1);
				{
				this.state = 282;
				this.match(GrammarParser.INC);
				}
				break;
			case GrammarParser.DEC:
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 283;
				this.match(GrammarParser.DEC);
				}
				break;
			case GrammarParser.DYNARR_START:
				this.enterOuterAlt(_localctx, 3);
				{
				this.state = 284;
				this.match(GrammarParser.DYNARR_START);
				this.state = 285;
				this.exp();
				this.state = 286;
				this.match(GrammarParser.DYNARR_END);
				}
				break;
			case GrammarParser.T__1:
				this.enterOuterAlt(_localctx, 4);
				{
				this.state = 288;
				this.match(GrammarParser.T__1);
				this.state = 289;
				this.expSeparator();
				this.state = 290;
				this.match(GrammarParser.T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public primary(): PrimaryContext {
		let _localctx: PrimaryContext = new PrimaryContext(this._ctx, this.state);
		this.enterRule(_localctx, 56, GrammarParser.RULE_primary);
		try {
			this.state = 318;
			this._errHandler.sync(this);
			switch ( this.interpreter.adaptivePredict(this._input, 36, this._ctx) ) {
			case 1:
				this.enterOuterAlt(_localctx, 1);
				{
				this.state = 294;
				this.match(GrammarParser.T__1);
				this.state = 295;
				this.exp();
				this.state = 296;
				this.match(GrammarParser.T__2);
				}
				break;

			case 2:
				this.enterOuterAlt(_localctx, 2);
				{
				this.state = 298;
				this.match(GrammarParser.INT);
				}
				break;

			case 3:
				this.enterOuterAlt(_localctx, 3);
				{
				this.state = 299;
				this.match(GrammarParser.DOUBLE);
				}
				break;

			case 4:
				this.enterOuterAlt(_localctx, 4);
				{
				this.state = 300;
				this.match(GrammarParser.STRING);
				}
				break;

			case 5:
				this.enterOuterAlt(_localctx, 5);
				{
				this.state = 301;
				this.match(GrammarParser.BOOL);
				}
				break;

			case 6:
				this.enterOuterAlt(_localctx, 6);
				{
				this.state = 302;
				this.match(GrammarParser.ID);
				}
				break;

			case 7:
				this.enterOuterAlt(_localctx, 7);
				{
				this.state = 303;
				this.match(GrammarParser.DYNARR_START);
				this.state = 304;
				this.match(GrammarParser.TYPE);
				this.state = 305;
				this.match(GrammarParser.DYNARR_END);
				this.state = 306;
				this.match(GrammarParser.ID);
				this.state = 307;
				this.match(GrammarParser.ASSIGN);
				this.state = 308;
				this.match(GrammarParser.DYNARR_START);
				this.state = 309;
				this.expSeparator();
				this.state = 310;
				this.match(GrammarParser.DYNARR_END);
				}
				break;

			case 8:
				this.enterOuterAlt(_localctx, 8);
				{
				this.state = 312;
				this.match(GrammarParser.ID);
				this.state = 313;
				this.match(GrammarParser.ASSIGN);
				this.state = 314;
				this.match(GrammarParser.DYNARR_START);
				this.state = 315;
				this.expSeparator();
				this.state = 316;
				this.match(GrammarParser.DYNARR_END);
				}
				break;
			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public expSeparator(): ExpSeparatorContext {
		let _localctx: ExpSeparatorContext = new ExpSeparatorContext(this._ctx, this.state);
		this.enterRule(_localctx, 58, GrammarParser.RULE_expSeparator);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 328;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if ((((_la) & ~0x1F) === 0 && ((1 << _la) & ((1 << GrammarParser.T__1) | (1 << GrammarParser.STRING) | (1 << GrammarParser.INT) | (1 << GrammarParser.DOUBLE) | (1 << GrammarParser.BOOL))) !== 0) || ((((_la - 35)) & ~0x1F) === 0 && ((1 << (_la - 35)) & ((1 << (GrammarParser.NOT - 35)) | (1 << (GrammarParser.ID - 35)) | (1 << (GrammarParser.DYNARR_START - 35)))) !== 0)) {
				{
				this.state = 320;
				this.exp();
				this.state = 325;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
				while (_la === GrammarParser.T__10) {
					{
					{
					this.state = 321;
					this.match(GrammarParser.T__10);
					this.state = 322;
					this.exp();
					}
					}
					this.state = 327;
					this._errHandler.sync(this);
					_la = this._input.LA(1);
				}
				}
			}

			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}
	// @RuleVersion(0)
	public paramSeparator(): ParamSeparatorContext {
		let _localctx: ParamSeparatorContext = new ParamSeparatorContext(this._ctx, this.state);
		this.enterRule(_localctx, 60, GrammarParser.RULE_paramSeparator);
		let _la: number;
		try {
			this.enterOuterAlt(_localctx, 1);
			{
			this.state = 338;
			this._errHandler.sync(this);
			_la = this._input.LA(1);
			if (_la === GrammarParser.TYPE || _la === GrammarParser.ID) {
				{
				this.state = 330;
				this.param();
				this.state = 335;
				this._errHandler.sync(this);
				_la = this._input.LA(1);
				while (_la === GrammarParser.T__10) {
					{
					{
					this.state = 331;
					this.match(GrammarParser.T__10);
					this.state = 332;
					this.param();
					}
					}
					this.state = 337;
					this._errHandler.sync(this);
					_la = this._input.LA(1);
				}
				}
			}

			}
		}
		catch (re) {
			if (re instanceof RecognitionException) {
				_localctx.exception = re;
				this._errHandler.reportError(this, re);
				this._errHandler.recover(this, re);
			} else {
				throw re;
			}
		}
		finally {
			this.exitRule();
		}
		return _localctx;
	}

	public static readonly _serializedATN: string =
		"\x03\uC91D\uCABA\u058D\uAFBA\u4F53\u0607\uEA8B\uC241\x031\u0157\x04\x02" +
		"\t\x02\x04\x03\t\x03\x04\x04\t\x04\x04\x05\t\x05\x04\x06\t\x06\x04\x07" +
		"\t\x07\x04\b\t\b\x04\t\t\t\x04\n\t\n\x04\v\t\v\x04\f\t\f\x04\r\t\r\x04" +
		"\x0E\t\x0E\x04\x0F\t\x0F\x04\x10\t\x10\x04\x11\t\x11\x04\x12\t\x12\x04" +
		"\x13\t\x13\x04\x14\t\x14\x04\x15\t\x15\x04\x16\t\x16\x04\x17\t\x17\x04" +
		"\x18\t\x18\x04\x19\t\x19\x04\x1A\t\x1A\x04\x1B\t\x1B\x04\x1C\t\x1C\x04" +
		"\x1D\t\x1D\x04\x1E\t\x1E\x04\x1F\t\x1F\x04 \t \x03\x02\x06\x02B\n\x02" +
		"\r\x02\x0E\x02C\x03\x03\x07\x03G\n\x03\f\x03\x0E\x03J\v\x03\x03\x03\x03" +
		"\x03\x06\x03N\n\x03\r\x03\x0E\x03O\x03\x03\x03\x03\x07\x03T\n\x03\f\x03" +
		"\x0E\x03W\v\x03\x05\x03Y\n\x03\x03\x03\x07\x03\\\n\x03\f\x03\x0E\x03_" +
		"\v\x03\x03\x03\x03\x03\x03\x04\x03\x04\x05\x04e\n\x04\x03\x05\x05\x05" +
		"h\n\x05\x03\x05\x03\x05\x03\x05\x03\x05\x03\x05\x03\x05\x03\x05\x03\x06" +
		"\x05\x06r\n\x06\x03\x06\x03\x06\x03\x07\x03\x07\x05\x07x\n\x07\x03\b\x03" +
		"\b\x03\b\x03\b\x05\b~\n\b\x03\t\x03\t\x03\t\x03\t\x05\t\x84\n\t\x03\n" +
		"\x03\n\x07\n\x88\n\n\f\n\x0E\n\x8B\v\n\x03\n\x03\n\x06\n\x8F\n\n\r\n\x0E" +
		"\n\x90\x03\n\x03\n\x07\n\x95\n\n\f\n\x0E\n\x98\v\n\x05\n\x9A\n\n\x03\n" +
		"\x07\n\x9D\n\n\f\n\x0E\n\xA0\v\n\x03\n\x03\n\x03\v\x03\v\x05\v\xA6\n\v" +
		"\x03\f\x03\f\x03\f\x03\f\x03\r\x03\r\x03\r\x03\r\x03\x0E\x03\x0E\x03\x0E" +
		"\x03\x0E\x07\x0E\xB4\n\x0E\f\x0E\x0E\x0E\xB7\v\x0E\x03\x0E\x03\x0E\x03" +
		"\x0E\x05\x0E\xBC\n\x0E\x05\x0E\xBE\n\x0E\x03\x0F\x05\x0F\xC1\n\x0F\x03" +
		"\x0F\x03\x0F\x03\x10\x05\x10\xC6\n\x10\x03\x10\x03\x10\x03\x10\x03\x10" +
		"\x03\x11\x03\x11\x06\x11\xCE\n\x11\r\x11\x0E\x11\xCF\x03\x11\x05\x11\xD3" +
		"\n\x11\x03\x12\x03\x12\x03\x13\x03\x13\x03\x13\x05\x13\xDA\n\x13\x03\x14" +
		"\x03\x14\x03\x14\x07\x14\xDF\n\x14\f\x14\x0E\x14\xE2\v\x14\x03\x15\x03" +
		"\x15\x03\x15\x07\x15\xE7\n\x15\f\x15\x0E\x15\xEA\v\x15\x03\x16\x03\x16" +
		"\x03\x16\x07\x16\xEF\n\x16\f\x16\x0E\x16\xF2\v\x16\x03\x17\x03\x17\x03" +
		"\x17\x07\x17\xF7\n\x17\f\x17\x0E\x17\xFA\v\x17\x03\x18\x03\x18\x03\x18" +
		"\x07\x18\xFF\n\x18\f\x18\x0E\x18\u0102\v\x18\x03\x19\x03\x19\x03\x19\x07" +
		"\x19\u0107\n\x19\f\x19\x0E\x19\u010A\v\x19\x03\x1A\x03\x1A\x03\x1A\x05" +
		"\x1A\u010F\n\x1A\x03\x1B\x03\x1B\x03\x1B\x05\x1B\u0114\n\x1B\x03\x1C\x03" +
		"\x1C\x07\x1C\u0118\n\x1C\f\x1C\x0E\x1C\u011B\v\x1C\x03\x1D\x03\x1D\x03" +
		"\x1D\x03\x1D\x03\x1D\x03\x1D\x03\x1D\x03\x1D\x03\x1D\x03\x1D\x05\x1D\u0127" +
		"\n\x1D\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E" +
		"\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E" +
		"\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x03\x1E\x05\x1E\u0141" +
		"\n\x1E\x03\x1F\x03\x1F\x03\x1F\x07\x1F\u0146\n\x1F\f\x1F\x0E\x1F\u0149" +
		"\v\x1F\x05\x1F\u014B\n\x1F\x03 \x03 \x03 \x07 \u0150\n \f \x0E \u0153" +
		"\v \x05 \u0155\n \x03 \x02\x02\x02!\x02\x02\x04\x02\x06\x02\b\x02\n\x02" +
		"\f\x02\x0E\x02\x10\x02\x12\x02\x14\x02\x16\x02\x18\x02\x1A\x02\x1C\x02" +
		"\x1E\x02 \x02\"\x02$\x02&\x02(\x02*\x02,\x02.\x020\x022\x024\x026\x02" +
		"8\x02:\x02<\x02>\x02\x02\b\x03\x02./\x04\x02\x13\x16\'\'\x03\x02!\"\x03" +
		"\x02\x1D \x03\x02\x17\x18\x04\x02\x19\x1A\x1C\x1C\x02\u016D\x02A\x03\x02" +
		"\x02\x02\x04H\x03\x02\x02\x02\x06d\x03\x02\x02\x02\bg\x03\x02\x02\x02" +
		"\nq\x03\x02\x02\x02\fw\x03\x02\x02\x02\x0E}\x03\x02\x02\x02\x10\x83\x03" +
		"\x02\x02\x02\x12\x85\x03\x02\x02\x02\x14\xA3\x03\x02\x02\x02\x16\xA7\x03" +
		"\x02\x02\x02\x18\xAB\x03\x02\x02\x02\x1A\xAF\x03\x02\x02\x02\x1C\xC0\x03" +
		"\x02\x02\x02\x1E\xC5\x03\x02\x02\x02 \xD2\x03\x02\x02\x02\"\xD4\x03\x02" +
		"\x02\x02$\xD6\x03\x02\x02\x02&\xDB\x03\x02\x02\x02(\xE3\x03\x02\x02\x02" +
		"*\xEB\x03\x02\x02\x02,\xF3\x03\x02\x02\x02.\xFB\x03\x02\x02\x020\u0103" +
		"\x03\x02\x02\x022\u010B\x03\x02\x02\x024\u0113\x03\x02\x02\x026\u0115" +
		"\x03\x02\x02\x028\u0126\x03\x02\x02\x02:\u0140\x03\x02\x02\x02<\u014A" +
		"\x03\x02\x02\x02>\u0154\x03\x02\x02\x02@B\t\x02\x02\x02A@\x03\x02\x02" +
		"\x02BC\x03\x02\x02\x02CA\x03\x02\x02\x02CD\x03\x02\x02\x02D\x03\x03\x02" +
		"\x02\x02EG\x05\x02\x02\x02FE\x03\x02\x02\x02GJ\x03\x02\x02\x02HF\x03\x02" +
		"\x02\x02HI\x03\x02\x02\x02IX\x03\x02\x02\x02JH\x03\x02\x02\x02KU\x05\x06" +
		"\x04\x02LN\x05\x02\x02\x02ML\x03\x02\x02\x02NO\x03\x02\x02\x02OM\x03\x02" +
		"\x02\x02OP\x03\x02\x02\x02PQ\x03\x02\x02\x02QR\x05\x06\x04\x02RT\x03\x02" +
		"\x02\x02SM\x03\x02\x02\x02TW\x03\x02\x02\x02US\x03\x02\x02\x02UV\x03\x02" +
		"\x02\x02VY\x03\x02\x02\x02WU\x03\x02\x02\x02XK\x03\x02\x02\x02XY\x03\x02" +
		"\x02\x02Y]\x03\x02\x02\x02Z\\\x05\x02\x02\x02[Z\x03\x02\x02\x02\\_\x03" +
		"\x02\x02\x02][\x03\x02\x02\x02]^\x03\x02\x02\x02^`\x03\x02\x02\x02_]\x03" +
		"\x02\x02\x02`a\x07\x02\x02\x03a\x05\x03\x02\x02\x02be\x05\b\x05\x02ce" +
		"\x05\f\x07\x02db\x03\x02\x02\x02dc\x03\x02\x02\x02e\x07\x03\x02\x02\x02" +
		"fh\x07\x0E\x02\x02gf\x03\x02\x02\x02gh\x03\x02\x02\x02hi\x03\x02\x02\x02" +
		"ij\x07\x03\x02\x02jk\x07&\x02\x02kl\x07\x04\x02\x02lm\x05> \x02mn\x07" +
		"\x05\x02\x02no\x05\x12\n\x02o\t\x03\x02\x02\x02pr\x07\x0E\x02\x02qp\x03" +
		"\x02\x02\x02qr\x03\x02\x02\x02rs\x03\x02\x02\x02st\x07&\x02\x02t\v\x03" +
		"\x02\x02\x02ux\x05\x0E\b\x02vx\x05\x10\t\x02wu\x03\x02\x02\x02wv\x03\x02" +
		"\x02\x02x\r\x03\x02\x02\x02y~\x05\x1C\x0F\x02z~\x05\x1E\x10\x02{~\x05" +
		"\x14\v\x02|~\x05\"\x12\x02}y\x03\x02\x02\x02}z\x03\x02\x02\x02}{\x03\x02" +
		"\x02\x02}|\x03\x02\x02\x02~\x0F\x03\x02\x02\x02\x7F\x84\x05\x16\f\x02" +
		"\x80\x84\x05\x18\r\x02\x81\x84\x05\x1A\x0E\x02\x82\x84\x05\x12\n\x02\x83" +
		"\x7F\x03\x02\x02\x02\x83\x80\x03\x02\x02\x02\x83\x81\x03\x02\x02\x02\x83" +
		"\x82\x03\x02\x02\x02\x84\x11\x03\x02\x02\x02\x85\x89\x07\x06\x02\x02\x86" +
		"\x88\x05\x02\x02\x02\x87\x86\x03\x02\x02\x02\x88\x8B\x03\x02\x02\x02\x89" +
		"\x87\x03\x02\x02\x02\x89\x8A\x03\x02\x02\x02\x8A\x99\x03\x02\x02\x02\x8B" +
		"\x89\x03\x02\x02\x02\x8C\x96\x05\f\x07\x02\x8D\x8F\x05\x02\x02\x02\x8E" +
		"\x8D\x03\x02\x02\x02\x8F\x90\x03\x02\x02\x02\x90\x8E\x03\x02\x02\x02\x90" +
		"\x91\x03\x02\x02\x02\x91\x92\x03\x02\x02\x02\x92\x93\x05\f\x07\x02\x93" +
		"\x95\x03\x02\x02\x02\x94\x8E\x03\x02\x02\x02\x95\x98\x03\x02\x02\x02\x96" +
		"\x94\x03\x02\x02\x02\x96\x97\x03\x02\x02\x02\x97\x9A\x03\x02\x02\x02\x98" +
		"\x96\x03\x02\x02\x02\x99\x8C\x03\x02\x02\x02\x99\x9A\x03\x02\x02\x02\x9A" +
		"\x9E\x03\x02\x02\x02\x9B\x9D\x05\x02\x02\x02\x9C\x9B\x03\x02\x02\x02\x9D" +
		"\xA0\x03\x02\x02\x02\x9E\x9C\x03\x02\x02\x02\x9E\x9F\x03\x02\x02\x02\x9F" +
		"\xA1\x03\x02\x02\x02\xA0\x9E\x03\x02\x02\x02\xA1\xA2\x07\x07\x02\x02\xA2" +
		"\x13\x03\x02\x02\x02\xA3\xA5\x07\b\x02\x02\xA4\xA6\x05\"\x12\x02\xA5\xA4" +
		"\x03\x02\x02\x02\xA5\xA6\x03\x02\x02\x02\xA6\x15\x03\x02\x02\x02\xA7\xA8" +
		"\x07\t\x02\x02\xA8\xA9\x05\"\x12\x02\xA9\xAA\x05\f\x07\x02\xAA\x17\x03" +
		"\x02\x02\x02\xAB\xAC\x07\n\x02\x02\xAC\xAD\x05\"\x12\x02\xAD\xAE\x05\f" +
		"\x07\x02\xAE\x19\x03\x02\x02\x02\xAF\xB0\x07\v\x02\x02\xB0\xB1\x05\"\x12" +
		"\x02\xB1\xBD\x05\x12\n\x02\xB2\xB4\x05\x02\x02\x02\xB3\xB2\x03\x02\x02" +
		"\x02\xB4\xB7\x03\x02\x02\x02\xB5\xB3\x03\x02\x02\x02\xB5\xB6\x03\x02\x02" +
		"\x02\xB6\xB8\x03\x02\x02\x02\xB7\xB5\x03\x02\x02\x02\xB8\xBB\x07\f\x02" +
		"\x02\xB9\xBC\x05\x1A\x0E\x02\xBA\xBC\x05\x12\n\x02\xBB\xB9\x03\x02\x02" +
		"\x02\xBB\xBA\x03\x02\x02\x02\xBC\xBE\x03\x02\x02\x02\xBD\xB5\x03\x02\x02" +
		"\x02\xBD\xBE\x03\x02\x02\x02\xBE\x1B\x03\x02\x02\x02\xBF\xC1\x07\x0E\x02" +
		"\x02\xC0\xBF\x03\x02\x02\x02\xC0\xC1\x03\x02\x02\x02\xC1\xC2\x03\x02\x02" +
		"\x02\xC2\xC3\x07&\x02\x02\xC3\x1D\x03\x02\x02\x02\xC4\xC6\x07\x0E\x02" +
		"\x02\xC5\xC4\x03\x02\x02\x02\xC5\xC6\x03\x02\x02\x02\xC6\xC7\x03\x02\x02" +
		"\x02\xC7\xC8\x07&\x02\x02\xC8\xC9\x07\'\x02\x02\xC9\xCA\x05\"\x12\x02" +
		"\xCA\x1F\x03\x02\x02\x02\xCB\xD3\x07/\x02\x02\xCC\xCE\x07.\x02\x02\xCD" +
		"\xCC\x03\x02\x02\x02\xCE\xCF\x03\x02\x02\x02\xCF\xCD\x03\x02\x02\x02\xCF" +
		"\xD0\x03\x02\x02\x02\xD0\xD3\x03\x02\x02\x02\xD1\xD3\x07\x02\x02\x03\xD2" +
		"\xCB\x03\x02\x02\x02\xD2\xCD\x03\x02\x02\x02\xD2\xD1\x03\x02\x02\x02\xD3" +
		"!\x03\x02\x02\x02\xD4\xD5\x05$\x13\x02\xD5#\x03\x02\x02\x02\xD6\xD9\x05" +
		"&\x14\x02\xD7\xD8\t\x03\x02\x02\xD8\xDA\x05$\x13\x02\xD9\xD7\x03\x02\x02" +
		"\x02\xD9\xDA\x03\x02\x02\x02\xDA%\x03\x02\x02\x02\xDB\xE0\x05(\x15\x02" +
		"\xDC\xDD\x07$\x02\x02\xDD\xDF\x05(\x15\x02\xDE\xDC\x03\x02\x02\x02\xDF" +
		"\xE2\x03\x02\x02\x02\xE0\xDE\x03\x02\x02\x02\xE0\xE1\x03\x02\x02\x02\xE1" +
		"\'\x03\x02\x02\x02\xE2\xE0\x03\x02\x02\x02\xE3\xE8\x05*\x16\x02\xE4\xE5" +
		"\x07#\x02\x02\xE5\xE7\x05*\x16\x02\xE6\xE4\x03\x02\x02\x02\xE7\xEA\x03" +
		"\x02\x02\x02\xE8\xE6\x03\x02\x02\x02\xE8\xE9\x03\x02\x02\x02\xE9)\x03" +
		"\x02\x02\x02\xEA\xE8\x03\x02\x02\x02\xEB\xF0\x05,\x17\x02\xEC\xED\t\x04" +
		"\x02\x02\xED\xEF\x05,\x17\x02\xEE\xEC\x03\x02\x02\x02\xEF\xF2\x03\x02" +
		"\x02\x02\xF0\xEE\x03\x02\x02\x02\xF0\xF1\x03\x02\x02\x02\xF1+\x03\x02" +
		"\x02\x02\xF2\xF0\x03\x02\x02\x02\xF3\xF8\x05.\x18\x02\xF4\xF5\t\x05\x02" +
		"\x02\xF5\xF7\x05.\x18\x02\xF6\xF4\x03\x02\x02\x02\xF7\xFA\x03\x02\x02" +
		"\x02\xF8\xF6\x03\x02\x02\x02\xF8\xF9\x03\x02\x02\x02\xF9-\x03\x02\x02" +
		"\x02\xFA\xF8\x03\x02\x02\x02\xFB\u0100\x050\x19\x02\xFC\xFD\t\x06\x02" +
		"\x02\xFD\xFF\x050\x19\x02\xFE\xFC\x03\x02\x02\x02\xFF\u0102\x03\x02\x02" +
		"\x02\u0100\xFE\x03\x02\x02\x02\u0100\u0101\x03\x02\x02\x02\u0101/\x03" +
		"\x02\x02\x02\u0102\u0100\x03\x02\x02\x02\u0103\u0108\x052\x1A\x02\u0104" +
		"\u0105\t\x07\x02\x02\u0105\u0107\x052\x1A\x02\u0106\u0104\x03\x02\x02" +
		"\x02\u0107\u010A\x03\x02\x02\x02\u0108\u0106\x03\x02\x02\x02\u0108\u0109" +
		"\x03\x02\x02\x02\u01091\x03\x02\x02\x02\u010A\u0108\x03\x02\x02\x02\u010B" +
		"\u010E\x054\x1B\x02\u010C\u010D\x07\x1B\x02\x02\u010D\u010F\x052\x1A\x02" +
		"\u010E\u010C\x03\x02\x02\x02\u010E\u010F\x03\x02\x02\x02\u010F3\x03\x02" +
		"\x02\x02\u0110\u0111\x07%\x02\x02\u0111\u0114\x054\x1B\x02\u0112\u0114" +
		"\x056\x1C\x02\u0113\u0110\x03\x02\x02\x02\u0113\u0112\x03\x02\x02\x02" +
		"\u01145\x03\x02\x02\x02\u0115\u0119\x05:\x1E\x02\u0116\u0118\x058\x1D" +
		"\x02\u0117\u0116\x03\x02\x02\x02\u0118\u011B\x03\x02\x02\x02\u0119\u0117" +
		"\x03\x02\x02\x02\u0119\u011A\x03\x02\x02\x02\u011A7\x03\x02\x02\x02\u011B" +
		"\u0119\x03\x02\x02\x02\u011C\u0127\x07*\x02\x02\u011D\u0127\x07+\x02\x02" +
		"\u011E\u011F\x07(\x02\x02\u011F\u0120\x05\"\x12\x02\u0120\u0121\x07)\x02" +
		"\x02\u0121\u0127\x03\x02\x02\x02\u0122\u0123\x07\x04\x02\x02\u0123\u0124" +
		"\x05<\x1F\x02\u0124\u0125\x07\x05\x02\x02\u0125\u0127\x03\x02\x02\x02" +
		"\u0126\u011C\x03\x02\x02\x02\u0126\u011D\x03\x02\x02\x02\u0126\u011E\x03" +
		"\x02\x02\x02\u0126\u0122\x03\x02\x02\x02\u01279\x03\x02\x02\x02\u0128" +
		"\u0129\x07\x04\x02\x02\u0129\u012A\x05\"\x12\x02\u012A\u012B\x07\x05\x02" +
		"\x02\u012B\u0141\x03\x02\x02\x02\u012C\u0141\x07\x10\x02\x02\u012D\u0141" +
		"\x07\x11\x02\x02\u012E\u0141\x07\x0F\x02\x02\u012F\u0141\x07\x12\x02\x02" +
		"\u0130\u0141\x07&\x02\x02\u0131\u0132\x07(\x02\x02\u0132\u0133\x07\x0E" +
		"\x02\x02\u0133\u0134\x07)\x02\x02\u0134\u0135\x07&\x02\x02\u0135\u0136" +
		"\x07\'\x02\x02\u0136\u0137\x07(\x02\x02\u0137\u0138\x05<\x1F\x02\u0138" +
		"\u0139\x07)\x02\x02\u0139\u0141\x03\x02\x02\x02\u013A\u013B\x07&\x02\x02" +
		"\u013B\u013C\x07\'\x02\x02\u013C\u013D\x07(\x02\x02\u013D\u013E\x05<\x1F" +
		"\x02\u013E\u013F\x07)\x02\x02\u013F\u0141\x03\x02\x02\x02\u0140\u0128" +
		"\x03\x02\x02\x02\u0140\u012C\x03\x02\x02\x02\u0140\u012D\x03\x02\x02\x02" +
		"\u0140\u012E\x03\x02\x02\x02\u0140\u012F\x03\x02\x02\x02\u0140\u0130\x03" +
		"\x02\x02\x02\u0140\u0131\x03\x02\x02\x02\u0140\u013A\x03\x02\x02\x02\u0141" +
		";\x03\x02\x02\x02\u0142\u0147\x05\"\x12\x02\u0143\u0144\x07\r\x02\x02" +
		"\u0144\u0146\x05\"\x12\x02\u0145\u0143\x03\x02\x02\x02\u0146\u0149\x03" +
		"\x02\x02\x02\u0147\u0145\x03\x02\x02\x02\u0147\u0148\x03\x02\x02\x02\u0148" +
		"\u014B\x03\x02\x02\x02\u0149\u0147\x03\x02\x02\x02\u014A\u0142\x03\x02" +
		"\x02\x02\u014A\u014B\x03\x02\x02\x02\u014B=\x03\x02\x02\x02\u014C\u0151" +
		"\x05\n\x06\x02\u014D\u014E\x07\r\x02\x02\u014E\u0150\x05\n\x06\x02\u014F" +
		"\u014D\x03\x02\x02\x02\u0150\u0153\x03\x02\x02\x02\u0151\u014F\x03\x02" +
		"\x02\x02\u0151\u0152\x03\x02\x02\x02\u0152\u0155\x03\x02\x02\x02\u0153" +
		"\u0151\x03\x02\x02\x02\u0154\u014C\x03\x02\x02\x02\u0154\u0155\x03\x02" +
		"\x02\x02\u0155?\x03\x02\x02\x02+CHOUX]dgqw}\x83\x89\x90\x96\x99\x9E\xA5" +
		"\xB5\xBB\xBD\xC0\xC5\xCF\xD2\xD9\xE0\xE8\xF0\xF8\u0100\u0108\u010E\u0113" +
		"\u0119\u0126\u0140\u0147\u014A\u0151\u0154";
	public static __ATN: ATN;
	public static get _ATN(): ATN {
		if (!GrammarParser.__ATN) {
			GrammarParser.__ATN = new ATNDeserializer().deserialize(Utils.toCharArray(GrammarParser._serializedATN));
		}

		return GrammarParser.__ATN;
	}

}

export class SeparatorContext extends ParserRuleContext {
	public NL(): TerminalNode[];
	public NL(i: number): TerminalNode;
	public NL(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.NL);
		} else {
			return this.getToken(GrammarParser.NL, i);
		}
	}
	public SEMI(): TerminalNode[];
	public SEMI(i: number): TerminalNode;
	public SEMI(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.SEMI);
		} else {
			return this.getToken(GrammarParser.SEMI, i);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_separator; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterSeparator) {
			listener.enterSeparator(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitSeparator) {
			listener.exitSeparator(this);
		}
	}
}


export class ProgramContext extends ParserRuleContext {
	public EOF(): TerminalNode { return this.getToken(GrammarParser.EOF, 0); }
	public separator(): SeparatorContext[];
	public separator(i: number): SeparatorContext;
	public separator(i?: number): SeparatorContext | SeparatorContext[] {
		if (i === undefined) {
			return this.getRuleContexts(SeparatorContext);
		} else {
			return this.getRuleContext(i, SeparatorContext);
		}
	}
	public def(): DefContext[];
	public def(i: number): DefContext;
	public def(i?: number): DefContext | DefContext[] {
		if (i === undefined) {
			return this.getRuleContexts(DefContext);
		} else {
			return this.getRuleContext(i, DefContext);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_program; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterProgram) {
			listener.enterProgram(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitProgram) {
			listener.exitProgram(this);
		}
	}
}


export class DefContext extends ParserRuleContext {
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_def; }
	public copyFrom(ctx: DefContext): void {
		super.copyFrom(ctx);
	}
}
export class DFuncContext extends DefContext {
	public func(): FuncContext {
		return this.getRuleContext(0, FuncContext);
	}
	constructor(ctx: DefContext) {
		super(ctx.parent, ctx.invokingState);
		this.copyFrom(ctx);
	}
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterDFunc) {
			listener.enterDFunc(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitDFunc) {
			listener.exitDFunc(this);
		}
	}
}
export class DStmContext extends DefContext {
	public stmt(): StmtContext {
		return this.getRuleContext(0, StmtContext);
	}
	constructor(ctx: DefContext) {
		super(ctx.parent, ctx.invokingState);
		this.copyFrom(ctx);
	}
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterDStm) {
			listener.enterDStm(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitDStm) {
			listener.exitDStm(this);
		}
	}
}


export class FuncContext extends ParserRuleContext {
	public ID(): TerminalNode { return this.getToken(GrammarParser.ID, 0); }
	public paramSeparator(): ParamSeparatorContext {
		return this.getRuleContext(0, ParamSeparatorContext);
	}
	public block(): BlockContext {
		return this.getRuleContext(0, BlockContext);
	}
	public TYPE(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.TYPE, 0); }
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_func; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterFunc) {
			listener.enterFunc(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitFunc) {
			listener.exitFunc(this);
		}
	}
}


export class ParamContext extends ParserRuleContext {
	public ID(): TerminalNode { return this.getToken(GrammarParser.ID, 0); }
	public TYPE(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.TYPE, 0); }
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_param; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterParam) {
			listener.enterParam(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitParam) {
			listener.exitParam(this);
		}
	}
}


export class StmtContext extends ParserRuleContext {
	public simpleStmt(): SimpleStmtContext | undefined {
		return this.tryGetRuleContext(0, SimpleStmtContext);
	}
	public blockStmt(): BlockStmtContext | undefined {
		return this.tryGetRuleContext(0, BlockStmtContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_stmt; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterStmt) {
			listener.enterStmt(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitStmt) {
			listener.exitStmt(this);
		}
	}
}


export class SimpleStmtContext extends ParserRuleContext {
	public decl(): DeclContext | undefined {
		return this.tryGetRuleContext(0, DeclContext);
	}
	public init(): InitContext | undefined {
		return this.tryGetRuleContext(0, InitContext);
	}
	public returnStmt(): ReturnStmtContext | undefined {
		return this.tryGetRuleContext(0, ReturnStmtContext);
	}
	public exp(): ExpContext | undefined {
		return this.tryGetRuleContext(0, ExpContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_simpleStmt; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterSimpleStmt) {
			listener.enterSimpleStmt(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitSimpleStmt) {
			listener.exitSimpleStmt(this);
		}
	}
}


export class BlockStmtContext extends ParserRuleContext {
	public whileStmt(): WhileStmtContext | undefined {
		return this.tryGetRuleContext(0, WhileStmtContext);
	}
	public doStmt(): DoStmtContext | undefined {
		return this.tryGetRuleContext(0, DoStmtContext);
	}
	public ifStmt(): IfStmtContext | undefined {
		return this.tryGetRuleContext(0, IfStmtContext);
	}
	public block(): BlockContext | undefined {
		return this.tryGetRuleContext(0, BlockContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_blockStmt; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterBlockStmt) {
			listener.enterBlockStmt(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitBlockStmt) {
			listener.exitBlockStmt(this);
		}
	}
}


export class BlockContext extends ParserRuleContext {
	public separator(): SeparatorContext[];
	public separator(i: number): SeparatorContext;
	public separator(i?: number): SeparatorContext | SeparatorContext[] {
		if (i === undefined) {
			return this.getRuleContexts(SeparatorContext);
		} else {
			return this.getRuleContext(i, SeparatorContext);
		}
	}
	public stmt(): StmtContext[];
	public stmt(i: number): StmtContext;
	public stmt(i?: number): StmtContext | StmtContext[] {
		if (i === undefined) {
			return this.getRuleContexts(StmtContext);
		} else {
			return this.getRuleContext(i, StmtContext);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_block; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterBlock) {
			listener.enterBlock(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitBlock) {
			listener.exitBlock(this);
		}
	}
}


export class ReturnStmtContext extends ParserRuleContext {
	public exp(): ExpContext | undefined {
		return this.tryGetRuleContext(0, ExpContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_returnStmt; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterReturnStmt) {
			listener.enterReturnStmt(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitReturnStmt) {
			listener.exitReturnStmt(this);
		}
	}
}


export class WhileStmtContext extends ParserRuleContext {
	public exp(): ExpContext {
		return this.getRuleContext(0, ExpContext);
	}
	public stmt(): StmtContext {
		return this.getRuleContext(0, StmtContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_whileStmt; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterWhileStmt) {
			listener.enterWhileStmt(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitWhileStmt) {
			listener.exitWhileStmt(this);
		}
	}
}


export class DoStmtContext extends ParserRuleContext {
	public exp(): ExpContext {
		return this.getRuleContext(0, ExpContext);
	}
	public stmt(): StmtContext {
		return this.getRuleContext(0, StmtContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_doStmt; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterDoStmt) {
			listener.enterDoStmt(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitDoStmt) {
			listener.exitDoStmt(this);
		}
	}
}


export class IfStmtContext extends ParserRuleContext {
	public exp(): ExpContext {
		return this.getRuleContext(0, ExpContext);
	}
	public block(): BlockContext[];
	public block(i: number): BlockContext;
	public block(i?: number): BlockContext | BlockContext[] {
		if (i === undefined) {
			return this.getRuleContexts(BlockContext);
		} else {
			return this.getRuleContext(i, BlockContext);
		}
	}
	public ifStmt(): IfStmtContext | undefined {
		return this.tryGetRuleContext(0, IfStmtContext);
	}
	public separator(): SeparatorContext[];
	public separator(i: number): SeparatorContext;
	public separator(i?: number): SeparatorContext | SeparatorContext[] {
		if (i === undefined) {
			return this.getRuleContexts(SeparatorContext);
		} else {
			return this.getRuleContext(i, SeparatorContext);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_ifStmt; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterIfStmt) {
			listener.enterIfStmt(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitIfStmt) {
			listener.exitIfStmt(this);
		}
	}
}


export class DeclContext extends ParserRuleContext {
	public ID(): TerminalNode { return this.getToken(GrammarParser.ID, 0); }
	public TYPE(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.TYPE, 0); }
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_decl; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterDecl) {
			listener.enterDecl(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitDecl) {
			listener.exitDecl(this);
		}
	}
}


export class InitContext extends ParserRuleContext {
	public ID(): TerminalNode { return this.getToken(GrammarParser.ID, 0); }
	public ASSIGN(): TerminalNode { return this.getToken(GrammarParser.ASSIGN, 0); }
	public exp(): ExpContext {
		return this.getRuleContext(0, ExpContext);
	}
	public TYPE(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.TYPE, 0); }
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_init; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterInit) {
			listener.enterInit(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitInit) {
			listener.exitInit(this);
		}
	}
}


export class TerminatorContext extends ParserRuleContext {
	public SEMI(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.SEMI, 0); }
	public NL(): TerminalNode[];
	public NL(i: number): TerminalNode;
	public NL(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.NL);
		} else {
			return this.getToken(GrammarParser.NL, i);
		}
	}
	public EOF(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.EOF, 0); }
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_terminator; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterTerminator) {
			listener.enterTerminator(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitTerminator) {
			listener.exitTerminator(this);
		}
	}
}


export class ExpContext extends ParserRuleContext {
	public assignExpr(): AssignExprContext {
		return this.getRuleContext(0, AssignExprContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_exp; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterExp) {
			listener.enterExp(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitExp) {
			listener.exitExp(this);
		}
	}
}


export class AssignExprContext extends ParserRuleContext {
	public orExpr(): OrExprContext {
		return this.getRuleContext(0, OrExprContext);
	}
	public assignExpr(): AssignExprContext | undefined {
		return this.tryGetRuleContext(0, AssignExprContext);
	}
	public ASSIGN(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.ASSIGN, 0); }
	public PLUS_ASSIGN(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.PLUS_ASSIGN, 0); }
	public MINUS_ASSIGN(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.MINUS_ASSIGN, 0); }
	public DIV_ASSIGN(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.DIV_ASSIGN, 0); }
	public MULT_ASSIGN(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.MULT_ASSIGN, 0); }
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_assignExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterAssignExpr) {
			listener.enterAssignExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitAssignExpr) {
			listener.exitAssignExpr(this);
		}
	}
}


export class OrExprContext extends ParserRuleContext {
	public andExpr(): AndExprContext[];
	public andExpr(i: number): AndExprContext;
	public andExpr(i?: number): AndExprContext | AndExprContext[] {
		if (i === undefined) {
			return this.getRuleContexts(AndExprContext);
		} else {
			return this.getRuleContext(i, AndExprContext);
		}
	}
	public OR(): TerminalNode[];
	public OR(i: number): TerminalNode;
	public OR(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.OR);
		} else {
			return this.getToken(GrammarParser.OR, i);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_orExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterOrExpr) {
			listener.enterOrExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitOrExpr) {
			listener.exitOrExpr(this);
		}
	}
}


export class AndExprContext extends ParserRuleContext {
	public equalityExpr(): EqualityExprContext[];
	public equalityExpr(i: number): EqualityExprContext;
	public equalityExpr(i?: number): EqualityExprContext | EqualityExprContext[] {
		if (i === undefined) {
			return this.getRuleContexts(EqualityExprContext);
		} else {
			return this.getRuleContext(i, EqualityExprContext);
		}
	}
	public AND(): TerminalNode[];
	public AND(i: number): TerminalNode;
	public AND(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.AND);
		} else {
			return this.getToken(GrammarParser.AND, i);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_andExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterAndExpr) {
			listener.enterAndExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitAndExpr) {
			listener.exitAndExpr(this);
		}
	}
}


export class EqualityExprContext extends ParserRuleContext {
	public relational(): RelationalContext[];
	public relational(i: number): RelationalContext;
	public relational(i?: number): RelationalContext | RelationalContext[] {
		if (i === undefined) {
			return this.getRuleContexts(RelationalContext);
		} else {
			return this.getRuleContext(i, RelationalContext);
		}
	}
	public EQ(): TerminalNode[];
	public EQ(i: number): TerminalNode;
	public EQ(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.EQ);
		} else {
			return this.getToken(GrammarParser.EQ, i);
		}
	}
	public NE(): TerminalNode[];
	public NE(i: number): TerminalNode;
	public NE(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.NE);
		} else {
			return this.getToken(GrammarParser.NE, i);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_equalityExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterEqualityExpr) {
			listener.enterEqualityExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitEqualityExpr) {
			listener.exitEqualityExpr(this);
		}
	}
}


export class RelationalContext extends ParserRuleContext {
	public addExpr(): AddExprContext[];
	public addExpr(i: number): AddExprContext;
	public addExpr(i?: number): AddExprContext | AddExprContext[] {
		if (i === undefined) {
			return this.getRuleContexts(AddExprContext);
		} else {
			return this.getRuleContext(i, AddExprContext);
		}
	}
	public GT(): TerminalNode[];
	public GT(i: number): TerminalNode;
	public GT(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.GT);
		} else {
			return this.getToken(GrammarParser.GT, i);
		}
	}
	public LT(): TerminalNode[];
	public LT(i: number): TerminalNode;
	public LT(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.LT);
		} else {
			return this.getToken(GrammarParser.LT, i);
		}
	}
	public GE(): TerminalNode[];
	public GE(i: number): TerminalNode;
	public GE(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.GE);
		} else {
			return this.getToken(GrammarParser.GE, i);
		}
	}
	public LE(): TerminalNode[];
	public LE(i: number): TerminalNode;
	public LE(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.LE);
		} else {
			return this.getToken(GrammarParser.LE, i);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_relational; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterRelational) {
			listener.enterRelational(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitRelational) {
			listener.exitRelational(this);
		}
	}
}


export class AddExprContext extends ParserRuleContext {
	public mulExpr(): MulExprContext[];
	public mulExpr(i: number): MulExprContext;
	public mulExpr(i?: number): MulExprContext | MulExprContext[] {
		if (i === undefined) {
			return this.getRuleContexts(MulExprContext);
		} else {
			return this.getRuleContext(i, MulExprContext);
		}
	}
	public PLUS(): TerminalNode[];
	public PLUS(i: number): TerminalNode;
	public PLUS(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.PLUS);
		} else {
			return this.getToken(GrammarParser.PLUS, i);
		}
	}
	public MINUS(): TerminalNode[];
	public MINUS(i: number): TerminalNode;
	public MINUS(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.MINUS);
		} else {
			return this.getToken(GrammarParser.MINUS, i);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_addExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterAddExpr) {
			listener.enterAddExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitAddExpr) {
			listener.exitAddExpr(this);
		}
	}
}


export class MulExprContext extends ParserRuleContext {
	public powerExpr(): PowerExprContext[];
	public powerExpr(i: number): PowerExprContext;
	public powerExpr(i?: number): PowerExprContext | PowerExprContext[] {
		if (i === undefined) {
			return this.getRuleContexts(PowerExprContext);
		} else {
			return this.getRuleContext(i, PowerExprContext);
		}
	}
	public MULTIPLY(): TerminalNode[];
	public MULTIPLY(i: number): TerminalNode;
	public MULTIPLY(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.MULTIPLY);
		} else {
			return this.getToken(GrammarParser.MULTIPLY, i);
		}
	}
	public DIVIDE(): TerminalNode[];
	public DIVIDE(i: number): TerminalNode;
	public DIVIDE(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.DIVIDE);
		} else {
			return this.getToken(GrammarParser.DIVIDE, i);
		}
	}
	public MODULO(): TerminalNode[];
	public MODULO(i: number): TerminalNode;
	public MODULO(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.MODULO);
		} else {
			return this.getToken(GrammarParser.MODULO, i);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_mulExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterMulExpr) {
			listener.enterMulExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitMulExpr) {
			listener.exitMulExpr(this);
		}
	}
}


export class PowerExprContext extends ParserRuleContext {
	public unaryExpr(): UnaryExprContext {
		return this.getRuleContext(0, UnaryExprContext);
	}
	public POWER(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.POWER, 0); }
	public powerExpr(): PowerExprContext | undefined {
		return this.tryGetRuleContext(0, PowerExprContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_powerExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterPowerExpr) {
			listener.enterPowerExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitPowerExpr) {
			listener.exitPowerExpr(this);
		}
	}
}


export class UnaryExprContext extends ParserRuleContext {
	public unaryExpr(): UnaryExprContext | undefined {
		return this.tryGetRuleContext(0, UnaryExprContext);
	}
	public NOT(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.NOT, 0); }
	public postfixExpr(): PostfixExprContext | undefined {
		return this.tryGetRuleContext(0, PostfixExprContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_unaryExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterUnaryExpr) {
			listener.enterUnaryExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitUnaryExpr) {
			listener.exitUnaryExpr(this);
		}
	}
}


export class PostfixExprContext extends ParserRuleContext {
	public primary(): PrimaryContext {
		return this.getRuleContext(0, PrimaryContext);
	}
	public postFixOp(): PostFixOpContext[];
	public postFixOp(i: number): PostFixOpContext;
	public postFixOp(i?: number): PostFixOpContext | PostFixOpContext[] {
		if (i === undefined) {
			return this.getRuleContexts(PostFixOpContext);
		} else {
			return this.getRuleContext(i, PostFixOpContext);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_postfixExpr; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterPostfixExpr) {
			listener.enterPostfixExpr(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitPostfixExpr) {
			listener.exitPostfixExpr(this);
		}
	}
}


export class PostFixOpContext extends ParserRuleContext {
	public INC(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.INC, 0); }
	public DEC(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.DEC, 0); }
	public DYNARR_START(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.DYNARR_START, 0); }
	public exp(): ExpContext | undefined {
		return this.tryGetRuleContext(0, ExpContext);
	}
	public DYNARR_END(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.DYNARR_END, 0); }
	public expSeparator(): ExpSeparatorContext | undefined {
		return this.tryGetRuleContext(0, ExpSeparatorContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_postFixOp; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterPostFixOp) {
			listener.enterPostFixOp(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitPostFixOp) {
			listener.exitPostFixOp(this);
		}
	}
}


export class PrimaryContext extends ParserRuleContext {
	public exp(): ExpContext | undefined {
		return this.tryGetRuleContext(0, ExpContext);
	}
	public INT(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.INT, 0); }
	public DOUBLE(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.DOUBLE, 0); }
	public STRING(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.STRING, 0); }
	public BOOL(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.BOOL, 0); }
	public ID(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.ID, 0); }
	public DYNARR_START(): TerminalNode[];
	public DYNARR_START(i: number): TerminalNode;
	public DYNARR_START(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.DYNARR_START);
		} else {
			return this.getToken(GrammarParser.DYNARR_START, i);
		}
	}
	public TYPE(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.TYPE, 0); }
	public DYNARR_END(): TerminalNode[];
	public DYNARR_END(i: number): TerminalNode;
	public DYNARR_END(i?: number): TerminalNode | TerminalNode[] {
		if (i === undefined) {
			return this.getTokens(GrammarParser.DYNARR_END);
		} else {
			return this.getToken(GrammarParser.DYNARR_END, i);
		}
	}
	public ASSIGN(): TerminalNode | undefined { return this.tryGetToken(GrammarParser.ASSIGN, 0); }
	public expSeparator(): ExpSeparatorContext | undefined {
		return this.tryGetRuleContext(0, ExpSeparatorContext);
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_primary; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterPrimary) {
			listener.enterPrimary(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitPrimary) {
			listener.exitPrimary(this);
		}
	}
}


export class ExpSeparatorContext extends ParserRuleContext {
	public exp(): ExpContext[];
	public exp(i: number): ExpContext;
	public exp(i?: number): ExpContext | ExpContext[] {
		if (i === undefined) {
			return this.getRuleContexts(ExpContext);
		} else {
			return this.getRuleContext(i, ExpContext);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_expSeparator; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterExpSeparator) {
			listener.enterExpSeparator(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitExpSeparator) {
			listener.exitExpSeparator(this);
		}
	}
}


export class ParamSeparatorContext extends ParserRuleContext {
	public param(): ParamContext[];
	public param(i: number): ParamContext;
	public param(i?: number): ParamContext | ParamContext[] {
		if (i === undefined) {
			return this.getRuleContexts(ParamContext);
		} else {
			return this.getRuleContext(i, ParamContext);
		}
	}
	constructor(parent: ParserRuleContext | undefined, invokingState: number) {
		super(parent, invokingState);
	}
	// @Override
	public get ruleIndex(): number { return GrammarParser.RULE_paramSeparator; }
	// @Override
	public enterRule(listener: GrammarListener): void {
		if (listener.enterParamSeparator) {
			listener.enterParamSeparator(this);
		}
	}
	// @Override
	public exitRule(listener: GrammarListener): void {
		if (listener.exitParamSeparator) {
			listener.exitParamSeparator(this);
		}
	}
}


