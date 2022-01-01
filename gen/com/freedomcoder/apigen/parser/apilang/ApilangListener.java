// Generated from /Users/freedomcoder/Projects/APIMator/src/com/freedomcoder/apigen/parser/apilang/Apilang.g4 by ANTLR 4.9.2
package com.freedomcoder.apigen.parser.apilang;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ApilangParser}.
 */
public interface ApilangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ApilangParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(ApilangParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(ApilangParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(ApilangParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(ApilangParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(ApilangParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(ApilangParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#typeModifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeModifier(ApilangParser.TypeModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#typeModifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeModifier(ApilangParser.TypeModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#objectDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterObjectDeclaration(ApilangParser.ObjectDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#objectDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitObjectDeclaration(ApilangParser.ObjectDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#apiDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterApiDeclaration(ApilangParser.ApiDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#apiDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitApiDeclaration(ApilangParser.ApiDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#apiTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterApiTypeDeclaration(ApilangParser.ApiTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#apiTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitApiTypeDeclaration(ApilangParser.ApiTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#apiGroupDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterApiGroupDeclaration(ApilangParser.ApiGroupDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#apiGroupDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitApiGroupDeclaration(ApilangParser.ApiGroupDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#apiMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterApiMethodDeclaration(ApilangParser.ApiMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#apiMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitApiMethodDeclaration(ApilangParser.ApiMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#throwsDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterThrowsDeclaration(ApilangParser.ThrowsDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#throwsDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitThrowsDeclaration(ApilangParser.ThrowsDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#throwsDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterThrowsDeclaratorList(ApilangParser.ThrowsDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#throwsDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitThrowsDeclaratorList(ApilangParser.ThrowsDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#responseDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterResponseDeclaration(ApilangParser.ResponseDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#responseDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitResponseDeclaration(ApilangParser.ResponseDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#extFieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExtFieldDeclaration(ApilangParser.ExtFieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#extFieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExtFieldDeclaration(ApilangParser.ExtFieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#inlineObjectDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInlineObjectDeclaration(ApilangParser.InlineObjectDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#inlineObjectDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInlineObjectDeclaration(ApilangParser.InlineObjectDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(ApilangParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(ApilangParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#inlineTypeName}.
	 * @param ctx the parse tree
	 */
	void enterInlineTypeName(ApilangParser.InlineTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#inlineTypeName}.
	 * @param ctx the parse tree
	 */
	void exitInlineTypeName(ApilangParser.InlineTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#fieldModifier}.
	 * @param ctx the parse tree
	 */
	void enterFieldModifier(ApilangParser.FieldModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#fieldModifier}.
	 * @param ctx the parse tree
	 */
	void exitFieldModifier(ApilangParser.FieldModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorList(ApilangParser.VariableDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorList(ApilangParser.VariableDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(ApilangParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(ApilangParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(ApilangParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(ApilangParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#normalAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterNormalAnnotation(ApilangParser.NormalAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#normalAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitNormalAnnotation(ApilangParser.NormalAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#elementValuePairList}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePairList(ApilangParser.ElementValuePairListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#elementValuePairList}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePairList(ApilangParser.ElementValuePairListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePair(ApilangParser.ElementValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePair(ApilangParser.ElementValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void enterElementValue(ApilangParser.ElementValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void exitElementValue(ApilangParser.ElementValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterElementValueArrayInitializer(ApilangParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitElementValueArrayInitializer(ApilangParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#elementValueList}.
	 * @param ctx the parse tree
	 */
	void enterElementValueList(ApilangParser.ElementValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#elementValueList}.
	 * @param ctx the parse tree
	 */
	void exitElementValueList(ApilangParser.ElementValueListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#markerAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterMarkerAnnotation(ApilangParser.MarkerAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#markerAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitMarkerAnnotation(ApilangParser.MarkerAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ApilangParser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterSingleElementAnnotation(ApilangParser.SingleElementAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ApilangParser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitSingleElementAnnotation(ApilangParser.SingleElementAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(ApilangParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(ApilangParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumberAtom(ApilangParser.NumberAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumberAtom(ApilangParser.NumberAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAtom(ApilangParser.BooleanAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAtom(ApilangParser.BooleanAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIdAtom(ApilangParser.IdAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIdAtom(ApilangParser.IdAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStringAtom(ApilangParser.StringAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStringAtom(ApilangParser.StringAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNullAtom(ApilangParser.NullAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNullAtom(ApilangParser.NullAtomContext ctx);
}