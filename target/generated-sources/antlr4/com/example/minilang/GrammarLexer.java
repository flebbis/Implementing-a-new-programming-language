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
		MULT_ASSIGN=26, DIV_ASSIGN=27;
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
			"DIV_ASSIGN"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", null, null, null, null, "'+'", "'-'", "'*'", "'/'", 
			null, "'**'", "'>'", "'<'", "'<='", "'>='", "'=='", "'!='", "'and'", 
			"'or'", "'not'", null, "'='", "'+='", "'-='", "'*='", "'/='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "STRING", "DOUBLE", "INT", "BOOL", "PLUS", "MINUS", 
			"MULTIPLY", "DIVIDE", "WHITESPACE", "POWER", "GT", "LT", "LE", "GE", 
			"EQ", "NE", "AND", "OR", "NOT", "ID", "ASSIGN", "PLUS_ASSIGN", "MINUS_ASSIGN", 
			"MULT_ASSIGN", "DIV_ASSIGN"
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
		"\u0004\u0000\u001b\u00b1\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0005\u0002@\b\u0002\n\u0002\f\u0002C\t\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"J\b\u0002\n\u0002\f\u0002M\t\u0002\u0001\u0002\u0003\u0002P\b\u0002\u0001"+
		"\u0003\u0004\u0003S\b\u0003\u000b\u0003\f\u0003T\u0001\u0003\u0003\u0003"+
		"X\b\u0003\u0001\u0003\u0005\u0003[\b\u0003\n\u0003\f\u0003^\t\u0003\u0001"+
		"\u0004\u0004\u0004a\b\u0004\u000b\u0004\f\u0004b\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005n\b\u0005\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0004\ny\b\n\u000b"+
		"\n\f\nz\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0005\u0015\u009f\b\u0015\n\u0015\f\u0015"+
		"\u00a2\t\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0000\u0000\u001b\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010"+
		"!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a"+
		"5\u001b\u0001\u0000\u0006\u0002\u0000\"\"\\\\\u0002\u0000\'\'\\\\\u0001"+
		"\u000009\u0003\u0000\t\n\r\r  \u0003\u0000AZ__az\u0004\u000009AZ__az\u00bc"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/"+
		"\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000"+
		"\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00017\u0001\u0000\u0000\u0000"+
		"\u00039\u0001\u0000\u0000\u0000\u0005O\u0001\u0000\u0000\u0000\u0007R"+
		"\u0001\u0000\u0000\u0000\t`\u0001\u0000\u0000\u0000\u000bm\u0001\u0000"+
		"\u0000\u0000\ro\u0001\u0000\u0000\u0000\u000fq\u0001\u0000\u0000\u0000"+
		"\u0011s\u0001\u0000\u0000\u0000\u0013u\u0001\u0000\u0000\u0000\u0015x"+
		"\u0001\u0000\u0000\u0000\u0017~\u0001\u0000\u0000\u0000\u0019\u0081\u0001"+
		"\u0000\u0000\u0000\u001b\u0083\u0001\u0000\u0000\u0000\u001d\u0085\u0001"+
		"\u0000\u0000\u0000\u001f\u0088\u0001\u0000\u0000\u0000!\u008b\u0001\u0000"+
		"\u0000\u0000#\u008e\u0001\u0000\u0000\u0000%\u0091\u0001\u0000\u0000\u0000"+
		"\'\u0095\u0001\u0000\u0000\u0000)\u0098\u0001\u0000\u0000\u0000+\u009c"+
		"\u0001\u0000\u0000\u0000-\u00a3\u0001\u0000\u0000\u0000/\u00a5\u0001\u0000"+
		"\u0000\u00001\u00a8\u0001\u0000\u0000\u00003\u00ab\u0001\u0000\u0000\u0000"+
		"5\u00ae\u0001\u0000\u0000\u000078\u0005(\u0000\u00008\u0002\u0001\u0000"+
		"\u0000\u00009:\u0005)\u0000\u0000:\u0004\u0001\u0000\u0000\u0000;A\u0005"+
		"\"\u0000\u0000<=\u0005\\\u0000\u0000=@\t\u0000\u0000\u0000>@\b\u0000\u0000"+
		"\u0000?<\u0001\u0000\u0000\u0000?>\u0001\u0000\u0000\u0000@C\u0001\u0000"+
		"\u0000\u0000A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000BD\u0001"+
		"\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000DP\u0005\"\u0000\u0000EK\u0005"+
		"\'\u0000\u0000FG\u0005\\\u0000\u0000GJ\t\u0000\u0000\u0000HJ\b\u0001\u0000"+
		"\u0000IF\u0001\u0000\u0000\u0000IH\u0001\u0000\u0000\u0000JM\u0001\u0000"+
		"\u0000\u0000KI\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LN\u0001"+
		"\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000NP\u0005\'\u0000\u0000O;\u0001"+
		"\u0000\u0000\u0000OE\u0001\u0000\u0000\u0000P\u0006\u0001\u0000\u0000"+
		"\u0000QS\u0007\u0002\u0000\u0000RQ\u0001\u0000\u0000\u0000ST\u0001\u0000"+
		"\u0000\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UW\u0001"+
		"\u0000\u0000\u0000VX\u0005.\u0000\u0000WV\u0001\u0000\u0000\u0000WX\u0001"+
		"\u0000\u0000\u0000X\\\u0001\u0000\u0000\u0000Y[\u0007\u0002\u0000\u0000"+
		"ZY\u0001\u0000\u0000\u0000[^\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000"+
		"\u0000\\]\u0001\u0000\u0000\u0000]\b\u0001\u0000\u0000\u0000^\\\u0001"+
		"\u0000\u0000\u0000_a\u0007\u0002\u0000\u0000`_\u0001\u0000\u0000\u0000"+
		"ab\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000"+
		"\u0000c\n\u0001\u0000\u0000\u0000de\u0005t\u0000\u0000ef\u0005r\u0000"+
		"\u0000fg\u0005u\u0000\u0000gn\u0005e\u0000\u0000hi\u0005f\u0000\u0000"+
		"ij\u0005a\u0000\u0000jk\u0005l\u0000\u0000kl\u0005s\u0000\u0000ln\u0005"+
		"e\u0000\u0000md\u0001\u0000\u0000\u0000mh\u0001\u0000\u0000\u0000n\f\u0001"+
		"\u0000\u0000\u0000op\u0005+\u0000\u0000p\u000e\u0001\u0000\u0000\u0000"+
		"qr\u0005-\u0000\u0000r\u0010\u0001\u0000\u0000\u0000st\u0005*\u0000\u0000"+
		"t\u0012\u0001\u0000\u0000\u0000uv\u0005/\u0000\u0000v\u0014\u0001\u0000"+
		"\u0000\u0000wy\u0007\u0003\u0000\u0000xw\u0001\u0000\u0000\u0000yz\u0001"+
		"\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000"+
		"{|\u0001\u0000\u0000\u0000|}\u0006\n\u0000\u0000}\u0016\u0001\u0000\u0000"+
		"\u0000~\u007f\u0005*\u0000\u0000\u007f\u0080\u0005*\u0000\u0000\u0080"+
		"\u0018\u0001\u0000\u0000\u0000\u0081\u0082\u0005>\u0000\u0000\u0082\u001a"+
		"\u0001\u0000\u0000\u0000\u0083\u0084\u0005<\u0000\u0000\u0084\u001c\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\u0005<\u0000\u0000\u0086\u0087\u0005=\u0000"+
		"\u0000\u0087\u001e\u0001\u0000\u0000\u0000\u0088\u0089\u0005>\u0000\u0000"+
		"\u0089\u008a\u0005=\u0000\u0000\u008a \u0001\u0000\u0000\u0000\u008b\u008c"+
		"\u0005=\u0000\u0000\u008c\u008d\u0005=\u0000\u0000\u008d\"\u0001\u0000"+
		"\u0000\u0000\u008e\u008f\u0005!\u0000\u0000\u008f\u0090\u0005=\u0000\u0000"+
		"\u0090$\u0001\u0000\u0000\u0000\u0091\u0092\u0005a\u0000\u0000\u0092\u0093"+
		"\u0005n\u0000\u0000\u0093\u0094\u0005d\u0000\u0000\u0094&\u0001\u0000"+
		"\u0000\u0000\u0095\u0096\u0005o\u0000\u0000\u0096\u0097\u0005r\u0000\u0000"+
		"\u0097(\u0001\u0000\u0000\u0000\u0098\u0099\u0005n\u0000\u0000\u0099\u009a"+
		"\u0005o\u0000\u0000\u009a\u009b\u0005t\u0000\u0000\u009b*\u0001\u0000"+
		"\u0000\u0000\u009c\u00a0\u0007\u0004\u0000\u0000\u009d\u009f\u0007\u0005"+
		"\u0000\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009f\u00a2\u0001\u0000"+
		"\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a1,\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000"+
		"\u0000\u00a3\u00a4\u0005=\u0000\u0000\u00a4.\u0001\u0000\u0000\u0000\u00a5"+
		"\u00a6\u0005+\u0000\u0000\u00a6\u00a7\u0005=\u0000\u0000\u00a70\u0001"+
		"\u0000\u0000\u0000\u00a8\u00a9\u0005-\u0000\u0000\u00a9\u00aa\u0005=\u0000"+
		"\u0000\u00aa2\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005*\u0000\u0000\u00ac"+
		"\u00ad\u0005=\u0000\u0000\u00ad4\u0001\u0000\u0000\u0000\u00ae\u00af\u0005"+
		"/\u0000\u0000\u00af\u00b0\u0005=\u0000\u0000\u00b06\u0001\u0000\u0000"+
		"\u0000\r\u0000?AIKOTW\\bmz\u00a0\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}