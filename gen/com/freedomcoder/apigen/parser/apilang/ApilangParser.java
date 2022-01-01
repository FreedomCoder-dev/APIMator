// Generated from /Users/freedomcoder/Projects/APIMator/src/com/freedomcoder/apigen/parser/apilang/Apilang.g4 by ANTLR 4.9.2
package com.freedomcoder.apigen.parser.apilang;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ApilangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, ASSIGN=3, LT=4, GT=5, OPAR=6, CPAR=7, OBRACE=8, CBRACE=9, 
		LBRACK=10, RBRACK=11, SEMI=12, COMMA=13, DOT=14, IMPORT=15, API=16, GROUP=17, 
		METHOD=18, OBJECT=19, THROWS=20, ERROR=21, PUBLIC=22, PRIVATE=23, TRUE=24, 
		FALSE=25, NULL=26, ARRBRACK=27, ID=28, INT=29, FLOAT=30, DESCRIPTION=31, 
		STRING=32, COMMENT=33, LINE_COMMENT=34, SPACE=35, OTHER=36;
	public static final int
		RULE_parse = 0, RULE_importDeclaration = 1, RULE_typeDeclaration = 2, 
		RULE_typeModifier = 3, RULE_objectDeclaration = 4, RULE_apiDeclaration = 5, 
		RULE_apiTypeDeclaration = 6, RULE_apiGroupDeclaration = 7, RULE_apiMethodDeclaration = 8, 
		RULE_throwsDeclaration = 9, RULE_throwsDeclaratorList = 10, RULE_responseDeclaration = 11, 
		RULE_extFieldDeclaration = 12, RULE_inlineObjectDeclaration = 13, RULE_fieldDeclaration = 14, 
		RULE_inlineTypeName = 15, RULE_fieldModifier = 16, RULE_variableDeclaratorList = 17, 
		RULE_variableDeclarator = 18, RULE_annotation = 19, RULE_normalAnnotation = 20, 
		RULE_elementValuePairList = 21, RULE_elementValuePair = 22, RULE_elementValue = 23, 
		RULE_elementValueArrayInitializer = 24, RULE_elementValueList = 25, RULE_markerAnnotation = 26, 
		RULE_singleElementAnnotation = 27, RULE_atom = 28;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "importDeclaration", "typeDeclaration", "typeModifier", "objectDeclaration", 
			"apiDeclaration", "apiTypeDeclaration", "apiGroupDeclaration", "apiMethodDeclaration", 
			"throwsDeclaration", "throwsDeclaratorList", "responseDeclaration", "extFieldDeclaration", 
			"inlineObjectDeclaration", "fieldDeclaration", "inlineTypeName", "fieldModifier", 
			"variableDeclaratorList", "variableDeclarator", "annotation", "normalAnnotation", 
			"elementValuePairList", "elementValuePair", "elementValue", "elementValueArrayInitializer", 
			"elementValueList", "markerAnnotation", "singleElementAnnotation", "atom"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'protected'", "'@'", "'='", "'<'", "'>'", "'('", "')'", "'{'", 
			"'}'", "'['", "']'", "';'", "','", "'.'", "'import'", "'api'", "'group'", 
			"'method'", "'object'", "'throws'", "'error'", "'public'", "'private'", 
			"'true'", "'false'", "'null'", "'[]'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "ASSIGN", "LT", "GT", "OPAR", "CPAR", "OBRACE", "CBRACE", 
			"LBRACK", "RBRACK", "SEMI", "COMMA", "DOT", "IMPORT", "API", "GROUP", 
			"METHOD", "OBJECT", "THROWS", "ERROR", "PUBLIC", "PRIVATE", "TRUE", "FALSE", 
			"NULL", "ARRBRACK", "ID", "INT", "FLOAT", "DESCRIPTION", "STRING", "COMMENT", 
			"LINE_COMMENT", "SPACE", "OTHER"
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
	public String getGrammarFileName() { return "Apilang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ApilangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ParseContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ApilangParser.EOF, 0); }
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<TypeDeclarationContext> typeDeclaration() {
			return getRuleContexts(TypeDeclarationContext.class);
		}
		public TypeDeclarationContext typeDeclaration(int i) {
			return getRuleContext(TypeDeclarationContext.class,i);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitParse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(58);
				importDeclaration();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << API) | (1L << OBJECT) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(64);
				typeDeclaration();
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(70);
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

	public static class ImportDeclarationContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(ApilangParser.IMPORT, 0); }
		public TerminalNode STRING() { return getToken(ApilangParser.STRING, 0); }
		public TerminalNode SEMI() { return getToken(ApilangParser.SEMI, 0); }
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterImportDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitImportDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitImportDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(IMPORT);
			setState(73);
			match(STRING);
			setState(74);
			match(SEMI);
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

	public static class TypeDeclarationContext extends ParserRuleContext {
		public ApiDeclarationContext apiDeclaration() {
			return getRuleContext(ApiDeclarationContext.class,0);
		}
		public ObjectDeclarationContext objectDeclaration() {
			return getRuleContext(ObjectDeclarationContext.class,0);
		}
		public TypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterTypeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitTypeDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDeclarationContext typeDeclaration() throws RecognitionException {
		TypeDeclarationContext _localctx = new TypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typeDeclaration);
		try {
			setState(78);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				apiDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				objectDeclaration();
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

	public static class TypeModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(ApilangParser.PUBLIC, 0); }
		public TerminalNode PRIVATE() { return getToken(ApilangParser.PRIVATE, 0); }
		public TypeModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterTypeModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitTypeModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitTypeModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeModifierContext typeModifier() throws RecognitionException {
		TypeModifierContext _localctx = new TypeModifierContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typeModifier);
		try {
			setState(84);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				match(PUBLIC);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				match(T__0);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				match(PRIVATE);
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

	public static class ObjectDeclarationContext extends ParserRuleContext {
		public TerminalNode OBJECT() { return getToken(ApilangParser.OBJECT, 0); }
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OBRACE() { return getToken(ApilangParser.OBRACE, 0); }
		public TerminalNode CBRACE() { return getToken(ApilangParser.CBRACE, 0); }
		public List<TypeModifierContext> typeModifier() {
			return getRuleContexts(TypeModifierContext.class);
		}
		public TypeModifierContext typeModifier(int i) {
			return getRuleContext(TypeModifierContext.class,i);
		}
		public List<ExtFieldDeclarationContext> extFieldDeclaration() {
			return getRuleContexts(ExtFieldDeclarationContext.class);
		}
		public ExtFieldDeclarationContext extFieldDeclaration(int i) {
			return getRuleContext(ExtFieldDeclarationContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(ApilangParser.SEMI, 0); }
		public TerminalNode DESCRIPTION() { return getToken(ApilangParser.DESCRIPTION, 0); }
		public ObjectDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterObjectDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitObjectDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitObjectDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectDeclarationContext objectDeclaration() throws RecognitionException {
		ObjectDeclarationContext _localctx = new ObjectDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_objectDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(86);
				typeModifier();
				}
				}
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(92);
			match(OBJECT);
			setState(93);
			match(ID);
			setState(94);
			match(OBRACE);
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE) | (1L << ID))) != 0)) {
				{
				{
				setState(95);
				extFieldDeclaration();
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(101);
			match(CBRACE);
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(102);
				match(SEMI);
				}
			}

			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DESCRIPTION) {
				{
				setState(105);
				match(DESCRIPTION);
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

	public static class ApiDeclarationContext extends ParserRuleContext {
		public TerminalNode API() { return getToken(ApilangParser.API, 0); }
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OBRACE() { return getToken(ApilangParser.OBRACE, 0); }
		public TerminalNode CBRACE() { return getToken(ApilangParser.CBRACE, 0); }
		public List<TypeModifierContext> typeModifier() {
			return getRuleContexts(TypeModifierContext.class);
		}
		public TypeModifierContext typeModifier(int i) {
			return getRuleContext(TypeModifierContext.class,i);
		}
		public List<ApiTypeDeclarationContext> apiTypeDeclaration() {
			return getRuleContexts(ApiTypeDeclarationContext.class);
		}
		public ApiTypeDeclarationContext apiTypeDeclaration(int i) {
			return getRuleContext(ApiTypeDeclarationContext.class,i);
		}
		public ApiDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_apiDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterApiDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitApiDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitApiDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ApiDeclarationContext apiDeclaration() throws RecognitionException {
		ApiDeclarationContext _localctx = new ApiDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_apiDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(108);
				typeModifier();
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(114);
			match(API);
			setState(115);
			match(ID);
			setState(116);
			match(OBRACE);
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << GROUP) | (1L << METHOD) | (1L << OBJECT) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(117);
				apiTypeDeclaration();
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123);
			match(CBRACE);
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

	public static class ApiTypeDeclarationContext extends ParserRuleContext {
		public ApiGroupDeclarationContext apiGroupDeclaration() {
			return getRuleContext(ApiGroupDeclarationContext.class,0);
		}
		public ApiMethodDeclarationContext apiMethodDeclaration() {
			return getRuleContext(ApiMethodDeclarationContext.class,0);
		}
		public ObjectDeclarationContext objectDeclaration() {
			return getRuleContext(ObjectDeclarationContext.class,0);
		}
		public ApiTypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_apiTypeDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterApiTypeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitApiTypeDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitApiTypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ApiTypeDeclarationContext apiTypeDeclaration() throws RecognitionException {
		ApiTypeDeclarationContext _localctx = new ApiTypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_apiTypeDeclaration);
		try {
			setState(128);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				apiGroupDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				apiMethodDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(127);
				objectDeclaration();
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

	public static class ApiGroupDeclarationContext extends ParserRuleContext {
		public TerminalNode GROUP() { return getToken(ApilangParser.GROUP, 0); }
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OBRACE() { return getToken(ApilangParser.OBRACE, 0); }
		public TerminalNode CBRACE() { return getToken(ApilangParser.CBRACE, 0); }
		public List<TypeModifierContext> typeModifier() {
			return getRuleContexts(TypeModifierContext.class);
		}
		public TypeModifierContext typeModifier(int i) {
			return getRuleContext(TypeModifierContext.class,i);
		}
		public ThrowsDeclarationContext throwsDeclaration() {
			return getRuleContext(ThrowsDeclarationContext.class,0);
		}
		public List<ApiTypeDeclarationContext> apiTypeDeclaration() {
			return getRuleContexts(ApiTypeDeclarationContext.class);
		}
		public ApiTypeDeclarationContext apiTypeDeclaration(int i) {
			return getRuleContext(ApiTypeDeclarationContext.class,i);
		}
		public ApiGroupDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_apiGroupDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterApiGroupDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitApiGroupDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitApiGroupDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ApiGroupDeclarationContext apiGroupDeclaration() throws RecognitionException {
		ApiGroupDeclarationContext _localctx = new ApiGroupDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_apiGroupDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(130);
				typeModifier();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136);
			match(GROUP);
			setState(137);
			match(ID);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==THROWS) {
				{
				setState(138);
				throwsDeclaration();
				}
			}

			setState(141);
			match(OBRACE);
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << GROUP) | (1L << METHOD) | (1L << OBJECT) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(142);
				apiTypeDeclaration();
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148);
			match(CBRACE);
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

	public static class ApiMethodDeclarationContext extends ParserRuleContext {
		public TerminalNode METHOD() { return getToken(ApilangParser.METHOD, 0); }
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OPAR() { return getToken(ApilangParser.OPAR, 0); }
		public TerminalNode CPAR() { return getToken(ApilangParser.CPAR, 0); }
		public TerminalNode OBRACE() { return getToken(ApilangParser.OBRACE, 0); }
		public TerminalNode CBRACE() { return getToken(ApilangParser.CBRACE, 0); }
		public List<TypeModifierContext> typeModifier() {
			return getRuleContexts(TypeModifierContext.class);
		}
		public TypeModifierContext typeModifier(int i) {
			return getRuleContext(TypeModifierContext.class,i);
		}
		public ThrowsDeclarationContext throwsDeclaration() {
			return getRuleContext(ThrowsDeclarationContext.class,0);
		}
		public List<FieldDeclarationContext> fieldDeclaration() {
			return getRuleContexts(FieldDeclarationContext.class);
		}
		public FieldDeclarationContext fieldDeclaration(int i) {
			return getRuleContext(FieldDeclarationContext.class,i);
		}
		public List<ResponseDeclarationContext> responseDeclaration() {
			return getRuleContexts(ResponseDeclarationContext.class);
		}
		public ResponseDeclarationContext responseDeclaration(int i) {
			return getRuleContext(ResponseDeclarationContext.class,i);
		}
		public ApiMethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_apiMethodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterApiMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitApiMethodDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitApiMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ApiMethodDeclarationContext apiMethodDeclaration() throws RecognitionException {
		ApiMethodDeclarationContext _localctx = new ApiMethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_apiMethodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(150);
				typeModifier();
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(156);
			match(METHOD);
			setState(157);
			match(ID);
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==THROWS) {
				{
				setState(158);
				throwsDeclaration();
				}
			}

			setState(161);
			match(OPAR);
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE) | (1L << ID))) != 0)) {
				{
				{
				setState(162);
				fieldDeclaration();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(168);
			match(CPAR);
			setState(169);
			match(OBRACE);
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE) | (1L << ID))) != 0)) {
				{
				{
				setState(170);
				responseDeclaration();
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(176);
			match(CBRACE);
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

	public static class ThrowsDeclarationContext extends ParserRuleContext {
		public TerminalNode THROWS() { return getToken(ApilangParser.THROWS, 0); }
		public ThrowsDeclaratorListContext throwsDeclaratorList() {
			return getRuleContext(ThrowsDeclaratorListContext.class,0);
		}
		public ThrowsDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwsDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterThrowsDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitThrowsDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitThrowsDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowsDeclarationContext throwsDeclaration() throws RecognitionException {
		ThrowsDeclarationContext _localctx = new ThrowsDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_throwsDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(THROWS);
			setState(179);
			throwsDeclaratorList();
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

	public static class ThrowsDeclaratorListContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ApilangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ApilangParser.ID, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApilangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApilangParser.COMMA, i);
		}
		public ThrowsDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwsDeclaratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterThrowsDeclaratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitThrowsDeclaratorList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitThrowsDeclaratorList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowsDeclaratorListContext throwsDeclaratorList() throws RecognitionException {
		ThrowsDeclaratorListContext _localctx = new ThrowsDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_throwsDeclaratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(ID);
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(182);
				match(COMMA);
				setState(183);
				match(ID);
				}
				}
				setState(188);
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

	public static class ResponseDeclarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OBRACE() { return getToken(ApilangParser.OBRACE, 0); }
		public TerminalNode CBRACE() { return getToken(ApilangParser.CBRACE, 0); }
		public List<TypeModifierContext> typeModifier() {
			return getRuleContexts(TypeModifierContext.class);
		}
		public TypeModifierContext typeModifier(int i) {
			return getRuleContext(TypeModifierContext.class,i);
		}
		public List<ExtFieldDeclarationContext> extFieldDeclaration() {
			return getRuleContexts(ExtFieldDeclarationContext.class);
		}
		public ExtFieldDeclarationContext extFieldDeclaration(int i) {
			return getRuleContext(ExtFieldDeclarationContext.class,i);
		}
		public ResponseDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_responseDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterResponseDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitResponseDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitResponseDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ResponseDeclarationContext responseDeclaration() throws RecognitionException {
		ResponseDeclarationContext _localctx = new ResponseDeclarationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_responseDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(189);
				typeModifier();
				}
				}
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(195);
			match(ID);
			setState(196);
			match(OBRACE);
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE) | (1L << ID))) != 0)) {
				{
				{
				setState(197);
				extFieldDeclaration();
				}
				}
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(203);
			match(CBRACE);
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

	public static class ExtFieldDeclarationContext extends ParserRuleContext {
		public FieldDeclarationContext fieldDeclaration() {
			return getRuleContext(FieldDeclarationContext.class,0);
		}
		public InlineObjectDeclarationContext inlineObjectDeclaration() {
			return getRuleContext(InlineObjectDeclarationContext.class,0);
		}
		public ExtFieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extFieldDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterExtFieldDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitExtFieldDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitExtFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExtFieldDeclarationContext extFieldDeclaration() throws RecognitionException {
		ExtFieldDeclarationContext _localctx = new ExtFieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_extFieldDeclaration);
		try {
			setState(207);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				fieldDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(206);
				inlineObjectDeclaration();
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

	public static class InlineObjectDeclarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OBRACE() { return getToken(ApilangParser.OBRACE, 0); }
		public TerminalNode CBRACE() { return getToken(ApilangParser.CBRACE, 0); }
		public List<TypeModifierContext> typeModifier() {
			return getRuleContexts(TypeModifierContext.class);
		}
		public TypeModifierContext typeModifier(int i) {
			return getRuleContext(TypeModifierContext.class,i);
		}
		public InlineTypeNameContext inlineTypeName() {
			return getRuleContext(InlineTypeNameContext.class,0);
		}
		public List<TerminalNode> ARRBRACK() { return getTokens(ApilangParser.ARRBRACK); }
		public TerminalNode ARRBRACK(int i) {
			return getToken(ApilangParser.ARRBRACK, i);
		}
		public List<ExtFieldDeclarationContext> extFieldDeclaration() {
			return getRuleContexts(ExtFieldDeclarationContext.class);
		}
		public ExtFieldDeclarationContext extFieldDeclaration(int i) {
			return getRuleContext(ExtFieldDeclarationContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(ApilangParser.SEMI, 0); }
		public TerminalNode DESCRIPTION() { return getToken(ApilangParser.DESCRIPTION, 0); }
		public InlineObjectDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineObjectDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterInlineObjectDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitInlineObjectDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitInlineObjectDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineObjectDeclarationContext inlineObjectDeclaration() throws RecognitionException {
		InlineObjectDeclarationContext _localctx = new InlineObjectDeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_inlineObjectDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(209);
				typeModifier();
				}
				}
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(215);
			match(ID);
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(216);
				inlineTypeName();
				}
			}

			setState(222);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARRBRACK) {
				{
				{
				setState(219);
				match(ARRBRACK);
				}
				}
				setState(224);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(225);
			match(OBRACE);
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE) | (1L << ID))) != 0)) {
				{
				{
				setState(226);
				extFieldDeclaration();
				}
				}
				setState(231);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(232);
			match(CBRACE);
			setState(234);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(233);
				match(SEMI);
				}
			}

			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DESCRIPTION) {
				{
				setState(236);
				match(DESCRIPTION);
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

	public static class FieldDeclarationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public VariableDeclaratorListContext variableDeclaratorList() {
			return getRuleContext(VariableDeclaratorListContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(ApilangParser.SEMI, 0); }
		public List<FieldModifierContext> fieldModifier() {
			return getRuleContexts(FieldModifierContext.class);
		}
		public FieldModifierContext fieldModifier(int i) {
			return getRuleContext(FieldModifierContext.class,i);
		}
		public List<TerminalNode> ARRBRACK() { return getTokens(ApilangParser.ARRBRACK); }
		public TerminalNode ARRBRACK(int i) {
			return getToken(ApilangParser.ARRBRACK, i);
		}
		public TerminalNode DESCRIPTION() { return getToken(ApilangParser.DESCRIPTION, 0); }
		public FieldDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterFieldDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitFieldDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitFieldDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldDeclarationContext fieldDeclaration() throws RecognitionException {
		FieldDeclarationContext _localctx = new FieldDeclarationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_fieldDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(239);
				fieldModifier();
				}
				}
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(245);
			match(ID);
			setState(249);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARRBRACK) {
				{
				{
				setState(246);
				match(ARRBRACK);
				}
				}
				setState(251);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(252);
			variableDeclaratorList();
			setState(253);
			match(SEMI);
			setState(255);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DESCRIPTION) {
				{
				setState(254);
				match(DESCRIPTION);
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

	public static class InlineTypeNameContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(ApilangParser.LT, 0); }
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode GT() { return getToken(ApilangParser.GT, 0); }
		public InlineTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineTypeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterInlineTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitInlineTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitInlineTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineTypeNameContext inlineTypeName() throws RecognitionException {
		InlineTypeNameContext _localctx = new InlineTypeNameContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_inlineTypeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			match(LT);
			setState(258);
			match(ID);
			setState(259);
			match(GT);
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

	public static class FieldModifierContext extends ParserRuleContext {
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(ApilangParser.PUBLIC, 0); }
		public TerminalNode PRIVATE() { return getToken(ApilangParser.PRIVATE, 0); }
		public FieldModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterFieldModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitFieldModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitFieldModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldModifierContext fieldModifier() throws RecognitionException {
		FieldModifierContext _localctx = new FieldModifierContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_fieldModifier);
		try {
			setState(265);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				annotation();
				}
				break;
			case PUBLIC:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				match(PUBLIC);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 3);
				{
				setState(263);
				match(T__0);
				}
				break;
			case PRIVATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(264);
				match(PRIVATE);
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

	public static class VariableDeclaratorListContext extends ParserRuleContext {
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApilangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApilangParser.COMMA, i);
		}
		public VariableDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterVariableDeclaratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitVariableDeclaratorList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitVariableDeclaratorList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorListContext variableDeclaratorList() throws RecognitionException {
		VariableDeclaratorListContext _localctx = new VariableDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_variableDeclaratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			variableDeclarator();
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(268);
				match(COMMA);
				setState(269);
				variableDeclarator();
				}
				}
				setState(274);
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

	public static class VariableDeclaratorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(ApilangParser.ASSIGN, 0); }
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterVariableDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitVariableDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			match(ID);
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(276);
				match(ASSIGN);
				setState(277);
				atom();
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

	public static class AnnotationContext extends ParserRuleContext {
		public NormalAnnotationContext normalAnnotation() {
			return getRuleContext(NormalAnnotationContext.class,0);
		}
		public MarkerAnnotationContext markerAnnotation() {
			return getRuleContext(MarkerAnnotationContext.class,0);
		}
		public SingleElementAnnotationContext singleElementAnnotation() {
			return getRuleContext(SingleElementAnnotationContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_annotation);
		try {
			setState(283);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(280);
				normalAnnotation();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(281);
				markerAnnotation();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(282);
				singleElementAnnotation();
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

	public static class NormalAnnotationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OPAR() { return getToken(ApilangParser.OPAR, 0); }
		public TerminalNode CPAR() { return getToken(ApilangParser.CPAR, 0); }
		public ElementValuePairListContext elementValuePairList() {
			return getRuleContext(ElementValuePairListContext.class,0);
		}
		public NormalAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_normalAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterNormalAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitNormalAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitNormalAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NormalAnnotationContext normalAnnotation() throws RecognitionException {
		NormalAnnotationContext _localctx = new NormalAnnotationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_normalAnnotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(T__1);
			setState(286);
			match(ID);
			setState(287);
			match(OPAR);
			setState(289);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(288);
				elementValuePairList();
				}
			}

			setState(291);
			match(CPAR);
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

	public static class ElementValuePairListContext extends ParserRuleContext {
		public List<ElementValuePairContext> elementValuePair() {
			return getRuleContexts(ElementValuePairContext.class);
		}
		public ElementValuePairContext elementValuePair(int i) {
			return getRuleContext(ElementValuePairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApilangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApilangParser.COMMA, i);
		}
		public ElementValuePairListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePairList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterElementValuePairList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitElementValuePairList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitElementValuePairList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairListContext elementValuePairList() throws RecognitionException {
		ElementValuePairListContext _localctx = new ElementValuePairListContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_elementValuePairList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			elementValuePair();
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(294);
				match(COMMA);
				setState(295);
				elementValuePair();
				}
				}
				setState(300);
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

	public static class ElementValuePairContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(ApilangParser.ASSIGN, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public ElementValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValuePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterElementValuePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitElementValuePair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitElementValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValuePairContext elementValuePair() throws RecognitionException {
		ElementValuePairContext _localctx = new ElementValuePairContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_elementValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			match(ID);
			setState(302);
			match(ASSIGN);
			setState(303);
			elementValue();
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

	public static class ElementValueContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public ElementValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterElementValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitElementValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitElementValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueContext elementValue() throws RecognitionException {
		ElementValueContext _localctx = new ElementValueContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_elementValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			atom();
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

	public static class ElementValueArrayInitializerContext extends ParserRuleContext {
		public TerminalNode OBRACE() { return getToken(ApilangParser.OBRACE, 0); }
		public TerminalNode CBRACE() { return getToken(ApilangParser.CBRACE, 0); }
		public ElementValueListContext elementValueList() {
			return getRuleContext(ElementValueListContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(ApilangParser.COMMA, 0); }
		public ElementValueArrayInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValueArrayInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterElementValueArrayInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitElementValueArrayInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitElementValueArrayInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueArrayInitializerContext elementValueArrayInitializer() throws RecognitionException {
		ElementValueArrayInitializerContext _localctx = new ElementValueArrayInitializerContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_elementValueArrayInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(OBRACE);
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPAR) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
				{
				setState(308);
				elementValueList();
				}
			}

			setState(312);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(311);
				match(COMMA);
				}
			}

			setState(314);
			match(CBRACE);
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

	public static class ElementValueListContext extends ParserRuleContext {
		public List<ElementValueContext> elementValue() {
			return getRuleContexts(ElementValueContext.class);
		}
		public ElementValueContext elementValue(int i) {
			return getRuleContext(ElementValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ApilangParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ApilangParser.COMMA, i);
		}
		public ElementValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementValueList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterElementValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitElementValueList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitElementValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementValueListContext elementValueList() throws RecognitionException {
		ElementValueListContext _localctx = new ElementValueListContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_elementValueList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			elementValue();
			setState(321);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(317);
					match(COMMA);
					setState(318);
					elementValue();
					}
					} 
				}
				setState(323);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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

	public static class MarkerAnnotationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public MarkerAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_markerAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterMarkerAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitMarkerAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitMarkerAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MarkerAnnotationContext markerAnnotation() throws RecognitionException {
		MarkerAnnotationContext _localctx = new MarkerAnnotationContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_markerAnnotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(T__1);
			setState(325);
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

	public static class SingleElementAnnotationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public TerminalNode OPAR() { return getToken(ApilangParser.OPAR, 0); }
		public ElementValueContext elementValue() {
			return getRuleContext(ElementValueContext.class,0);
		}
		public TerminalNode CPAR() { return getToken(ApilangParser.CPAR, 0); }
		public SingleElementAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleElementAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterSingleElementAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitSingleElementAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitSingleElementAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleElementAnnotationContext singleElementAnnotation() throws RecognitionException {
		SingleElementAnnotationContext _localctx = new SingleElementAnnotationContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_singleElementAnnotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(T__1);
			setState(328);
			match(ID);
			setState(329);
			match(OPAR);
			setState(330);
			elementValue();
			setState(331);
			match(CPAR);
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

	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParExprContext extends AtomContext {
		public TerminalNode OPAR() { return getToken(ApilangParser.OPAR, 0); }
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode CPAR() { return getToken(ApilangParser.CPAR, 0); }
		public ParExprContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterParExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitParExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitParExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanAtomContext extends AtomContext {
		public TerminalNode TRUE() { return getToken(ApilangParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(ApilangParser.FALSE, 0); }
		public BooleanAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterBooleanAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitBooleanAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitBooleanAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdAtomContext extends AtomContext {
		public TerminalNode ID() { return getToken(ApilangParser.ID, 0); }
		public IdAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterIdAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitIdAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitIdAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringAtomContext extends AtomContext {
		public TerminalNode STRING() { return getToken(ApilangParser.STRING, 0); }
		public StringAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterStringAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitStringAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitStringAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumberAtomContext extends AtomContext {
		public TerminalNode INT() { return getToken(ApilangParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(ApilangParser.FLOAT, 0); }
		public NumberAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterNumberAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitNumberAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitNumberAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullAtomContext extends AtomContext {
		public TerminalNode NULL() { return getToken(ApilangParser.NULL, 0); }
		public NullAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).enterNullAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ApilangListener ) ((ApilangListener)listener).exitNullAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ApilangVisitor ) return ((ApilangVisitor<? extends T>)visitor).visitNullAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_atom);
		int _la;
		try {
			setState(342);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPAR:
				_localctx = new ParExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(333);
				match(OPAR);
				setState(334);
				atom();
				setState(335);
				match(CPAR);
				}
				break;
			case INT:
			case FLOAT:
				_localctx = new NumberAtomContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(337);
				_la = _input.LA(1);
				if ( !(_la==INT || _la==FLOAT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case TRUE:
			case FALSE:
				_localctx = new BooleanAtomContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(338);
				_la = _input.LA(1);
				if ( !(_la==TRUE || _la==FALSE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case ID:
				_localctx = new IdAtomContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(339);
				match(ID);
				}
				break;
			case STRING:
				_localctx = new StringAtomContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(340);
				match(STRING);
				}
				break;
			case NULL:
				_localctx = new NullAtomContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(341);
				match(NULL);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3&\u015b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\7\2>\n\2\f\2\16"+
		"\2A\13\2\3\2\7\2D\n\2\f\2\16\2G\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\5"+
		"\4Q\n\4\3\5\3\5\3\5\3\5\5\5W\n\5\3\6\7\6Z\n\6\f\6\16\6]\13\6\3\6\3\6\3"+
		"\6\3\6\7\6c\n\6\f\6\16\6f\13\6\3\6\3\6\5\6j\n\6\3\6\5\6m\n\6\3\7\7\7p"+
		"\n\7\f\7\16\7s\13\7\3\7\3\7\3\7\3\7\7\7y\n\7\f\7\16\7|\13\7\3\7\3\7\3"+
		"\b\3\b\3\b\5\b\u0083\n\b\3\t\7\t\u0086\n\t\f\t\16\t\u0089\13\t\3\t\3\t"+
		"\3\t\5\t\u008e\n\t\3\t\3\t\7\t\u0092\n\t\f\t\16\t\u0095\13\t\3\t\3\t\3"+
		"\n\7\n\u009a\n\n\f\n\16\n\u009d\13\n\3\n\3\n\3\n\5\n\u00a2\n\n\3\n\3\n"+
		"\7\n\u00a6\n\n\f\n\16\n\u00a9\13\n\3\n\3\n\3\n\7\n\u00ae\n\n\f\n\16\n"+
		"\u00b1\13\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\7\f\u00bb\n\f\f\f\16\f"+
		"\u00be\13\f\3\r\7\r\u00c1\n\r\f\r\16\r\u00c4\13\r\3\r\3\r\3\r\7\r\u00c9"+
		"\n\r\f\r\16\r\u00cc\13\r\3\r\3\r\3\16\3\16\5\16\u00d2\n\16\3\17\7\17\u00d5"+
		"\n\17\f\17\16\17\u00d8\13\17\3\17\3\17\5\17\u00dc\n\17\3\17\7\17\u00df"+
		"\n\17\f\17\16\17\u00e2\13\17\3\17\3\17\7\17\u00e6\n\17\f\17\16\17\u00e9"+
		"\13\17\3\17\3\17\5\17\u00ed\n\17\3\17\5\17\u00f0\n\17\3\20\7\20\u00f3"+
		"\n\20\f\20\16\20\u00f6\13\20\3\20\3\20\7\20\u00fa\n\20\f\20\16\20\u00fd"+
		"\13\20\3\20\3\20\3\20\5\20\u0102\n\20\3\21\3\21\3\21\3\21\3\22\3\22\3"+
		"\22\3\22\5\22\u010c\n\22\3\23\3\23\3\23\7\23\u0111\n\23\f\23\16\23\u0114"+
		"\13\23\3\24\3\24\3\24\5\24\u0119\n\24\3\25\3\25\3\25\5\25\u011e\n\25\3"+
		"\26\3\26\3\26\3\26\5\26\u0124\n\26\3\26\3\26\3\27\3\27\3\27\7\27\u012b"+
		"\n\27\f\27\16\27\u012e\13\27\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\5"+
		"\32\u0138\n\32\3\32\5\32\u013b\n\32\3\32\3\32\3\33\3\33\3\33\7\33\u0142"+
		"\n\33\f\33\16\33\u0145\13\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3"+
		"\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0159\n\36\3\36"+
		"\2\2\37\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:"+
		"\2\4\3\2\37 \3\2\32\33\2\u0170\2?\3\2\2\2\4J\3\2\2\2\6P\3\2\2\2\bV\3\2"+
		"\2\2\n[\3\2\2\2\fq\3\2\2\2\16\u0082\3\2\2\2\20\u0087\3\2\2\2\22\u009b"+
		"\3\2\2\2\24\u00b4\3\2\2\2\26\u00b7\3\2\2\2\30\u00c2\3\2\2\2\32\u00d1\3"+
		"\2\2\2\34\u00d6\3\2\2\2\36\u00f4\3\2\2\2 \u0103\3\2\2\2\"\u010b\3\2\2"+
		"\2$\u010d\3\2\2\2&\u0115\3\2\2\2(\u011d\3\2\2\2*\u011f\3\2\2\2,\u0127"+
		"\3\2\2\2.\u012f\3\2\2\2\60\u0133\3\2\2\2\62\u0135\3\2\2\2\64\u013e\3\2"+
		"\2\2\66\u0146\3\2\2\28\u0149\3\2\2\2:\u0158\3\2\2\2<>\5\4\3\2=<\3\2\2"+
		"\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@E\3\2\2\2A?\3\2\2\2BD\5\6\4\2CB\3\2\2"+
		"\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2\2GE\3\2\2\2HI\7\2\2\3I\3\3\2"+
		"\2\2JK\7\21\2\2KL\7\"\2\2LM\7\16\2\2M\5\3\2\2\2NQ\5\f\7\2OQ\5\n\6\2PN"+
		"\3\2\2\2PO\3\2\2\2Q\7\3\2\2\2RW\5(\25\2SW\7\30\2\2TW\7\3\2\2UW\7\31\2"+
		"\2VR\3\2\2\2VS\3\2\2\2VT\3\2\2\2VU\3\2\2\2W\t\3\2\2\2XZ\5\b\5\2YX\3\2"+
		"\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\^\3\2\2\2][\3\2\2\2^_\7\25\2\2_`"+
		"\7\36\2\2`d\7\n\2\2ac\5\32\16\2ba\3\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2"+
		"\2eg\3\2\2\2fd\3\2\2\2gi\7\13\2\2hj\7\16\2\2ih\3\2\2\2ij\3\2\2\2jl\3\2"+
		"\2\2km\7!\2\2lk\3\2\2\2lm\3\2\2\2m\13\3\2\2\2np\5\b\5\2on\3\2\2\2ps\3"+
		"\2\2\2qo\3\2\2\2qr\3\2\2\2rt\3\2\2\2sq\3\2\2\2tu\7\22\2\2uv\7\36\2\2v"+
		"z\7\n\2\2wy\5\16\b\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2"+
		"|z\3\2\2\2}~\7\13\2\2~\r\3\2\2\2\177\u0083\5\20\t\2\u0080\u0083\5\22\n"+
		"\2\u0081\u0083\5\n\6\2\u0082\177\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0081"+
		"\3\2\2\2\u0083\17\3\2\2\2\u0084\u0086\5\b\5\2\u0085\u0084\3\2\2\2\u0086"+
		"\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a\3\2"+
		"\2\2\u0089\u0087\3\2\2\2\u008a\u008b\7\23\2\2\u008b\u008d\7\36\2\2\u008c"+
		"\u008e\5\24\13\2\u008d\u008c\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3"+
		"\2\2\2\u008f\u0093\7\n\2\2\u0090\u0092\5\16\b\2\u0091\u0090\3\2\2\2\u0092"+
		"\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0096\3\2"+
		"\2\2\u0095\u0093\3\2\2\2\u0096\u0097\7\13\2\2\u0097\21\3\2\2\2\u0098\u009a"+
		"\5\b\5\2\u0099\u0098\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2\2\2\u009b"+
		"\u009c\3\2\2\2\u009c\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u009f\7\24"+
		"\2\2\u009f\u00a1\7\36\2\2\u00a0\u00a2\5\24\13\2\u00a1\u00a0\3\2\2\2\u00a1"+
		"\u00a2\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a7\7\b\2\2\u00a4\u00a6\5\36"+
		"\20\2\u00a5\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7"+
		"\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\7\t"+
		"\2\2\u00ab\u00af\7\n\2\2\u00ac\u00ae\5\30\r\2\u00ad\u00ac\3\2\2\2\u00ae"+
		"\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b2\3\2"+
		"\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b3\7\13\2\2\u00b3\23\3\2\2\2\u00b4\u00b5"+
		"\7\26\2\2\u00b5\u00b6\5\26\f\2\u00b6\25\3\2\2\2\u00b7\u00bc\7\36\2\2\u00b8"+
		"\u00b9\7\17\2\2\u00b9\u00bb\7\36\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00be\3"+
		"\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\27\3\2\2\2\u00be"+
		"\u00bc\3\2\2\2\u00bf\u00c1\5\b\5\2\u00c0\u00bf\3\2\2\2\u00c1\u00c4\3\2"+
		"\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c5\3\2\2\2\u00c4"+
		"\u00c2\3\2\2\2\u00c5\u00c6\7\36\2\2\u00c6\u00ca\7\n\2\2\u00c7\u00c9\5"+
		"\32\16\2\u00c8\u00c7\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca"+
		"\u00cb\3\2\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cd\u00ce\7\13"+
		"\2\2\u00ce\31\3\2\2\2\u00cf\u00d2\5\36\20\2\u00d0\u00d2\5\34\17\2\u00d1"+
		"\u00cf\3\2\2\2\u00d1\u00d0\3\2\2\2\u00d2\33\3\2\2\2\u00d3\u00d5\5\b\5"+
		"\2\u00d4\u00d3\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7"+
		"\3\2\2\2\u00d7\u00d9\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9\u00db\7\36\2\2"+
		"\u00da\u00dc\5 \21\2\u00db\u00da\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00e0"+
		"\3\2\2\2\u00dd\u00df\7\35\2\2\u00de\u00dd\3\2\2\2\u00df\u00e2\3\2\2\2"+
		"\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00e0"+
		"\3\2\2\2\u00e3\u00e7\7\n\2\2\u00e4\u00e6\5\32\16\2\u00e5\u00e4\3\2\2\2"+
		"\u00e6\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00ea"+
		"\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00ec\7\13\2\2\u00eb\u00ed\7\16\2\2"+
		"\u00ec\u00eb\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ef\3\2\2\2\u00ee\u00f0"+
		"\7!\2\2\u00ef\u00ee\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\35\3\2\2\2\u00f1"+
		"\u00f3\5\"\22\2\u00f2\u00f1\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4\u00f2\3"+
		"\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f7\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f7"+
		"\u00fb\7\36\2\2\u00f8\u00fa\7\35\2\2\u00f9\u00f8\3\2\2\2\u00fa\u00fd\3"+
		"\2\2\2\u00fb\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3\2\2\2\u00fd"+
		"\u00fb\3\2\2\2\u00fe\u00ff\5$\23\2\u00ff\u0101\7\16\2\2\u0100\u0102\7"+
		"!\2\2\u0101\u0100\3\2\2\2\u0101\u0102\3\2\2\2\u0102\37\3\2\2\2\u0103\u0104"+
		"\7\6\2\2\u0104\u0105\7\36\2\2\u0105\u0106\7\7\2\2\u0106!\3\2\2\2\u0107"+
		"\u010c\5(\25\2\u0108\u010c\7\30\2\2\u0109\u010c\7\3\2\2\u010a\u010c\7"+
		"\31\2\2\u010b\u0107\3\2\2\2\u010b\u0108\3\2\2\2\u010b\u0109\3\2\2\2\u010b"+
		"\u010a\3\2\2\2\u010c#\3\2\2\2\u010d\u0112\5&\24\2\u010e\u010f\7\17\2\2"+
		"\u010f\u0111\5&\24\2\u0110\u010e\3\2\2\2\u0111\u0114\3\2\2\2\u0112\u0110"+
		"\3\2\2\2\u0112\u0113\3\2\2\2\u0113%\3\2\2\2\u0114\u0112\3\2\2\2\u0115"+
		"\u0118\7\36\2\2\u0116\u0117\7\5\2\2\u0117\u0119\5:\36\2\u0118\u0116\3"+
		"\2\2\2\u0118\u0119\3\2\2\2\u0119\'\3\2\2\2\u011a\u011e\5*\26\2\u011b\u011e"+
		"\5\66\34\2\u011c\u011e\58\35\2\u011d\u011a\3\2\2\2\u011d\u011b\3\2\2\2"+
		"\u011d\u011c\3\2\2\2\u011e)\3\2\2\2\u011f\u0120\7\4\2\2\u0120\u0121\7"+
		"\36\2\2\u0121\u0123\7\b\2\2\u0122\u0124\5,\27\2\u0123\u0122\3\2\2\2\u0123"+
		"\u0124\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0126\7\t\2\2\u0126+\3\2\2\2"+
		"\u0127\u012c\5.\30\2\u0128\u0129\7\17\2\2\u0129\u012b\5.\30\2\u012a\u0128"+
		"\3\2\2\2\u012b\u012e\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d"+
		"-\3\2\2\2\u012e\u012c\3\2\2\2\u012f\u0130\7\36\2\2\u0130\u0131\7\5\2\2"+
		"\u0131\u0132\5\60\31\2\u0132/\3\2\2\2\u0133\u0134\5:\36\2\u0134\61\3\2"+
		"\2\2\u0135\u0137\7\n\2\2\u0136\u0138\5\64\33\2\u0137\u0136\3\2\2\2\u0137"+
		"\u0138\3\2\2\2\u0138\u013a\3\2\2\2\u0139\u013b\7\17\2\2\u013a\u0139\3"+
		"\2\2\2\u013a\u013b\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013d\7\13\2\2\u013d"+
		"\63\3\2\2\2\u013e\u0143\5\60\31\2\u013f\u0140\7\17\2\2\u0140\u0142\5\60"+
		"\31\2\u0141\u013f\3\2\2\2\u0142\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143"+
		"\u0144\3\2\2\2\u0144\65\3\2\2\2\u0145\u0143\3\2\2\2\u0146\u0147\7\4\2"+
		"\2\u0147\u0148\7\36\2\2\u0148\67\3\2\2\2\u0149\u014a\7\4\2\2\u014a\u014b"+
		"\7\36\2\2\u014b\u014c\7\b\2\2\u014c\u014d\5\60\31\2\u014d\u014e\7\t\2"+
		"\2\u014e9\3\2\2\2\u014f\u0150\7\b\2\2\u0150\u0151\5:\36\2\u0151\u0152"+
		"\7\t\2\2\u0152\u0159\3\2\2\2\u0153\u0159\t\2\2\2\u0154\u0159\t\3\2\2\u0155"+
		"\u0159\7\36\2\2\u0156\u0159\7\"\2\2\u0157\u0159\7\34\2\2\u0158\u014f\3"+
		"\2\2\2\u0158\u0153\3\2\2\2\u0158\u0154\3\2\2\2\u0158\u0155\3\2\2\2\u0158"+
		"\u0156\3\2\2\2\u0158\u0157\3\2\2\2\u0159;\3\2\2\2+?EPV[dilqz\u0082\u0087"+
		"\u008d\u0093\u009b\u00a1\u00a7\u00af\u00bc\u00c2\u00ca\u00d1\u00d6\u00db"+
		"\u00e0\u00e7\u00ec\u00ef\u00f4\u00fb\u0101\u010b\u0112\u0118\u011d\u0123"+
		"\u012c\u0137\u013a\u0143\u0158";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}