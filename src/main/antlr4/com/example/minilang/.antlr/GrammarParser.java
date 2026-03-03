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
		T__9=10, T__10=11, STRING=12, DOUBLE=13, INT=14, BOOL=15, PLUS=16, MINUS=17, 
		MULTIPLY=18, DIVIDE=19, WHITESPACE=20, POWER=21, GT=22, LT=23, LE=24, 
		GE=25, EQ=26, NE=27, AND=28, OR=29, NOT=30, ID=31, ASSIGN=32, PLUS_ASSIGN=33, 
		MINUS_ASSIGN=34, MULT_ASSIGN=35, DIV_ASSIGN=36, DYNARR_START=37, DYNARR_END=38, 
		SEXPEND=39, TYPE=40, INC=41, DEC=42, LINE_COMMENT=43, BLOCK_COMMENT=44, 
		WS=45;
	public static final int
		RULE_program = 0, RULE_def = 1, RULE_func = 2, RULE_param = 3, RULE_stm = 4, 
		RULE_decl = 5, RULE_init = 6, RULE_block = 7, RULE_ifStmt = 8, RULE_exp = 9, 
		RULE_expSeparator = 10, RULE_paramSeparator = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "def", "func", "param", "stm", "decl", "init", "block", "ifStmt", 
			"exp", "expSeparator", "paramSeparator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'func'", "'('", "')'", "'return'", "'while'", "'{'", "'}'", "'do'", 
			"'if'", "'else'", "','", null, null, null, null, "'+'", "'-'", "'*'", 
			"'/'", null, "'**'", "'>'", "'<'", "'<='", "'>='", "'=='", "'!='", "'and'", 
			"'or'", "'not'", null, "'='", "'+='", "'-='", "'*='", "'/='", "'['", 
			"']'", null, null, "'++'", "'--'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"STRING", "DOUBLE", "INT", "BOOL", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", 
			"WHITESPACE", "POWER", "GT", "LT", "LE", "GE", "EQ", "NE", "AND", "OR", 
			"NOT", "ID", "ASSIGN", "PLUS_ASSIGN", "MINUS_ASSIGN", "MULT_ASSIGN", 
			"DIV_ASSIGN", "DYNARR_START", "DYNARR_END", "SEXPEND", "TYPE", "INC", 
			"DEC", "LINE_COMMENT", "BLOCK_COMMENT", "WS"
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
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
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
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1239098094454L) != 0)) {
				{
				{
				setState(24);
				def();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
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
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DStmContext extends DefContext {
		public StmContext stm() {
			return getRuleContext(StmContext.class,0);
		}
		public DStmContext(DefContext ctx) { copyFrom(ctx); }
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_def);
		try {
			setState(34);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				_localctx = new DStmContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(32);
				stm();
				}
				break;
			case 2:
				_localctx = new DFuncContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				func();
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
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
	 
		public FuncContext() { }
		public void copyFrom(FuncContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncNoInferenceContext extends FuncContext {
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public ParamSeparatorContext paramSeparator() {
			return getRuleContext(ParamSeparatorContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FuncNoInferenceContext(FuncContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncInferenceContext extends FuncContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public ExpSeparatorContext expSeparator() {
			return getRuleContext(ExpSeparatorContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FuncInferenceContext(FuncContext ctx) { copyFrom(ctx); }
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_func);
		try {
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPE:
				_localctx = new FuncNoInferenceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				match(TYPE);
				setState(37);
				match(T__0);
				setState(38);
				match(ID);
				setState(39);
				match(T__1);
				setState(40);
				paramSeparator();
				setState(41);
				match(T__2);
				setState(42);
				block();
				}
				break;
			case T__0:
				_localctx = new FuncInferenceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				match(T__0);
				setState(45);
				match(ID);
				setState(46);
				match(T__1);
				setState(47);
				expSeparator();
				setState(48);
				match(T__2);
				setState(49);
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
	public static class ParamContext extends ParserRuleContext {
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
	 
		public ParamContext() { }
		public void copyFrom(ParamContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParamDeclContext extends ParamContext {
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public ParamDeclContext(ParamContext ctx) { copyFrom(ctx); }
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_param);
		try {
			_localctx = new ParamDeclContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(TYPE);
			setState(54);
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
	public static class StmContext extends ParserRuleContext {
		public StmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stm; }
	 
		public StmContext() { }
		public void copyFrom(StmContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SWhileContext extends StmContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public StmContext stm() {
			return getRuleContext(StmContext.class,0);
		}
		public SWhileContext(StmContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SInitContext extends StmContext {
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public TerminalNode SEXPEND() { return getToken(GrammarParser.SEXPEND, 0); }
		public SInitContext(StmContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SReturnContext extends StmContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public SReturnContext(StmContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SExpContext extends StmContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode SEXPEND() { return getToken(GrammarParser.SEXPEND, 0); }
		public SExpContext(StmContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SIfElseContext extends StmContext {
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public SIfElseContext(StmContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SBlockContext extends StmContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public SBlockContext(StmContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SDoContext extends StmContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public StmContext stm() {
			return getRuleContext(StmContext.class,0);
		}
		public SDoContext(StmContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SDeclContext extends StmContext {
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public TerminalNode SEXPEND() { return getToken(GrammarParser.SEXPEND, 0); }
		public SDeclContext(StmContext ctx) { copyFrom(ctx); }
	}

	public final StmContext stm() throws RecognitionException {
		StmContext _localctx = new StmContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_stm);
		try {
			setState(81);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				_localctx = new SExpContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				exp(0);
				setState(57);
				match(SEXPEND);
				}
				break;
			case 2:
				_localctx = new SReturnContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				match(T__3);
				setState(60);
				exp(0);
				}
				break;
			case 3:
				_localctx = new SWhileContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(61);
				match(T__4);
				setState(62);
				exp(0);
				setState(63);
				match(T__5);
				setState(64);
				stm();
				setState(65);
				match(T__6);
				}
				break;
			case 4:
				_localctx = new SDoContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				match(T__7);
				setState(68);
				exp(0);
				setState(69);
				match(T__5);
				setState(70);
				stm();
				setState(71);
				match(T__6);
				}
				break;
			case 5:
				_localctx = new SIfElseContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(73);
				ifStmt();
				}
				break;
			case 6:
				_localctx = new SBlockContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(74);
				block();
				}
				break;
			case 7:
				_localctx = new SDeclContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(75);
				decl();
				setState(76);
				match(SEXPEND);
				}
				break;
			case 8:
				_localctx = new SInitContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(78);
				init();
				setState(79);
				match(SEXPEND);
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
	public static class DeclContext extends ParserRuleContext {
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
	 
		public DeclContext() { }
		public void copyFrom(DeclContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclNoInferenceContext extends DeclContext {
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public DeclNoInferenceContext(DeclContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclInferenceContext extends DeclContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public DeclInferenceContext(DeclContext ctx) { copyFrom(ctx); }
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_decl);
		try {
			setState(86);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPE:
				_localctx = new DeclNoInferenceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				match(TYPE);
				setState(84);
				match(ID);
				}
				break;
			case ID:
				_localctx = new DeclInferenceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				match(ID);
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
	public static class InitContext extends ParserRuleContext {
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
	 
		public InitContext() { }
		public void copyFrom(InitContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InitNoInferenceContext extends InitContext {
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GrammarParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public InitNoInferenceContext(InitContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InitInferenceContext extends InitContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GrammarParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public InitInferenceContext(InitContext ctx) { copyFrom(ctx); }
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_init);
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPE:
				_localctx = new InitNoInferenceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				match(TYPE);
				setState(89);
				match(ID);
				setState(90);
				match(ASSIGN);
				setState(91);
				exp(0);
				}
				break;
			case ID:
				_localctx = new InitInferenceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				match(ID);
				setState(93);
				match(ASSIGN);
				setState(94);
				exp(0);
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
		public List<StmContext> stm() {
			return getRuleContexts(StmContext.class);
		}
		public StmContext stm(int i) {
			return getRuleContext(StmContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T__5);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1239098094452L) != 0)) {
				{
				{
				setState(98);
				stm();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
			match(T__6);
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
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
	 
		public IfStmtContext() { }
		public void copyFrom(IfStmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfElseIfContext extends IfStmtContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public IfElseIfContext(IfStmtContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfElseContext extends IfStmtContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public IfElseContext(IfStmtContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfContext extends IfStmtContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfContext(IfStmtContext ctx) { copyFrom(ctx); }
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_ifStmt);
		int _la;
		try {
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new IfElseIfContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				match(T__8);
				setState(107);
				exp(0);
				setState(108);
				block();
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(109);
					match(T__9);
					setState(110);
					ifStmt();
					}
				}

				}
				break;
			case 2:
				_localctx = new IfElseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
				match(T__8);
				setState(114);
				exp(0);
				setState(115);
				block();
				setState(116);
				match(T__9);
				setState(117);
				block();
				}
				break;
			case 3:
				_localctx = new IfContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(119);
				match(T__8);
				setState(120);
				exp(0);
				setState(121);
				block();
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
	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EPlusAssignExpContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(GrammarParser.PLUS_ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public EPlusAssignExpContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EMulDivContext extends ExpContext {
		public ExpContext left;
		public ExpContext right;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode MULTIPLY() { return getToken(GrammarParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(GrammarParser.DIVIDE, 0); }
		public EMulDivContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EDivAssignExpContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode DIV_ASSIGN() { return getToken(GrammarParser.DIV_ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public EDivAssignExpContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DynamicArrayNoInferrenceContext extends ExpContext {
		public List<TerminalNode> DYNARR_START() { return getTokens(GrammarParser.DYNARR_START); }
		public TerminalNode DYNARR_START(int i) {
			return getToken(GrammarParser.DYNARR_START, i);
		}
		public TerminalNode TYPE() { return getToken(GrammarParser.TYPE, 0); }
		public List<TerminalNode> DYNARR_END() { return getTokens(GrammarParser.DYNARR_END); }
		public TerminalNode DYNARR_END(int i) {
			return getToken(GrammarParser.DYNARR_END, i);
		}
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GrammarParser.ASSIGN, 0); }
		public ExpSeparatorContext expSeparator() {
			return getRuleContext(ExpSeparatorContext.class,0);
		}
		public DynamicArrayNoInferrenceContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EFunctionCallContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public ExpSeparatorContext expSeparator() {
			return getRuleContext(ExpSeparatorContext.class,0);
		}
		public EFunctionCallContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EAssignmentExpContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GrammarParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public EAssignmentExpContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EOrContext extends ExpContext {
		public ExpContext left;
		public ExpContext right;
		public TerminalNode OR() { return getToken(GrammarParser.OR, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public EOrContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EIntContext extends ExpContext {
		public TerminalNode INT() { return getToken(GrammarParser.INT, 0); }
		public EIntContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EParensExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public EParensExpContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EIdExpContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public EIdExpContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EAddSubContext extends ExpContext {
		public ExpContext left;
		public ExpContext right;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(GrammarParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(GrammarParser.MINUS, 0); }
		public EAddSubContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EMinusAssignExpContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode MINUS_ASSIGN() { return getToken(GrammarParser.MINUS_ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public EMinusAssignExpContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DynamicArrayInferenceContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GrammarParser.ASSIGN, 0); }
		public TerminalNode DYNARR_START() { return getToken(GrammarParser.DYNARR_START, 0); }
		public ExpSeparatorContext expSeparator() {
			return getRuleContext(ExpSeparatorContext.class,0);
		}
		public TerminalNode DYNARR_END() { return getToken(GrammarParser.DYNARR_END, 0); }
		public DynamicArrayInferenceContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EAndContext extends ExpContext {
		public ExpContext left;
		public ExpContext right;
		public TerminalNode AND() { return getToken(GrammarParser.AND, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public EAndContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EIncContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode INC() { return getToken(GrammarParser.INC, 0); }
		public EIncContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EPowerContext extends ExpContext {
		public ExpContext left;
		public ExpContext right;
		public TerminalNode POWER() { return getToken(GrammarParser.POWER, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public EPowerContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EDoubleContext extends ExpContext {
		public TerminalNode DOUBLE() { return getToken(GrammarParser.DOUBLE, 0); }
		public EDoubleContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EDecContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode DEC() { return getToken(GrammarParser.DEC, 0); }
		public EDecContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EStringContext extends ExpContext {
		public TerminalNode STRING() { return getToken(GrammarParser.STRING, 0); }
		public EStringContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EArrayIndexingContext extends ExpContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode DYNARR_START() { return getToken(GrammarParser.DYNARR_START, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode DYNARR_END() { return getToken(GrammarParser.DYNARR_END, 0); }
		public EArrayIndexingContext(ExpContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ERelationalContext extends ExpContext {
		public ExpContext left;
		public ExpContext right;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode GE() { return getToken(GrammarParser.GE, 0); }
		public TerminalNode LE() { return getToken(GrammarParser.LE, 0); }
		public TerminalNode GT() { return getToken(GrammarParser.GT, 0); }
		public TerminalNode LT() { return getToken(GrammarParser.LT, 0); }
		public ERelationalContext(ExpContext ctx) { copyFrom(ctx); }
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				_localctx = new EParensExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(126);
				match(T__1);
				setState(127);
				exp(0);
				setState(128);
				match(T__2);
				}
				break;
			case 2:
				{
				_localctx = new EIntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(130);
				match(INT);
				}
				break;
			case 3:
				{
				_localctx = new EDoubleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(131);
				match(DOUBLE);
				}
				break;
			case 4:
				{
				_localctx = new EStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(132);
				match(STRING);
				}
				break;
			case 5:
				{
				_localctx = new EIdExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(133);
				match(ID);
				}
				break;
			case 6:
				{
				_localctx = new EAssignmentExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(134);
				match(ID);
				setState(135);
				match(ASSIGN);
				setState(136);
				exp(10);
				}
				break;
			case 7:
				{
				_localctx = new EPlusAssignExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(137);
				match(ID);
				setState(138);
				match(PLUS_ASSIGN);
				setState(139);
				exp(9);
				}
				break;
			case 8:
				{
				_localctx = new EMinusAssignExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(140);
				match(ID);
				setState(141);
				match(MINUS_ASSIGN);
				setState(142);
				exp(8);
				}
				break;
			case 9:
				{
				_localctx = new EDivAssignExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(143);
				match(ID);
				setState(144);
				match(DIV_ASSIGN);
				setState(145);
				exp(7);
				}
				break;
			case 10:
				{
				_localctx = new DynamicArrayNoInferrenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(146);
				match(DYNARR_START);
				setState(147);
				match(TYPE);
				setState(148);
				match(DYNARR_END);
				setState(149);
				match(ID);
				setState(150);
				match(ASSIGN);
				setState(151);
				match(DYNARR_START);
				setState(152);
				expSeparator();
				setState(153);
				match(DYNARR_END);
				}
				break;
			case 11:
				{
				_localctx = new DynamicArrayInferenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(155);
				match(ID);
				setState(156);
				match(ASSIGN);
				setState(157);
				match(DYNARR_START);
				setState(158);
				expSeparator();
				setState(159);
				match(DYNARR_END);
				}
				break;
			case 12:
				{
				_localctx = new EFunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(161);
				match(ID);
				setState(162);
				match(T__1);
				setState(163);
				expSeparator();
				setState(164);
				match(T__2);
				}
				break;
			case 13:
				{
				_localctx = new EArrayIndexingContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(166);
				match(ID);
				setState(167);
				match(DYNARR_START);
				setState(168);
				exp(0);
				setState(169);
				match(DYNARR_END);
				}
				break;
			case 14:
				{
				_localctx = new EIncContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(171);
				match(ID);
				setState(172);
				match(INC);
				}
				break;
			case 15:
				{
				_localctx = new EDecContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(173);
				match(ID);
				setState(174);
				match(DEC);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(197);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(195);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new EMulDivContext(new ExpContext(_parentctx, _parentState));
						((EMulDivContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(177);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(178);
						_la = _input.LA(1);
						if ( !(_la==MULTIPLY || _la==DIVIDE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(179);
						((EMulDivContext)_localctx).right = exp(21);
						}
						break;
					case 2:
						{
						_localctx = new EAddSubContext(new ExpContext(_parentctx, _parentState));
						((EAddSubContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(180);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(181);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(182);
						((EAddSubContext)_localctx).right = exp(20);
						}
						break;
					case 3:
						{
						_localctx = new EPowerContext(new ExpContext(_parentctx, _parentState));
						((EPowerContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(183);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(184);
						match(POWER);
						setState(185);
						((EPowerContext)_localctx).right = exp(19);
						}
						break;
					case 4:
						{
						_localctx = new ERelationalContext(new ExpContext(_parentctx, _parentState));
						((ERelationalContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(186);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(187);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 62914560L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(188);
						((ERelationalContext)_localctx).right = exp(18);
						}
						break;
					case 5:
						{
						_localctx = new EAndContext(new ExpContext(_parentctx, _parentState));
						((EAndContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(189);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(190);
						match(AND);
						setState(191);
						((EAndContext)_localctx).right = exp(17);
						}
						break;
					case 6:
						{
						_localctx = new EOrContext(new ExpContext(_parentctx, _parentState));
						((EOrContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(192);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(193);
						match(OR);
						setState(194);
						((EOrContext)_localctx).right = exp(16);
						}
						break;
					}
					} 
				}
				setState(199);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
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
	}

	public final ExpSeparatorContext expSeparator() throws RecognitionException {
		ExpSeparatorContext _localctx = new ExpSeparatorContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expSeparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 139586465796L) != 0)) {
				{
				setState(200);
				exp(0);
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(201);
					match(T__10);
					setState(202);
					exp(0);
					}
					}
					setState(207);
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
	}

	public final ParamSeparatorContext paramSeparator() throws RecognitionException {
		ParamSeparatorContext _localctx = new ParamSeparatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_paramSeparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(210);
				param();
				setState(215);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(211);
					match(T__10);
					setState(212);
					param();
					}
					}
					setState(217);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 20);
		case 1:
			return precpred(_ctx, 19);
		case 2:
			return precpred(_ctx, 18);
		case 3:
			return precpred(_ctx, 17);
		case 4:
			return precpred(_ctx, 16);
		case 5:
			return precpred(_ctx, 15);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001-\u00dd\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001#\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u00024\b\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004R\b\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005W\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006`\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0005\u0007d\b\u0007\n\u0007\f\u0007g\t\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003"+
		"\bp\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b|\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\t\u00b0\b\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t\u00c4\b\t\n\t\f\t\u00c7\t\t\u0001\n\u0001\n"+
		"\u0001\n\u0005\n\u00cc\b\n\n\n\f\n\u00cf\t\n\u0003\n\u00d1\b\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0005\u000b\u00d6\b\u000b\n\u000b\f\u000b\u00d9"+
		"\t\u000b\u0003\u000b\u00db\b\u000b\u0001\u000b\u0000\u0001\u0012\f\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0003\u0001"+
		"\u0000\u0012\u0013\u0001\u0000\u0010\u0011\u0001\u0000\u0016\u0019\u00f8"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0002\"\u0001\u0000\u0000\u0000\u0004"+
		"3\u0001\u0000\u0000\u0000\u00065\u0001\u0000\u0000\u0000\bQ\u0001\u0000"+
		"\u0000\u0000\nV\u0001\u0000\u0000\u0000\f_\u0001\u0000\u0000\u0000\u000e"+
		"a\u0001\u0000\u0000\u0000\u0010{\u0001\u0000\u0000\u0000\u0012\u00af\u0001"+
		"\u0000\u0000\u0000\u0014\u00d0\u0001\u0000\u0000\u0000\u0016\u00da\u0001"+
		"\u0000\u0000\u0000\u0018\u001a\u0003\u0002\u0001\u0000\u0019\u0018\u0001"+
		"\u0000\u0000\u0000\u001a\u001d\u0001\u0000\u0000\u0000\u001b\u0019\u0001"+
		"\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000\u0000\u001c\u001e\u0001"+
		"\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001e\u001f\u0005"+
		"\u0000\u0000\u0001\u001f\u0001\u0001\u0000\u0000\u0000 #\u0003\b\u0004"+
		"\u0000!#\u0003\u0004\u0002\u0000\" \u0001\u0000\u0000\u0000\"!\u0001\u0000"+
		"\u0000\u0000#\u0003\u0001\u0000\u0000\u0000$%\u0005(\u0000\u0000%&\u0005"+
		"\u0001\u0000\u0000&\'\u0005\u001f\u0000\u0000\'(\u0005\u0002\u0000\u0000"+
		"()\u0003\u0016\u000b\u0000)*\u0005\u0003\u0000\u0000*+\u0003\u000e\u0007"+
		"\u0000+4\u0001\u0000\u0000\u0000,-\u0005\u0001\u0000\u0000-.\u0005\u001f"+
		"\u0000\u0000./\u0005\u0002\u0000\u0000/0\u0003\u0014\n\u000001\u0005\u0003"+
		"\u0000\u000012\u0003\u000e\u0007\u000024\u0001\u0000\u0000\u00003$\u0001"+
		"\u0000\u0000\u00003,\u0001\u0000\u0000\u00004\u0005\u0001\u0000\u0000"+
		"\u000056\u0005(\u0000\u000067\u0005\u001f\u0000\u00007\u0007\u0001\u0000"+
		"\u0000\u000089\u0003\u0012\t\u00009:\u0005\'\u0000\u0000:R\u0001\u0000"+
		"\u0000\u0000;<\u0005\u0004\u0000\u0000<R\u0003\u0012\t\u0000=>\u0005\u0005"+
		"\u0000\u0000>?\u0003\u0012\t\u0000?@\u0005\u0006\u0000\u0000@A\u0003\b"+
		"\u0004\u0000AB\u0005\u0007\u0000\u0000BR\u0001\u0000\u0000\u0000CD\u0005"+
		"\b\u0000\u0000DE\u0003\u0012\t\u0000EF\u0005\u0006\u0000\u0000FG\u0003"+
		"\b\u0004\u0000GH\u0005\u0007\u0000\u0000HR\u0001\u0000\u0000\u0000IR\u0003"+
		"\u0010\b\u0000JR\u0003\u000e\u0007\u0000KL\u0003\n\u0005\u0000LM\u0005"+
		"\'\u0000\u0000MR\u0001\u0000\u0000\u0000NO\u0003\f\u0006\u0000OP\u0005"+
		"\'\u0000\u0000PR\u0001\u0000\u0000\u0000Q8\u0001\u0000\u0000\u0000Q;\u0001"+
		"\u0000\u0000\u0000Q=\u0001\u0000\u0000\u0000QC\u0001\u0000\u0000\u0000"+
		"QI\u0001\u0000\u0000\u0000QJ\u0001\u0000\u0000\u0000QK\u0001\u0000\u0000"+
		"\u0000QN\u0001\u0000\u0000\u0000R\t\u0001\u0000\u0000\u0000ST\u0005(\u0000"+
		"\u0000TW\u0005\u001f\u0000\u0000UW\u0005\u001f\u0000\u0000VS\u0001\u0000"+
		"\u0000\u0000VU\u0001\u0000\u0000\u0000W\u000b\u0001\u0000\u0000\u0000"+
		"XY\u0005(\u0000\u0000YZ\u0005\u001f\u0000\u0000Z[\u0005 \u0000\u0000["+
		"`\u0003\u0012\t\u0000\\]\u0005\u001f\u0000\u0000]^\u0005 \u0000\u0000"+
		"^`\u0003\u0012\t\u0000_X\u0001\u0000\u0000\u0000_\\\u0001\u0000\u0000"+
		"\u0000`\r\u0001\u0000\u0000\u0000ae\u0005\u0006\u0000\u0000bd\u0003\b"+
		"\u0004\u0000cb\u0001\u0000\u0000\u0000dg\u0001\u0000\u0000\u0000ec\u0001"+
		"\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fh\u0001\u0000\u0000\u0000"+
		"ge\u0001\u0000\u0000\u0000hi\u0005\u0007\u0000\u0000i\u000f\u0001\u0000"+
		"\u0000\u0000jk\u0005\t\u0000\u0000kl\u0003\u0012\t\u0000lo\u0003\u000e"+
		"\u0007\u0000mn\u0005\n\u0000\u0000np\u0003\u0010\b\u0000om\u0001\u0000"+
		"\u0000\u0000op\u0001\u0000\u0000\u0000p|\u0001\u0000\u0000\u0000qr\u0005"+
		"\t\u0000\u0000rs\u0003\u0012\t\u0000st\u0003\u000e\u0007\u0000tu\u0005"+
		"\n\u0000\u0000uv\u0003\u000e\u0007\u0000v|\u0001\u0000\u0000\u0000wx\u0005"+
		"\t\u0000\u0000xy\u0003\u0012\t\u0000yz\u0003\u000e\u0007\u0000z|\u0001"+
		"\u0000\u0000\u0000{j\u0001\u0000\u0000\u0000{q\u0001\u0000\u0000\u0000"+
		"{w\u0001\u0000\u0000\u0000|\u0011\u0001\u0000\u0000\u0000}~\u0006\t\uffff"+
		"\uffff\u0000~\u007f\u0005\u0002\u0000\u0000\u007f\u0080\u0003\u0012\t"+
		"\u0000\u0080\u0081\u0005\u0003\u0000\u0000\u0081\u00b0\u0001\u0000\u0000"+
		"\u0000\u0082\u00b0\u0005\u000e\u0000\u0000\u0083\u00b0\u0005\r\u0000\u0000"+
		"\u0084\u00b0\u0005\f\u0000\u0000\u0085\u00b0\u0005\u001f\u0000\u0000\u0086"+
		"\u0087\u0005\u001f\u0000\u0000\u0087\u0088\u0005 \u0000\u0000\u0088\u00b0"+
		"\u0003\u0012\t\n\u0089\u008a\u0005\u001f\u0000\u0000\u008a\u008b\u0005"+
		"!\u0000\u0000\u008b\u00b0\u0003\u0012\t\t\u008c\u008d\u0005\u001f\u0000"+
		"\u0000\u008d\u008e\u0005\"\u0000\u0000\u008e\u00b0\u0003\u0012\t\b\u008f"+
		"\u0090\u0005\u001f\u0000\u0000\u0090\u0091\u0005$\u0000\u0000\u0091\u00b0"+
		"\u0003\u0012\t\u0007\u0092\u0093\u0005%\u0000\u0000\u0093\u0094\u0005"+
		"(\u0000\u0000\u0094\u0095\u0005&\u0000\u0000\u0095\u0096\u0005\u001f\u0000"+
		"\u0000\u0096\u0097\u0005 \u0000\u0000\u0097\u0098\u0005%\u0000\u0000\u0098"+
		"\u0099\u0003\u0014\n\u0000\u0099\u009a\u0005&\u0000\u0000\u009a\u00b0"+
		"\u0001\u0000\u0000\u0000\u009b\u009c\u0005\u001f\u0000\u0000\u009c\u009d"+
		"\u0005 \u0000\u0000\u009d\u009e\u0005%\u0000\u0000\u009e\u009f\u0003\u0014"+
		"\n\u0000\u009f\u00a0\u0005&\u0000\u0000\u00a0\u00b0\u0001\u0000\u0000"+
		"\u0000\u00a1\u00a2\u0005\u001f\u0000\u0000\u00a2\u00a3\u0005\u0002\u0000"+
		"\u0000\u00a3\u00a4\u0003\u0014\n\u0000\u00a4\u00a5\u0005\u0003\u0000\u0000"+
		"\u00a5\u00b0\u0001\u0000\u0000\u0000\u00a6\u00a7\u0005\u001f\u0000\u0000"+
		"\u00a7\u00a8\u0005%\u0000\u0000\u00a8\u00a9\u0003\u0012\t\u0000\u00a9"+
		"\u00aa\u0005&\u0000\u0000\u00aa\u00b0\u0001\u0000\u0000\u0000\u00ab\u00ac"+
		"\u0005\u001f\u0000\u0000\u00ac\u00b0\u0005)\u0000\u0000\u00ad\u00ae\u0005"+
		"\u001f\u0000\u0000\u00ae\u00b0\u0005*\u0000\u0000\u00af}\u0001\u0000\u0000"+
		"\u0000\u00af\u0082\u0001\u0000\u0000\u0000\u00af\u0083\u0001\u0000\u0000"+
		"\u0000\u00af\u0084\u0001\u0000\u0000\u0000\u00af\u0085\u0001\u0000\u0000"+
		"\u0000\u00af\u0086\u0001\u0000\u0000\u0000\u00af\u0089\u0001\u0000\u0000"+
		"\u0000\u00af\u008c\u0001\u0000\u0000\u0000\u00af\u008f\u0001\u0000\u0000"+
		"\u0000\u00af\u0092\u0001\u0000\u0000\u0000\u00af\u009b\u0001\u0000\u0000"+
		"\u0000\u00af\u00a1\u0001\u0000\u0000\u0000\u00af\u00a6\u0001\u0000\u0000"+
		"\u0000\u00af\u00ab\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000"+
		"\u0000\u00b0\u00c5\u0001\u0000\u0000\u0000\u00b1\u00b2\n\u0014\u0000\u0000"+
		"\u00b2\u00b3\u0007\u0000\u0000\u0000\u00b3\u00c4\u0003\u0012\t\u0015\u00b4"+
		"\u00b5\n\u0013\u0000\u0000\u00b5\u00b6\u0007\u0001\u0000\u0000\u00b6\u00c4"+
		"\u0003\u0012\t\u0014\u00b7\u00b8\n\u0012\u0000\u0000\u00b8\u00b9\u0005"+
		"\u0015\u0000\u0000\u00b9\u00c4\u0003\u0012\t\u0013\u00ba\u00bb\n\u0011"+
		"\u0000\u0000\u00bb\u00bc\u0007\u0002\u0000\u0000\u00bc\u00c4\u0003\u0012"+
		"\t\u0012\u00bd\u00be\n\u0010\u0000\u0000\u00be\u00bf\u0005\u001c\u0000"+
		"\u0000\u00bf\u00c4\u0003\u0012\t\u0011\u00c0\u00c1\n\u000f\u0000\u0000"+
		"\u00c1\u00c2\u0005\u001d\u0000\u0000\u00c2\u00c4\u0003\u0012\t\u0010\u00c3"+
		"\u00b1\u0001\u0000\u0000\u0000\u00c3\u00b4\u0001\u0000\u0000\u0000\u00c3"+
		"\u00b7\u0001\u0000\u0000\u0000\u00c3\u00ba\u0001\u0000\u0000\u0000\u00c3"+
		"\u00bd\u0001\u0000\u0000\u0000\u00c3\u00c0\u0001\u0000\u0000\u0000\u00c4"+
		"\u00c7\u0001\u0000\u0000\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5"+
		"\u00c6\u0001\u0000\u0000\u0000\u00c6\u0013\u0001\u0000\u0000\u0000\u00c7"+
		"\u00c5\u0001\u0000\u0000\u0000\u00c8\u00cd\u0003\u0012\t\u0000\u00c9\u00ca"+
		"\u0005\u000b\u0000\u0000\u00ca\u00cc\u0003\u0012\t\u0000\u00cb\u00c9\u0001"+
		"\u0000\u0000\u0000\u00cc\u00cf\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001"+
		"\u0000\u0000\u0000\u00cd\u00ce\u0001\u0000\u0000\u0000\u00ce\u00d1\u0001"+
		"\u0000\u0000\u0000\u00cf\u00cd\u0001\u0000\u0000\u0000\u00d0\u00c8\u0001"+
		"\u0000\u0000\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1\u0015\u0001"+
		"\u0000\u0000\u0000\u00d2\u00d7\u0003\u0006\u0003\u0000\u00d3\u00d4\u0005"+
		"\u000b\u0000\u0000\u00d4\u00d6\u0003\u0006\u0003\u0000\u00d5\u00d3\u0001"+
		"\u0000\u0000\u0000\u00d6\u00d9\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001"+
		"\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000\u0000\u00d8\u00db\u0001"+
		"\u0000\u0000\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00da\u00d2\u0001"+
		"\u0000\u0000\u0000\u00da\u00db\u0001\u0000\u0000\u0000\u00db\u0017\u0001"+
		"\u0000\u0000\u0000\u0010\u001b\"3QV_eo{\u00af\u00c3\u00c5\u00cd\u00d0"+
		"\u00d7\u00da";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}