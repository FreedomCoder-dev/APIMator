// Generated from /Users/freedomcoder/Projects/APIMator/src/com/freedomcoder/apigen/parser/apilang/Apilang.g4 by ANTLR 4.9.2
package com.freedomcoder.apigen.parser.apilang;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ApilangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ApilangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ApilangParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(ApilangParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(ApilangParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(ApilangParser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#typeModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeModifier(ApilangParser.TypeModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#objectDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectDeclaration(ApilangParser.ObjectDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#apiDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApiDeclaration(ApilangParser.ApiDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#apiTypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApiTypeDeclaration(ApilangParser.ApiTypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#apiGroupDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApiGroupDeclaration(ApilangParser.ApiGroupDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#apiMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApiMethodDeclaration(ApilangParser.ApiMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#throwsDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowsDeclaration(ApilangParser.ThrowsDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#throwsDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowsDeclaratorList(ApilangParser.ThrowsDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#responseDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResponseDeclaration(ApilangParser.ResponseDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#extFieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtFieldDeclaration(ApilangParser.ExtFieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#inlineObjectDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineObjectDeclaration(ApilangParser.InlineObjectDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(ApilangParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#inlineTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInlineTypeName(ApilangParser.InlineTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#fieldModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldModifier(ApilangParser.FieldModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorList(ApilangParser.VariableDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(ApilangParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(ApilangParser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#normalAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalAnnotation(ApilangParser.NormalAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#elementValuePairList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePairList(ApilangParser.ElementValuePairListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#elementValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePair(ApilangParser.ElementValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#elementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValue(ApilangParser.ElementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueArrayInitializer(ApilangParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#elementValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueList(ApilangParser.ElementValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#markerAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMarkerAnnotation(ApilangParser.MarkerAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ApilangParser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleElementAnnotation(ApilangParser.SingleElementAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpr(ApilangParser.ParExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberAtom(ApilangParser.NumberAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanAtom(ApilangParser.BooleanAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdAtom(ApilangParser.IdAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringAtom(ApilangParser.StringAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullAtom}
	 * labeled alternative in {@link ApilangParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullAtom(ApilangParser.NullAtomContext ctx);
}