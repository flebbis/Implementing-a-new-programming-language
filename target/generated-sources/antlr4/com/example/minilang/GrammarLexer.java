// Generated from com/example/minilang/Grammar.g4 by ANTLR 4.13.1
package com.example.minilang;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, STRING=3, DOUBLE=4, INT=5, BOOL=6, PLUS=7, MINUS=8, MULTIPLY=9, 
		DIVIDE=10, WHITESPACE=11, POWER=12, GT=13, LT=14, LE=15, GE=16, EQ=17, 
		NE=18, AND=19, OR=20, NOT=21, ID=22, ASSIGN=23, PLUS_ASSIGN=24, MINUS_ASSIGN=25, 
		MULT_ASSIGN=26, DIV_ASSIGN=27, DYNARR_START=28, DYNARR_END=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "STRING", "DOUBLE", "INT", "BOOL", "PLUS", "MINUS", "MULTIPLY", 
			"DIVIDE", "WHITESPACE", "POWER", "GT", "LT", "LE", "GE", "EQ", "NE", 
			"AND", "OR", "NOT", "ID", "ASSIGN", "PLUS_ASSIGN", "MINUS_ASSIGN", "MULT_ASSIGN", 
			"DIV_ASSIGN", "DYNARR_START", "DYNARR_END"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", null, null, null, null, "'+'", "'-'", "'*'", "'/'", 
			null, "'**'", "'>'", "'<'", "'<='", "'>='", "'=='", "'!='", "'and'", 
			"'or'", "'not'", null, "'='", "'+='", "'-='", "'*='", "'/='", "'['", 
			"']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "STRING", "DOUBLE", "INT", "BOOL", "PLUS", "MINUS", 
			"MULTIPLY", "DIVIDE", "WHITESPACE", "POWER", "GT", "LT", "LE", "GE", 
			"EQ", "NE", "AND", "OR", "NOT", "ID", "ASSIGN", "PLUS_ASSIGN", "MINUS_ASSIGN", 
			"MULT_ASSIGN", "DIV_ASSIGN", "DYNARR_START", "DYNARR_END"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u001d\u00b9\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002D\b\u0002\n\u0002\f\u0002G\t\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002N\b\u0002\n\u0002\f\u0002"+
		"Q\t\u0002\u0001\u0002\u0003\u0002T\b\u0002\u0001\u0003\u0004\u0003W\b"+
		"\u0003\u000b\u0003\f\u0003X\u0001\u0003\u0003\u0003\\\b\u0003\u0001\u0003"+
		"\u0005\u0003_\b\u0003\n\u0003\f\u0003b\t\u0003\u0001\u0004\u0004\u0004"+
		"e\b\u0004\u000b\u0004\f\u0004f\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003"+
		"\u0005r\b\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\n\u0004\n}\b\n\u000b\n\f\n~\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015"+
		"\u0001\u0015\u0005\u0015\u00a3\b\u0015\n\u0015\f\u0015\u00a6\t\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0000"+
		"\u0000\u001d\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b"+
		"\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016"+
		"-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d\u0001\u0000\u0006\u0002"+
		"\u0000\"\"\\\\\u0002\u0000\'\'\\\\\u0001\u000009\u0003\u0000\t\n\r\r "+
		" \u0003\u0000AZ__az\u0004\u000009AZ__az\u00c4\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000"+
		"#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001"+
		"\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000"+
		"\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u0000"+
		"1\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001"+
		"\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000"+
		"\u0000\u0001;\u0001\u0000\u0000\u0000\u0003=\u0001\u0000\u0000\u0000\u0005"+
		"S\u0001\u0000\u0000\u0000\u0007V\u0001\u0000\u0000\u0000\td\u0001\u0000"+
		"\u0000\u0000\u000bq\u0001\u0000\u0000\u0000\rs\u0001\u0000\u0000\u0000"+
		"\u000fu\u0001\u0000\u0000\u0000\u0011w\u0001\u0000\u0000\u0000\u0013y"+
		"\u0001\u0000\u0000\u0000\u0015|\u0001\u0000\u0000\u0000\u0017\u0082\u0001"+
		"\u0000\u0000\u0000\u0019\u0085\u0001\u0000\u0000\u0000\u001b\u0087\u0001"+
		"\u0000\u0000\u0000\u001d\u0089\u0001\u0000\u0000\u0000\u001f\u008c\u0001"+
		"\u0000\u0000\u0000!\u008f\u0001\u0000\u0000\u0000#\u0092\u0001\u0000\u0000"+
		"\u0000%\u0095\u0001\u0000\u0000\u0000\'\u0099\u0001\u0000\u0000\u0000"+
		")\u009c\u0001\u0000\u0000\u0000+\u00a0\u0001\u0000\u0000\u0000-\u00a7"+
		"\u0001\u0000\u0000\u0000/\u00a9\u0001\u0000\u0000\u00001\u00ac\u0001\u0000"+
		"\u0000\u00003\u00af\u0001\u0000\u0000\u00005\u00b2\u0001\u0000\u0000\u0000"+
		"7\u00b5\u0001\u0000\u0000\u00009\u00b7\u0001\u0000\u0000\u0000;<\u0005"+
		"(\u0000\u0000<\u0002\u0001\u0000\u0000\u0000=>\u0005)\u0000\u0000>\u0004"+
		"\u0001\u0000\u0000\u0000?E\u0005\"\u0000\u0000@A\u0005\\\u0000\u0000A"+
		"D\t\u0000\u0000\u0000BD\b\u0000\u0000\u0000C@\u0001\u0000\u0000\u0000"+
		"CB\u0001\u0000\u0000\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000"+
		"\u0000EF\u0001\u0000\u0000\u0000FH\u0001\u0000\u0000\u0000GE\u0001\u0000"+
		"\u0000\u0000HT\u0005\"\u0000\u0000IO\u0005\'\u0000\u0000JK\u0005\\\u0000"+
		"\u0000KN\t\u0000\u0000\u0000LN\b\u0001\u0000\u0000MJ\u0001\u0000\u0000"+
		"\u0000ML\u0001\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000"+
		"\u0000\u0000OP\u0001\u0000\u0000\u0000PR\u0001\u0000\u0000\u0000QO\u0001"+
		"\u0000\u0000\u0000RT\u0005\'\u0000\u0000S?\u0001\u0000\u0000\u0000SI\u0001"+
		"\u0000\u0000\u0000T\u0006\u0001\u0000\u0000\u0000UW\u0007\u0002\u0000"+
		"\u0000VU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000\u0000XV\u0001\u0000"+
		"\u0000\u0000XY\u0001\u0000\u0000\u0000Y[\u0001\u0000\u0000\u0000Z\\\u0005"+
		".\u0000\u0000[Z\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\`"+
		"\u0001\u0000\u0000\u0000]_\u0007\u0002\u0000\u0000^]\u0001\u0000\u0000"+
		"\u0000_b\u0001\u0000\u0000\u0000`^\u0001\u0000\u0000\u0000`a\u0001\u0000"+
		"\u0000\u0000a\b\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000ce\u0007"+
		"\u0002\u0000\u0000dc\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000"+
		"fd\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000\u0000g\n\u0001\u0000\u0000"+
		"\u0000hi\u0005t\u0000\u0000ij\u0005r\u0000\u0000jk\u0005u\u0000\u0000"+
		"kr\u0005e\u0000\u0000lm\u0005f\u0000\u0000mn\u0005a\u0000\u0000no\u0005"+
		"l\u0000\u0000op\u0005s\u0000\u0000pr\u0005e\u0000\u0000qh\u0001\u0000"+
		"\u0000\u0000ql\u0001\u0000\u0000\u0000r\f\u0001\u0000\u0000\u0000st\u0005"+
		"+\u0000\u0000t\u000e\u0001\u0000\u0000\u0000uv\u0005-\u0000\u0000v\u0010"+
		"\u0001\u0000\u0000\u0000wx\u0005*\u0000\u0000x\u0012\u0001\u0000\u0000"+
		"\u0000yz\u0005/\u0000\u0000z\u0014\u0001\u0000\u0000\u0000{}\u0007\u0003"+
		"\u0000\u0000|{\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000~|\u0001"+
		"\u0000\u0000\u0000~\u007f\u0001\u0000\u0000\u0000\u007f\u0080\u0001\u0000"+
		"\u0000\u0000\u0080\u0081\u0006\n\u0000\u0000\u0081\u0016\u0001\u0000\u0000"+
		"\u0000\u0082\u0083\u0005*\u0000\u0000\u0083\u0084\u0005*\u0000\u0000\u0084"+
		"\u0018\u0001\u0000\u0000\u0000\u0085\u0086\u0005>\u0000\u0000\u0086\u001a"+
		"\u0001\u0000\u0000\u0000\u0087\u0088\u0005<\u0000\u0000\u0088\u001c\u0001"+
		"\u0000\u0000\u0000\u0089\u008a\u0005<\u0000\u0000\u008a\u008b\u0005=\u0000"+
		"\u0000\u008b\u001e\u0001\u0000\u0000\u0000\u008c\u008d\u0005>\u0000\u0000"+
		"\u008d\u008e\u0005=\u0000\u0000\u008e \u0001\u0000\u0000\u0000\u008f\u0090"+
		"\u0005=\u0000\u0000\u0090\u0091\u0005=\u0000\u0000\u0091\"\u0001\u0000"+
		"\u0000\u0000\u0092\u0093\u0005!\u0000\u0000\u0093\u0094\u0005=\u0000\u0000"+
		"\u0094$\u0001\u0000\u0000\u0000\u0095\u0096\u0005a\u0000\u0000\u0096\u0097"+
		"\u0005n\u0000\u0000\u0097\u0098\u0005d\u0000\u0000\u0098&\u0001\u0000"+
		"\u0000\u0000\u0099\u009a\u0005o\u0000\u0000\u009a\u009b\u0005r\u0000\u0000"+
		"\u009b(\u0001\u0000\u0000\u0000\u009c\u009d\u0005n\u0000\u0000\u009d\u009e"+
		"\u0005o\u0000\u0000\u009e\u009f\u0005t\u0000\u0000\u009f*\u0001\u0000"+
		"\u0000\u0000\u00a0\u00a4\u0007\u0004\u0000\u0000\u00a1\u00a3\u0007\u0005"+
		"\u0000\u0000\u00a2\u00a1\u0001\u0000\u0000\u0000\u00a3\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a5,\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000"+
		"\u0000\u00a7\u00a8\u0005=\u0000\u0000\u00a8.\u0001\u0000\u0000\u0000\u00a9"+
		"\u00aa\u0005+\u0000\u0000\u00aa\u00ab\u0005=\u0000\u0000\u00ab0\u0001"+
		"\u0000\u0000\u0000\u00ac\u00ad\u0005-\u0000\u0000\u00ad\u00ae\u0005=\u0000"+
		"\u0000\u00ae2\u0001\u0000\u0000\u0000\u00af\u00b0\u0005*\u0000\u0000\u00b0"+
		"\u00b1\u0005=\u0000\u0000\u00b14\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005"+
		"/\u0000\u0000\u00b3\u00b4\u0005=\u0000\u0000\u00b46\u0001\u0000\u0000"+
		"\u0000\u00b5\u00b6\u0005[\u0000\u0000\u00b68\u0001\u0000\u0000\u0000\u00b7"+
		"\u00b8\u0005]\u0000\u0000\u00b8:\u0001\u0000\u0000\u0000\r\u0000CEMOS"+
		"X[`fq~\u00a4\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}