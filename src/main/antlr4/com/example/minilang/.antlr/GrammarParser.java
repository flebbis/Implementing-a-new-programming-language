// Generated from /Users/aishamohamed/Kandidatarbete/Implementing-a-new-programming-language/src/main/antlr4/com/example/minilang/Grammar.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, TYPE=12, STRING=13, INT=14, DOUBLE=15, BOOL=16, PLUS_ASSIGN=17, 
		MINUS_ASSIGN=18, MULT_ASSIGN=19, DIV_ASSIGN=20, PLUS=21, MINUS=22, MULTIPLY=23, 
		DIVIDE=24, POWER=25, MODULO=26, GT=27, LT=28, LE=29, GE=30, EQ=31, NE=32, 
		AND=33, OR=34, NOT=35, ID=36, ASSIGN=37, DYNARR_START=38, DYNARR_END=39, 
		INC=40, DEC=41, BOM=42, WS=43, NL=44, SEMI=45, LINE_COMMENT=46, BLOCK_COMMENT=47;
	public static final int
		RULE_separator = 0, RULE_program = 1, RULE_def = 2, RULE_func = 3, RULE_param = 4, 
		RULE_stmt = 5, RULE_simpleStmt = 6, RULE_blockStmt = 7, RULE_block = 8, 
		RULE_returnStmt = 9, RULE_whileStmt = 10, RULE_doStmt = 11, RULE_ifStmt = 12, 
		RULE_typeAnnotation = 13, RULE_decl = 14, RULE_init = 15, RULE_terminator = 16, 
		RULE_exp = 17, RULE_assignExpr = 18, RULE_orExpr = 19, RULE_andExpr = 20, 
		RULE_equalityExpr = 21, RULE_relational = 22, RULE_addExpr = 23, RULE_mulExpr = 24, 
		RULE_powerExpr = 25, RULE_unaryExpr = 26, RULE_postfixExpr = 27, RULE_postFixOp = 28, 
		RULE_primary = 29, RULE_expSeparator = 30, RULE_paramSeparator = 31;
	private static String[] makeRuleNames() {
		return new String[] {
			"separator", "program", "def", "func", "param", "stmt", "simpleStmt", 
			"blockStmt", "block", "returnStmt", "whileStmt", "doStmt", "ifStmt", 
			"typeAnnotation", "decl", "init", "terminator", "exp", "assignExpr", 
			"orExpr", "andExpr", "equalityExpr", "relational", "addExpr", "mulExpr", 
			"powerExpr", "unaryExpr", "postfixExpr", "postFixOp", "primary", "expSeparator", 
			"paramSeparator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'func'", "'('", "')'", "'{'", "'}'", "'return'", "'while'", "'do'", 
			"'if'", "'else'", "','", null, null, null, null, null, "'+='", "'-='", 
			"'*='", "'/='", "'+'", "'-'", "'*'", "'/'", "'**'", "'%'", "'>'", "'<'", 
			"'<='", "'>='", "'=='", "'!='", "'and'", "'or'", "'not'", null, "'='", 
			"'['", "']'", "'++'", "'--'", "'\\uFEFF'", null, null, "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"TYPE", "STRING", "INT", "DOUBLE", "BOOL", "PLUS_ASSIGN", "MINUS_ASSIGN", 
			"MULT_ASSIGN", "DIV_ASSIGN", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "POWER", 
			"MODULO", "GT", "LT", "LE", "GE", "EQ", "NE", "AND", "OR", "NOT", "ID", 
			"ASSIGN", "DYNARR_START", "DYNARR_END", "INC", "DEC", "BOM", "WS", "NL", 
			"SEMI", "LINE_COMMENT", "BLOCK_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SeparatorContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(GrammarParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(GrammarParser.NL, i);
		}
		public List<TerminalNode> SEMI() { return getTokens(GrammarParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(GrammarParser.SEMI, i);
		}
		public SeparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_separator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterSeparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitSeparator(this);
		}
	}

	public final SeparatorContext separator() throws RecognitionException {
		SeparatorContext _localctx = new SeparatorContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_separator);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(65); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(64);
					_la = _input.LA(1);
					if ( !(_la==NL || _la==SEMI) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(67); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public List<DefContext> def() {
			return getRuleContexts(DefContext.class);
		}
		public DefContext def(int i) {
			return getRuleContext(DefContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_program);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(69);
					separator();
					}
					} 
				}
				setState(74);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 377957250006L) != 0)) {
				{
				setState(75);
				def();
				setState(85);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(77); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(76);
							separator();
							}
							}
							setState(79); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==NL || _la==SEMI );
						setState(81);
						def();
						}
						} 
					}
					setState(87);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				}
				}
			}

			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL || _la==SEMI) {
				{
				{
				setState(90);
				separator();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefContext extends ParserRuleContext {
		public DefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def; }
	 
		public DefContext() { }
		public void copyFrom(DefContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DFuncContext extends DefContext {
		public FuncContext func() {
			return getRuleContext(FuncContext.class,0);
		}
		public DFuncContext(DefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterDFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitDFunc(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DStmContext extends DefContext {
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public DStmContext(DefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterDStm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitDStm(this);
		}
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_def);
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new DFuncContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				func();
				}
				break;
			case 2:
				_localctx = new DStmContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(99);
				stmt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public ParamSeparatorContext paramSeparator() {
			return getRuleContext(ParamSeparatorContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TypeAnnotationContext typeAnnotation() {
			return getRuleContext(TypeAnnotationContext.class,0);
		}
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitFunc(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE || _la==DYNARR_START) {
				{
				setState(102);
				typeAnnotation();
				}
			}

			setState(105);
			match(T__0);
			setState(106);
			match(ID);
			setState(107);
			match(T__1);
			setState(108);
			paramSeparator();
			setState(109);
			match(T__2);
			setState(110);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitParam(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_param);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(112);
				match(TYPE);
				}
			}

			setState(115);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtContext extends ParserRuleContext {
		public SimpleStmtContext simpleStmt() {
			return getRuleContext(SimpleStmtContext.class,0);
		}
		public BlockStmtContext blockStmt() {
			return getRuleContext(BlockStmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitStmt(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_stmt);
		try {
			setState(119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case T__5:
			case TYPE:
			case STRING:
			case INT:
			case DOUBLE:
			case BOOL:
			case NOT:
			case ID:
			case DYNARR_START:
				enterOuterAlt(_localctx, 1);
				{
				setState(117);
				simpleStmt();
				}
				break;
			case T__3:
			case T__6:
			case T__7:
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				blockStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleStmtContext extends ParserRuleContext {
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public ReturnStmtContext returnStmt() {
			return getRuleContext(ReturnStmtContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public SimpleStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterSimpleStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitSimpleStmt(this);
		}
	}

	public final SimpleStmtContext simpleStmt() throws RecognitionException {
		SimpleStmtContext _localctx = new SimpleStmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_simpleStmt);
		try {
			setState(125);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				decl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				init();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(123);
				returnStmt();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(124);
				exp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockStmtContext extends ParserRuleContext {
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public DoStmtContext doStmt() {
			return getRuleContext(DoStmtContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterBlockStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitBlockStmt(this);
		}
	}

	public final BlockStmtContext blockStmt() throws RecognitionException {
		BlockStmtContext _localctx = new BlockStmtContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_blockStmt);
		try {
			setState(131);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(127);
				whileStmt();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				doStmt();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				ifStmt();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				setState(130);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(T__3);
			setState(137);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(134);
					separator();
					}
					} 
				}
				setState(139);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 377957250004L) != 0)) {
				{
				setState(140);
				stmt();
				setState(150);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(142); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(141);
							separator();
							}
							}
							setState(144); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( _la==NL || _la==SEMI );
						setState(146);
						stmt();
						}
						} 
					}
					setState(152);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				}
				}
			}

			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL || _la==SEMI) {
				{
				{
				setState(155);
				separator();
				}
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(161);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStmtContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitReturnStmt(this);
		}
	}

	public final ReturnStmtContext returnStmt() throws RecognitionException {
		ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_returnStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(T__5);
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 377957244932L) != 0)) {
				{
				setState(164);
				exp();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStmtContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitWhileStmt(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			match(T__6);
			setState(168);
			exp();
			setState(169);
			stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DoStmtContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public DoStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterDoStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitDoStmt(this);
		}
	}

	public final DoStmtContext doStmt() throws RecognitionException {
		DoStmtContext _localctx = new DoStmtContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_doStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			match(T__7);
			setState(172);
			exp();
			setState(173);
			stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitIfStmt(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ifStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(T__8);
			setState(176);
			exp();
			setState(177);
			block();
			setState(189);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==SEMI) {
					{
					{
					setState(178);
					separator();
					}
					}
					setState(183);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(184);
				match(T__9);
				setState(187);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__8:
					{
					setState(185);
					ifStmt();
					}
					break;
				case T__3:
					{
					setState(186);
					block();
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
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeAnnotationContext extends ParserRuleContext {
		public TypeAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeAnnotation; }
	 
		public TypeAnnotationContext() { }
		public void copyFrom(TypeAnnotationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayTypeContext extends TypeAnnotationContext {
		public TerminalNode DYNARR_START() { return getToken(GrammarParser.DYNARR_START, 0); }
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public TerminalNode DYNARR_END() { return getToken(GrammarParser.DYNARR_END, 0); }
		public ArrayTypeContext(TypeAnnotationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitArrayType(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleTypeContext extends TypeAnnotationContext {
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public SimpleTypeContext(TypeAnnotationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterSimpleType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitSimpleType(this);
		}
	}

	public final TypeAnnotationContext typeAnnotation() throws RecognitionException {
		TypeAnnotationContext _localctx = new TypeAnnotationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_typeAnnotation);
		try {
			setState(195);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPE:
				_localctx = new SimpleTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(191);
				match(TYPE);
				}
				break;
			case DYNARR_START:
				_localctx = new ArrayTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(192);
				match(DYNARR_START);
				setState(193);
				match(TYPE);
				setState(194);
				match(DYNARR_END);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TypeAnnotationContext typeAnnotation() {
			return getRuleContext(TypeAnnotationContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitDecl(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE || _la==DYNARR_START) {
				{
				setState(197);
				typeAnnotation();
				}
			}

			setState(200);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InitContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GrammarParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TypeAnnotationContext typeAnnotation() {
			return getRuleContext(TypeAnnotationContext.class,0);
		}
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitInit(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_init);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE || _la==DYNARR_START) {
				{
				setState(202);
				typeAnnotation();
				}
			}

			setState(205);
			match(ID);
			setState(206);
			match(ASSIGN);
			setState(207);
			exp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TerminatorContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(GrammarParser.SEMI, 0); }
		public List<TerminalNode> NL() { return getTokens(GrammarParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(GrammarParser.NL, i);
		}
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
		public TerminatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterTerminator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitTerminator(this);
		}
	}

	public final TerminatorContext terminator() throws RecognitionException {
		TerminatorContext _localctx = new TerminatorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_terminator);
		int _la;
		try {
			setState(216);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				enterOuterAlt(_localctx, 1);
				{
				setState(209);
				match(SEMI);
				}
				break;
			case NL:
				enterOuterAlt(_localctx, 2);
				{
				setState(211); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(210);
					match(NL);
					}
					}
					setState(213); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NL );
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 3);
				{
				setState(215);
				match(EOF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpContext extends ParserRuleContext {
		public AssignExprContext assignExpr() {
			return getRuleContext(AssignExprContext.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitExp(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			assignExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignExprContext extends ParserRuleContext {
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public AssignExprContext assignExpr() {
			return getRuleContext(AssignExprContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(GrammarParser.ASSIGN, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(GrammarParser.PLUS_ASSIGN, 0); }
		public TerminalNode MINUS_ASSIGN() { return getToken(GrammarParser.MINUS_ASSIGN, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(GrammarParser.DIV_ASSIGN, 0); }
		public TerminalNode MULT_ASSIGN() { return getToken(GrammarParser.MULT_ASSIGN, 0); }
		public AssignExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterAssignExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitAssignExpr(this);
		}
	}

	public final AssignExprContext assignExpr() throws RecognitionException {
		AssignExprContext _localctx = new AssignExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_assignExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			orExpr();
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 137440919552L) != 0)) {
				{
				setState(221);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 137440919552L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(222);
				assignExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OrExprContext extends ParserRuleContext {
		public List<AndExprContext> andExpr() {
			return getRuleContexts(AndExprContext.class);
		}
		public AndExprContext andExpr(int i) {
			return getRuleContext(AndExprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(GrammarParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(GrammarParser.OR, i);
		}
		public OrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitOrExpr(this);
		}
	}

	public final OrExprContext orExpr() throws RecognitionException {
		OrExprContext _localctx = new OrExprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_orExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			andExpr();
			setState(230);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(226);
				match(OR);
				setState(227);
				andExpr();
				}
				}
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AndExprContext extends ParserRuleContext {
		public List<EqualityExprContext> equalityExpr() {
			return getRuleContexts(EqualityExprContext.class);
		}
		public EqualityExprContext equalityExpr(int i) {
			return getRuleContext(EqualityExprContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(GrammarParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(GrammarParser.AND, i);
		}
		public AndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitAndExpr(this);
		}
	}

	public final AndExprContext andExpr() throws RecognitionException {
		AndExprContext _localctx = new AndExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_andExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			equalityExpr();
			setState(238);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(234);
				match(AND);
				setState(235);
				equalityExpr();
				}
				}
				setState(240);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprContext extends ParserRuleContext {
		public List<RelationalContext> relational() {
			return getRuleContexts(RelationalContext.class);
		}
		public RelationalContext relational(int i) {
			return getRuleContext(RelationalContext.class,i);
		}
		public List<TerminalNode> EQ() { return getTokens(GrammarParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(GrammarParser.EQ, i);
		}
		public List<TerminalNode> NE() { return getTokens(GrammarParser.NE); }
		public TerminalNode NE(int i) {
			return getToken(GrammarParser.NE, i);
		}
		public EqualityExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterEqualityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitEqualityExpr(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_equalityExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			relational();
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQ || _la==NE) {
				{
				{
				setState(242);
				_la = _input.LA(1);
				if ( !(_la==EQ || _la==NE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(243);
				relational();
				}
				}
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationalContext extends ParserRuleContext {
		public List<AddExprContext> addExpr() {
			return getRuleContexts(AddExprContext.class);
		}
		public AddExprContext addExpr(int i) {
			return getRuleContext(AddExprContext.class,i);
		}
		public List<TerminalNode> GT() { return getTokens(GrammarParser.GT); }
		public TerminalNode GT(int i) {
			return getToken(GrammarParser.GT, i);
		}
		public List<TerminalNode> LT() { return getTokens(GrammarParser.LT); }
		public TerminalNode LT(int i) {
			return getToken(GrammarParser.LT, i);
		}
		public List<TerminalNode> GE() { return getTokens(GrammarParser.GE); }
		public TerminalNode GE(int i) {
			return getToken(GrammarParser.GE, i);
		}
		public List<TerminalNode> LE() { return getTokens(GrammarParser.LE); }
		public TerminalNode LE(int i) {
			return getToken(GrammarParser.LE, i);
		}
		public RelationalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterRelational(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitRelational(this);
		}
	}

	public final RelationalContext relational() throws RecognitionException {
		RelationalContext _localctx = new RelationalContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_relational);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			addExpr();
			setState(254);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2013265920L) != 0)) {
				{
				{
				setState(250);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2013265920L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(251);
				addExpr();
				}
				}
				setState(256);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AddExprContext extends ParserRuleContext {
		public List<MulExprContext> mulExpr() {
			return getRuleContexts(MulExprContext.class);
		}
		public MulExprContext mulExpr(int i) {
			return getRuleContext(MulExprContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(GrammarParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(GrammarParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(GrammarParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(GrammarParser.MINUS, i);
		}
		public AddExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterAddExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitAddExpr(this);
		}
	}

	public final AddExprContext addExpr() throws RecognitionException {
		AddExprContext _localctx = new AddExprContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_addExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			mulExpr();
			setState(262);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(258);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(259);
				mulExpr();
				}
				}
				setState(264);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MulExprContext extends ParserRuleContext {
		public List<PowerExprContext> powerExpr() {
			return getRuleContexts(PowerExprContext.class);
		}
		public PowerExprContext powerExpr(int i) {
			return getRuleContext(PowerExprContext.class,i);
		}
		public List<TerminalNode> MULTIPLY() { return getTokens(GrammarParser.MULTIPLY); }
		public TerminalNode MULTIPLY(int i) {
			return getToken(GrammarParser.MULTIPLY, i);
		}
		public List<TerminalNode> DIVIDE() { return getTokens(GrammarParser.DIVIDE); }
		public TerminalNode DIVIDE(int i) {
			return getToken(GrammarParser.DIVIDE, i);
		}
		public List<TerminalNode> MODULO() { return getTokens(GrammarParser.MODULO); }
		public TerminalNode MODULO(int i) {
			return getToken(GrammarParser.MODULO, i);
		}
		public MulExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mulExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterMulExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitMulExpr(this);
		}
	}

	public final MulExprContext mulExpr() throws RecognitionException {
		MulExprContext _localctx = new MulExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_mulExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			powerExpr();
			setState(270);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 92274688L) != 0)) {
				{
				{
				setState(266);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 92274688L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(267);
				powerExpr();
				}
				}
				setState(272);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PowerExprContext extends ParserRuleContext {
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public TerminalNode POWER() { return getToken(GrammarParser.POWER, 0); }
		public PowerExprContext powerExpr() {
			return getRuleContext(PowerExprContext.class,0);
		}
		public PowerExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_powerExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPowerExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPowerExpr(this);
		}
	}

	public final PowerExprContext powerExpr() throws RecognitionException {
		PowerExprContext _localctx = new PowerExprContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_powerExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			unaryExpr();
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==POWER) {
				{
				setState(274);
				match(POWER);
				setState(275);
				powerExpr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExprContext extends ParserRuleContext {
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(GrammarParser.NOT, 0); }
		public PostfixExprContext postfixExpr() {
			return getRuleContext(PostfixExprContext.class,0);
		}
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterUnaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitUnaryExpr(this);
		}
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_unaryExpr);
		try {
			setState(281);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(278);
				match(NOT);
				}
				setState(279);
				unaryExpr();
				}
				break;
			case T__1:
			case STRING:
			case INT:
			case DOUBLE:
			case BOOL:
			case ID:
			case DYNARR_START:
				enterOuterAlt(_localctx, 2);
				{
				setState(280);
				postfixExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PostfixExprContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public List<PostFixOpContext> postFixOp() {
			return getRuleContexts(PostFixOpContext.class);
		}
		public PostFixOpContext postFixOp(int i) {
			return getRuleContext(PostFixOpContext.class,i);
		}
		public PostfixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPostfixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPostfixExpr(this);
		}
	}

	public final PostfixExprContext postfixExpr() throws RecognitionException {
		PostfixExprContext _localctx = new PostfixExprContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_postfixExpr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			primary();
			setState(287);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(284);
					postFixOp();
					}
					} 
				}
				setState(289);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PostFixOpContext extends ParserRuleContext {
		public TerminalNode INC() { return getToken(GrammarParser.INC, 0); }
		public TerminalNode DEC() { return getToken(GrammarParser.DEC, 0); }
		public TerminalNode DYNARR_START() { return getToken(GrammarParser.DYNARR_START, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode DYNARR_END() { return getToken(GrammarParser.DYNARR_END, 0); }
		public ExpSeparatorContext expSeparator() {
			return getRuleContext(ExpSeparatorContext.class,0);
		}
		public PostFixOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postFixOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPostFixOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPostFixOp(this);
		}
	}

	public final PostFixOpContext postFixOp() throws RecognitionException {
		PostFixOpContext _localctx = new PostFixOpContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_postFixOp);
		try {
			setState(300);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INC:
				enterOuterAlt(_localctx, 1);
				{
				setState(290);
				match(INC);
				}
				break;
			case DEC:
				enterOuterAlt(_localctx, 2);
				{
				setState(291);
				match(DEC);
				}
				break;
			case DYNARR_START:
				enterOuterAlt(_localctx, 3);
				{
				setState(292);
				match(DYNARR_START);
				setState(293);
				exp();
				setState(294);
				match(DYNARR_END);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 4);
				{
				setState(296);
				match(T__1);
				setState(297);
				expSeparator();
				setState(298);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryDoubleContext extends PrimaryContext {
		public TerminalNode DOUBLE() { return getToken(GrammarParser.DOUBLE, 0); }
		public PrimaryDoubleContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPrimaryDouble(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPrimaryDouble(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryStringContext extends PrimaryContext {
		public TerminalNode STRING() { return getToken(GrammarParser.STRING, 0); }
		public PrimaryStringContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPrimaryString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPrimaryString(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryArrayLiteralContext extends PrimaryContext {
		public TerminalNode DYNARR_START() { return getToken(GrammarParser.DYNARR_START, 0); }
		public ExpSeparatorContext expSeparator() {
			return getRuleContext(ExpSeparatorContext.class,0);
		}
		public TerminalNode DYNARR_END() { return getToken(GrammarParser.DYNARR_END, 0); }
		public PrimaryArrayLiteralContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPrimaryArrayLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPrimaryArrayLiteral(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryBoolContext extends PrimaryContext {
		public TerminalNode BOOL() { return getToken(GrammarParser.BOOL, 0); }
		public PrimaryBoolContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPrimaryBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPrimaryBool(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryIntContext extends PrimaryContext {
		public TerminalNode INT() { return getToken(GrammarParser.INT, 0); }
		public PrimaryIntContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPrimaryInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPrimaryInt(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryParenContext extends PrimaryContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public PrimaryParenContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPrimaryParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPrimaryParen(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryIdContext extends PrimaryContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public PrimaryIdContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPrimaryId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPrimaryId(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_primary);
		try {
			setState(315);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				_localctx = new PrimaryParenContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(302);
				match(T__1);
				setState(303);
				exp();
				setState(304);
				match(T__2);
				}
				break;
			case INT:
				_localctx = new PrimaryIntContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(306);
				match(INT);
				}
				break;
			case DOUBLE:
				_localctx = new PrimaryDoubleContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(307);
				match(DOUBLE);
				}
				break;
			case STRING:
				_localctx = new PrimaryStringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(308);
				match(STRING);
				}
				break;
			case BOOL:
				_localctx = new PrimaryBoolContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(309);
				match(BOOL);
				}
				break;
			case ID:
				_localctx = new PrimaryIdContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(310);
				match(ID);
				}
				break;
			case DYNARR_START:
				_localctx = new PrimaryArrayLiteralContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(311);
				match(DYNARR_START);
				setState(312);
				expSeparator();
				setState(313);
				match(DYNARR_END);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpSeparatorContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ExpSeparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expSeparator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterExpSeparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitExpSeparator(this);
		}
	}

	public final ExpSeparatorContext expSeparator() throws RecognitionException {
		ExpSeparatorContext _localctx = new ExpSeparatorContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_expSeparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 377957244932L) != 0)) {
				{
				setState(317);
				exp();
				setState(322);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(318);
					match(T__10);
					setState(319);
					exp();
					}
					}
					setState(324);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamSeparatorContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamSeparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramSeparator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterParamSeparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitParamSeparator(this);
		}
	}

	public final ParamSeparatorContext paramSeparator() throws RecognitionException {
		ParamSeparatorContext _localctx = new ParamSeparatorContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_paramSeparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE || _la==ID) {
				{
				setState(327);
				param();
				setState(332);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(328);
					match(T__10);
					setState(329);
					param();
					}
					}
					setState(334);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001/\u0152\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0001\u0000\u0004\u0000B\b\u0000\u000b\u0000"+
		"\f\u0000C\u0001\u0001\u0005\u0001G\b\u0001\n\u0001\f\u0001J\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0004\u0001N\b\u0001\u000b\u0001\f\u0001O\u0001\u0001"+
		"\u0001\u0001\u0005\u0001T\b\u0001\n\u0001\f\u0001W\t\u0001\u0003\u0001"+
		"Y\b\u0001\u0001\u0001\u0005\u0001\\\b\u0001\n\u0001\f\u0001_\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0003\u0002e\b\u0002\u0001"+
		"\u0003\u0003\u0003h\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0003\u0004r\b"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0003\u0005x\b"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006~\b"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u0084"+
		"\b\u0007\u0001\b\u0001\b\u0005\b\u0088\b\b\n\b\f\b\u008b\t\b\u0001\b\u0001"+
		"\b\u0004\b\u008f\b\b\u000b\b\f\b\u0090\u0001\b\u0001\b\u0005\b\u0095\b"+
		"\b\n\b\f\b\u0098\t\b\u0003\b\u009a\b\b\u0001\b\u0005\b\u009d\b\b\n\b\f"+
		"\b\u00a0\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0003\t\u00a6\b\t\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u00b4\b\f\n\f\f\f\u00b7\t\f\u0001"+
		"\f\u0001\f\u0001\f\u0003\f\u00bc\b\f\u0003\f\u00be\b\f\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0003\r\u00c4\b\r\u0001\u000e\u0003\u000e\u00c7\b\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000f\u0003\u000f\u00cc\b\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0004\u0010"+
		"\u00d4\b\u0010\u000b\u0010\f\u0010\u00d5\u0001\u0010\u0003\u0010\u00d9"+
		"\b\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0003"+
		"\u0012\u00e0\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00e5"+
		"\b\u0013\n\u0013\f\u0013\u00e8\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0005\u0014\u00ed\b\u0014\n\u0014\f\u0014\u00f0\t\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0005\u0015\u00f5\b\u0015\n\u0015\f\u0015\u00f8\t\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u00fd\b\u0016\n\u0016"+
		"\f\u0016\u0100\t\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017"+
		"\u0105\b\u0017\n\u0017\f\u0017\u0108\t\u0017\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0005\u0018\u010d\b\u0018\n\u0018\f\u0018\u0110\t\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0003\u0019\u0115\b\u0019\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0003\u001a\u011a\b\u001a\u0001\u001b\u0001\u001b\u0005\u001b"+
		"\u011e\b\u001b\n\u001b\f\u001b\u0121\t\u001b\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0003\u001c\u012d\b\u001c\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u013c"+
		"\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0005\u001e\u0141\b\u001e"+
		"\n\u001e\f\u001e\u0144\t\u001e\u0003\u001e\u0146\b\u001e\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0005\u001f\u014b\b\u001f\n\u001f\f\u001f\u014e\t\u001f"+
		"\u0003\u001f\u0150\b\u001f\u0001\u001f\u0000\u0000 \u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.02468:<>\u0000\u0006\u0001\u0000,-\u0002\u0000\u0011\u0014%%\u0001"+
		"\u0000\u001f \u0001\u0000\u001b\u001e\u0001\u0000\u0015\u0016\u0002\u0000"+
		"\u0017\u0018\u001a\u001a\u0167\u0000A\u0001\u0000\u0000\u0000\u0002H\u0001"+
		"\u0000\u0000\u0000\u0004d\u0001\u0000\u0000\u0000\u0006g\u0001\u0000\u0000"+
		"\u0000\bq\u0001\u0000\u0000\u0000\nw\u0001\u0000\u0000\u0000\f}\u0001"+
		"\u0000\u0000\u0000\u000e\u0083\u0001\u0000\u0000\u0000\u0010\u0085\u0001"+
		"\u0000\u0000\u0000\u0012\u00a3\u0001\u0000\u0000\u0000\u0014\u00a7\u0001"+
		"\u0000\u0000\u0000\u0016\u00ab\u0001\u0000\u0000\u0000\u0018\u00af\u0001"+
		"\u0000\u0000\u0000\u001a\u00c3\u0001\u0000\u0000\u0000\u001c\u00c6\u0001"+
		"\u0000\u0000\u0000\u001e\u00cb\u0001\u0000\u0000\u0000 \u00d8\u0001\u0000"+
		"\u0000\u0000\"\u00da\u0001\u0000\u0000\u0000$\u00dc\u0001\u0000\u0000"+
		"\u0000&\u00e1\u0001\u0000\u0000\u0000(\u00e9\u0001\u0000\u0000\u0000*"+
		"\u00f1\u0001\u0000\u0000\u0000,\u00f9\u0001\u0000\u0000\u0000.\u0101\u0001"+
		"\u0000\u0000\u00000\u0109\u0001\u0000\u0000\u00002\u0111\u0001\u0000\u0000"+
		"\u00004\u0119\u0001\u0000\u0000\u00006\u011b\u0001\u0000\u0000\u00008"+
		"\u012c\u0001\u0000\u0000\u0000:\u013b\u0001\u0000\u0000\u0000<\u0145\u0001"+
		"\u0000\u0000\u0000>\u014f\u0001\u0000\u0000\u0000@B\u0007\u0000\u0000"+
		"\u0000A@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CA\u0001\u0000"+
		"\u0000\u0000CD\u0001\u0000\u0000\u0000D\u0001\u0001\u0000\u0000\u0000"+
		"EG\u0003\u0000\u0000\u0000FE\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000"+
		"\u0000HF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000IX\u0001\u0000"+
		"\u0000\u0000JH\u0001\u0000\u0000\u0000KU\u0003\u0004\u0002\u0000LN\u0003"+
		"\u0000\u0000\u0000ML\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000"+
		"OM\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000"+
		"\u0000QR\u0003\u0004\u0002\u0000RT\u0001\u0000\u0000\u0000SM\u0001\u0000"+
		"\u0000\u0000TW\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000UV\u0001"+
		"\u0000\u0000\u0000VY\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000"+
		"XK\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000Y]\u0001\u0000\u0000"+
		"\u0000Z\\\u0003\u0000\u0000\u0000[Z\u0001\u0000\u0000\u0000\\_\u0001\u0000"+
		"\u0000\u0000][\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000^`\u0001"+
		"\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000`a\u0005\u0000\u0000\u0001"+
		"a\u0003\u0001\u0000\u0000\u0000be\u0003\u0006\u0003\u0000ce\u0003\n\u0005"+
		"\u0000db\u0001\u0000\u0000\u0000dc\u0001\u0000\u0000\u0000e\u0005\u0001"+
		"\u0000\u0000\u0000fh\u0003\u001a\r\u0000gf\u0001\u0000\u0000\u0000gh\u0001"+
		"\u0000\u0000\u0000hi\u0001\u0000\u0000\u0000ij\u0005\u0001\u0000\u0000"+
		"jk\u0005$\u0000\u0000kl\u0005\u0002\u0000\u0000lm\u0003>\u001f\u0000m"+
		"n\u0005\u0003\u0000\u0000no\u0003\u0010\b\u0000o\u0007\u0001\u0000\u0000"+
		"\u0000pr\u0005\f\u0000\u0000qp\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000"+
		"\u0000rs\u0001\u0000\u0000\u0000st\u0005$\u0000\u0000t\t\u0001\u0000\u0000"+
		"\u0000ux\u0003\f\u0006\u0000vx\u0003\u000e\u0007\u0000wu\u0001\u0000\u0000"+
		"\u0000wv\u0001\u0000\u0000\u0000x\u000b\u0001\u0000\u0000\u0000y~\u0003"+
		"\u001c\u000e\u0000z~\u0003\u001e\u000f\u0000{~\u0003\u0012\t\u0000|~\u0003"+
		"\"\u0011\u0000}y\u0001\u0000\u0000\u0000}z\u0001\u0000\u0000\u0000}{\u0001"+
		"\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000~\r\u0001\u0000\u0000\u0000"+
		"\u007f\u0084\u0003\u0014\n\u0000\u0080\u0084\u0003\u0016\u000b\u0000\u0081"+
		"\u0084\u0003\u0018\f\u0000\u0082\u0084\u0003\u0010\b\u0000\u0083\u007f"+
		"\u0001\u0000\u0000\u0000\u0083\u0080\u0001\u0000\u0000\u0000\u0083\u0081"+
		"\u0001\u0000\u0000\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0084\u000f"+
		"\u0001\u0000\u0000\u0000\u0085\u0089\u0005\u0004\u0000\u0000\u0086\u0088"+
		"\u0003\u0000\u0000\u0000\u0087\u0086\u0001\u0000\u0000\u0000\u0088\u008b"+
		"\u0001\u0000\u0000\u0000\u0089\u0087\u0001\u0000\u0000\u0000\u0089\u008a"+
		"\u0001\u0000\u0000\u0000\u008a\u0099\u0001\u0000\u0000\u0000\u008b\u0089"+
		"\u0001\u0000\u0000\u0000\u008c\u0096\u0003\n\u0005\u0000\u008d\u008f\u0003"+
		"\u0000\u0000\u0000\u008e\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001"+
		"\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0090\u0091\u0001"+
		"\u0000\u0000\u0000\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u0093\u0003"+
		"\n\u0005\u0000\u0093\u0095\u0001\u0000\u0000\u0000\u0094\u008e\u0001\u0000"+
		"\u0000\u0000\u0095\u0098\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000"+
		"\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u009a\u0001\u0000"+
		"\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0099\u008c\u0001\u0000"+
		"\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u009e\u0001\u0000"+
		"\u0000\u0000\u009b\u009d\u0003\u0000\u0000\u0000\u009c\u009b\u0001\u0000"+
		"\u0000\u0000\u009d\u00a0\u0001\u0000\u0000\u0000\u009e\u009c\u0001\u0000"+
		"\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\u0005"+
		"\u0000\u0000\u00a2\u0011\u0001\u0000\u0000\u0000\u00a3\u00a5\u0005\u0006"+
		"\u0000\u0000\u00a4\u00a6\u0003\"\u0011\u0000\u00a5\u00a4\u0001\u0000\u0000"+
		"\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6\u0013\u0001\u0000\u0000"+
		"\u0000\u00a7\u00a8\u0005\u0007\u0000\u0000\u00a8\u00a9\u0003\"\u0011\u0000"+
		"\u00a9\u00aa\u0003\n\u0005\u0000\u00aa\u0015\u0001\u0000\u0000\u0000\u00ab"+
		"\u00ac\u0005\b\u0000\u0000\u00ac\u00ad\u0003\"\u0011\u0000\u00ad\u00ae"+
		"\u0003\n\u0005\u0000\u00ae\u0017\u0001\u0000\u0000\u0000\u00af\u00b0\u0005"+
		"\t\u0000\u0000\u00b0\u00b1\u0003\"\u0011\u0000\u00b1\u00bd\u0003\u0010"+
		"\b\u0000\u00b2\u00b4\u0003\u0000\u0000\u0000\u00b3\u00b2\u0001\u0000\u0000"+
		"\u0000\u00b4\u00b7\u0001\u0000\u0000\u0000\u00b5\u00b3\u0001\u0000\u0000"+
		"\u0000\u00b5\u00b6\u0001\u0000\u0000\u0000\u00b6\u00b8\u0001\u0000\u0000"+
		"\u0000\u00b7\u00b5\u0001\u0000\u0000\u0000\u00b8\u00bb\u0005\n\u0000\u0000"+
		"\u00b9\u00bc\u0003\u0018\f\u0000\u00ba\u00bc\u0003\u0010\b\u0000\u00bb"+
		"\u00b9\u0001\u0000\u0000\u0000\u00bb\u00ba\u0001\u0000\u0000\u0000\u00bc"+
		"\u00be\u0001\u0000\u0000\u0000\u00bd\u00b5\u0001\u0000\u0000\u0000\u00bd"+
		"\u00be\u0001\u0000\u0000\u0000\u00be\u0019\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c4\u0005\f\u0000\u0000\u00c0\u00c1\u0005&\u0000\u0000\u00c1\u00c2"+
		"\u0005\f\u0000\u0000\u00c2\u00c4\u0005\'\u0000\u0000\u00c3\u00bf\u0001"+
		"\u0000\u0000\u0000\u00c3\u00c0\u0001\u0000\u0000\u0000\u00c4\u001b\u0001"+
		"\u0000\u0000\u0000\u00c5\u00c7\u0003\u001a\r\u0000\u00c6\u00c5\u0001\u0000"+
		"\u0000\u0000\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c9\u0005$\u0000\u0000\u00c9\u001d\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cc\u0003\u001a\r\u0000\u00cb\u00ca\u0001\u0000\u0000\u0000"+
		"\u00cb\u00cc\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000"+
		"\u00cd\u00ce\u0005$\u0000\u0000\u00ce\u00cf\u0005%\u0000\u0000\u00cf\u00d0"+
		"\u0003\"\u0011\u0000\u00d0\u001f\u0001\u0000\u0000\u0000\u00d1\u00d9\u0005"+
		"-\u0000\u0000\u00d2\u00d4\u0005,\u0000\u0000\u00d3\u00d2\u0001\u0000\u0000"+
		"\u0000\u00d4\u00d5\u0001\u0000\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000"+
		"\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6\u00d9\u0001\u0000\u0000"+
		"\u0000\u00d7\u00d9\u0005\u0000\u0000\u0001\u00d8\u00d1\u0001\u0000\u0000"+
		"\u0000\u00d8\u00d3\u0001\u0000\u0000\u0000\u00d8\u00d7\u0001\u0000\u0000"+
		"\u0000\u00d9!\u0001\u0000\u0000\u0000\u00da\u00db\u0003$\u0012\u0000\u00db"+
		"#\u0001\u0000\u0000\u0000\u00dc\u00df\u0003&\u0013\u0000\u00dd\u00de\u0007"+
		"\u0001\u0000\u0000\u00de\u00e0\u0003$\u0012\u0000\u00df\u00dd\u0001\u0000"+
		"\u0000\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0%\u0001\u0000\u0000"+
		"\u0000\u00e1\u00e6\u0003(\u0014\u0000\u00e2\u00e3\u0005\"\u0000\u0000"+
		"\u00e3\u00e5\u0003(\u0014\u0000\u00e4\u00e2\u0001\u0000\u0000\u0000\u00e5"+
		"\u00e8\u0001\u0000\u0000\u0000\u00e6\u00e4\u0001\u0000\u0000\u0000\u00e6"+
		"\u00e7\u0001\u0000\u0000\u0000\u00e7\'\u0001\u0000\u0000\u0000\u00e8\u00e6"+
		"\u0001\u0000\u0000\u0000\u00e9\u00ee\u0003*\u0015\u0000\u00ea\u00eb\u0005"+
		"!\u0000\u0000\u00eb\u00ed\u0003*\u0015\u0000\u00ec\u00ea\u0001\u0000\u0000"+
		"\u0000\u00ed\u00f0\u0001\u0000\u0000\u0000\u00ee\u00ec\u0001\u0000\u0000"+
		"\u0000\u00ee\u00ef\u0001\u0000\u0000\u0000\u00ef)\u0001\u0000\u0000\u0000"+
		"\u00f0\u00ee\u0001\u0000\u0000\u0000\u00f1\u00f6\u0003,\u0016\u0000\u00f2"+
		"\u00f3\u0007\u0002\u0000\u0000\u00f3\u00f5\u0003,\u0016\u0000\u00f4\u00f2"+
		"\u0001\u0000\u0000\u0000\u00f5\u00f8\u0001\u0000\u0000\u0000\u00f6\u00f4"+
		"\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000\u0000\u00f7+\u0001"+
		"\u0000\u0000\u0000\u00f8\u00f6\u0001\u0000\u0000\u0000\u00f9\u00fe\u0003"+
		".\u0017\u0000\u00fa\u00fb\u0007\u0003\u0000\u0000\u00fb\u00fd\u0003.\u0017"+
		"\u0000\u00fc\u00fa\u0001\u0000\u0000\u0000\u00fd\u0100\u0001\u0000\u0000"+
		"\u0000\u00fe\u00fc\u0001\u0000\u0000\u0000\u00fe\u00ff\u0001\u0000\u0000"+
		"\u0000\u00ff-\u0001\u0000\u0000\u0000\u0100\u00fe\u0001\u0000\u0000\u0000"+
		"\u0101\u0106\u00030\u0018\u0000\u0102\u0103\u0007\u0004\u0000\u0000\u0103"+
		"\u0105\u00030\u0018\u0000\u0104\u0102\u0001\u0000\u0000\u0000\u0105\u0108"+
		"\u0001\u0000\u0000\u0000\u0106\u0104\u0001\u0000\u0000\u0000\u0106\u0107"+
		"\u0001\u0000\u0000\u0000\u0107/\u0001\u0000\u0000\u0000\u0108\u0106\u0001"+
		"\u0000\u0000\u0000\u0109\u010e\u00032\u0019\u0000\u010a\u010b\u0007\u0005"+
		"\u0000\u0000\u010b\u010d\u00032\u0019\u0000\u010c\u010a\u0001\u0000\u0000"+
		"\u0000\u010d\u0110\u0001\u0000\u0000\u0000\u010e\u010c\u0001\u0000\u0000"+
		"\u0000\u010e\u010f\u0001\u0000\u0000\u0000\u010f1\u0001\u0000\u0000\u0000"+
		"\u0110\u010e\u0001\u0000\u0000\u0000\u0111\u0114\u00034\u001a\u0000\u0112"+
		"\u0113\u0005\u0019\u0000\u0000\u0113\u0115\u00032\u0019\u0000\u0114\u0112"+
		"\u0001\u0000\u0000\u0000\u0114\u0115\u0001\u0000\u0000\u0000\u01153\u0001"+
		"\u0000\u0000\u0000\u0116\u0117\u0005#\u0000\u0000\u0117\u011a\u00034\u001a"+
		"\u0000\u0118\u011a\u00036\u001b\u0000\u0119\u0116\u0001\u0000\u0000\u0000"+
		"\u0119\u0118\u0001\u0000\u0000\u0000\u011a5\u0001\u0000\u0000\u0000\u011b"+
		"\u011f\u0003:\u001d\u0000\u011c\u011e\u00038\u001c\u0000\u011d\u011c\u0001"+
		"\u0000\u0000\u0000\u011e\u0121\u0001\u0000\u0000\u0000\u011f\u011d\u0001"+
		"\u0000\u0000\u0000\u011f\u0120\u0001\u0000\u0000\u0000\u01207\u0001\u0000"+
		"\u0000\u0000\u0121\u011f\u0001\u0000\u0000\u0000\u0122\u012d\u0005(\u0000"+
		"\u0000\u0123\u012d\u0005)\u0000\u0000\u0124\u0125\u0005&\u0000\u0000\u0125"+
		"\u0126\u0003\"\u0011\u0000\u0126\u0127\u0005\'\u0000\u0000\u0127\u012d"+
		"\u0001\u0000\u0000\u0000\u0128\u0129\u0005\u0002\u0000\u0000\u0129\u012a"+
		"\u0003<\u001e\u0000\u012a\u012b\u0005\u0003\u0000\u0000\u012b\u012d\u0001"+
		"\u0000\u0000\u0000\u012c\u0122\u0001\u0000\u0000\u0000\u012c\u0123\u0001"+
		"\u0000\u0000\u0000\u012c\u0124\u0001\u0000\u0000\u0000\u012c\u0128\u0001"+
		"\u0000\u0000\u0000\u012d9\u0001\u0000\u0000\u0000\u012e\u012f\u0005\u0002"+
		"\u0000\u0000\u012f\u0130\u0003\"\u0011\u0000\u0130\u0131\u0005\u0003\u0000"+
		"\u0000\u0131\u013c\u0001\u0000\u0000\u0000\u0132\u013c\u0005\u000e\u0000"+
		"\u0000\u0133\u013c\u0005\u000f\u0000\u0000\u0134\u013c\u0005\r\u0000\u0000"+
		"\u0135\u013c\u0005\u0010\u0000\u0000\u0136\u013c\u0005$\u0000\u0000\u0137"+
		"\u0138\u0005&\u0000\u0000\u0138\u0139\u0003<\u001e\u0000\u0139\u013a\u0005"+
		"\'\u0000\u0000\u013a\u013c\u0001\u0000\u0000\u0000\u013b\u012e\u0001\u0000"+
		"\u0000\u0000\u013b\u0132\u0001\u0000\u0000\u0000\u013b\u0133\u0001\u0000"+
		"\u0000\u0000\u013b\u0134\u0001\u0000\u0000\u0000\u013b\u0135\u0001\u0000"+
		"\u0000\u0000\u013b\u0136\u0001\u0000\u0000\u0000\u013b\u0137\u0001\u0000"+
		"\u0000\u0000\u013c;\u0001\u0000\u0000\u0000\u013d\u0142\u0003\"\u0011"+
		"\u0000\u013e\u013f\u0005\u000b\u0000\u0000\u013f\u0141\u0003\"\u0011\u0000"+
		"\u0140\u013e\u0001\u0000\u0000\u0000\u0141\u0144\u0001\u0000\u0000\u0000"+
		"\u0142\u0140\u0001\u0000\u0000\u0000\u0142\u0143\u0001\u0000\u0000\u0000"+
		"\u0143\u0146\u0001\u0000\u0000\u0000\u0144\u0142\u0001\u0000\u0000\u0000"+
		"\u0145\u013d\u0001\u0000\u0000\u0000\u0145\u0146\u0001\u0000\u0000\u0000"+
		"\u0146=\u0001\u0000\u0000\u0000\u0147\u014c\u0003\b\u0004\u0000\u0148"+
		"\u0149\u0005\u000b\u0000\u0000\u0149\u014b\u0003\b\u0004\u0000\u014a\u0148"+
		"\u0001\u0000\u0000\u0000\u014b\u014e\u0001\u0000\u0000\u0000\u014c\u014a"+
		"\u0001\u0000\u0000\u0000\u014c\u014d\u0001\u0000\u0000\u0000\u014d\u0150"+
		"\u0001\u0000\u0000\u0000\u014e\u014c\u0001\u0000\u0000\u0000\u014f\u0147"+
		"\u0001\u0000\u0000\u0000\u014f\u0150\u0001\u0000\u0000\u0000\u0150?\u0001"+
		"\u0000\u0000\u0000*CHOUX]dgqw}\u0083\u0089\u0090\u0096\u0099\u009e\u00a5"+
		"\u00b5\u00bb\u00bd\u00c3\u00c6\u00cb\u00d5\u00d8\u00df\u00e6\u00ee\u00f6"+
		"\u00fe\u0106\u010e\u0114\u0119\u011f\u012c\u013b\u0142\u0145\u014c\u014f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}