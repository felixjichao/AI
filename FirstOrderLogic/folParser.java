// Generated from C:/My program/eclipse/workspace/FirstOrderLogic/src\fol.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class folParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, LPAREN=2, RPAREN=3, AND=4, OR=5, NOT=6, IMPLY=7, VARIABLE=8, PREDICATE=9, 
		WS=10;
	public static final int
		RULE_formula = 0;
	public static final String[] ruleNames = {
		"formula"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'('", "')'", "'&'", "'|'", "'~'", "'=>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "LPAREN", "RPAREN", "AND", "OR", "NOT", "IMPLY", "VARIABLE", 
		"PREDICATE", "WS"
	};
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
	public String getGrammarFileName() { return "fol.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public folParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FormulaContext extends ParserRuleContext {
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
	 
		public FormulaContext() { }
		public void copyFrom(FormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NegationContext extends FormulaContext {
		public TerminalNode NOT() { return getToken(folParser.NOT, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public NegationContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterNegation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitNegation(this);
		}
	}
	public static class ConjunctionContext extends FormulaContext {
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode AND() { return getToken(folParser.AND, 0); }
		public ConjunctionContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitConjunction(this);
		}
	}
	public static class DisjunctionContext extends FormulaContext {
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode OR() { return getToken(folParser.OR, 0); }
		public DisjunctionContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterDisjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitDisjunction(this);
		}
	}
	public static class ImplicationContext extends FormulaContext {
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode IMPLY() { return getToken(folParser.IMPLY, 0); }
		public ImplicationContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterImplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitImplication(this);
		}
	}
	public static class AtomContext extends FormulaContext {
		public List<TerminalNode> PREDICATE() { return getTokens(folParser.PREDICATE); }
		public TerminalNode PREDICATE(int i) {
			return getToken(folParser.PREDICATE, i);
		}
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public List<TerminalNode> VARIABLE() { return getTokens(folParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(folParser.VARIABLE, i);
		}
		public TerminalNode NOT() { return getToken(folParser.NOT, 0); }
		public AtomContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitAtom(this);
		}
	}
	public static class ParenthesisContext extends FormulaContext {
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public ParenthesisContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterParenthesis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitParenthesis(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		return formula(0);
	}

	private FormulaContext formula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FormulaContext _localctx = new FormulaContext(_ctx, _parentState);
		FormulaContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_formula, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				_localctx = new AtomContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(4);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(3);
					match(NOT);
					}
				}

				setState(6);
				match(PREDICATE);
				setState(7);
				match(LPAREN);
				setState(8);
				_la = _input.LA(1);
				if ( !(_la==VARIABLE || _la==PREDICATE) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(13);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(9);
					match(T__0);
					setState(10);
					_la = _input.LA(1);
					if ( !(_la==VARIABLE || _la==PREDICATE) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
					}
					setState(15);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(16);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new ParenthesisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(17);
				match(LPAREN);
				setState(18);
				formula(0);
				setState(19);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new NegationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(21);
				match(NOT);
				setState(22);
				formula(4);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(36);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(34);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new ConjunctionContext(new FormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(25);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(26);
						match(AND);
						setState(27);
						formula(4);
						}
						break;
					case 2:
						{
						_localctx = new DisjunctionContext(new FormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(28);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(29);
						match(OR);
						setState(30);
						formula(3);
						}
						break;
					case 3:
						{
						_localctx = new ImplicationContext(new FormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(31);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(32);
						match(IMPLY);
						setState(33);
						formula(2);
						}
						break;
					}
					} 
				}
				setState(38);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return formula_sempred((FormulaContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean formula_sempred(FormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\f*\4\2\t\2\3\2\3"+
		"\2\5\2\7\n\2\3\2\3\2\3\2\3\2\3\2\7\2\16\n\2\f\2\16\2\21\13\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\5\2\32\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2"+
		"%\n\2\f\2\16\2(\13\2\3\2\2\3\2\3\2\2\3\3\2\n\13/\2\31\3\2\2\2\4\6\b\2"+
		"\1\2\5\7\7\b\2\2\6\5\3\2\2\2\6\7\3\2\2\2\7\b\3\2\2\2\b\t\7\13\2\2\t\n"+
		"\7\4\2\2\n\17\t\2\2\2\13\f\7\3\2\2\f\16\t\2\2\2\r\13\3\2\2\2\16\21\3\2"+
		"\2\2\17\r\3\2\2\2\17\20\3\2\2\2\20\22\3\2\2\2\21\17\3\2\2\2\22\32\7\5"+
		"\2\2\23\24\7\4\2\2\24\25\5\2\2\2\25\26\7\5\2\2\26\32\3\2\2\2\27\30\7\b"+
		"\2\2\30\32\5\2\2\6\31\4\3\2\2\2\31\23\3\2\2\2\31\27\3\2\2\2\32&\3\2\2"+
		"\2\33\34\f\5\2\2\34\35\7\6\2\2\35%\5\2\2\6\36\37\f\4\2\2\37 \7\7\2\2 "+
		"%\5\2\2\5!\"\f\3\2\2\"#\7\t\2\2#%\5\2\2\4$\33\3\2\2\2$\36\3\2\2\2$!\3"+
		"\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'\3\3\2\2\2(&\3\2\2\2\7\6\17\31"+
		"$&";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}