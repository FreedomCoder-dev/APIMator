package org.walkmod.javalang;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Comment;
import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.ImportDeclaration;
import org.walkmod.javalang.ast.PackageDeclaration;
import org.walkmod.javalang.ast.TypeParameter;
import org.walkmod.javalang.ast.body.AnnotationDeclaration;
import org.walkmod.javalang.ast.body.AnnotationMemberDeclaration;
import org.walkmod.javalang.ast.body.BodyDeclaration;
import org.walkmod.javalang.ast.body.ClassOrInterfaceDeclaration;
import org.walkmod.javalang.ast.body.ConstructorDeclaration;
import org.walkmod.javalang.ast.body.EmptyMemberDeclaration;
import org.walkmod.javalang.ast.body.EmptyTypeDeclaration;
import org.walkmod.javalang.ast.body.EnumConstantDeclaration;
import org.walkmod.javalang.ast.body.EnumDeclaration;
import org.walkmod.javalang.ast.body.FieldDeclaration;
import org.walkmod.javalang.ast.body.InitializerDeclaration;
import org.walkmod.javalang.ast.body.JavadocComment;
import org.walkmod.javalang.ast.body.MethodDeclaration;
import org.walkmod.javalang.ast.body.ModifierSet;
import org.walkmod.javalang.ast.body.Parameter;
import org.walkmod.javalang.ast.body.TypeDeclaration;
import org.walkmod.javalang.ast.body.VariableDeclarator;
import org.walkmod.javalang.ast.body.VariableDeclaratorId;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.expr.ArrayAccessExpr;
import org.walkmod.javalang.ast.expr.ArrayCreationExpr;
import org.walkmod.javalang.ast.expr.ArrayInitializerExpr;
import org.walkmod.javalang.ast.expr.AssignExpr;
import org.walkmod.javalang.ast.expr.BinaryExpr;
import org.walkmod.javalang.ast.expr.BooleanLiteralExpr;
import org.walkmod.javalang.ast.expr.CastExpr;
import org.walkmod.javalang.ast.expr.CharLiteralExpr;
import org.walkmod.javalang.ast.expr.ClassExpr;
import org.walkmod.javalang.ast.expr.ConditionalExpr;
import org.walkmod.javalang.ast.expr.DoubleLiteralExpr;
import org.walkmod.javalang.ast.expr.EnclosedExpr;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.expr.FieldAccessExpr;
import org.walkmod.javalang.ast.expr.InstanceOfExpr;
import org.walkmod.javalang.ast.expr.IntegerLiteralExpr;
import org.walkmod.javalang.ast.expr.IntegerLiteralMinValueExpr;
import org.walkmod.javalang.ast.expr.LambdaExpr;
import org.walkmod.javalang.ast.expr.LongLiteralExpr;
import org.walkmod.javalang.ast.expr.LongLiteralMinValueExpr;
import org.walkmod.javalang.ast.expr.MarkerAnnotationExpr;
import org.walkmod.javalang.ast.expr.MemberValuePair;
import org.walkmod.javalang.ast.expr.MethodCallExpr;
import org.walkmod.javalang.ast.expr.MethodReferenceExpr;
import org.walkmod.javalang.ast.expr.NameExpr;
import org.walkmod.javalang.ast.expr.NormalAnnotationExpr;
import org.walkmod.javalang.ast.expr.NullLiteralExpr;
import org.walkmod.javalang.ast.expr.ObjectCreationExpr;
import org.walkmod.javalang.ast.expr.QualifiedNameExpr;
import org.walkmod.javalang.ast.expr.SingleMemberAnnotationExpr;
import org.walkmod.javalang.ast.expr.StringLiteralExpr;
import org.walkmod.javalang.ast.expr.SuperExpr;
import org.walkmod.javalang.ast.expr.ThisExpr;
import org.walkmod.javalang.ast.expr.TypeExpr;
import org.walkmod.javalang.ast.expr.UnaryExpr;
import org.walkmod.javalang.ast.expr.VariableDeclarationExpr;
import org.walkmod.javalang.ast.stmt.AssertStmt;
import org.walkmod.javalang.ast.stmt.BlockStmt;
import org.walkmod.javalang.ast.stmt.BreakStmt;
import org.walkmod.javalang.ast.stmt.CatchClause;
import org.walkmod.javalang.ast.stmt.ContinueStmt;
import org.walkmod.javalang.ast.stmt.DoStmt;
import org.walkmod.javalang.ast.stmt.EmptyStmt;
import org.walkmod.javalang.ast.stmt.ExplicitConstructorInvocationStmt;
import org.walkmod.javalang.ast.stmt.ExpressionStmt;
import org.walkmod.javalang.ast.stmt.ForStmt;
import org.walkmod.javalang.ast.stmt.ForeachStmt;
import org.walkmod.javalang.ast.stmt.IfStmt;
import org.walkmod.javalang.ast.stmt.LabeledStmt;
import org.walkmod.javalang.ast.stmt.ReturnStmt;
import org.walkmod.javalang.ast.stmt.Statement;
import org.walkmod.javalang.ast.stmt.SwitchEntryStmt;
import org.walkmod.javalang.ast.stmt.SwitchStmt;
import org.walkmod.javalang.ast.stmt.SynchronizedStmt;
import org.walkmod.javalang.ast.stmt.ThrowStmt;
import org.walkmod.javalang.ast.stmt.TryStmt;
import org.walkmod.javalang.ast.stmt.TypeDeclarationStmt;
import org.walkmod.javalang.ast.stmt.WhileStmt;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.ast.type.IntersectionType;
import org.walkmod.javalang.ast.type.PrimitiveType;
import org.walkmod.javalang.ast.type.ReferenceType;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.ast.type.VoidType;
import org.walkmod.javalang.ast.type.WildcardType;

final class ASTParser implements ASTParserConstants {

	private Expression generateLambda(Expression ret, Statement lambdaBody) {
		List params = null;
		VariableDeclaratorId id = null;
		Expression inner = null;

		if (ret instanceof EnclosedExpr) {
			inner = ((EnclosedExpr) ret).getInner();
			if (inner != null && inner instanceof NameExpr) {
				id = new VariableDeclaratorId(inner.getBeginLine(), inner.getBeginColumn(), inner.getEndLine(), inner.getEndColumn(), ((NameExpr) inner).getName(), 0);
				params = add(params, new Parameter(ret.getBeginLine(), ret.getBeginColumn(), ret.getEndLine(), ret.getEndColumn(), 0, null, null, false, id));
				ret = new LambdaExpr(ret.getBeginLine(), ret.getBeginColumn(), lambdaBody.getEndLine(), lambdaBody.getEndColumn(), params, lambdaBody, true);
			} else {
				ret = new LambdaExpr(ret.getBeginLine(), ret.getBeginColumn(), lambdaBody.getEndLine(), lambdaBody.getEndColumn(), null, lambdaBody, true);
			}
		} else if (ret instanceof NameExpr) {
			id = new VariableDeclaratorId(ret.getBeginLine(), ret.getBeginColumn(), ret.getEndLine(), ret.getEndColumn(), ((NameExpr) ret).getName(), 0);
			params = add(params, new Parameter(ret.getBeginLine(), ret.getBeginColumn(), ret.getEndLine(), ret.getEndColumn(), 0, null, null, false, id));
			ret = new LambdaExpr(ret.getBeginLine(), ret.getBeginColumn(), ret.getEndLine(), ret.getEndColumn(), params, lambdaBody, false);
		} else if (ret instanceof LambdaExpr) {
			((LambdaExpr) ret).setBody(lambdaBody);
			ret.setEndLine(lambdaBody.getEndLine());
			ret.setEndColumn(lambdaBody.getEndColumn());
		} else {
			ret = null;
		}
		return ret;
	}

	void reset(InputStream in, String encoding) {
		ReInit(in, encoding);
		token_source.clearComments();
	}

	private List add(List list, Object obj) {
		if (list == null) {
			list = new LinkedList();
		}
		list.add(obj);
		return list;
	}

	private List add(int pos, List list, Object obj) {
		if (list == null) {
			list = new LinkedList();
		}
		list.add(pos, obj);
		return list;
	}

	private class Modifier {

		final int modifiers;
		final List annotations;
		final int beginLine;
		final int beginColumn;

		public Modifier(int beginLine, int beginColumn, int modifiers, List annotations) {
			this.beginLine = beginLine;
			this.beginColumn = beginColumn;
			this.modifiers = modifiers;
			this.annotations = annotations;
		}
	}

	public int addModifier(int modifiers, int mod, Token token) throws ParseException {
		if ((ModifierSet.hasModifier(modifiers, mod))) {
			throwParseException(token, "Duplicated modifier");
		}
		return ModifierSet.addModifier(modifiers, mod);
	}

	private void pushJavadoc() {
		token_source.pushJavadoc();
	}

	private JavadocComment popJavadoc() {
		return token_source.popJavadoc();
	}

	private List<Comment> getComments() {
		return token_source.getComments();
	}

	private void throwParseException(Token token, String message) throws ParseException {
		StringBuilder buf = new StringBuilder();
		buf.append(message);
		buf.append(": \"");
		buf.append(token.image);
		buf.append("\" at line ");
		buf.append(token.beginLine);
		buf.append(", column ");
		buf.append(token.beginColumn);
		ParseException e = new ParseException(buf.toString());
		e.currentToken = token;
		throw e;
	}

	static final class GTToken extends Token {

		int realKind = GT;

		GTToken(int kind, String image) {
			this.kind = kind;
			this.image = image;
		}

		public static Token newToken(int kind, String image) {
			return new GTToken(kind, image);
		}
	}

	public final CompilationUnit CompilationUnit() throws ParseException {
		PackageDeclaration pakage = null;
		List imports = null;
		ImportDeclaration in = null;
		List types = null;
		TypeDeclaration tn = null;
		int line = -1;
		int column = 0;
		if (jj_2_1(2147483647)) {
			pakage = PackageDeclaration();
			line = pakage.getBeginLine();
			column = pakage.getBeginColumn();
		} else {
		}
		label_1: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case IMPORT:
					break;
				default:
						jj_la1[0] = jj_gen;
						break label_1;
			}
			in = ImportDeclaration();
			if (line == -1) {
				line = in.getBeginLine();
				column = in.getBeginColumn();
			}
			imports = add(imports, in);
		}
		label_2: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ABSTRACT:
				case CLASS:
				case ENUM:
				case FINAL:
				case INTERFACE:
				case NATIVE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case STATIC:
				case STRICTFP:
				case SYNCHRONIZED:
				case TRANSIENT:
				case VOLATILE:
				case SEMICOLON:
				case AT:
					break;
				default:
						jj_la1[1] = jj_gen;
						break label_2;
			}
			tn = TypeDeclaration();
			if (line == -1) {
				line = tn.getBeginLine();
				column = tn.getBeginColumn();
			}
			types = add(types, tn);
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case 0:
					jj_consume_token(0);
					break;
			case 131:
					jj_consume_token(131);
					break;
			default:
					jj_la1[2] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return new CompilationUnit(line == -1 ? 0 : line, column, token.endLine, token.endColumn, pakage, imports, types, getComments());
		}
		throw new Error("Missing return statement in function");
	}

	public final PackageDeclaration PackageDeclaration() throws ParseException {
		List annotations = null;
		AnnotationExpr ann;
		NameExpr name;
		int line;
		int column;
		label_3: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[3] = jj_gen;
						break label_3;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		jj_consume_token(PACKAGE);
		line = token.beginLine;
		column = token.beginColumn;
		name = Name();
		jj_consume_token(SEMICOLON);
		{
			if (true) return new PackageDeclaration(line, column, token.endLine, token.endColumn, annotations, name);
		}
		throw new Error("Missing return statement in function");
	}

	public final ImportDeclaration ImportDeclaration() throws ParseException {
		NameExpr name;
		boolean isStatic = false;
		boolean isAsterisk = false;
		int line;
		int column;
		jj_consume_token(IMPORT);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case STATIC:
					jj_consume_token(STATIC);
					isStatic = true;
					break;
			default:
					jj_la1[4] = jj_gen;
		}
		name = Name();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case DOT:
					jj_consume_token(DOT);
					jj_consume_token(STAR);
					isAsterisk = true;
					break;
			default:
					jj_la1[5] = jj_gen;
		}
		jj_consume_token(SEMICOLON);
		{
			if (true) return new ImportDeclaration(line, column, token.endLine, token.endColumn, name, isStatic, isAsterisk);
		}
		throw new Error("Missing return statement in function");
	}

	public final Modifier Modifiers() throws ParseException {
		int beginLine = -1;
		int beginColumn = -1;
		int modifiers = 0;
		List annotations = null;
		AnnotationExpr ann;
		label_4: while (true) {
			if (jj_2_2(2)) {
			} else {
				break label_4;
			}
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case PUBLIC:
						jj_consume_token(PUBLIC);
						modifiers = addModifier(modifiers, ModifierSet.PUBLIC, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case STATIC:
						jj_consume_token(STATIC);
						modifiers = addModifier(modifiers, ModifierSet.STATIC, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case PROTECTED:
						jj_consume_token(PROTECTED);
						modifiers = addModifier(modifiers, ModifierSet.PROTECTED, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case PRIVATE:
						jj_consume_token(PRIVATE);
						modifiers = addModifier(modifiers, ModifierSet.PRIVATE, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case FINAL:
						jj_consume_token(FINAL);
						modifiers = addModifier(modifiers, ModifierSet.FINAL, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case ABSTRACT:
						jj_consume_token(ABSTRACT);
						modifiers = addModifier(modifiers, ModifierSet.ABSTRACT, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case SYNCHRONIZED:
						jj_consume_token(SYNCHRONIZED);
						modifiers = addModifier(modifiers, ModifierSet.SYNCHRONIZED, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case NATIVE:
						jj_consume_token(NATIVE);
						modifiers = addModifier(modifiers, ModifierSet.NATIVE, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case TRANSIENT:
						jj_consume_token(TRANSIENT);
						modifiers = addModifier(modifiers, ModifierSet.TRANSIENT, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case VOLATILE:
						jj_consume_token(VOLATILE);
						modifiers = addModifier(modifiers, ModifierSet.VOLATILE, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case STRICTFP:
						jj_consume_token(STRICTFP);
						modifiers = addModifier(modifiers, ModifierSet.STRICTFP, token);
						if (beginLine == -1) {
							beginLine = token.beginLine;
							beginColumn = token.beginColumn;
						}
						break;
				case AT:
						ann = Annotation();
						annotations = add(annotations, ann);
						if (beginLine == -1) {
							beginLine = ann.getBeginLine();
							beginColumn = ann.getBeginColumn();
						}
						break;
				default:
						jj_la1[6] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return new Modifier(beginLine, beginColumn, modifiers, annotations);
		}
		throw new Error("Missing return statement in function");
	}

	public final TypeDeclaration TypeDeclaration() throws ParseException {
		Modifier modifier;
		TypeDeclaration ret;
		pushJavadoc();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case SEMICOLON:
					jj_consume_token(SEMICOLON);
					ret = new EmptyTypeDeclaration(token.beginLine, token.beginColumn, token.endLine, token.endColumn, popJavadoc());
					break;
			case ABSTRACT:
			case CLASS:
			case ENUM:
			case FINAL:
			case INTERFACE:
			case NATIVE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case STATIC:
			case STRICTFP:
			case SYNCHRONIZED:
			case TRANSIENT:
			case VOLATILE:
			case AT:
					modifier = Modifiers();
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case CLASS:
						case INTERFACE:
								ret = ClassOrInterfaceDeclaration(modifier);
								break;
						case ENUM:
								ret = EnumDeclaration(modifier);
								break;
						case AT:
								ret = AnnotationTypeDeclaration(modifier);
								break;
						default:
								jj_la1[7] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
					}
					break;
			default:
					jj_la1[8] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final ClassOrInterfaceDeclaration ClassOrInterfaceDeclaration(Modifier modifier) throws ParseException {
		boolean isInterface = false;
		String name;
		List typePar = null;
		List extList = null;
		List impList = null;
		List members;
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case CLASS:
					jj_consume_token(CLASS);
					break;
			case INTERFACE:
					jj_consume_token(INTERFACE);
					isInterface = true;
					break;
			default:
					jj_la1[9] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		if (line == -1) {
			line = token.beginLine;
			column = token.beginColumn;
		}
		jj_consume_token(IDENTIFIER);
		name = token.image;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LT:
					typePar = TypeParameters();
					typePar.remove(0);
					break;
			default:
					jj_la1[10] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case EXTENDS:
					extList = ExtendsList(isInterface);
					break;
			default:
					jj_la1[11] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IMPLEMENTS:
					impList = ImplementsList(isInterface);
					break;
			default:
					jj_la1[12] = jj_gen;
		}
		members = ClassOrInterfaceBody(isInterface);
		{
			if (true) return new ClassOrInterfaceDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), modifier.modifiers, modifier.annotations, isInterface, name, typePar, extList, impList, members);
		}
		throw new Error("Missing return statement in function");
	}

	public final List ExtendsList(boolean isInterface) throws ParseException {
		boolean extendsMoreThanOne = false;
		List ret = new LinkedList();
		ClassOrInterfaceType cit;
		AnnotationExpr ann;
		List annotations = null;
		jj_consume_token(EXTENDS);
		label_5: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[13] = jj_gen;
						break label_5;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		cit = ClassOrInterfaceType();
		cit.setAnnotations(annotations);
		ret.add(cit);
		label_6: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[14] = jj_gen;
						break label_6;
			}
			jj_consume_token(COMMA);
			cit = ClassOrInterfaceType();
			ret.add(cit);
			extendsMoreThanOne = true;
		}
		if (extendsMoreThanOne && !isInterface) throwParseException(token, "A class cannot extend more than one other class");
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List ImplementsList(boolean isInterface) throws ParseException {
		List ret = new LinkedList();
		ClassOrInterfaceType cit;
		AnnotationExpr ann;
		List annotations = null;
		jj_consume_token(IMPLEMENTS);
		label_7: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[15] = jj_gen;
						break label_7;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		cit = ClassOrInterfaceType();
		cit.setAnnotations(annotations);
		ret.add(cit);
		label_8: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[16] = jj_gen;
						break label_8;
			}
			jj_consume_token(COMMA);
			cit = ClassOrInterfaceType();
			ret.add(cit);
		}
		if (isInterface) throwParseException(token, "An interface cannot implement other interfaces");
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final EnumDeclaration EnumDeclaration(Modifier modifier) throws ParseException {
		String name;
		List impList = null;
		EnumConstantDeclaration entry;
		List entries = null;
		BodyDeclaration member;
		List members = null;
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		jj_consume_token(ENUM);
		if (line == -1) {
			line = token.beginLine;
			column = token.beginColumn;
		}
		jj_consume_token(IDENTIFIER);
		name = token.image;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IMPLEMENTS:
					impList = ImplementsList(false);
					break;
			default:
					jj_la1[17] = jj_gen;
		}
		jj_consume_token(LBRACE);
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IDENTIFIER:
			case AT:
					entries = new LinkedList();
					entry = EnumConstantDeclaration();
					entries.add(entry);
					label_9: while (true) {
						if (jj_2_3(2)) {
						} else {
							break label_9;
						}
						jj_consume_token(COMMA);
						entry = EnumConstantDeclaration();
						entries.add(entry);
					}
					break;
			default:
					jj_la1[18] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case COMMA:
					jj_consume_token(COMMA);
					break;
			default:
					jj_la1[19] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case SEMICOLON:
					jj_consume_token(SEMICOLON);
					label_10: while (true) {
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case ABSTRACT:
							case BOOLEAN:
							case BYTE:
							case CHAR:
							case CLASS:
							case _DEFAULT:
							case DOUBLE:
							case ENUM:
							case FINAL:
							case FLOAT:
							case INT:
							case INTERFACE:
							case LONG:
							case NATIVE:
							case PRIVATE:
							case PROTECTED:
							case PUBLIC:
							case SHORT:
							case STATIC:
							case STRICTFP:
							case SYNCHRONIZED:
							case TRANSIENT:
							case VOID:
							case VOLATILE:
							case IDENTIFIER:
							case LBRACE:
							case SEMICOLON:
							case AT:
							case LT:
								break;
							default:
									jj_la1[20] = jj_gen;
									break label_10;
						}
						member = ClassOrInterfaceBodyDeclaration(false);
						members = add(members, member);
					}
					break;
			default:
					jj_la1[21] = jj_gen;
		}
		jj_consume_token(RBRACE);
		{
			if (true) return new EnumDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), modifier.modifiers, modifier.annotations, name, impList, entries, members);
		}
		throw new Error("Missing return statement in function");
	}

	public final EnumConstantDeclaration EnumConstantDeclaration() throws ParseException {
		List annotations = null;
		AnnotationExpr ann;
		String name;
		List args = null;
		List classBody = null;
		int line = -1;
		int column = -1;
		pushJavadoc();
		label_11: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[22] = jj_gen;
						break label_11;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
			if (line == -1) {
				line = ann.getBeginLine();
				column = ann.getBeginColumn();
			}
		}
		jj_consume_token(IDENTIFIER);
		name = token.image;
		if (line == -1) {
			line = token.beginLine;
			column = token.beginColumn;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LPAREN:
					args = Arguments();
					break;
			default:
					jj_la1[23] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LBRACE:
					classBody = ClassOrInterfaceBody(false);
					break;
			default:
					jj_la1[24] = jj_gen;
		}
		{
			if (true) return new EnumConstantDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), annotations, name, args, classBody);
		}
		throw new Error("Missing return statement in function");
	}

	public final List TypeParameters() throws ParseException {
		List ret = new LinkedList();
		TypeParameter tp;
		List annotations = null;
		AnnotationExpr ann;
		jj_consume_token(LT);
		ret.add(new int[] { token.beginLine, token.beginColumn });
		label_12: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[25] = jj_gen;
						break label_12;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		tp = TypeParameter();
		ret.add(tp);
		tp.setAnnotations(annotations);
		annotations = null;
		label_13: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[26] = jj_gen;
						break label_13;
			}
			jj_consume_token(COMMA);
			label_14: while (true) {
				switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case AT:
						break;
					default:
							jj_la1[27] = jj_gen;
							break label_14;
				}
				ann = Annotation();
				annotations = add(annotations, ann);
			}
			tp = TypeParameter();
			ret.add(tp);
			tp.setAnnotations(annotations);
			annotations = null;
		}
		jj_consume_token(GT);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final TypeParameter TypeParameter() throws ParseException {
		String name;
		List typeBound = null;
		int line;
		int column;
		jj_consume_token(IDENTIFIER);
		name = token.image;
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case EXTENDS:
					typeBound = TypeBound();
					break;
			default:
					jj_la1[28] = jj_gen;
		}
		{
			if (true) return new TypeParameter(line, column, token.endLine, token.endColumn, name, typeBound);
		}
		throw new Error("Missing return statement in function");
	}

	public final List TypeBound() throws ParseException {
		List ret = new LinkedList();
		ClassOrInterfaceType cit;
		AnnotationExpr ann;
		List annotations = null;
		jj_consume_token(EXTENDS);
		label_15: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[29] = jj_gen;
						break label_15;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		cit = ClassOrInterfaceType();
		cit.setAnnotations(annotations);
		ret.add(cit);
		annotations = null;
		label_16: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case BIT_AND:
					break;
				default:
						jj_la1[30] = jj_gen;
						break label_16;
			}
			jj_consume_token(BIT_AND);
			label_17: while (true) {
				switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case AT:
						break;
					default:
							jj_la1[31] = jj_gen;
							break label_17;
				}
				ann = Annotation();
				annotations = add(annotations, ann);
			}
			cit = ClassOrInterfaceType();
			cit.setAnnotations(annotations);
			ret.add(cit);
			annotations = null;
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List ClassOrInterfaceBody(boolean isInterface) throws ParseException {
		List ret = new LinkedList();
		BodyDeclaration member;
		jj_consume_token(LBRACE);
		label_18: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ABSTRACT:
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case CLASS:
				case _DEFAULT:
				case DOUBLE:
				case ENUM:
				case FINAL:
				case FLOAT:
				case INT:
				case INTERFACE:
				case LONG:
				case NATIVE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case SHORT:
				case STATIC:
				case STRICTFP:
				case SYNCHRONIZED:
				case TRANSIENT:
				case VOID:
				case VOLATILE:
				case IDENTIFIER:
				case LBRACE:
				case SEMICOLON:
				case AT:
				case LT:
					break;
				default:
						jj_la1[32] = jj_gen;
						break label_18;
			}
			member = ClassOrInterfaceBodyDeclaration(isInterface);
			ret.add(member);
		}
		jj_consume_token(RBRACE);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final BodyDeclaration ClassOrInterfaceBodyDeclaration(boolean isInterface) throws ParseException {
		boolean isNestedInterface = false;
		Modifier modifier;
		Modifier modifier2 = null;
		int aux = 0;
		BodyDeclaration ret;
		boolean isDefault = false;
		pushJavadoc();
		if (jj_2_6(2)) {
			ret = InitializerDeclaration();
			if (isInterface) throwParseException(token, "An interface cannot have initializers");
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ABSTRACT:
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case CLASS:
				case _DEFAULT:
				case DOUBLE:
				case ENUM:
				case FINAL:
				case FLOAT:
				case INT:
				case INTERFACE:
				case LONG:
				case NATIVE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case SHORT:
				case STATIC:
				case STRICTFP:
				case SYNCHRONIZED:
				case TRANSIENT:
				case VOID:
				case VOLATILE:
				case IDENTIFIER:
				case AT:
				case LT:
						modifier = Modifiers();
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case _DEFAULT:
									jj_consume_token(_DEFAULT);
									modifier2 = Modifiers();
									if (!isInterface) {
										throwParseException(token, "An interface cannot have default members");
									}
									isDefault = true;
									break;
							default:
									jj_la1[33] = jj_gen;
						}
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case CLASS:
							case INTERFACE:
									ret = ClassOrInterfaceDeclaration(modifier);
									break;
							case ENUM:
									ret = EnumDeclaration(modifier);
									break;
							case AT:
									ret = AnnotationTypeDeclaration(modifier);
									break;
							default:
									jj_la1[34] = jj_gen;
									if (jj_2_4(2147483647)) {
										ret = ConstructorDeclaration(modifier);
									} else if (jj_2_5(2147483647)) {
										ret = FieldDeclaration(modifier);
									} else {
										switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
											case BOOLEAN:
											case BYTE:
											case CHAR:
											case DOUBLE:
											case FLOAT:
											case INT:
											case LONG:
											case SHORT:
											case VOID:
											case IDENTIFIER:
											case LT:
													ret = MethodDeclaration(modifier);
													if (isDefault && ret != null && ((MethodDeclaration) ret).getBody() == null) {
														throwParseException(token, "\"default\" methods must have a body");
													}
													((MethodDeclaration) ret).setDefault(isDefault);
													if (modifier2 != null) {
														aux = modifier2.modifiers;
													}
													((MethodDeclaration) ret).setModifiers(addModifier(modifier.modifiers, aux, token));
													break;
											default:
													jj_la1[35] = jj_gen;
													jj_consume_token(-1);
													throw new ParseException();
										}
									}
						}
						if (isDefault && !(ret instanceof MethodDeclaration)) {
							throwParseException(token, "Just methods can have the keyword \"default\".");
						}
						break;
				case SEMICOLON:
						jj_consume_token(SEMICOLON);
						ret = new EmptyMemberDeclaration(token.beginLine, token.beginColumn, token.endLine, token.endColumn, popJavadoc());
						break;
				default:
						jj_la1[36] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final FieldDeclaration FieldDeclaration(Modifier modifier) throws ParseException {
		Type type;
		List variables = new LinkedList();
		VariableDeclarator val;

		type = Type();
		val = VariableDeclarator();
		variables.add(val);
		label_19: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[37] = jj_gen;
						break label_19;
			}
			jj_consume_token(COMMA);
			val = VariableDeclarator();
			variables.add(val);
		}
		jj_consume_token(SEMICOLON);
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		if (line == -1) {
			line = type.getBeginLine();
			column = type.getBeginColumn();
		}
		{
			if (true) return new FieldDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), modifier.modifiers, modifier.annotations, type, variables);
		}
		throw new Error("Missing return statement in function");
	}

	public final VariableDeclarator VariableDeclarator() throws ParseException {
		VariableDeclaratorId id;
		Expression init = null;
		id = VariableDeclaratorId();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case ASSIGN:
					jj_consume_token(ASSIGN);
					init = VariableInitializer();
					break;
			default:
					jj_la1[38] = jj_gen;
		}
		{
			if (true) return new VariableDeclarator(id.getBeginLine(), id.getBeginColumn(), token.endLine, token.endColumn, id, init);
		}
		throw new Error("Missing return statement in function");
	}

	public final VariableDeclaratorId VariableDeclaratorId() throws ParseException {
		String name;
		int arrayCount = 0;
		int line;
		int column;
		jj_consume_token(IDENTIFIER);
		name = token.image;
		line = token.beginLine;
		column = token.beginColumn;
		label_20: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LBRACKET:
					break;
				default:
						jj_la1[39] = jj_gen;
						break label_20;
			}
			jj_consume_token(LBRACKET);
			jj_consume_token(RBRACKET);
			arrayCount++;
		}
		{
			if (true) return new VariableDeclaratorId(line, column, token.endLine, token.endColumn, name, arrayCount);
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression VariableInitializer() throws ParseException {
		Expression ret;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LBRACE:
					ret = ArrayInitializer();
					break;
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case BANG:
			case TILDE:
			case INCR:
			case DECR:
			case PLUS:
			case MINUS:
					ret = Expression();
					break;
			default:
					jj_la1[40] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final ArrayInitializerExpr ArrayInitializer() throws ParseException {
		List values = null;
		Expression val;
		int line;
		int column;
		jj_consume_token(LBRACE);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case LBRACE:
			case BANG:
			case TILDE:
			case INCR:
			case DECR:
			case PLUS:
			case MINUS:
					val = VariableInitializer();
					values = add(values, val);
					label_21: while (true) {
						if (jj_2_7(2)) {
						} else {
							break label_21;
						}
						jj_consume_token(COMMA);
						val = VariableInitializer();
						values = add(values, val);
					}
					break;
			default:
					jj_la1[41] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case COMMA:
					jj_consume_token(COMMA);
					break;
			default:
					jj_la1[42] = jj_gen;
		}
		jj_consume_token(RBRACE);
		{
			if (true) return new ArrayInitializerExpr(line, column, token.endLine, token.endColumn, values);
		}
		throw new Error("Missing return statement in function");
	}

	public final MethodDeclaration MethodDeclaration(Modifier modifier) throws ParseException {
		List typeParameters = null;
		Type type;
		String name;
		List parameters;
		int arrayCount = 0;
		List throws_ = null;
		BlockStmt block = null;
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LT:
					typeParameters = TypeParameters();
					int[] lineCol = (int[]) typeParameters.remove(0);
					if (line == -1) {
						line = lineCol[0];
						column = lineCol[1];
					}
					break;
			default:
					jj_la1[43] = jj_gen;
		}
		type = ResultType();
		if (line == -1) {
			line = type.getBeginLine();
			column = type.getBeginColumn();
		}
		jj_consume_token(IDENTIFIER);
		name = token.image;
		parameters = FormalParameters();
		label_22: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LBRACKET:
					break;
				default:
						jj_la1[44] = jj_gen;
						break label_22;
			}
			jj_consume_token(LBRACKET);
			jj_consume_token(RBRACKET);
			arrayCount++;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case THROWS:
					jj_consume_token(THROWS);
					throws_ = ClassOrInterfaceTypeList();
					break;
			default:
					jj_la1[45] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LBRACE:
					block = Block();
					break;
			case SEMICOLON:
					jj_consume_token(SEMICOLON);
					break;
			default:
					jj_la1[46] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return new MethodDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), modifier.modifiers, modifier.annotations, typeParameters, type, name, parameters, arrayCount, throws_, block);
		}
		throw new Error("Missing return statement in function");
	}

	public final List FormalParameters() throws ParseException {
		List ret = null;
		Parameter par;
		jj_consume_token(LPAREN);
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case ABSTRACT:
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FINAL:
			case FLOAT:
			case INT:
			case LONG:
			case NATIVE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case SHORT:
			case STATIC:
			case STRICTFP:
			case SYNCHRONIZED:
			case TRANSIENT:
			case VOLATILE:
			case IDENTIFIER:
			case AT:
					par = FormalParameter();
					ret = add(ret, par);
					label_23: while (true) {
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case COMMA:
								break;
							default:
									jj_la1[47] = jj_gen;
									break label_23;
						}
						jj_consume_token(COMMA);
						par = FormalParameter();
						ret = add(ret, par);
					}
					break;
			default:
					jj_la1[48] = jj_gen;
		}
		jj_consume_token(RPAREN);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List FormalLambdaParameters() throws ParseException {
		List ret = null;
		Parameter par;
		jj_consume_token(COMMA);
		par = FormalParameter();
		ret = add(ret, par);
		label_24: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[49] = jj_gen;
						break label_24;
			}
			jj_consume_token(COMMA);
			par = FormalParameter();
			ret = add(ret, par);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List InferredLambdaParameters() throws ParseException {
		List ret = null;
		VariableDeclaratorId id;
		jj_consume_token(COMMA);
		id = VariableDeclaratorId();
		ret = add(ret, new Parameter(id.getBeginLine(), id.getBeginColumn(), id.getEndLine(), id.getEndColumn(), 0, null, null, false, id));
		label_25: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[50] = jj_gen;
						break label_25;
			}
			jj_consume_token(COMMA);
			id = VariableDeclaratorId();
			ret = add(ret, new Parameter(id.getBeginLine(), id.getBeginColumn(), id.getEndLine(), id.getEndColumn(), 0, null, null, false, id));
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Parameter FormalParameter() throws ParseException {
		Modifier modifier;
		Type type;
		boolean isVarArg = false;
		VariableDeclaratorId id;
		modifier = Modifiers();
		type = Type();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case ELLIPSIS:
					jj_consume_token(ELLIPSIS);
					isVarArg = true;
					break;
			default:
					jj_la1[51] = jj_gen;
		}
		id = VariableDeclaratorId();
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		if (line == -1) {
			line = type.getBeginLine();
			column = type.getBeginColumn();
		}
		{
			if (true) return new Parameter(line, column, token.endLine, token.endColumn, modifier.modifiers, modifier.annotations, type, isVarArg, id);
		}
		throw new Error("Missing return statement in function");
	}

	public final ConstructorDeclaration ConstructorDeclaration(Modifier modifier) throws ParseException {
		List typeParameters = null;
		String name;
		List parameters;
		List throws_ = null;
		ExplicitConstructorInvocationStmt exConsInv = null;
		List stmts;
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		int bbLine = 0;
		int bbColumn = 0;
		int beLine = 0;
		int beColumn = 0;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LT:
					typeParameters = TypeParameters();
					int[] lineCol = (int[]) typeParameters.remove(0);
					if (line == -1) {
						line = lineCol[0];
						column = lineCol[1];
					}
					break;
			default:
					jj_la1[52] = jj_gen;
		}
		jj_consume_token(IDENTIFIER);
		name = token.image;
		if (line == -1) {
			line = token.beginLine;
			column = token.beginColumn;
		}
		parameters = FormalParameters();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case THROWS:
					jj_consume_token(THROWS);
					throws_ = ClassOrInterfaceTypeList();
					break;
			default:
					jj_la1[53] = jj_gen;
		}
		jj_consume_token(LBRACE);
		bbLine = token.beginLine;
		bbColumn = token.beginColumn;
		if (jj_2_8(2147483647)) {
			exConsInv = ExplicitConstructorInvocation();
		} else {
		}
		stmts = Statements();
		jj_consume_token(RBRACE);
		if (exConsInv != null) {
			stmts = add(0, stmts, exConsInv);
		}
		{
			if (true) return new ConstructorDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), modifier.modifiers, modifier.annotations, typeParameters, name, parameters, throws_, new BlockStmt(bbLine, bbColumn, token.endLine, token.endColumn, stmts));
		}
		throw new Error("Missing return statement in function");
	}

	public final ExplicitConstructorInvocationStmt ExplicitConstructorInvocation() throws ParseException {
		boolean isThis = false;
		List args;
		Expression expr = null;
		List typeArgs = null;
		int line = -1;
		int column = 0;
		if (jj_2_10(2147483647)) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LT:
				case 132:
						typeArgs = TypeArguments();
						int[] lineCol = (int[]) typeArgs.remove(0);
						line = lineCol[0];
						column = lineCol[1];
						break;
				default:
						jj_la1[54] = jj_gen;
			}
			jj_consume_token(THIS);
			if (line == -1) {
				line = token.beginLine;
				column = token.beginColumn;
			}
			isThis = true;
			args = Arguments();
			jj_consume_token(SEMICOLON);
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FALSE:
				case FLOAT:
				case INT:
				case LONG:
				case NEW:
				case NULL:
				case SHORT:
				case SUPER:
				case THIS:
				case TRUE:
				case VOID:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case LT:
				case 132:
						if (jj_2_9(2147483647)) {
							expr = PrimaryExpressionWithoutSuperSuffix();
							jj_consume_token(DOT);
							line = expr.getBeginLine();
							column = expr.getBeginColumn();
						} else {
						}
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case LT:
							case 132:
									typeArgs = TypeArguments();
									int[] lineCol = (int[]) typeArgs.remove(0);
									if (line == -1) {
										line = lineCol[0];
										column = lineCol[1];
									}
									break;
							default:
									jj_la1[55] = jj_gen;
						}
						jj_consume_token(SUPER);
						if (line == -1) {
							line = token.beginLine;
							column = token.beginColumn;
						}
						args = Arguments();
						jj_consume_token(SEMICOLON);
						break;
				default:
						jj_la1[56] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return new ExplicitConstructorInvocationStmt(line, column, token.endLine, token.endColumn, typeArgs, isThis, expr, args);
		}
		throw new Error("Missing return statement in function");
	}

	public final List Statements() throws ParseException {
		List ret = null;
		Statement stmt;
		label_26: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ABSTRACT:
				case ASSERT:
				case BOOLEAN:
				case BREAK:
				case BYTE:
				case CHAR:
				case CLASS:
				case CONTINUE:
				case DO:
				case DOUBLE:
				case FALSE:
				case FINAL:
				case FLOAT:
				case FOR:
				case IF:
				case INT:
				case INTERFACE:
				case LONG:
				case NATIVE:
				case NEW:
				case NULL:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case RETURN:
				case SHORT:
				case STATIC:
				case STRICTFP:
				case SUPER:
				case SWITCH:
				case SYNCHRONIZED:
				case THIS:
				case THROW:
				case TRANSIENT:
				case TRUE:
				case TRY:
				case VOID:
				case VOLATILE:
				case WHILE:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case LBRACE:
				case SEMICOLON:
				case AT:
				case INCR:
				case DECR:
					break;
				default:
						jj_la1[57] = jj_gen;
						break label_26;
			}
			stmt = BlockStatement();
			ret = add(ret, stmt);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final InitializerDeclaration InitializerDeclaration() throws ParseException {
		BlockStmt block;
		int line = -1;
		int column = 0;
		boolean isStatic = false;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case STATIC:
					jj_consume_token(STATIC);
					isStatic = true;
					line = token.beginLine;
					column = token.beginColumn;
					break;
			default:
					jj_la1[58] = jj_gen;
		}
		block = Block();
		if (line == -1) {
			line = block.getBeginLine();
			column = block.getBeginColumn();
		}
		{
			if (true) return new InitializerDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), isStatic, block);
		}
		throw new Error("Missing return statement in function");
	}

	public final Type Type() throws ParseException {
		Type ret;
		if (jj_2_11(2)) {
			ret = ReferenceType();
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FLOAT:
				case INT:
				case LONG:
				case SHORT:
						ret = PrimitiveType();
						break;
				default:
						jj_la1[59] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final ReferenceType ReferenceType() throws ParseException {
		Type type;
		int arrayCount = 0;
		List annotations = null;
		List accum = null;
		AnnotationExpr ann;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
					type = PrimitiveType();
					label_27: while (true) {
						label_28: while (true) {
							switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
								case AT:
									break;
								default:
										jj_la1[60] = jj_gen;
										break label_28;
							}
							ann = Annotation();
							annotations = add(annotations, ann);
						}
						jj_consume_token(LBRACKET);
						jj_consume_token(RBRACKET);
						arrayCount++;
						accum = add(accum, annotations);
						annotations = null;
						if (jj_2_12(2)) {
						} else {
							break label_27;
						}
					}
					break;
			case IDENTIFIER:
					type = ClassOrInterfaceType();
					label_29: while (true) {
						if (jj_2_13(2)) {
						} else {
							break label_29;
						}
						label_30: while (true) {
							switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
								case AT:
									break;
								default:
										jj_la1[61] = jj_gen;
										break label_30;
							}
							ann = Annotation();
							annotations = add(annotations, ann);
						}
						jj_consume_token(LBRACKET);
						jj_consume_token(RBRACKET);
						arrayCount++;
						accum = add(accum, annotations);
						annotations = null;
					}
					break;
			default:
					jj_la1[62] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return new ReferenceType(type.getBeginLine(), type.getBeginColumn(), token.endLine, token.endColumn, type, arrayCount, null, accum);
		}
		throw new Error("Missing return statement in function");
	}

	public final ClassOrInterfaceType ClassOrInterfaceType() throws ParseException {
		ClassOrInterfaceType ret;
		String name;
		List typeArgs = null;
		int line;
		int column;
		List annotations = null;
		AnnotationExpr ann;
		jj_consume_token(IDENTIFIER);
		line = token.beginLine;
		column = token.beginColumn;
		name = token.image;
		if (jj_2_14(2)) {
			typeArgs = TypeArguments();
			typeArgs.remove(0);
		} else {
		}
		ret = new ClassOrInterfaceType(line, column, token.endLine, token.endColumn, null, name, typeArgs);
		typeArgs = null;
		label_31: while (true) {
			if (jj_2_15(2)) {
			} else {
				break label_31;
			}
			jj_consume_token(DOT);
			label_32: while (true) {
				switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case AT:
						break;
					default:
							jj_la1[63] = jj_gen;
							break label_32;
				}
				ann = Annotation();
				annotations = add(annotations, ann);
			}
			jj_consume_token(IDENTIFIER);
			name = token.image;
			if (jj_2_16(2)) {
				typeArgs = TypeArguments();
				typeArgs.remove(0);
			} else {
			}
			ret = new ClassOrInterfaceType(line, column, token.endLine, token.endColumn, ret, name, typeArgs);
			ret.setAnnotations(annotations);
			annotations = null;
			typeArgs = null;
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List TypeArguments() throws ParseException {
		List ret = new LinkedList();
		Type type;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LT:
					jj_consume_token(LT);
					ret.add(new int[] { token.beginLine, token.beginColumn });
					type = TypeArgument();
					ret.add(type);
					label_33: while (true) {
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case COMMA:
								break;
							default:
									jj_la1[64] = jj_gen;
									break label_33;
						}
						jj_consume_token(COMMA);
						type = TypeArgument();
						ret.add(type);
					}
					jj_consume_token(GT);
					{
						if (true) return ret;
					}
					break;
			case 132:
					jj_consume_token(132);
					ret.add(null);
					{
						if (true) return ret;
					}
					break;
			default:
					jj_la1[65] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		throw new Error("Missing return statement in function");
	}

	public final Type TypeArgument() throws ParseException {
		Type ret;
		List annotations = null;
		AnnotationExpr ann;
		label_34: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[66] = jj_gen;
						break label_34;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
			case IDENTIFIER:
					ret = ReferenceType();
					break;
			case HOOK:
					ret = Wildcard();
					break;
			default:
					jj_la1[67] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		ret.setAnnotations(annotations);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final WildcardType Wildcard() throws ParseException {
		ReferenceType ext = null;
		ReferenceType sup = null;
		int line;
		int column;
		AnnotationExpr ann;
		List annotations = null;
		jj_consume_token(HOOK);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case EXTENDS:
			case SUPER:
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case EXTENDS:
								jj_consume_token(EXTENDS);
								label_35: while (true) {
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case AT:
											break;
										default:
												jj_la1[68] = jj_gen;
												break label_35;
									}
									ann = Annotation();
									annotations = add(annotations, ann);
								}
								ext = ReferenceType();
								ext.setAnnotations(annotations);
								break;
						case SUPER:
								jj_consume_token(SUPER);
								label_36: while (true) {
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case AT:
											break;
										default:
												jj_la1[69] = jj_gen;
												break label_36;
									}
									ann = Annotation();
									annotations = add(annotations, ann);
								}
								sup = ReferenceType();
								sup.setAnnotations(annotations);
								break;
						default:
								jj_la1[70] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
					}
					break;
			default:
					jj_la1[71] = jj_gen;
		}
		{
			if (true) return new WildcardType(line, column, token.endLine, token.endColumn, ext, sup);
		}
		throw new Error("Missing return statement in function");
	}

	public final PrimitiveType PrimitiveType() throws ParseException {
		PrimitiveType ret;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
					jj_consume_token(BOOLEAN);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Boolean);
					break;
			case CHAR:
					jj_consume_token(CHAR);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Char);
					break;
			case BYTE:
					jj_consume_token(BYTE);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Byte);
					break;
			case SHORT:
					jj_consume_token(SHORT);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Short);
					break;
			case INT:
					jj_consume_token(INT);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Int);
					break;
			case LONG:
					jj_consume_token(LONG);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Long);
					break;
			case FLOAT:
					jj_consume_token(FLOAT);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Float);
					break;
			case DOUBLE:
					jj_consume_token(DOUBLE);
					ret = new PrimitiveType(token.beginLine, token.beginColumn, token.endLine, token.endColumn, PrimitiveType.Primitive.Double);
					break;
			default:
					jj_la1[72] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Type ResultType() throws ParseException {
		Type ret;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case VOID:
					jj_consume_token(VOID);
					ret = new VoidType(token.beginLine, token.beginColumn, token.endLine, token.endColumn);
					break;
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
			case IDENTIFIER:
					ret = Type();
					break;
			default:
					jj_la1[73] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final NameExpr Name() throws ParseException {
		NameExpr ret;
		jj_consume_token(IDENTIFIER);
		ret = new NameExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, token.image);
		label_37: while (true) {
			if (jj_2_17(2)) {
			} else {
				break label_37;
			}
			jj_consume_token(DOT);
			jj_consume_token(IDENTIFIER);
			ret = new QualifiedNameExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, token.image);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List ClassOrInterfaceTypeList() throws ParseException {
		List ret = new LinkedList();
		ClassOrInterfaceType type;
		List annotations = null;
		AnnotationExpr ann;
		label_38: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[74] = jj_gen;
						break label_38;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		type = ClassOrInterfaceType();
		type.setAnnotations(annotations);
		ret.add(type);
		annotations = null;
		label_39: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[75] = jj_gen;
						break label_39;
			}
			jj_consume_token(COMMA);
			label_40: while (true) {
				switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case AT:
						break;
					default:
							jj_la1[76] = jj_gen;
							break label_40;
				}
				ann = Annotation();
				annotations = add(annotations, ann);
			}
			type = ClassOrInterfaceType();
			type.setAnnotations(annotations);
			ret.add(type);
			annotations = null;
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression Expression() throws ParseException {
		Expression ret;
		AssignExpr.Operator op;
		Expression value;
		Statement lambdaBody = null;
		List params = null;
		List typeArgs = null;
		VariableDeclaratorId id = null;
		Expression inner = null;
		ret = ConditionalExpression();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case ASSIGN:
			case PLUSASSIGN:
			case MINUSASSIGN:
			case STARASSIGN:
			case SLASHASSIGN:
			case ANDASSIGN:
			case ORASSIGN:
			case XORASSIGN:
			case REMASSIGN:
			case LSHIFTASSIGN:
			case RSIGNEDSHIFTASSIGN:
			case RUNSIGNEDSHIFTASSIGN:
			case ARROW:
			case DOUBLECOLON:
					if (jj_2_18(2)) {
						op = AssignmentOperator();
						value = Expression();
						ret = new AssignExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, value, op);
					} else {
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case ARROW:
									jj_consume_token(ARROW);
									lambdaBody = LambdaBody();
									if (ret instanceof CastExpr) {
										inner = generateLambda(((CastExpr) ret).getExpr(), lambdaBody);
										((CastExpr) ret).setExpr(inner);
									} else if (ret instanceof ConditionalExpr) {
										ConditionalExpr ce = (ConditionalExpr) ret;
										if (ce.getElseExpr() != null) {
											ce.setElseExpr(generateLambda(ce.getElseExpr(), lambdaBody));
										}
									} else {
										ret = generateLambda(ret, lambdaBody);
									}
									break;
							case DOUBLECOLON:
									jj_consume_token(DOUBLECOLON);
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case LT:
												typeArgs = TypeParameters();
												typeArgs.remove(0);
												break;
										default:
												jj_la1[77] = jj_gen;
									}
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case IDENTIFIER:
												jj_consume_token(IDENTIFIER);
												break;
										case NEW:
												jj_consume_token(NEW);
												break;
										default:
												jj_la1[78] = jj_gen;
												jj_consume_token(-1);
												throw new ParseException();
									}
									ret = new MethodReferenceExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, typeArgs, token.image);
									break;
							default:
									jj_la1[79] = jj_gen;
									jj_consume_token(-1);
									throw new ParseException();
						}
					}
					break;
			default:
					jj_la1[80] = jj_gen;
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final AssignExpr.Operator AssignmentOperator() throws ParseException {
		AssignExpr.Operator ret;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case ASSIGN:
					jj_consume_token(ASSIGN);
					ret = AssignExpr.Operator.assign;
					break;
			case STARASSIGN:
					jj_consume_token(STARASSIGN);
					ret = AssignExpr.Operator.star;
					break;
			case SLASHASSIGN:
					jj_consume_token(SLASHASSIGN);
					ret = AssignExpr.Operator.slash;
					break;
			case REMASSIGN:
					jj_consume_token(REMASSIGN);
					ret = AssignExpr.Operator.rem;
					break;
			case PLUSASSIGN:
					jj_consume_token(PLUSASSIGN);
					ret = AssignExpr.Operator.plus;
					break;
			case MINUSASSIGN:
					jj_consume_token(MINUSASSIGN);
					ret = AssignExpr.Operator.minus;
					break;
			case LSHIFTASSIGN:
					jj_consume_token(LSHIFTASSIGN);
					ret = AssignExpr.Operator.lShift;
					break;
			case RSIGNEDSHIFTASSIGN:
					jj_consume_token(RSIGNEDSHIFTASSIGN);
					ret = AssignExpr.Operator.rSignedShift;
					break;
			case RUNSIGNEDSHIFTASSIGN:
					jj_consume_token(RUNSIGNEDSHIFTASSIGN);
					ret = AssignExpr.Operator.rUnsignedShift;
					break;
			case ANDASSIGN:
					jj_consume_token(ANDASSIGN);
					ret = AssignExpr.Operator.and;
					break;
			case XORASSIGN:
					jj_consume_token(XORASSIGN);
					ret = AssignExpr.Operator.xor;
					break;
			case ORASSIGN:
					jj_consume_token(ORASSIGN);
					ret = AssignExpr.Operator.or;
					break;
			default:
					jj_la1[81] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression ConditionalExpression() throws ParseException {
		Expression ret;
		Expression left;
		Expression right;
		ret = ConditionalOrExpression();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case HOOK:
					jj_consume_token(HOOK);
					left = Expression();
					jj_consume_token(COLON);
					right = ConditionalExpression();
					ret = new ConditionalExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, left, right);
					break;
			default:
					jj_la1[82] = jj_gen;
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression ConditionalOrExpression() throws ParseException {
		Expression ret;
		Expression right;
		ret = ConditionalAndExpression();
		label_41: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case SC_OR:
					break;
				default:
						jj_la1[83] = jj_gen;
						break label_41;
			}
			jj_consume_token(SC_OR);
			right = ConditionalAndExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, BinaryExpr.Operator.or);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression ConditionalAndExpression() throws ParseException {
		Expression ret;
		Expression right;
		ret = InclusiveOrExpression();
		label_42: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case SC_AND:
					break;
				default:
						jj_la1[84] = jj_gen;
						break label_42;
			}
			jj_consume_token(SC_AND);
			right = InclusiveOrExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, BinaryExpr.Operator.and);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression InclusiveOrExpression() throws ParseException {
		Expression ret;
		Expression right;
		ret = ExclusiveOrExpression();
		label_43: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case BIT_OR:
					break;
				default:
						jj_la1[85] = jj_gen;
						break label_43;
			}
			jj_consume_token(BIT_OR);
			right = ExclusiveOrExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, BinaryExpr.Operator.binOr);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression ExclusiveOrExpression() throws ParseException {
		Expression ret;
		Expression right;
		ret = AndExpression();
		label_44: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case XOR:
					break;
				default:
						jj_la1[86] = jj_gen;
						break label_44;
			}
			jj_consume_token(XOR);
			right = AndExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, BinaryExpr.Operator.xor);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression AndExpression() throws ParseException {
		Expression ret;
		Expression right;
		ret = EqualityExpression();
		label_45: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case BIT_AND:
					break;
				default:
						jj_la1[87] = jj_gen;
						break label_45;
			}
			jj_consume_token(BIT_AND);
			right = EqualityExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, BinaryExpr.Operator.binAnd);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression EqualityExpression() throws ParseException {
		Expression ret;
		Expression right;
		BinaryExpr.Operator op;
		ret = InstanceOfExpression();
		label_46: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case EQ:
				case NE:
					break;
				default:
						jj_la1[88] = jj_gen;
						break label_46;
			}
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case EQ:
						jj_consume_token(EQ);
						op = BinaryExpr.Operator.equals;
						break;
				case NE:
						jj_consume_token(NE);
						op = BinaryExpr.Operator.notEquals;
						break;
				default:
						jj_la1[89] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
			right = InstanceOfExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, op);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression InstanceOfExpression() throws ParseException {
		Expression ret;
		Type type;
		ret = RelationalExpression();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case INSTANCEOF:
					jj_consume_token(INSTANCEOF);
					type = Type();
					ret = new InstanceOfExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, type);
					break;
			default:
					jj_la1[90] = jj_gen;
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression RelationalExpression() throws ParseException {
		Expression ret;
		Expression right;
		BinaryExpr.Operator op;
		ret = ShiftExpression();
		label_47: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LT:
				case LE:
				case GE:
				case GT:
					break;
				default:
						jj_la1[91] = jj_gen;
						break label_47;
			}
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LT:
						jj_consume_token(LT);
						op = BinaryExpr.Operator.less;
						break;
				case GT:
						jj_consume_token(GT);
						op = BinaryExpr.Operator.greater;
						break;
				case LE:
						jj_consume_token(LE);
						op = BinaryExpr.Operator.lessEquals;
						break;
				case GE:
						jj_consume_token(GE);
						op = BinaryExpr.Operator.greaterEquals;
						break;
				default:
						jj_la1[92] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
			right = ShiftExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, op);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression ShiftExpression() throws ParseException {
		Expression ret;
		Expression right;
		BinaryExpr.Operator op;
		ret = AdditiveExpression();
		label_48: while (true) {
			if (jj_2_19(1)) {
			} else {
				break label_48;
			}
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LSHIFT:
						jj_consume_token(LSHIFT);
						op = BinaryExpr.Operator.lShift;
						break;
				default:
						jj_la1[93] = jj_gen;
						if (jj_2_20(1)) {
							RSIGNEDSHIFT();
							op = BinaryExpr.Operator.rSignedShift;
						} else if (jj_2_21(1)) {
							RUNSIGNEDSHIFT();
							op = BinaryExpr.Operator.rUnsignedShift;
						} else {
							jj_consume_token(-1);
							throw new ParseException();
						}
			}
			right = AdditiveExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, op);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression AdditiveExpression() throws ParseException {
		Expression ret;
		Expression right;
		BinaryExpr.Operator op;
		ret = MultiplicativeExpression();
		label_49: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case PLUS:
				case MINUS:
					break;
				default:
						jj_la1[94] = jj_gen;
						break label_49;
			}
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case PLUS:
						jj_consume_token(PLUS);
						op = BinaryExpr.Operator.plus;
						break;
				case MINUS:
						jj_consume_token(MINUS);
						op = BinaryExpr.Operator.minus;
						break;
				default:
						jj_la1[95] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
			right = MultiplicativeExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, op);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression MultiplicativeExpression() throws ParseException {
		Expression ret;
		Expression right;
		BinaryExpr.Operator op;
		ret = UnaryExpression();
		label_50: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case STAR:
				case SLASH:
				case REM:
					break;
				default:
						jj_la1[96] = jj_gen;
						break label_50;
			}
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case STAR:
						jj_consume_token(STAR);
						op = BinaryExpr.Operator.times;
						break;
				case SLASH:
						jj_consume_token(SLASH);
						op = BinaryExpr.Operator.divide;
						break;
				case REM:
						jj_consume_token(REM);
						op = BinaryExpr.Operator.remainder;
						break;
				default:
						jj_la1[97] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
			right = UnaryExpression();
			ret = new BinaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, right, op);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression UnaryExpression() throws ParseException {
		Expression ret;
		UnaryExpr.Operator op;
		int line = 0;
		int column = 0;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case INCR:
					ret = PreIncrementExpression();
					break;
			case DECR:
					ret = PreDecrementExpression();
					break;
			case PLUS:
			case MINUS:
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case PLUS:
								jj_consume_token(PLUS);
								op = UnaryExpr.Operator.positive;
								line = token.beginLine;
								column = token.beginColumn;
								break;
						case MINUS:
								jj_consume_token(MINUS);
								op = UnaryExpr.Operator.negative;
								line = token.beginLine;
								column = token.beginColumn;
								break;
						default:
								jj_la1[98] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
					}
					ret = UnaryExpression();
					if (op == UnaryExpr.Operator.negative) {
						if (ret instanceof IntegerLiteralExpr && ((IntegerLiteralExpr) ret).isMinValue()) {
							ret = new IntegerLiteralMinValueExpr(line, column, token.endLine, token.endColumn);
						} else if (ret instanceof LongLiteralExpr && ((LongLiteralExpr) ret).isMinValue()) {
							ret = new LongLiteralMinValueExpr(line, column, token.endLine, token.endColumn);
						} else {
							ret = new UnaryExpr(line, column, token.endLine, token.endColumn, ret, op);
						}
					} else {
						ret = new UnaryExpr(line, column, token.endLine, token.endColumn, ret, op);
					}
					break;
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case BANG:
			case TILDE:
					ret = UnaryExpressionNotPlusMinus();
					break;
			default:
					jj_la1[99] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PreIncrementExpression() throws ParseException {
		Expression ret;
		int line;
		int column;
		jj_consume_token(INCR);
		line = token.beginLine;
		column = token.beginColumn;
		ret = UnaryExpression();
		ret = new UnaryExpr(line, column, token.endLine, token.endColumn, ret, UnaryExpr.Operator.preIncrement);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PreDecrementExpression() throws ParseException {
		Expression ret;
		int line;
		int column;
		jj_consume_token(DECR);
		line = token.beginLine;
		column = token.beginColumn;
		ret = UnaryExpression();
		ret = new UnaryExpr(line, column, token.endLine, token.endColumn, ret, UnaryExpr.Operator.preDecrement);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression UnaryExpressionNotPlusMinus() throws ParseException {
		Expression ret;
		UnaryExpr.Operator op;
		int line = 0;
		int column = 0;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BANG:
			case TILDE:
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case TILDE:
								jj_consume_token(TILDE);
								op = UnaryExpr.Operator.inverse;
								line = token.beginLine;
								column = token.beginColumn;
								break;
						case BANG:
								jj_consume_token(BANG);
								op = UnaryExpr.Operator.not;
								line = token.beginLine;
								column = token.beginColumn;
								break;
						default:
								jj_la1[100] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
					}
					ret = UnaryExpression();
					ret = new UnaryExpr(line, column, token.endLine, token.endColumn, ret, op);
					break;
			default:
					jj_la1[101] = jj_gen;
					if (jj_2_22(2147483647)) {
						ret = CastExpression();
					} else {
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case BOOLEAN:
							case BYTE:
							case CHAR:
							case DOUBLE:
							case FALSE:
							case FLOAT:
							case INT:
							case LONG:
							case NEW:
							case NULL:
							case SHORT:
							case SUPER:
							case THIS:
							case TRUE:
							case VOID:
							case LONG_LITERAL:
							case INTEGER_LITERAL:
							case FLOATING_POINT_LITERAL:
							case CHARACTER_LITERAL:
							case STRING_LITERAL:
							case IDENTIFIER:
							case LPAREN:
									ret = PostfixExpression();
									break;
							default:
									jj_la1[102] = jj_gen;
									jj_consume_token(-1);
									throw new ParseException();
						}
					}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PostfixExpression() throws ParseException {
		Expression ret;
		UnaryExpr.Operator op;
		ret = PrimaryExpression();
		if (jj_2_23(2)) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case INCR:
						jj_consume_token(INCR);
						op = UnaryExpr.Operator.posIncrement;
						break;
				case DECR:
						jj_consume_token(DECR);
						op = UnaryExpr.Operator.posDecrement;
						break;
				default:
						jj_la1[103] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
			ret = new UnaryExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, op);
		} else {
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression CastExpression() throws ParseException {
		Expression ret;
		Type type;
		int line;
		int column;
		Type st = null;
		AnnotationExpr ann;
		List annotations = null;
		List secondaryTypes = null;
		jj_consume_token(LPAREN);
		line = token.beginLine;
		column = token.beginColumn;
		label_51: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[104] = jj_gen;
						break label_51;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		if (jj_2_24(2)) {
			type = PrimitiveType();
			jj_consume_token(RPAREN);
			ret = UnaryExpression();
			type.setAnnotations(annotations);
			ret = new CastExpr(line, column, token.endLine, token.endColumn, type, ret);
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FLOAT:
				case INT:
				case LONG:
				case SHORT:
				case IDENTIFIER:
						type = ReferenceType();
						secondaryTypes = add(secondaryTypes, type);
						label_52: while (true) {
							switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
								case BIT_AND:
									break;
								default:
										jj_la1[105] = jj_gen;
										break label_52;
							}
							jj_consume_token(BIT_AND);
							st = ReferenceType();
							secondaryTypes = add(secondaryTypes, st);
						}
						jj_consume_token(RPAREN);
						ret = UnaryExpressionNotPlusMinus();
						if (st != null) {
							type = new IntersectionType(type.getBeginLine(), type.getBeginColumn(), token.endLine, token.endColumn, secondaryTypes);
						}
						type.setAnnotations(annotations);
						ret = new CastExpr(line, column, token.endLine, token.endColumn, type, ret);
						break;
				default:
						jj_la1[106] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PrimaryExpression() throws ParseException {
		Expression ret;
		Expression inner;
		ret = PrimaryPrefix();
		label_53: while (true) {
			if (jj_2_25(2)) {
			} else {
				break label_53;
			}
			ret = PrimarySuffix(ret);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PrimaryExpressionWithoutSuperSuffix() throws ParseException {
		Expression ret;
		Expression inner;
		ret = PrimaryPrefix();
		label_54: while (true) {
			if (jj_2_26(2147483647)) {
			} else {
				break label_54;
			}
			ret = PrimarySuffixWithoutSuper(ret);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PrimaryPrefix() throws ParseException {
		Expression ret = null;
		String name;
		List typeArgs = null;
		List args = null;
		boolean hasArgs = false;
		boolean isLambda = false;
		Type type;
		int line;
		int column;
		Parameter p = null;
		VariableDeclaratorId id = null;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case FALSE:
			case NULL:
			case TRUE:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
					ret = Literal();
					break;
			case THIS:
					jj_consume_token(THIS);
					ret = new ThisExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, null);
					break;
			case SUPER:
					jj_consume_token(SUPER);
					ret = new SuperExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, null);
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case DOT:
								jj_consume_token(DOT);
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case LT:
									case 132:
											typeArgs = TypeArguments();
											typeArgs.remove(0);
											break;
									default:
											jj_la1[107] = jj_gen;
								}
								jj_consume_token(IDENTIFIER);
								name = token.image;
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case LPAREN:
											args = Arguments();
											hasArgs = true;
											break;
									default:
											jj_la1[108] = jj_gen;
								}
								ret = hasArgs
								 ? new MethodCallExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, typeArgs, name, args)
								 : new FieldAccessExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, null, name);
								break;
						case DOUBLECOLON:
								jj_consume_token(DOUBLECOLON);
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case LT:
											typeArgs = TypeParameters();
											typeArgs.remove(0);
											break;
									default:
											jj_la1[109] = jj_gen;
								}
								switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
									case IDENTIFIER:
											jj_consume_token(IDENTIFIER);
											break;
									case NEW:
											jj_consume_token(NEW);
											break;
									default:
											jj_la1[110] = jj_gen;
											jj_consume_token(-1);
											throw new ParseException();
								}
								ret = new MethodReferenceExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, typeArgs, token.image);
								break;
						case LPAREN:
								args = Arguments();
								new MethodCallExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, typeArgs, null, args);
								break;
						default:
								jj_la1[111] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
					}
					break;
			case LPAREN:
					jj_consume_token(LPAREN);
					line = token.beginLine;
					column = token.beginColumn;
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case ABSTRACT:
						case BOOLEAN:
						case BYTE:
						case CHAR:
						case DOUBLE:
						case FALSE:
						case FINAL:
						case FLOAT:
						case INT:
						case LONG:
						case NATIVE:
						case NEW:
						case NULL:
						case PRIVATE:
						case PROTECTED:
						case PUBLIC:
						case SHORT:
						case STATIC:
						case STRICTFP:
						case SUPER:
						case SYNCHRONIZED:
						case THIS:
						case TRANSIENT:
						case TRUE:
						case VOID:
						case VOLATILE:
						case LONG_LITERAL:
						case INTEGER_LITERAL:
						case FLOATING_POINT_LITERAL:
						case CHARACTER_LITERAL:
						case STRING_LITERAL:
						case IDENTIFIER:
						case LPAREN:
						case AT:
						case BANG:
						case TILDE:
						case INCR:
						case DECR:
						case PLUS:
						case MINUS:
								if (jj_2_27(2147483647)) {
									p = FormalParameter();
									isLambda = true;
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case COMMA:
												args = FormalLambdaParameters();
												break;
										default:
												jj_la1[112] = jj_gen;
									}
								} else {
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case BOOLEAN:
										case BYTE:
										case CHAR:
										case DOUBLE:
										case FALSE:
										case FLOAT:
										case INT:
										case LONG:
										case NEW:
										case NULL:
										case SHORT:
										case SUPER:
										case THIS:
										case TRUE:
										case VOID:
										case LONG_LITERAL:
										case INTEGER_LITERAL:
										case FLOATING_POINT_LITERAL:
										case CHARACTER_LITERAL:
										case STRING_LITERAL:
										case IDENTIFIER:
										case LPAREN:
										case BANG:
										case TILDE:
										case INCR:
										case DECR:
										case PLUS:
										case MINUS:
												ret = Expression();
												switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
													case COMMA:
															args = InferredLambdaParameters();
															isLambda = true;
															break;
													default:
															jj_la1[113] = jj_gen;
												}
												break;
										default:
												jj_la1[114] = jj_gen;
												jj_consume_token(-1);
												throw new ParseException();
									}
								}
								break;
						default:
								jj_la1[115] = jj_gen;
					}
					jj_consume_token(RPAREN);
					if (!isLambda) {
						ret = new EnclosedExpr(line, column, token.endLine, token.endColumn, ret);
					} else {
						if (ret != null) {
							if (ret instanceof NameExpr) {
								id = new VariableDeclaratorId(ret.getBeginLine(), ret.getBeginColumn(), ret.getEndLine(), ret.getEndColumn(), ((NameExpr) ret).getName(), 0);
								p = new Parameter(ret.getBeginLine(), ret.getBeginColumn(), ret.getEndLine(), ret.getEndColumn(), 0, null, null, false, id);
							}

						}
						args = add(0, args, p);
						ret = new LambdaExpr(p.getBeginLine(), p.getBeginColumn(), token.endLine, token.endColumn, args, null, true);
					}
					break;
			case NEW:
					ret = AllocationExpression(null);
					break;
			default:
					jj_la1[119] = jj_gen;
					if (jj_2_28(2147483647)) {
						type = ResultType();
						jj_consume_token(DOT);
						jj_consume_token(CLASS);
						ret = new ClassExpr(type.getBeginLine(), type.getBeginColumn(), token.endLine, token.endColumn, type);
					} else if (jj_2_29(2147483647)) {
						type = ResultType();
						jj_consume_token(DOUBLECOLON);
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case LT:
									typeArgs = TypeParameters();
									typeArgs.remove(0);
									break;
							default:
									jj_la1[116] = jj_gen;
						}
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case IDENTIFIER:
									jj_consume_token(IDENTIFIER);
									break;
							case NEW:
									jj_consume_token(NEW);
									break;
							default:
									jj_la1[117] = jj_gen;
									jj_consume_token(-1);
									throw new ParseException();
						}
						ret = new TypeExpr(type.getBeginLine(), type.getBeginColumn(), type.getEndLine(), type.getEndColumn(), type);
						ret = new MethodReferenceExpr(ret.getBeginLine(), ret.getBeginColumn(), token.endLine, token.endColumn, ret, typeArgs, token.image);
					} else {
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case IDENTIFIER:
									jj_consume_token(IDENTIFIER);
									name = token.image;
									line = token.beginLine;
									column = token.beginColumn;
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case LPAREN:
												args = Arguments();
												ret = new MethodCallExpr(line, column, token.endLine, token.endColumn, null, null, name, args);
												break;
										default:
												jj_la1[118] = jj_gen;
									}
									if (ret == null) {
										ret = new NameExpr(line, column, token.endLine, token.endColumn, name);
									}
									break;
							default:
									jj_la1[120] = jj_gen;
									jj_consume_token(-1);
									throw new ParseException();
						}
					}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PrimarySuffix(Expression scope) throws ParseException {
		Expression ret;
		if (jj_2_30(2)) {
			ret = PrimarySuffixWithoutSuper(scope);
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case DOT:
						jj_consume_token(DOT);
						jj_consume_token(SUPER);
						ret = new SuperExpr(scope.getBeginLine(), scope.getBeginColumn(), token.endLine, token.endColumn, scope);
						break;
				default:
						jj_la1[121] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression PrimarySuffixWithoutSuper(Expression scope) throws ParseException {
		Expression ret;
		List typeArgs = null;
		List args = null;
		boolean hasArgs = false;
		String name;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case DOT:
					jj_consume_token(DOT);
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case THIS:
								jj_consume_token(THIS);
								ret = new ThisExpr(scope.getBeginLine(), scope.getBeginColumn(), token.endLine, token.endColumn, scope);
								break;
						case NEW:
								ret = AllocationExpression(scope);
								break;
						default:
								jj_la1[124] = jj_gen;
								if (jj_2_31(2147483647)) {
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case LT:
										case 132:
												typeArgs = TypeArguments();
												typeArgs.remove(0);
												break;
										default:
												jj_la1[122] = jj_gen;
									}
									jj_consume_token(IDENTIFIER);
									name = token.image;
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case LPAREN:
												args = Arguments();
												hasArgs = true;
												break;
										default:
												jj_la1[123] = jj_gen;
									}
									ret = hasArgs
									 ? new MethodCallExpr(scope.getBeginLine(), scope.getBeginColumn(), token.endLine, token.endColumn, scope, typeArgs, name, args)
									 : new FieldAccessExpr(scope.getBeginLine(), scope.getBeginColumn(), token.endLine, token.endColumn, scope, typeArgs, name);
								} else {
									jj_consume_token(-1);
									throw new ParseException();
								}
					}
					break;
			case LBRACKET:
					jj_consume_token(LBRACKET);
					ret = Expression();
					jj_consume_token(RBRACKET);
					ret = new ArrayAccessExpr(scope.getBeginLine(), scope.getBeginColumn(), token.endLine, token.endColumn, scope, ret);
					break;
			default:
					jj_la1[125] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression Literal() throws ParseException {
		Expression ret;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case INTEGER_LITERAL:
					jj_consume_token(INTEGER_LITERAL);
					ret = new IntegerLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, token.image);
					break;
			case LONG_LITERAL:
					jj_consume_token(LONG_LITERAL);
					ret = new LongLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, token.image);
					break;
			case FLOATING_POINT_LITERAL:
					jj_consume_token(FLOATING_POINT_LITERAL);
					ret = new DoubleLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, token.image);
					break;
			case CHARACTER_LITERAL:
					jj_consume_token(CHARACTER_LITERAL);
					ret = new CharLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, token.image.substring(1, token.image.length() - 1));
					break;
			case STRING_LITERAL:
					jj_consume_token(STRING_LITERAL);
					ret = new StringLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, token.image.substring(1, token.image.length() - 1));
					break;
			case FALSE:
			case TRUE:
					ret = BooleanLiteral();
					break;
			case NULL:
					ret = NullLiteral();
					break;
			default:
					jj_la1[126] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression BooleanLiteral() throws ParseException {
		Expression ret;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case TRUE:
					jj_consume_token(TRUE);
					ret = new BooleanLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, true);
					break;
			case FALSE:
					jj_consume_token(FALSE);
					ret = new BooleanLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn, false);
					break;
			default:
					jj_la1[127] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression NullLiteral() throws ParseException {
		jj_consume_token(NULL);
		{
			if (true) return new NullLiteralExpr(token.beginLine, token.beginColumn, token.endLine, token.endColumn);
		}
		throw new Error("Missing return statement in function");
	}

	public final List Arguments() throws ParseException {
		List ret = null;
		jj_consume_token(LPAREN);
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case BANG:
			case TILDE:
			case INCR:
			case DECR:
			case PLUS:
			case MINUS:
					ret = ArgumentList();
					break;
			default:
					jj_la1[128] = jj_gen;
		}
		jj_consume_token(RPAREN);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List ArgumentList() throws ParseException {
		List ret = new LinkedList();
		Expression expr;
		expr = Expression();
		ret.add(expr);
		label_55: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[129] = jj_gen;
						break label_55;
			}
			jj_consume_token(COMMA);
			expr = Expression();
			ret.add(expr);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression AllocationExpression(Expression scope) throws ParseException {
		Expression ret;
		ArrayCreationExpr arrayExpr;
		Type type;
		Object[] arr = null;
		List typeArgs = null;
		List anonymousBody = null;
		List args;
		int line;
		int column;
		List annotations = null;
		AnnotationExpr ann;
		jj_consume_token(NEW);
		if (scope == null) {
			line = token.beginLine;
			column = token.beginColumn;
		} else {
			line = scope.getBeginLine();
			column = scope.getBeginColumn();
		}
		label_56: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[130] = jj_gen;
						break label_56;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
					type = PrimitiveType();
					type.setAnnotations(annotations);
					arrayExpr = new ArrayCreationExpr(line, column, token.endLine, token.endColumn, type, null, 0);
					arr = ArrayDimsAndInits();
					arrayExpr.setArraysAnnotations((List) arr[2]);
					if (arr[0] instanceof Integer) {
						arrayExpr.setArrayCount(((Integer) arr[0]).intValue());
						arrayExpr.setInitializer((ArrayInitializerExpr) arr[1]);
					} else {
						arrayExpr.setArrayCount(((Integer) arr[1]).intValue());
						arrayExpr.setDimensions((List) arr[0]);
					}
					ret = arrayExpr;
					break;
			case IDENTIFIER:
			case LT:
			case 132:
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case LT:
						case 132:
								typeArgs = TypeArguments();
								label_57: while (true) {
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case AT:
											break;
										default:
												jj_la1[131] = jj_gen;
												break label_57;
									}
									ann = Annotation();
									annotations = add(annotations, ann);
								}
								typeArgs.remove(0);
								break;
						default:
								jj_la1[132] = jj_gen;
					}
					type = ClassOrInterfaceType();
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case LBRACKET:
						case AT:
								arr = ArrayDimsAndInits();
								type.setAnnotations(annotations);
								arrayExpr = new ArrayCreationExpr(line, column, token.endLine, token.endColumn, type, null, 0);
								arrayExpr.setArraysAnnotations((List) arr[2]);
								if (arr[0] instanceof Integer) {
									arrayExpr.setArrayCount(((Integer) arr[0]).intValue());
									arrayExpr.setInitializer((ArrayInitializerExpr) arr[1]);
								} else {
									arrayExpr.setArrayCount(((Integer) arr[1]).intValue());
									arrayExpr.setDimensions((List) arr[0]);
								}
								ret = arrayExpr;
								break;
						case LPAREN:
								args = Arguments();
								if (jj_2_32(2)) {
									anonymousBody = ClassOrInterfaceBody(false);
								} else {
								}
								type.setAnnotations(annotations);
								ret = new ObjectCreationExpr(line, column, token.endLine, token.endColumn, scope, (ClassOrInterfaceType) type, typeArgs, args, anonymousBody);
								break;
						default:
								jj_la1[133] = jj_gen;
								jj_consume_token(-1);
								throw new ParseException();
					}
					break;
			default:
					jj_la1[134] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Object[] ArrayDimsAndInits() throws ParseException {
		Object[] ret = new Object[3];
		Expression expr;
		List inits = null;
		int i = 0;
		List accum = null;
		List annotations = null;
		AnnotationExpr ann;
		label_58: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
					break;
				default:
						jj_la1[135] = jj_gen;
						break label_58;
			}
			ann = Annotation();
			annotations = add(annotations, ann);
		}
		if (jj_2_35(2)) {
			label_59: while (true) {
				jj_consume_token(LBRACKET);
				expr = Expression();
				accum = add(accum, annotations);
				annotations = null;
				inits = add(inits, expr);
				jj_consume_token(RBRACKET);
				if (jj_2_33(2)) {
				} else {
					break label_59;
				}
			}
			label_60: while (true) {
				if (jj_2_34(2)) {
				} else {
					break label_60;
				}
				label_61: while (true) {
					switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
						case AT:
							break;
						default:
								jj_la1[136] = jj_gen;
								break label_61;
					}
					ann = Annotation();
					annotations = add(annotations, ann);
				}
				jj_consume_token(LBRACKET);
				jj_consume_token(RBRACKET);
				i++;
			}
			accum = add(accum, annotations);
			annotations = null;
			ret[0] = inits;
			ret[1] = new Integer(i);
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LBRACKET:
						label_62: while (true) {
							jj_consume_token(LBRACKET);
							jj_consume_token(RBRACKET);
							i++;
							switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
								case LBRACKET:
									break;
								default:
										jj_la1[137] = jj_gen;
										break label_62;
							}
						}
						expr = ArrayInitializer();
						accum = add(accum, annotations);
						annotations = null;
						ret[0] = new Integer(i);
						ret[1] = expr;
						break;
				default:
						jj_la1[138] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		ret[2] = accum;
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Statement Statement() throws ParseException {
		Statement ret;
		if (jj_2_36(2)) {
			ret = LabeledStatement();
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ASSERT:
						ret = AssertStatement();
						break;
				case LBRACE:
						ret = Block();
						break;
				case SEMICOLON:
						ret = EmptyStatement();
						break;
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FALSE:
				case FLOAT:
				case INT:
				case LONG:
				case NEW:
				case NULL:
				case SHORT:
				case SUPER:
				case THIS:
				case TRUE:
				case VOID:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case INCR:
				case DECR:
						ret = StatementExpression();
						break;
				case SWITCH:
						ret = SwitchStatement();
						break;
				case IF:
						ret = IfStatement();
						break;
				case WHILE:
						ret = WhileStatement();
						break;
				case DO:
						ret = DoStatement();
						break;
				case FOR:
						ret = ForStatement();
						break;
				case BREAK:
						ret = BreakStatement();
						break;
				case CONTINUE:
						ret = ContinueStatement();
						break;
				case RETURN:
						ret = ReturnStatement();
						break;
				case THROW:
						ret = ThrowStatement();
						break;
				case SYNCHRONIZED:
						ret = SynchronizedStatement();
						break;
				case TRY:
						ret = TryStatement();
						break;
				default:
						jj_la1[139] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final AssertStmt AssertStatement() throws ParseException {
		Expression check;
		Expression msg = null;
		int line;
		int column;
		jj_consume_token(ASSERT);
		line = token.beginLine;
		column = token.beginColumn;
		check = Expression();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case COLON:
					jj_consume_token(COLON);
					msg = Expression();
					break;
			default:
					jj_la1[140] = jj_gen;
		}
		jj_consume_token(SEMICOLON);
		{
			if (true) return new AssertStmt(line, column, token.endLine, token.endColumn, check, msg);
		}
		throw new Error("Missing return statement in function");
	}

	public final LabeledStmt LabeledStatement() throws ParseException {
		String label;
		Statement stmt;
		int line;
		int column;
		jj_consume_token(IDENTIFIER);
		line = token.beginLine;
		column = token.beginColumn;
		label = token.image;
		jj_consume_token(COLON);
		stmt = Statement();
		{
			if (true) return new LabeledStmt(line, column, token.endLine, token.endColumn, label, stmt);
		}
		throw new Error("Missing return statement in function");
	}

	public final BlockStmt Block() throws ParseException {
		List stmts;
		int beginLine;
		int beginColumn;
		jj_consume_token(LBRACE);
		beginLine = token.beginLine;
		beginColumn = token.beginColumn;
		stmts = Statements();
		jj_consume_token(RBRACE);
		{
			if (true) return new BlockStmt(beginLine, beginColumn, token.endLine, token.endColumn, stmts);
		}
		throw new Error("Missing return statement in function");
	}

	public final Statement BlockStatement() throws ParseException {
		Statement ret;
		Expression expr;
		ClassOrInterfaceDeclaration typeDecl;
		Modifier modifier;
		if (jj_2_37(2147483647)) {
			pushJavadoc();
			modifier = Modifiers();
			typeDecl = ClassOrInterfaceDeclaration(modifier);
			ret = new TypeDeclarationStmt(typeDecl.getBeginLine(), typeDecl.getBeginColumn(), token.endLine, token.endColumn, typeDecl);
		} else if (jj_2_38(2147483647)) {
			expr = VariableDeclarationExpression();
			jj_consume_token(SEMICOLON);
			ret = new ExpressionStmt(expr.getBeginLine(), expr.getBeginColumn(), token.endLine, token.endColumn, expr);
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ASSERT:
				case BOOLEAN:
				case BREAK:
				case BYTE:
				case CHAR:
				case CONTINUE:
				case DO:
				case DOUBLE:
				case FALSE:
				case FLOAT:
				case FOR:
				case IF:
				case INT:
				case LONG:
				case NEW:
				case NULL:
				case RETURN:
				case SHORT:
				case SUPER:
				case SWITCH:
				case SYNCHRONIZED:
				case THIS:
				case THROW:
				case TRUE:
				case TRY:
				case VOID:
				case WHILE:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case LBRACE:
				case SEMICOLON:
				case INCR:
				case DECR:
						ret = Statement();
						break;
				default:
						jj_la1[141] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final VariableDeclarationExpr VariableDeclarationExpression() throws ParseException {
		Modifier modifier;
		Type type;
		List vars = new LinkedList();
		VariableDeclarator var;
		modifier = Modifiers();
		type = Type();
		var = VariableDeclarator();
		vars.add(var);
		label_63: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[142] = jj_gen;
						break label_63;
			}
			jj_consume_token(COMMA);
			var = VariableDeclarator();
			vars.add(var);
		}
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		if (line == -1) {
			line = type.getBeginLine();
			column = type.getBeginColumn();
		}
		{
			if (true) return new VariableDeclarationExpr(line, column, token.endLine, token.endColumn, modifier.modifiers, modifier.annotations, type, vars);
		}
		throw new Error("Missing return statement in function");
	}

	public final EmptyStmt EmptyStatement() throws ParseException {
		jj_consume_token(SEMICOLON);
		{
			if (true) return new EmptyStmt(token.beginLine, token.beginColumn, token.endLine, token.endColumn);
		}
		throw new Error("Missing return statement in function");
	}

	public final Statement LambdaBody() throws ParseException {
		Expression expr;
		Statement n = null;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case BANG:
			case TILDE:
			case INCR:
			case DECR:
			case PLUS:
			case MINUS:
					expr = Expression();
					n = new ExpressionStmt(expr.getBeginLine(), expr.getBeginColumn(), token.endLine, token.endColumn, expr);
					break;
			case LBRACE:
					n = Block();
					break;
			default:
					jj_la1[143] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return n;
		}
		throw new Error("Missing return statement in function");
	}

	public final ExpressionStmt StatementExpression() throws ParseException {
		Expression expr;
		AssignExpr.Operator op;
		Expression value;
		Type type;
		List typeArgs = null;
		Statement lambdaBody = null;
		VariableDeclaratorId id = null;
		List params = null;
		Expression inner = null;
		if (jj_2_39(2)) {
			expr = PreIncrementExpression();
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case DECR:
						expr = PreDecrementExpression();
						break;
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FALSE:
				case FLOAT:
				case INT:
				case LONG:
				case NEW:
				case NULL:
				case SHORT:
				case SUPER:
				case THIS:
				case TRUE:
				case VOID:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
						expr = PrimaryExpression();
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case ASSIGN:
							case INCR:
							case DECR:
							case PLUSASSIGN:
							case MINUSASSIGN:
							case STARASSIGN:
							case SLASHASSIGN:
							case ANDASSIGN:
							case ORASSIGN:
							case XORASSIGN:
							case REMASSIGN:
							case LSHIFTASSIGN:
							case RSIGNEDSHIFTASSIGN:
							case RUNSIGNEDSHIFTASSIGN:
							case ARROW:
							case DOUBLECOLON:
									switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
										case INCR:
												jj_consume_token(INCR);
												expr = new UnaryExpr(expr.getBeginLine(), expr.getBeginColumn(), token.endLine, token.endColumn, expr, UnaryExpr.Operator.posIncrement);
												break;
										case DECR:
												jj_consume_token(DECR);
												expr = new UnaryExpr(expr.getBeginLine(), expr.getBeginColumn(), token.endLine, token.endColumn, expr, UnaryExpr.Operator.posDecrement);
												break;
										case ASSIGN:
										case PLUSASSIGN:
										case MINUSASSIGN:
										case STARASSIGN:
										case SLASHASSIGN:
										case ANDASSIGN:
										case ORASSIGN:
										case XORASSIGN:
										case REMASSIGN:
										case LSHIFTASSIGN:
										case RSIGNEDSHIFTASSIGN:
										case RUNSIGNEDSHIFTASSIGN:
												op = AssignmentOperator();
												value = Expression();
												expr = new AssignExpr(expr.getBeginLine(), expr.getBeginColumn(), token.endLine, token.endColumn, expr, value, op);
												break;
										case DOUBLECOLON:
												jj_consume_token(DOUBLECOLON);
												switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
													case LT:
															typeArgs = TypeParameters();
															typeArgs.remove(0);
															break;
													default:
															jj_la1[144] = jj_gen;
												}
												switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
													case IDENTIFIER:
															jj_consume_token(IDENTIFIER);
															break;
													case NEW:
															jj_consume_token(NEW);
															break;
													default:
															jj_la1[145] = jj_gen;
															jj_consume_token(-1);
															throw new ParseException();
												}
												expr = new MethodReferenceExpr(expr.getBeginLine(), expr.getBeginColumn(), token.endLine, token.endColumn, expr, typeArgs, token.image);
												break;
										case ARROW:
												jj_consume_token(ARROW);
												lambdaBody = LambdaBody();
												if (expr instanceof CastExpr) {
													inner = generateLambda(((CastExpr) expr).getExpr(), lambdaBody);
													((CastExpr) expr).setExpr(inner);
												} else {
													expr = generateLambda(expr, lambdaBody);
												}
												break;
										default:
												jj_la1[146] = jj_gen;
												jj_consume_token(-1);
												throw new ParseException();
									}
									break;
							default:
									jj_la1[147] = jj_gen;
						}
						break;
				default:
						jj_la1[148] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		jj_consume_token(SEMICOLON);
		{
			if (true) return new ExpressionStmt(expr.getBeginLine(), expr.getBeginColumn(), token.endLine, token.endColumn, expr);
		}
		throw new Error("Missing return statement in function");
	}

	public final SwitchStmt SwitchStatement() throws ParseException {
		Expression selector;
		SwitchEntryStmt entry;
		List entries = null;
		int line;
		int column;
		jj_consume_token(SWITCH);
		line = token.beginLine;
		column = token.beginColumn;
		jj_consume_token(LPAREN);
		selector = Expression();
		jj_consume_token(RPAREN);
		jj_consume_token(LBRACE);
		label_64: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case CASE:
				case _DEFAULT:
					break;
				default:
						jj_la1[149] = jj_gen;
						break label_64;
			}
			entry = SwitchEntry();
			entries = add(entries, entry);
		}
		jj_consume_token(RBRACE);
		{
			if (true) return new SwitchStmt(line, column, token.endLine, token.endColumn, selector, entries);
		}
		throw new Error("Missing return statement in function");
	}

	public final SwitchEntryStmt SwitchEntry() throws ParseException {
		Expression label = null;
		List stmts;
		int line;
		int column;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case CASE:
					jj_consume_token(CASE);
					line = token.beginLine;
					column = token.beginColumn;
					label = Expression();
					break;
			case _DEFAULT:
					jj_consume_token(_DEFAULT);
					line = token.beginLine;
					column = token.beginColumn;
					break;
			default:
					jj_la1[150] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		jj_consume_token(COLON);
		stmts = Statements();
		{
			if (true) return new SwitchEntryStmt(line, column, token.endLine, token.endColumn, label, stmts);
		}
		throw new Error("Missing return statement in function");
	}

	public final IfStmt IfStatement() throws ParseException {
		Expression condition;
		Statement thenStmt;
		Statement elseStmt = null;
		int line;
		int column;
		jj_consume_token(IF);
		line = token.beginLine;
		column = token.beginColumn;
		jj_consume_token(LPAREN);
		condition = Expression();
		jj_consume_token(RPAREN);
		thenStmt = Statement();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case ELSE:
					jj_consume_token(ELSE);
					elseStmt = Statement();
					break;
			default:
					jj_la1[151] = jj_gen;
		}
		{
			if (true) return new IfStmt(line, column, token.endLine, token.endColumn, condition, thenStmt, elseStmt);
		}
		throw new Error("Missing return statement in function");
	}

	public final WhileStmt WhileStatement() throws ParseException {
		Expression condition;
		Statement body;
		int line;
		int column;
		jj_consume_token(WHILE);
		line = token.beginLine;
		column = token.beginColumn;
		jj_consume_token(LPAREN);
		condition = Expression();
		jj_consume_token(RPAREN);
		body = Statement();
		{
			if (true) return new WhileStmt(line, column, token.endLine, token.endColumn, condition, body);
		}
		throw new Error("Missing return statement in function");
	}

	public final DoStmt DoStatement() throws ParseException {
		Expression condition;
		Statement body;
		int line;
		int column;
		jj_consume_token(DO);
		line = token.beginLine;
		column = token.beginColumn;
		body = Statement();
		jj_consume_token(WHILE);
		jj_consume_token(LPAREN);
		condition = Expression();
		jj_consume_token(RPAREN);
		jj_consume_token(SEMICOLON);
		{
			if (true) return new DoStmt(line, column, token.endLine, token.endColumn, body, condition);
		}
		throw new Error("Missing return statement in function");
	}

	public final Statement ForStatement() throws ParseException {
		String id = null;
		VariableDeclarationExpr varExpr = null;
		Expression expr = null;
		List init = null;
		List update = null;
		Statement body;
		int line;
		int column;
		jj_consume_token(FOR);
		line = token.beginLine;
		column = token.beginColumn;
		jj_consume_token(LPAREN);
		if (jj_2_40(2147483647)) {
			varExpr = VariableDeclarationExpression();
			jj_consume_token(COLON);
			expr = Expression();
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ABSTRACT:
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FALSE:
				case FINAL:
				case FLOAT:
				case INT:
				case LONG:
				case NATIVE:
				case NEW:
				case NULL:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case SHORT:
				case STATIC:
				case STRICTFP:
				case SUPER:
				case SYNCHRONIZED:
				case THIS:
				case TRANSIENT:
				case TRUE:
				case VOID:
				case VOLATILE:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case SEMICOLON:
				case AT:
				case BANG:
				case TILDE:
				case INCR:
				case DECR:
				case PLUS:
				case MINUS:
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case ABSTRACT:
							case BOOLEAN:
							case BYTE:
							case CHAR:
							case DOUBLE:
							case FALSE:
							case FINAL:
							case FLOAT:
							case INT:
							case LONG:
							case NATIVE:
							case NEW:
							case NULL:
							case PRIVATE:
							case PROTECTED:
							case PUBLIC:
							case SHORT:
							case STATIC:
							case STRICTFP:
							case SUPER:
							case SYNCHRONIZED:
							case THIS:
							case TRANSIENT:
							case TRUE:
							case VOID:
							case VOLATILE:
							case LONG_LITERAL:
							case INTEGER_LITERAL:
							case FLOATING_POINT_LITERAL:
							case CHARACTER_LITERAL:
							case STRING_LITERAL:
							case IDENTIFIER:
							case LPAREN:
							case AT:
							case BANG:
							case TILDE:
							case INCR:
							case DECR:
							case PLUS:
							case MINUS:
									init = ForInit();
									break;
							default:
									jj_la1[152] = jj_gen;
						}
						jj_consume_token(SEMICOLON);
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case BOOLEAN:
							case BYTE:
							case CHAR:
							case DOUBLE:
							case FALSE:
							case FLOAT:
							case INT:
							case LONG:
							case NEW:
							case NULL:
							case SHORT:
							case SUPER:
							case THIS:
							case TRUE:
							case VOID:
							case LONG_LITERAL:
							case INTEGER_LITERAL:
							case FLOATING_POINT_LITERAL:
							case CHARACTER_LITERAL:
							case STRING_LITERAL:
							case IDENTIFIER:
							case LPAREN:
							case BANG:
							case TILDE:
							case INCR:
							case DECR:
							case PLUS:
							case MINUS:
									expr = Expression();
									break;
							default:
									jj_la1[153] = jj_gen;
						}
						jj_consume_token(SEMICOLON);
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case BOOLEAN:
							case BYTE:
							case CHAR:
							case DOUBLE:
							case FALSE:
							case FLOAT:
							case INT:
							case LONG:
							case NEW:
							case NULL:
							case SHORT:
							case SUPER:
							case THIS:
							case TRUE:
							case VOID:
							case LONG_LITERAL:
							case INTEGER_LITERAL:
							case FLOATING_POINT_LITERAL:
							case CHARACTER_LITERAL:
							case STRING_LITERAL:
							case IDENTIFIER:
							case LPAREN:
							case BANG:
							case TILDE:
							case INCR:
							case DECR:
							case PLUS:
							case MINUS:
									update = ForUpdate();
									break;
							default:
									jj_la1[154] = jj_gen;
						}
						break;
				default:
						jj_la1[155] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		jj_consume_token(RPAREN);
		body = Statement();
		if (varExpr != null) {
			{
				if (true) return new ForeachStmt(line, column, token.endLine, token.endColumn, varExpr, expr, body);
			}
		}
		{
			if (true) return new ForStmt(line, column, token.endLine, token.endColumn, init, expr, update, body);
		}
		throw new Error("Missing return statement in function");
	}

	public final List ForInit() throws ParseException {
		List ret;
		Expression expr;
		if (jj_2_41(2147483647)) {
			expr = VariableDeclarationExpression();
			ret = new LinkedList();
			ret.add(expr);
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FALSE:
				case FLOAT:
				case INT:
				case LONG:
				case NEW:
				case NULL:
				case SHORT:
				case SUPER:
				case THIS:
				case TRUE:
				case VOID:
				case LONG_LITERAL:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case BANG:
				case TILDE:
				case INCR:
				case DECR:
				case PLUS:
				case MINUS:
						ret = ExpressionList();
						break;
				default:
						jj_la1[156] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List ExpressionList() throws ParseException {
		List ret = new LinkedList();
		Expression expr;
		expr = Expression();
		ret.add(expr);
		label_65: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[157] = jj_gen;
						break label_65;
			}
			jj_consume_token(COMMA);
			expr = Expression();
			ret.add(expr);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final List ForUpdate() throws ParseException {
		List ret;
		ret = ExpressionList();
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final BreakStmt BreakStatement() throws ParseException {
		String id = null;
		int line;
		int column;
		jj_consume_token(BREAK);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IDENTIFIER:
					jj_consume_token(IDENTIFIER);
					id = token.image;
					break;
			default:
					jj_la1[158] = jj_gen;
		}
		jj_consume_token(SEMICOLON);
		{
			if (true) return new BreakStmt(line, column, token.endLine, token.endColumn, id);
		}
		throw new Error("Missing return statement in function");
	}

	public final ContinueStmt ContinueStatement() throws ParseException {
		String id = null;
		int line;
		int column;
		jj_consume_token(CONTINUE);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IDENTIFIER:
					jj_consume_token(IDENTIFIER);
					id = token.image;
					break;
			default:
					jj_la1[159] = jj_gen;
		}
		jj_consume_token(SEMICOLON);
		{
			if (true) return new ContinueStmt(line, column, token.endLine, token.endColumn, id);
		}
		throw new Error("Missing return statement in function");
	}

	public final ReturnStmt ReturnStatement() throws ParseException {
		Expression expr = null;
		int line;
		int column;
		jj_consume_token(RETURN);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case BANG:
			case TILDE:
			case INCR:
			case DECR:
			case PLUS:
			case MINUS:
					expr = Expression();
					break;
			default:
					jj_la1[160] = jj_gen;
		}
		jj_consume_token(SEMICOLON);
		{
			if (true) return new ReturnStmt(line, column, token.endLine, token.endColumn, expr);
		}
		throw new Error("Missing return statement in function");
	}

	public final ThrowStmt ThrowStatement() throws ParseException {
		Expression expr;
		int line;
		int column;
		jj_consume_token(THROW);
		line = token.beginLine;
		column = token.beginColumn;
		expr = Expression();
		jj_consume_token(SEMICOLON);
		{
			if (true) return new ThrowStmt(line, column, token.endLine, token.endColumn, expr);
		}
		throw new Error("Missing return statement in function");
	}

	public final SynchronizedStmt SynchronizedStatement() throws ParseException {
		Expression expr;
		BlockStmt block;
		int line;
		int column;
		jj_consume_token(SYNCHRONIZED);
		line = token.beginLine;
		column = token.beginColumn;
		jj_consume_token(LPAREN);
		expr = Expression();
		jj_consume_token(RPAREN);
		block = Block();
		{
			if (true) return new SynchronizedStmt(line, column, token.endLine, token.endColumn, expr, block);
		}
		throw new Error("Missing return statement in function");
	}

	public final TryStmt TryStatement() throws ParseException {
		List resources = new LinkedList();
		BlockStmt tryBlock;
		BlockStmt finallyBlock = null;
		List catchs = null;
		Parameter except;
		BlockStmt catchBlock;
		Modifier exceptModifier;
		Type exceptType;
		List exceptTypes = new LinkedList();
		VariableDeclaratorId exceptId;
		int line;
		int column;
		int cLine;
		int cColumn;
		jj_consume_token(TRY);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LPAREN:
					resources = ResourceSpecification();
					break;
			default:
					jj_la1[161] = jj_gen;
		}
		tryBlock = Block();
		label_66: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case CATCH:
					break;
				default:
						jj_la1[162] = jj_gen;
						break label_66;
			}
			jj_consume_token(CATCH);
			cLine = token.beginLine;
			cColumn = token.beginColumn;
			jj_consume_token(LPAREN);
			exceptModifier = Modifiers();
			exceptType = Type();
			exceptTypes.add(exceptType);
			label_67: while (true) {
				switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case BIT_OR:
						break;
					default:
							jj_la1[163] = jj_gen;
							break label_67;
				}
				jj_consume_token(BIT_OR);
				exceptType = Type();
				exceptTypes.add(exceptType);
			}
			exceptId = VariableDeclaratorId();
			jj_consume_token(RPAREN);
			catchBlock = Block();
			catchs = add(catchs, new CatchClause(cLine, cColumn, token.endLine, token.endColumn, exceptModifier.modifiers, exceptModifier.annotations, exceptTypes, exceptId, catchBlock));
			exceptTypes = new LinkedList();
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case FINALLY:
					jj_consume_token(FINALLY);
					finallyBlock = Block();
					break;
			default:
					jj_la1[164] = jj_gen;
		}
		TryStmt tmp = new TryStmt(line, column, token.endLine, token.endColumn, resources, tryBlock, catchs, finallyBlock);
		{
			if (true) return tmp;
		}
		throw new Error("Missing return statement in function");
	}

	public final List ResourceSpecification() throws ParseException {
		List vars;
		jj_consume_token(LPAREN);
		vars = Resources();
		if (jj_2_42(2)) {
			jj_consume_token(SEMICOLON);
		} else {
		}
		jj_consume_token(RPAREN);
		{
			if (true) return vars;
		}
		throw new Error("Missing return statement in function");
	}

	public final List Resources() throws ParseException {
		List vars = new LinkedList();
		VariableDeclarationExpr var;

		var = VariableDeclarationExpression();
		vars.add(var);
		label_68: while (true) {
			if (jj_2_43(2)) {
			} else {
				break label_68;
			}
			jj_consume_token(SEMICOLON);
			var = VariableDeclarationExpression();
			vars.add(var);
		}
		{
			if (true) return vars;
		}
		throw new Error("Missing return statement in function");
	}

	public final void RUNSIGNEDSHIFT() throws ParseException {
		if (getToken(1).kind == GT && ((GTToken) getToken(1)).realKind == RUNSIGNEDSHIFT) {

		} else {
			jj_consume_token(-1);
			throw new ParseException();
		}
		jj_consume_token(GT);
		jj_consume_token(GT);
		jj_consume_token(GT);
	}

	public final void RSIGNEDSHIFT() throws ParseException {
		if (getToken(1).kind == GT && ((GTToken) getToken(1)).realKind == RSIGNEDSHIFT) {

		} else {
			jj_consume_token(-1);
			throw new ParseException();
		}
		jj_consume_token(GT);
		jj_consume_token(GT);
	}

	public final AnnotationExpr Annotation() throws ParseException {
		AnnotationExpr ret;
		if (jj_2_44(2147483647)) {
			ret = NormalAnnotation();
		} else if (jj_2_45(2147483647)) {
			ret = SingleMemberAnnotation();
		} else {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AT:
						ret = MarkerAnnotation();
						break;
				default:
						jj_la1[165] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
			}
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final NormalAnnotationExpr NormalAnnotation() throws ParseException {
		NameExpr name;
		List pairs = null;
		int line;
		int column;
		jj_consume_token(AT);
		line = token.beginLine;
		column = token.beginColumn;
		name = Name();
		jj_consume_token(LPAREN);
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IDENTIFIER:
					pairs = MemberValuePairs();
					break;
			default:
					jj_la1[166] = jj_gen;
		}
		jj_consume_token(RPAREN);
		{
			if (true) return new NormalAnnotationExpr(line, column, token.endLine, token.endColumn, name, pairs);
		}
		throw new Error("Missing return statement in function");
	}

	public final MarkerAnnotationExpr MarkerAnnotation() throws ParseException {
		NameExpr name;
		int line;
		int column;
		jj_consume_token(AT);
		line = token.beginLine;
		column = token.beginColumn;
		name = Name();
		{
			if (true) return new MarkerAnnotationExpr(line, column, token.endLine, token.endColumn, name);
		}
		throw new Error("Missing return statement in function");
	}

	public final SingleMemberAnnotationExpr SingleMemberAnnotation() throws ParseException {
		NameExpr name;
		Expression memberVal;
		int line;
		int column;
		jj_consume_token(AT);
		line = token.beginLine;
		column = token.beginColumn;
		name = Name();
		jj_consume_token(LPAREN);
		memberVal = MemberValue();
		jj_consume_token(RPAREN);
		{
			if (true) return new SingleMemberAnnotationExpr(line, column, token.endLine, token.endColumn, name, memberVal);
		}
		throw new Error("Missing return statement in function");
	}

	public final List MemberValuePairs() throws ParseException {
		List ret = new LinkedList();
		MemberValuePair pair;
		pair = MemberValuePair();
		ret.add(pair);
		label_69: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case COMMA:
					break;
				default:
						jj_la1[167] = jj_gen;
						break label_69;
			}
			jj_consume_token(COMMA);
			pair = MemberValuePair();
			ret.add(pair);
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final MemberValuePair MemberValuePair() throws ParseException {
		String name;
		Expression value;
		int line;
		int column;
		jj_consume_token(IDENTIFIER);
		name = token.image;
		line = token.beginLine;
		column = token.beginColumn;
		jj_consume_token(ASSIGN);
		value = MemberValue();
		{
			if (true) return new MemberValuePair(line, column, token.endLine, token.endColumn, name, value);
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression MemberValue() throws ParseException {
		Expression ret;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case AT:
					ret = Annotation();
					break;
			case LBRACE:
					ret = MemberValueArrayInitializer();
					break;
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case BANG:
			case TILDE:
			case INCR:
			case DECR:
			case PLUS:
			case MINUS:
					ret = ConditionalExpression();
					break;
			default:
					jj_la1[168] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression MemberValueArrayInitializer() throws ParseException {
		List ret = new LinkedList();
		Expression member;
		int line;
		int column;
		jj_consume_token(LBRACE);
		line = token.beginLine;
		column = token.beginColumn;
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case LONG_LITERAL:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case LBRACE:
			case AT:
			case BANG:
			case TILDE:
			case INCR:
			case DECR:
			case PLUS:
			case MINUS:
					member = MemberValue();
					ret.add(member);
					label_70: while (true) {
						if (jj_2_46(2)) {
						} else {
							break label_70;
						}
						jj_consume_token(COMMA);
						member = MemberValue();
						ret.add(member);
					}
					break;
			default:
					jj_la1[169] = jj_gen;
		}
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case COMMA:
					jj_consume_token(COMMA);
					break;
			default:
					jj_la1[170] = jj_gen;
		}
		jj_consume_token(RBRACE);
		{
			if (true) return new ArrayInitializerExpr(line, column, token.endLine, token.endColumn, ret);
		}
		throw new Error("Missing return statement in function");
	}

	public final AnnotationDeclaration AnnotationTypeDeclaration(Modifier modifier) throws ParseException {
		String name;
		List members;
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		jj_consume_token(AT);
		if (line == -1) {
			line = token.beginLine;
			column = token.beginColumn;
		}
		jj_consume_token(INTERFACE);
		jj_consume_token(IDENTIFIER);
		name = token.image;
		members = AnnotationTypeBody();
		{
			if (true) return new AnnotationDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), modifier.modifiers, modifier.annotations, name, members);
		}
		throw new Error("Missing return statement in function");
	}

	public final List AnnotationTypeBody() throws ParseException {
		List ret = null;
		BodyDeclaration member;
		jj_consume_token(LBRACE);
		label_71: while (true) {
			switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case ABSTRACT:
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case CLASS:
				case DOUBLE:
				case ENUM:
				case FINAL:
				case FLOAT:
				case INT:
				case INTERFACE:
				case LONG:
				case NATIVE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case SHORT:
				case STATIC:
				case STRICTFP:
				case SYNCHRONIZED:
				case TRANSIENT:
				case VOLATILE:
				case IDENTIFIER:
				case SEMICOLON:
				case AT:
					break;
				default:
						jj_la1[171] = jj_gen;
						break label_71;
			}
			member = AnnotationBodyDeclaration();
			ret = add(ret, member);
		}
		jj_consume_token(RBRACE);
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final BodyDeclaration AnnotationBodyDeclaration() throws ParseException {
		Modifier modifier;
		BodyDeclaration ret;
		pushJavadoc();
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case SEMICOLON:
					jj_consume_token(SEMICOLON);
					ret = new EmptyTypeDeclaration(token.beginLine, token.beginColumn, token.endLine, token.endColumn, popJavadoc());
					break;
			case ABSTRACT:
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case CLASS:
			case DOUBLE:
			case ENUM:
			case FINAL:
			case FLOAT:
			case INT:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case SHORT:
			case STATIC:
			case STRICTFP:
			case SYNCHRONIZED:
			case TRANSIENT:
			case VOLATILE:
			case IDENTIFIER:
			case AT:
					modifier = Modifiers();
					if (jj_2_47(2147483647)) {
						ret = AnnotationTypeMemberDeclaration(modifier);
					} else {
						switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
							case CLASS:
							case INTERFACE:
									ret = ClassOrInterfaceDeclaration(modifier);
									break;
							case ENUM:
									ret = EnumDeclaration(modifier);
									break;
							case AT:
									ret = AnnotationTypeDeclaration(modifier);
									break;
							case BOOLEAN:
							case BYTE:
							case CHAR:
							case DOUBLE:
							case FLOAT:
							case INT:
							case LONG:
							case SHORT:
							case IDENTIFIER:
									ret = FieldDeclaration(modifier);
									break;
							default:
									jj_la1[172] = jj_gen;
									jj_consume_token(-1);
									throw new ParseException();
						}
					}
					break;
			default:
					jj_la1[173] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
		}
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	public final AnnotationMemberDeclaration AnnotationTypeMemberDeclaration(Modifier modifier) throws ParseException {
		Type type;
		String name;
		Expression defaultVal = null;
		type = Type();
		jj_consume_token(IDENTIFIER);
		name = token.image;
		jj_consume_token(LPAREN);
		jj_consume_token(RPAREN);
		switch((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case _DEFAULT:
					defaultVal = DefaultValue();
					break;
			default:
					jj_la1[174] = jj_gen;
		}
		jj_consume_token(SEMICOLON);
		int line = modifier.beginLine;
		int column = modifier.beginColumn;
		{
			if (line == -1) {
				line = type.getBeginLine();
				column = type.getBeginColumn();
			}
		}
		{
			if (true) return new AnnotationMemberDeclaration(line, column, token.endLine, token.endColumn, popJavadoc(), modifier.modifiers, modifier.annotations, type, name, defaultVal);
		}
		throw new Error("Missing return statement in function");
	}

	public final Expression DefaultValue() throws ParseException {
		Expression ret;
		jj_consume_token(_DEFAULT);
		ret = MemberValue();
		{
			if (true) return ret;
		}
		throw new Error("Missing return statement in function");
	}

	private boolean jj_2_1(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_1();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(0, xla);
		}
	}

	private boolean jj_2_2(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_2();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(1, xla);
		}
	}

	private boolean jj_2_3(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_3();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(2, xla);
		}
	}

	private boolean jj_2_4(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_4();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(3, xla);
		}
	}

	private boolean jj_2_5(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_5();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(4, xla);
		}
	}

	private boolean jj_2_6(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_6();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(5, xla);
		}
	}

	private boolean jj_2_7(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_7();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(6, xla);
		}
	}

	private boolean jj_2_8(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_8();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(7, xla);
		}
	}

	private boolean jj_2_9(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_9();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(8, xla);
		}
	}

	private boolean jj_2_10(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_10();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(9, xla);
		}
	}

	private boolean jj_2_11(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_11();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(10, xla);
		}
	}

	private boolean jj_2_12(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_12();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(11, xla);
		}
	}

	private boolean jj_2_13(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_13();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(12, xla);
		}
	}

	private boolean jj_2_14(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_14();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(13, xla);
		}
	}

	private boolean jj_2_15(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_15();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(14, xla);
		}
	}

	private boolean jj_2_16(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_16();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(15, xla);
		}
	}

	private boolean jj_2_17(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_17();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(16, xla);
		}
	}

	private boolean jj_2_18(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_18();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(17, xla);
		}
	}

	private boolean jj_2_19(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_19();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(18, xla);
		}
	}

	private boolean jj_2_20(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_20();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(19, xla);
		}
	}

	private boolean jj_2_21(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_21();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(20, xla);
		}
	}

	private boolean jj_2_22(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_22();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(21, xla);
		}
	}

	private boolean jj_2_23(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_23();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(22, xla);
		}
	}

	private boolean jj_2_24(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_24();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(23, xla);
		}
	}

	private boolean jj_2_25(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_25();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(24, xla);
		}
	}

	private boolean jj_2_26(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_26();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(25, xla);
		}
	}

	private boolean jj_2_27(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_27();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(26, xla);
		}
	}

	private boolean jj_2_28(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_28();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(27, xla);
		}
	}

	private boolean jj_2_29(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_29();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(28, xla);
		}
	}

	private boolean jj_2_30(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_30();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(29, xla);
		}
	}

	private boolean jj_2_31(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_31();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(30, xla);
		}
	}

	private boolean jj_2_32(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_32();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(31, xla);
		}
	}

	private boolean jj_2_33(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_33();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(32, xla);
		}
	}

	private boolean jj_2_34(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_34();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(33, xla);
		}
	}

	private boolean jj_2_35(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_35();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(34, xla);
		}
	}

	private boolean jj_2_36(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_36();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(35, xla);
		}
	}

	private boolean jj_2_37(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_37();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(36, xla);
		}
	}

	private boolean jj_2_38(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_38();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(37, xla);
		}
	}

	private boolean jj_2_39(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_39();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(38, xla);
		}
	}

	private boolean jj_2_40(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_40();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(39, xla);
		}
	}

	private boolean jj_2_41(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_41();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(40, xla);
		}
	}

	private boolean jj_2_42(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_42();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(41, xla);
		}
	}

	private boolean jj_2_43(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_43();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(42, xla);
		}
	}

	private boolean jj_2_44(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_44();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(43, xla);
		}
	}

	private boolean jj_2_45(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_45();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(44, xla);
		}
	}

	private boolean jj_2_46(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_46();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(45, xla);
		}
	}

	private boolean jj_2_47(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_47();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(46, xla);
		}
	}

	private boolean jj_3R_235() {
		return jj_3R_104();
	}

	private boolean jj_3R_236() {
		return jj_3R_267();
	}

	private boolean jj_3R_266() {
		return jj_scan_token(BANG);
	}

	private boolean jj_3R_265() {
		return jj_scan_token(TILDE);
	}

	private boolean jj_3R_234() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_265()) {
			jj_scanpos = xsp;
			if (jj_3R_266()) return true;
		}
		return jj_3R_174();
	}

	private boolean jj_3R_240() {
		if (jj_3R_116()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_271()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_272()) {
			jj_scanpos = xsp;
			if (jj_3R_273()) {
				jj_scanpos = xsp;
				if (jj_3R_274()) {
					jj_scanpos = xsp;
					if (jj_3R_275()) {
						jj_scanpos = xsp;
						if (jj_3R_276()) {
							jj_scanpos = xsp;
							return jj_3R_277();
						}
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_360() {
		if (jj_scan_token(SYNCHRONIZED)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_100()) return true;
		if (jj_scan_token(RPAREN)) return true;
		return jj_3R_128();
	}

	private boolean jj_3_6() {
		return jj_3R_89();
	}

	private boolean jj_3R_202() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_234()) {
			jj_scanpos = xsp;
			if (jj_3R_235()) {
				jj_scanpos = xsp;
				return jj_3R_236();
			}
		}
		return false;
	}

	private boolean jj_3R_359() {
		if (jj_scan_token(THROW)) return true;
		if (jj_3R_100()) return true;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_207() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_6()) {
			jj_scanpos = xsp;
			if (jj_3R_240()) {
				jj_scanpos = xsp;
				return jj_3R_241();
			}
		}
		return false;
	}

	private boolean jj_3R_242() {
		if (jj_scan_token(DECR)) return true;
		return jj_3R_174();
	}

	private boolean jj_3R_358() {
		if (jj_scan_token(RETURN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_423()) jj_scanpos = xsp;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_171() {
		return jj_3R_207();
	}

	private boolean jj_3R_463() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_100();
	}

	private boolean jj_3R_279() {
		return jj_3R_123();
	}

	private boolean jj_3R_113() {
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_171()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3R_305() {
		return jj_3R_123();
	}

	private boolean jj_3R_118() {
		if (jj_scan_token(INCR)) return true;
		return jj_3R_174();
	}

	private boolean jj_3R_212() {
		return jj_3R_202();
	}

	private boolean jj_3R_418() {
		if (jj_scan_token(ELSE)) return true;
		return jj_3R_306();
	}

	private boolean jj_3R_357() {
		if (jj_scan_token(CONTINUE)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_422()) jj_scanpos = xsp;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_280() {
		if (jj_scan_token(BIT_AND)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_305()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_3R_198();
	}

	private boolean jj_3R_245() {
		if (jj_scan_token(EXTENDS)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_279()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_3R_198()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_280()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3_3() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_85();
	}

	private boolean jj_3R_437() {
		return jj_3R_454();
	}

	private boolean jj_3R_356() {
		if (jj_scan_token(BREAK)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_421()) jj_scanpos = xsp;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_244() {
		return jj_scan_token(MINUS);
	}

	private boolean jj_3R_243() {
		return jj_scan_token(PLUS);
	}

	private boolean jj_3R_211() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_243()) {
			jj_scanpos = xsp;
			if (jj_3R_244()) return true;
		}
		return jj_3R_174();
	}

	private boolean jj_3R_182() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_217()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_210() {
		return jj_3R_242();
	}

	private boolean jj_3R_209() {
		return jj_3R_118();
	}

	private boolean jj_3R_408() {
		return jj_3R_113();
	}

	private boolean jj_3R_454() {
		return jj_3R_461();
	}

	private boolean jj_3R_218() {
		return jj_3R_123();
	}

	private boolean jj_3R_183() {
		if (jj_scan_token(COMMA)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_218()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_3R_182();
	}

	private boolean jj_3_41() {
		if (jj_3R_116()) return true;
		if (jj_3R_87()) return true;
		return jj_scan_token(IDENTIFIER);
	}

	private boolean jj_3R_174() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_209()) {
			jj_scanpos = xsp;
			if (jj_3R_210()) {
				jj_scanpos = xsp;
				if (jj_3R_211()) {
					jj_scanpos = xsp;
					return jj_3R_212();
				}
			}
		}
		return false;
	}

	private boolean jj_3R_181() {
		return jj_3R_123();
	}

	private boolean jj_3R_461() {
		if (jj_3R_100()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_463()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_125() {
		if (jj_scan_token(LT)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_181()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_3R_182()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_183()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_scan_token(GT);
	}

	private boolean jj_3R_432() {
		return jj_scan_token(REM);
	}

	private boolean jj_3R_460() {
		return jj_3R_461();
	}

	private boolean jj_3R_431() {
		return jj_scan_token(SLASH);
	}

	private boolean jj_3R_436() {
		return jj_3R_100();
	}

	private boolean jj_3R_430() {
		return jj_scan_token(STAR);
	}

	private boolean jj_3R_459() {
		return jj_3R_117();
	}

	private boolean jj_3R_413() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_430()) {
			jj_scanpos = xsp;
			if (jj_3R_431()) {
				jj_scanpos = xsp;
				if (jj_3R_432()) return true;
			}
		}
		return jj_3R_174();
	}

	private boolean jj_3R_407() {
		return jj_3R_187();
	}

	private boolean jj_3R_398() {
		return jj_3R_207();
	}

	private boolean jj_3R_376() {
		if (jj_3R_174()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_413()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_124() {
		return jj_3R_123();
	}

	private boolean jj_3R_453() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_459()) {
			jj_scanpos = xsp;
			return jj_3R_460();
		}
		return false;
	}

	private boolean jj_3R_85() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_124()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(IDENTIFIER)) return true;
		xsp = jj_scanpos;
		if (jj_3R_407()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_408()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_415() {
		return jj_scan_token(MINUS);
	}

	private boolean jj_3R_414() {
		return jj_scan_token(PLUS);
	}

	private boolean jj_3R_402() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_414()) {
			jj_scanpos = xsp;
			if (jj_3R_415()) return true;
		}
		return jj_3R_376();
	}

	private boolean jj_3R_383() {
		if (jj_scan_token(SEMICOLON)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_398()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3_40() {
		if (jj_3R_117()) return true;
		return jj_scan_token(COLON);
	}

	private boolean jj_3R_435() {
		return jj_3R_453();
	}

	private boolean jj_3R_372() {
		if (jj_3R_376()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_402()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_420() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_435()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		xsp = jj_scanpos;
		if (jj_3R_436()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		xsp = jj_scanpos;
		if (jj_3R_437()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_419() {
		if (jj_3R_117()) return true;
		if (jj_scan_token(COLON)) return true;
		return jj_3R_100();
	}

	private boolean jj_3R_382() {
		if (jj_3R_85()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_3()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3_21() {
		return jj_3R_103();
	}

	private boolean jj_3_20() {
		return jj_3R_102();
	}

	private boolean jj_3R_101() {
		return jj_scan_token(LSHIFT);
	}

	private boolean jj_3_19() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_101()) {
			jj_scanpos = xsp;
			if (jj_3_20()) {
				jj_scanpos = xsp;
				if (jj_3_21()) return true;
			}
		}
		return jj_3R_372();
	}

	private boolean jj_3R_355() {
		if (jj_scan_token(FOR)) return true;
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_419()) {
			jj_scanpos = xsp;
			if (jj_3R_420()) return true;
		}
		if (jj_scan_token(RPAREN)) return true;
		return jj_3R_306();
	}

	private boolean jj_3R_381() {
		return jj_3R_397();
	}

	private boolean jj_3R_373() {
		if (jj_scan_token(INSTANCEOF)) return true;
		return jj_3R_87();
	}

	private boolean jj_3R_299() {
		if (jj_scan_token(ENUM)) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_381()) jj_scanpos = xsp;
		if (jj_scan_token(LBRACE)) return true;
		xsp = jj_scanpos;
		if (jj_3R_382()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(88)) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_383()) jj_scanpos = xsp;
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3R_370() {
		if (jj_3R_372()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_19()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_405() {
		return jj_3R_123();
	}

	private boolean jj_3R_395() {
		return jj_scan_token(GE);
	}

	private boolean jj_3R_394() {
		return jj_scan_token(LE);
	}

	private boolean jj_3R_393() {
		return jj_scan_token(GT);
	}

	private boolean jj_3R_392() {
		return jj_scan_token(LT);
	}

	private boolean jj_3R_354() {
		if (jj_scan_token(DO)) return true;
		if (jj_3R_306()) return true;
		if (jj_scan_token(WHILE)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_100()) return true;
		if (jj_scan_token(RPAREN)) return true;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_377() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_392()) {
			jj_scanpos = xsp;
			if (jj_3R_393()) {
				jj_scanpos = xsp;
				if (jj_3R_394()) {
					jj_scanpos = xsp;
					if (jj_3R_395()) return true;
				}
			}
		}
		return jj_3R_370();
	}

	private boolean jj_3R_406() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_198();
	}

	private boolean jj_3R_368() {
		if (jj_3R_370()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_377()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_397() {
		if (jj_scan_token(IMPLEMENTS)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_405()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_3R_198()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_406()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_353() {
		if (jj_scan_token(WHILE)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_100()) return true;
		if (jj_scan_token(RPAREN)) return true;
		return jj_3R_306();
	}

	private boolean jj_3R_403() {
		return jj_3R_123();
	}

	private boolean jj_3R_369() {
		if (jj_scan_token(BIT_AND)) return true;
		return jj_3R_345();
	}

	private boolean jj_3R_364() {
		if (jj_3R_368()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_373()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_429() {
		return jj_3R_123();
	}

	private boolean jj_3R_404() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_198();
	}

	private boolean jj_3R_352() {
		if (jj_scan_token(IF)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_100()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_306()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_418()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_412() {
		if (jj_scan_token(COMMA)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_429()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_3R_198();
	}

	private boolean jj_3R_396() {
		if (jj_scan_token(EXTENDS)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_403()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_3R_198()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_404()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_375() {
		return jj_scan_token(NE);
	}

	private boolean jj_3R_374() {
		return jj_scan_token(EQ);
	}

	private boolean jj_3R_371() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_374()) {
			jj_scanpos = xsp;
			if (jj_3R_375()) return true;
		}
		return jj_3R_364();
	}

	private boolean jj_3R_365() {
		if (jj_scan_token(XOR)) return true;
		return jj_3R_315();
	}

	private boolean jj_3R_346() {
		if (jj_scan_token(BIT_OR)) return true;
		return jj_3R_293();
	}

	private boolean jj_3R_345() {
		if (jj_3R_364()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_371()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_319() {
		return jj_scan_token(INTERFACE);
	}

	private boolean jj_3R_452() {
		return jj_scan_token(_DEFAULT);
	}

	private boolean jj_3R_451() {
		if (jj_scan_token(CASE)) return true;
		return jj_3R_100();
	}

	private boolean jj_3R_380() {
		return jj_3R_397();
	}

	private boolean jj_3R_379() {
		return jj_3R_396();
	}

	private boolean jj_3R_378() {
		return jj_3R_125();
	}

	private boolean jj_3R_316() {
		if (jj_scan_token(SC_AND)) return true;
		return jj_3R_264();
	}

	private boolean jj_3R_315() {
		if (jj_3R_345()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_369()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_298() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(20)) {
			jj_scanpos = xsp;
			if (jj_3R_319()) return true;
		}
		if (jj_scan_token(IDENTIFIER)) return true;
		xsp = jj_scanpos;
		if (jj_3R_378()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_379()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_380()) jj_scanpos = xsp;
		return jj_3R_113();
	}

	private boolean jj_3R_434() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_451()) {
			jj_scanpos = xsp;
			if (jj_3R_452()) return true;
		}
		if (jj_scan_token(COLON)) return true;
		return jj_3R_184();
	}

	private boolean jj_3R_295() {
		if (jj_scan_token(SC_OR)) return true;
		return jj_3R_233();
	}

	private boolean jj_3R_293() {
		if (jj_3R_315()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_365()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_417() {
		return jj_3R_434();
	}

	private boolean jj_3R_351() {
		if (jj_scan_token(SWITCH)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_100()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_417()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3R_264() {
		if (jj_3R_293()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_346()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_173() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_172();
	}

	private boolean jj_3R_458() {
		return jj_3R_125();
	}

	private boolean jj_3R_233() {
		if (jj_3R_264()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_316()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_450() {
		if (jj_scan_token(ARROW)) return true;
		return jj_3R_296();
	}

	private boolean jj_3R_449() {
		if (jj_scan_token(DOUBLECOLON)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_458()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(78)) {
			jj_scanpos = xsp;
			return jj_scan_token(43);
		}
		return false;
	}

	private boolean jj_3R_448() {
		if (jj_3R_99()) return true;
		return jj_3R_100();
	}

	private boolean jj_3R_447() {
		return jj_scan_token(DECR);
	}

	private boolean jj_3R_200() {
		if (jj_3R_233()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_295()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_446() {
		return jj_scan_token(INCR);
	}

	private boolean jj_3R_433() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_446()) {
			jj_scanpos = xsp;
			if (jj_3R_447()) {
				jj_scanpos = xsp;
				if (jj_3R_448()) {
					jj_scanpos = xsp;
					if (jj_3R_449()) {
						jj_scanpos = xsp;
						return jj_3R_450();
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_367() {
		if (jj_3R_294()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_433()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_84() {
		return jj_3R_123();
	}

	private boolean jj_3R_366() {
		return jj_3R_242();
	}

	private boolean jj_3R_268() {
		if (jj_scan_token(HOOK)) return true;
		if (jj_3R_100()) return true;
		if (jj_scan_token(COLON)) return true;
		return jj_3R_151();
	}

	private boolean jj_3R_83() {
		return jj_scan_token(STRICTFP);
	}

	private boolean jj_3R_151() {
		if (jj_3R_200()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_268()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_82() {
		return jj_scan_token(VOLATILE);
	}

	private boolean jj_3R_81() {
		return jj_scan_token(TRANSIENT);
	}

	private boolean jj_3R_80() {
		return jj_scan_token(NATIVE);
	}

	private boolean jj_3_39() {
		return jj_3R_118();
	}

	private boolean jj_3R_79() {
		return jj_scan_token(SYNCHRONIZED);
	}

	private boolean jj_3R_350() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_39()) {
			jj_scanpos = xsp;
			if (jj_3R_366()) {
				jj_scanpos = xsp;
				if (jj_3R_367()) return true;
			}
		}
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_150() {
		return jj_scan_token(ORASSIGN);
	}

	private boolean jj_3R_78() {
		return jj_scan_token(ABSTRACT);
	}

	private boolean jj_3R_149() {
		return jj_scan_token(XORASSIGN);
	}

	private boolean jj_3R_148() {
		return jj_scan_token(ANDASSIGN);
	}

	private boolean jj_3R_77() {
		return jj_scan_token(FINAL);
	}

	private boolean jj_3R_147() {
		return jj_scan_token(RUNSIGNEDSHIFTASSIGN);
	}

	private boolean jj_3R_146() {
		return jj_scan_token(RSIGNEDSHIFTASSIGN);
	}

	private boolean jj_3R_76() {
		return jj_scan_token(PRIVATE);
	}

	private boolean jj_3R_145() {
		return jj_scan_token(LSHIFTASSIGN);
	}

	private boolean jj_3R_144() {
		return jj_scan_token(MINUSASSIGN);
	}

	private boolean jj_3R_75() {
		return jj_scan_token(PROTECTED);
	}

	private boolean jj_3R_143() {
		return jj_scan_token(PLUSASSIGN);
	}

	private boolean jj_3R_142() {
		return jj_scan_token(REMASSIGN);
	}

	private boolean jj_3R_74() {
		return jj_scan_token(STATIC);
	}

	private boolean jj_3R_141() {
		return jj_scan_token(SLASHASSIGN);
	}

	private boolean jj_3R_140() {
		return jj_scan_token(STARASSIGN);
	}

	private boolean jj_3R_73() {
		return jj_scan_token(PUBLIC);
	}

	private boolean jj_3R_139() {
		return jj_scan_token(ASSIGN);
	}

	private boolean jj_3_2() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_73()) {
			jj_scanpos = xsp;
			if (jj_3R_74()) {
				jj_scanpos = xsp;
				if (jj_3R_75()) {
					jj_scanpos = xsp;
					if (jj_3R_76()) {
						jj_scanpos = xsp;
						if (jj_3R_77()) {
							jj_scanpos = xsp;
							if (jj_3R_78()) {
								jj_scanpos = xsp;
								if (jj_3R_79()) {
									jj_scanpos = xsp;
									if (jj_3R_80()) {
										jj_scanpos = xsp;
										if (jj_3R_81()) {
											jj_scanpos = xsp;
											if (jj_3R_82()) {
												jj_scanpos = xsp;
												if (jj_3R_83()) {
													jj_scanpos = xsp;
													return jj_3R_84();
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_116() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_2()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_99() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_139()) {
			jj_scanpos = xsp;
			if (jj_3R_140()) {
				jj_scanpos = xsp;
				if (jj_3R_141()) {
					jj_scanpos = xsp;
					if (jj_3R_142()) {
						jj_scanpos = xsp;
						if (jj_3R_143()) {
							jj_scanpos = xsp;
							if (jj_3R_144()) {
								jj_scanpos = xsp;
								if (jj_3R_145()) {
									jj_scanpos = xsp;
									if (jj_3R_146()) {
										jj_scanpos = xsp;
										if (jj_3R_147()) {
											jj_scanpos = xsp;
											if (jj_3R_148()) {
												jj_scanpos = xsp;
												if (jj_3R_149()) {
													jj_scanpos = xsp;
													return jj_3R_150();
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_318() {
		return jj_3R_128();
	}

	private boolean jj_3R_317() {
		return jj_3R_100();
	}

	private boolean jj_3R_297() {
		return jj_3R_125();
	}

	private boolean jj_3R_114() {
		return jj_3R_123();
	}

	private boolean jj_3R_296() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_317()) {
			jj_scanpos = xsp;
			return jj_3R_318();
		}
		return false;
	}

	private boolean jj_3R_270() {
		if (jj_scan_token(DOUBLECOLON)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_297()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(78)) {
			jj_scanpos = xsp;
			return jj_scan_token(43);
		}
		return false;
	}

	private boolean jj_3R_416() {
		if (jj_scan_token(COLON)) return true;
		return jj_3R_100();
	}

	private boolean jj_3R_349() {
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3_34() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_114()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(LBRACKET)) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3R_122() {
		return jj_3R_123();
	}

	private boolean jj_3R_72() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_122()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(PACKAGE)) return true;
		if (jj_3R_119()) return true;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3_18() {
		if (jj_3R_99()) return true;
		return jj_3R_100();
	}

	private boolean jj_3_1() {
		return jj_3R_72();
	}

	private boolean jj_3R_269() {
		if (jj_scan_token(ARROW)) return true;
		return jj_3R_296();
	}

	private boolean jj_3_38() {
		return jj_3R_117();
	}

	private boolean jj_3R_117() {
		if (jj_3R_116()) return true;
		if (jj_3R_87()) return true;
		if (jj_3R_172()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_173()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_239() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_18()) {
			jj_scanpos = xsp;
			if (jj_3R_269()) {
				jj_scanpos = xsp;
				return jj_3R_270();
			}
		}
		return false;
	}

	private boolean jj_3_37() {
		if (jj_3R_116()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(20)) {
			jj_scanpos = xsp;
			return jj_scan_token(40);
		}
		return false;
	}

	private boolean jj_3R_283() {
		return jj_3R_306();
	}

	private boolean jj_3R_100() {
		if (jj_3R_151()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_239()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_282() {
		if (jj_3R_117()) return true;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_281() {
		if (jj_3R_116()) return true;
		return jj_3R_298();
	}

	private boolean jj_3R_246() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_281()) {
			jj_scanpos = xsp;
			if (jj_3R_282()) {
				jj_scanpos = xsp;
				return jj_3R_283();
			}
		}
		return false;
	}

	private boolean jj_3R_411() {
		return jj_3R_123();
	}

	private boolean jj_3R_401() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_411()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_3R_198()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_412()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_128() {
		if (jj_scan_token(LBRACE)) return true;
		if (jj_3R_184()) return true;
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3_17() {
		if (jj_scan_token(DOT)) return true;
		return jj_scan_token(IDENTIFIER);
	}

	private boolean jj_3R_119() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_17()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_115() {
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(COLON)) return true;
		return jj_3R_306();
	}

	private boolean jj_3R_170() {
		return jj_3R_87();
	}

	private boolean jj_3R_169() {
		return jj_scan_token(VOID);
	}

	private boolean jj_3R_348() {
		if (jj_scan_token(ASSERT)) return true;
		if (jj_3R_100()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_416()) jj_scanpos = xsp;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_111() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_169()) {
			jj_scanpos = xsp;
			return jj_3R_170();
		}
		return false;
	}

	private boolean jj_3R_339() {
		return jj_3R_361();
	}

	private boolean jj_3R_338() {
		return jj_3R_360();
	}

	private boolean jj_3R_337() {
		return jj_3R_359();
	}

	private boolean jj_3R_336() {
		return jj_3R_358();
	}

	private boolean jj_3R_335() {
		return jj_3R_357();
	}

	private boolean jj_3R_163() {
		return jj_scan_token(DOUBLE);
	}

	private boolean jj_3R_334() {
		return jj_3R_356();
	}

	private boolean jj_3R_162() {
		return jj_scan_token(FLOAT);
	}

	private boolean jj_3R_333() {
		return jj_3R_355();
	}

	private boolean jj_3R_161() {
		return jj_scan_token(LONG);
	}

	private boolean jj_3R_332() {
		return jj_3R_354();
	}

	private boolean jj_3R_160() {
		return jj_scan_token(INT);
	}

	private boolean jj_3R_363() {
		return jj_3R_123();
	}

	private boolean jj_3R_331() {
		return jj_3R_353();
	}

	private boolean jj_3R_159() {
		return jj_scan_token(SHORT);
	}

	private boolean jj_3R_330() {
		return jj_3R_352();
	}

	private boolean jj_3R_362() {
		return jj_3R_123();
	}

	private boolean jj_3R_158() {
		return jj_scan_token(BYTE);
	}

	private boolean jj_3R_329() {
		return jj_3R_351();
	}

	private boolean jj_3R_157() {
		return jj_scan_token(CHAR);
	}

	private boolean jj_3R_328() {
		return jj_3R_350();
	}

	private boolean jj_3R_156() {
		return jj_scan_token(BOOLEAN);
	}

	private boolean jj_3R_327() {
		return jj_3R_349();
	}

	private boolean jj_3R_344() {
		if (jj_scan_token(SUPER)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_363()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_3R_94();
	}

	private boolean jj_3R_326() {
		return jj_3R_128();
	}

	private boolean jj_3R_107() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_156()) {
			jj_scanpos = xsp;
			if (jj_3R_157()) {
				jj_scanpos = xsp;
				if (jj_3R_158()) {
					jj_scanpos = xsp;
					if (jj_3R_159()) {
						jj_scanpos = xsp;
						if (jj_3R_160()) {
							jj_scanpos = xsp;
							if (jj_3R_161()) {
								jj_scanpos = xsp;
								if (jj_3R_162()) {
									jj_scanpos = xsp;
									return jj_3R_163();
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_325() {
		return jj_3R_348();
	}

	private boolean jj_3R_343() {
		if (jj_scan_token(EXTENDS)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_362()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_3R_94();
	}

	private boolean jj_3R_314() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_343()) {
			jj_scanpos = xsp;
			return jj_3R_344();
		}
		return false;
	}

	private boolean jj_3_36() {
		return jj_3R_115();
	}

	private boolean jj_3R_229() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_199();
	}

	private boolean jj_3R_306() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_36()) {
			jj_scanpos = xsp;
			if (jj_3R_325()) {
				jj_scanpos = xsp;
				if (jj_3R_326()) {
					jj_scanpos = xsp;
					if (jj_3R_327()) {
						jj_scanpos = xsp;
						if (jj_3R_328()) {
							jj_scanpos = xsp;
							if (jj_3R_329()) {
								jj_scanpos = xsp;
								if (jj_3R_330()) {
									jj_scanpos = xsp;
									if (jj_3R_331()) {
										jj_scanpos = xsp;
										if (jj_3R_332()) {
											jj_scanpos = xsp;
											if (jj_3R_333()) {
												jj_scanpos = xsp;
												if (jj_3R_334()) {
													jj_scanpos = xsp;
													if (jj_3R_335()) {
														jj_scanpos = xsp;
														if (jj_3R_336()) {
															jj_scanpos = xsp;
															if (jj_3R_337()) {
																jj_scanpos = xsp;
																if (jj_3R_338()) {
																	jj_scanpos = xsp;
																	return jj_3R_339();
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_342() {
		if (jj_scan_token(LBRACKET)) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3R_263() {
		if (jj_scan_token(HOOK)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_314()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_312() {
		Token xsp;
		if (jj_3R_342()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_342()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_3R_185();
	}

	private boolean jj_3_33() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_3R_100()) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3_32() {
		return jj_3R_113();
	}

	private boolean jj_3_35() {
		Token xsp;
		if (jj_3_33()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_33()) {
				jj_scanpos = xsp;
				break;
			}
		}
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_34()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_311() {
		return jj_3R_123();
	}

	private boolean jj_3R_232() {
		return jj_3R_263();
	}

	private boolean jj_3R_289() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_311()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3_35()) {
			jj_scanpos = xsp;
			return jj_3R_312();
		}
		return false;
	}

	private boolean jj_3R_313() {
		return jj_3R_123();
	}

	private boolean jj_3R_231() {
		return jj_3R_94();
	}

	private boolean jj_3R_230() {
		return jj_3R_123();
	}

	private boolean jj_3R_199() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_230()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3R_231()) {
			jj_scanpos = xsp;
			return jj_3R_232();
		}
		return false;
	}

	private boolean jj_3R_96() {
		return jj_3R_123();
	}

	private boolean jj_3R_98() {
		return jj_3R_123();
	}

	private boolean jj_3R_292() {
		if (jj_3R_187()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_32()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_138() {
		return jj_scan_token(132);
	}

	private boolean jj_3R_95() {
		return jj_3R_123();
	}

	private boolean jj_3R_386() {
		if (jj_scan_token(THROWS)) return true;
		return jj_3R_401();
	}

	private boolean jj_3_13() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_96()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(LBRACKET)) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3R_137() {
		if (jj_scan_token(LT)) return true;
		if (jj_3R_199()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_229()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_scan_token(GT);
	}

	private boolean jj_3R_97() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_137()) {
			jj_scanpos = xsp;
			return jj_3R_138();
		}
		return false;
	}

	private boolean jj_3R_291() {
		return jj_3R_289();
	}

	private boolean jj_3_16() {
		return jj_3R_97();
	}

	private boolean jj_3_15() {
		if (jj_scan_token(DOT)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_98()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(IDENTIFIER)) return true;
		xsp = jj_scanpos;
		if (jj_3_16()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_462() {
		return jj_3R_464();
	}

	private boolean jj_3R_290() {
		if (jj_3R_97()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_313()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3_12() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_95()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(LBRACKET)) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3R_262() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_290()) jj_scanpos = xsp;
		if (jj_3R_198()) return true;
		xsp = jj_scanpos;
		if (jj_3R_291()) {
			jj_scanpos = xsp;
			return jj_3R_292();
		}
		return false;
	}

	private boolean jj_3R_284() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_100();
	}

	private boolean jj_3_14() {
		return jj_3R_97();
	}

	private boolean jj_3R_198() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_14()) jj_scanpos = xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_15()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_261() {
		if (jj_3R_107()) return true;
		return jj_3R_289();
	}

	private boolean jj_3R_136() {
		if (jj_3R_198()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_13()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_260() {
		return jj_3R_123();
	}

	private boolean jj_3R_135() {
		if (jj_3R_107()) return true;
		Token xsp;
		if (jj_3_12()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_12()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_226() {
		if (jj_scan_token(NEW)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_260()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3R_261()) {
			jj_scanpos = xsp;
			return jj_3R_262();
		}
		return false;
	}

	private boolean jj_3R_94() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_135()) {
			jj_scanpos = xsp;
			return jj_3R_136();
		}
		return false;
	}

	private boolean jj_3R_126() {
		return jj_3R_107();
	}

	private boolean jj_3_11() {
		return jj_3R_94();
	}

	private boolean jj_3R_464() {
		if (jj_scan_token(_DEFAULT)) return true;
		return jj_3R_121();
	}

	private boolean jj_3R_87() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_11()) {
			jj_scanpos = xsp;
			return jj_3R_126();
		}
		return false;
	}

	private boolean jj_3R_247() {
		if (jj_3R_100()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_284()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_220() {
		return jj_3R_247();
	}

	private boolean jj_3R_187() {
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_220()) jj_scanpos = xsp;
		return jj_scan_token(RPAREN);
	}

	private boolean jj_3R_127() {
		return jj_scan_token(STATIC);
	}

	private boolean jj_3R_457() {
		if (jj_3R_87()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_scan_token(RPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_462()) jj_scanpos = xsp;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_89() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_127()) jj_scanpos = xsp;
		return jj_3R_128();
	}

	private boolean jj_3_47() {
		if (jj_3R_87()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return jj_scan_token(LPAREN);
	}

	private boolean jj_3R_445() {
		return jj_3R_302();
	}

	private boolean jj_3R_219() {
		return jj_3R_246();
	}

	private boolean jj_3R_286() {
		return jj_scan_token(NULL);
	}

	private boolean jj_3R_308() {
		return jj_scan_token(FALSE);
	}

	private boolean jj_3R_444() {
		return jj_3R_300();
	}

	private boolean jj_3R_184() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_219()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_307() {
		return jj_scan_token(TRUE);
	}

	private boolean jj_3R_443() {
		return jj_3R_299();
	}

	private boolean jj_3_9() {
		if (jj_3R_92()) return true;
		return jj_scan_token(DOT);
	}

	private boolean jj_3R_442() {
		return jj_3R_298();
	}

	private boolean jj_3R_441() {
		return jj_3R_457();
	}

	private boolean jj_3R_93() {
		return jj_3R_97();
	}

	private boolean jj_3R_189() {
		return jj_3R_97();
	}

	private boolean jj_3_46() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_121();
	}

	private boolean jj_3R_285() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_307()) {
			jj_scanpos = xsp;
			return jj_3R_308();
		}
		return false;
	}

	private boolean jj_3_10() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_93()) jj_scanpos = xsp;
		if (jj_scan_token(THIS)) return true;
		return jj_scan_token(LPAREN);
	}

	private boolean jj_3R_254() {
		return jj_3R_286();
	}

	private boolean jj_3R_188() {
		if (jj_3R_92()) return true;
		return jj_scan_token(DOT);
	}

	private boolean jj_3R_427() {
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_253() {
		return jj_3R_285();
	}

	private boolean jj_3R_428() {
		if (jj_3R_116()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_441()) {
			jj_scanpos = xsp;
			if (jj_3R_442()) {
				jj_scanpos = xsp;
				if (jj_3R_443()) {
					jj_scanpos = xsp;
					if (jj_3R_444()) {
						jj_scanpos = xsp;
						return jj_3R_445();
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_132() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_188()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_189()) jj_scanpos = xsp;
		if (jj_scan_token(SUPER)) return true;
		if (jj_3R_187()) return true;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_186() {
		return jj_3R_97();
	}

	private boolean jj_3R_252() {
		return jj_scan_token(STRING_LITERAL);
	}

	private boolean jj_3R_131() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_186()) jj_scanpos = xsp;
		if (jj_scan_token(THIS)) return true;
		if (jj_3R_187()) return true;
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_409() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_427()) {
			jj_scanpos = xsp;
			return jj_3R_428();
		}
		return false;
	}

	private boolean jj_3R_251() {
		return jj_scan_token(CHARACTER_LITERAL);
	}

	private boolean jj_3R_250() {
		return jj_scan_token(FLOATING_POINT_LITERAL);
	}

	private boolean jj_3R_399() {
		return jj_3R_409();
	}

	private boolean jj_3R_91() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_131()) {
			jj_scanpos = xsp;
			return jj_3R_132();
		}
		return false;
	}

	private boolean jj_3R_249() {
		return jj_scan_token(LONG_LITERAL);
	}

	private boolean jj_3R_384() {
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_399()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3R_248() {
		return jj_scan_token(INTEGER_LITERAL);
	}

	private boolean jj_3_7() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_90();
	}

	private boolean jj_3R_112() {
		return jj_3R_97();
	}

	private boolean jj_3_8() {
		return jj_3R_91();
	}

	private boolean jj_3_31() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_112()) jj_scanpos = xsp;
		return jj_scan_token(IDENTIFIER);
	}

	private boolean jj_3R_221() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_248()) {
			jj_scanpos = xsp;
			if (jj_3R_249()) {
				jj_scanpos = xsp;
				if (jj_3R_250()) {
					jj_scanpos = xsp;
					if (jj_3R_251()) {
						jj_scanpos = xsp;
						if (jj_3R_252()) {
							jj_scanpos = xsp;
							if (jj_3R_253()) {
								jj_scanpos = xsp;
								return jj_3R_254();
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_167() {
		return jj_scan_token(ELLIPSIS);
	}

	private boolean jj_3R_166() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_3R_100()) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3R_238() {
		return jj_3R_187();
	}

	private boolean jj_3R_300() {
		if (jj_scan_token(AT)) return true;
		if (jj_scan_token(INTERFACE)) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return jj_3R_384();
	}

	private boolean jj_3R_237() {
		return jj_3R_97();
	}

	private boolean jj_3R_387() {
		return jj_3R_91();
	}

	private boolean jj_3R_340() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_110();
	}

	private boolean jj_3R_205() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_237()) jj_scanpos = xsp;
		if (jj_scan_token(IDENTIFIER)) return true;
		xsp = jj_scanpos;
		if (jj_3R_238()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_204() {
		return jj_3R_226();
	}

	private boolean jj_3R_324() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_323();
	}

	private boolean jj_3R_320() {
		return jj_3R_125();
	}

	private boolean jj_3R_203() {
		return jj_scan_token(THIS);
	}

	private boolean jj_3R_347() {
		if (jj_3R_121()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_46()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_301() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_320()) jj_scanpos = xsp;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_3R_385()) return true;
		xsp = jj_scanpos;
		if (jj_3R_386()) jj_scanpos = xsp;
		if (jj_scan_token(LBRACE)) return true;
		xsp = jj_scanpos;
		if (jj_3R_387()) jj_scanpos = xsp;
		if (jj_3R_184()) return true;
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3R_278() {
		return jj_3R_304();
	}

	private boolean jj_3R_287() {
		return jj_3R_309();
	}

	private boolean jj_3R_213() {
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_347()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(88)) jj_scanpos = xsp;
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3R_389() {
		if (jj_scan_token(LBRACKET)) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3R_410() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_110();
	}

	private boolean jj_3R_165() {
		if (jj_scan_token(DOT)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_203()) {
			jj_scanpos = xsp;
			if (jj_3R_204()) {
				jj_scanpos = xsp;
				return jj_3R_205();
			}
		}
		return false;
	}

	private boolean jj_3R_177() {
		return jj_3R_151();
	}

	private boolean jj_3R_176() {
		return jj_3R_213();
	}

	private boolean jj_3R_175() {
		return jj_3R_123();
	}

	private boolean jj_3R_109() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_165()) {
			jj_scanpos = xsp;
			return jj_3R_166();
		}
		return false;
	}

	private boolean jj_3R_164() {
		if (jj_scan_token(DOT)) return true;
		return jj_scan_token(SUPER);
	}

	private boolean jj_3R_121() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_175()) {
			jj_scanpos = xsp;
			if (jj_3R_176()) {
				jj_scanpos = xsp;
				return jj_3R_177();
			}
		}
		return false;
	}

	private boolean jj_3R_110() {
		if (jj_3R_116()) return true;
		if (jj_3R_87()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_167()) jj_scanpos = xsp;
		return jj_3R_168();
	}

	private boolean jj_3_30() {
		return jj_3R_109();
	}

	private boolean jj_3R_227() {
		return jj_3R_125();
	}

	private boolean jj_3R_323() {
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(ASSIGN)) return true;
		return jj_3R_121();
	}

	private boolean jj_3R_108() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_30()) {
			jj_scanpos = xsp;
			return jj_3R_164();
		}
		return false;
	}

	private boolean jj_3R_206() {
		if (jj_scan_token(LBRACKET)) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3R_341() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_168();
	}

	private boolean jj_3R_228() {
		return jj_3R_187();
	}

	private boolean jj_3R_322() {
		if (jj_3R_90()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_7()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_310() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_168()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_341()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_197() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_228()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_304() {
		if (jj_3R_323()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_324()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3_29() {
		if (jj_3R_111()) return true;
		return jj_scan_token(DOUBLECOLON);
	}

	private boolean jj_3_28() {
		if (jj_3R_111()) return true;
		if (jj_scan_token(DOT)) return true;
		return jj_scan_token(CLASS);
	}

	private boolean jj_3R_309() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_110()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_340()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_288() {
		return jj_3R_310();
	}

	private boolean jj_3R_215() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_119()) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_121()) return true;
		return jj_scan_token(RPAREN);
	}

	private boolean jj_3R_196() {
		if (jj_3R_111()) return true;
		if (jj_scan_token(DOUBLECOLON)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_227()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(78)) {
			jj_scanpos = xsp;
			return jj_scan_token(43);
		}
		return false;
	}

	private boolean jj_3R_400() {
		if (jj_3R_110()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_410()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_195() {
		if (jj_3R_111()) return true;
		if (jj_scan_token(DOT)) return true;
		return jj_scan_token(CLASS);
	}

	private boolean jj_3R_120() {
		if (jj_scan_token(IDENTIFIER)) return true;
		return jj_scan_token(ASSIGN);
	}

	private boolean jj_3R_194() {
		return jj_3R_226();
	}

	private boolean jj_3R_385() {
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_400()) jj_scanpos = xsp;
		return jj_scan_token(RPAREN);
	}

	private boolean jj_3_27() {
		return jj_3R_110();
	}

	private boolean jj_3R_216() {
		if (jj_scan_token(AT)) return true;
		return jj_3R_119();
	}

	private boolean jj_3R_391() {
		return jj_3R_128();
	}

	private boolean jj_3_45() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_119()) return true;
		return jj_scan_token(LPAREN);
	}

	private boolean jj_3R_390() {
		if (jj_scan_token(THROWS)) return true;
		return jj_3R_401();
	}

	private boolean jj_3R_259() {
		if (jj_3R_100()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_288()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_258() {
		if (jj_3R_110()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_287()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3_43() {
		if (jj_scan_token(SEMICOLON)) return true;
		return jj_3R_117();
	}

	private boolean jj_3_44() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_119()) return true;
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_120()) {
			jj_scanpos = xsp;
			return jj_scan_token(82);
		}
		return false;
	}

	private boolean jj_3R_214() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_119()) return true;
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_278()) jj_scanpos = xsp;
		return jj_scan_token(RPAREN);
	}

	private boolean jj_3R_321() {
		return jj_3R_125();
	}

	private boolean jj_3R_225() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_258()) {
			jj_scanpos = xsp;
			return jj_3R_259();
		}
		return false;
	}

	private boolean jj_3R_303() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_321()) jj_scanpos = xsp;
		if (jj_3R_111()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_3R_385()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_389()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3R_390()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_391()) {
			jj_scanpos = xsp;
			return jj_scan_token(87);
		}
		return false;
	}

	private boolean jj_3R_257() {
		return jj_3R_125();
	}

	private boolean jj_3R_180() {
		return jj_3R_216();
	}

	private boolean jj_3R_179() {
		return jj_3R_215();
	}

	private boolean jj_3R_193() {
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_225()) jj_scanpos = xsp;
		return jj_scan_token(RPAREN);
	}

	private boolean jj_3R_201() {
		if (jj_scan_token(BIT_AND)) return true;
		return jj_3R_94();
	}

	private boolean jj_3R_223() {
		if (jj_scan_token(DOUBLECOLON)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_257()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(78)) {
			jj_scanpos = xsp;
			return jj_scan_token(43);
		}
		return false;
	}

	private boolean jj_3R_178() {
		return jj_3R_214();
	}

	private boolean jj_3R_224() {
		return jj_3R_187();
	}

	private boolean jj_3R_185() {
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_322()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(88)) jj_scanpos = xsp;
		return jj_scan_token(RBRACE);
	}

	private boolean jj_3R_123() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_178()) {
			jj_scanpos = xsp;
			if (jj_3R_179()) {
				jj_scanpos = xsp;
				return jj_3R_180();
			}
		}
		return false;
	}

	private boolean jj_3R_256() {
		return jj_3R_187();
	}

	private boolean jj_3R_152() {
		return false;
	}

	private boolean jj_3R_222() {
		if (jj_scan_token(DOT)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_255()) jj_scanpos = xsp;
		if (jj_scan_token(IDENTIFIER)) return true;
		xsp = jj_scanpos;
		if (jj_3R_256()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_255() {
		return jj_3R_97();
	}

	private boolean jj_3R_130() {
		return jj_3R_100();
	}

	private boolean jj_3_26() {
		return jj_3R_109();
	}

	private boolean jj_3R_129() {
		return jj_3R_185();
	}

	private boolean jj_3R_208() {
		if (jj_scan_token(ASSIGN)) return true;
		return jj_3R_90();
	}

	private boolean jj_3R_192() {
		if (jj_scan_token(SUPER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_222()) {
			jj_scanpos = xsp;
			if (jj_3R_223()) {
				jj_scanpos = xsp;
				return jj_3R_224();
			}
		}
		return false;
	}

	private boolean jj_3R_191() {
		return jj_scan_token(THIS);
	}

	private boolean jj_3R_153() {
		return false;
	}

	private boolean jj_3R_190() {
		return jj_3R_221();
	}

	private boolean jj_3R_102() {
		jj_lookingAhead = true;
		jj_semLA = getToken(1).kind == GT && ((GTToken) getToken(1)).realKind == RSIGNEDSHIFT;
		jj_lookingAhead = false;
		if (!jj_semLA || jj_3R_152()) return true;
		if (jj_scan_token(GT)) return true;
		return jj_scan_token(GT);
	}

	private boolean jj_3R_90() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_129()) {
			jj_scanpos = xsp;
			return jj_3R_130();
		}
		return false;
	}

	private boolean jj_3R_134() {
		return jj_3R_109();
	}

	private boolean jj_3R_133() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_190()) {
			jj_scanpos = xsp;
			if (jj_3R_191()) {
				jj_scanpos = xsp;
				if (jj_3R_192()) {
					jj_scanpos = xsp;
					if (jj_3R_193()) {
						jj_scanpos = xsp;
						if (jj_3R_194()) {
							jj_scanpos = xsp;
							if (jj_3R_195()) {
								jj_scanpos = xsp;
								if (jj_3R_196()) {
									jj_scanpos = xsp;
									return jj_3R_197();
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean jj_3R_103() {
		jj_lookingAhead = true;
		jj_semLA = getToken(1).kind == GT && ((GTToken) getToken(1)).realKind == RUNSIGNEDSHIFT;
		jj_lookingAhead = false;
		if (!jj_semLA || jj_3R_153()) return true;
		if (jj_scan_token(GT)) return true;
		if (jj_scan_token(GT)) return true;
		return jj_scan_token(GT);
	}

	private boolean jj_3R_168() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_206()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3_25() {
		return jj_3R_108();
	}

	private boolean jj_3R_455() {
		if (jj_3R_117()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_43()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_172() {
		if (jj_3R_168()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_208()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3R_92() {
		if (jj_3R_133()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_134()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_88() {
		if (jj_scan_token(LBRACKET)) return true;
		return jj_scan_token(RBRACKET);
	}

	private boolean jj_3_42() {
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_456() {
		if (jj_scan_token(BIT_OR)) return true;
		return jj_3R_87();
	}

	private boolean jj_3R_440() {
		if (jj_scan_token(FINALLY)) return true;
		return jj_3R_128();
	}

	private boolean jj_3R_438() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_455()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_42()) jj_scanpos = xsp;
		return jj_scan_token(RPAREN);
	}

	private boolean jj_3R_426() {
		if (jj_scan_token(FINALLY)) return true;
		return jj_3R_128();
	}

	private boolean jj_3R_294() {
		if (jj_3R_133()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_25()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	private boolean jj_3R_388() {
		if (jj_scan_token(COMMA)) return true;
		return jj_3R_172();
	}

	private boolean jj_3R_439() {
		if (jj_scan_token(CATCH)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_116()) return true;
		if (jj_3R_87()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_456()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_3R_168()) return true;
		if (jj_scan_token(RPAREN)) return true;
		return jj_3R_128();
	}

	private boolean jj_3R_302() {
		if (jj_3R_87()) return true;
		if (jj_3R_172()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_388()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3R_155() {
		if (jj_3R_94()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_201()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(RPAREN)) return true;
		return jj_3R_202();
	}

	private boolean jj_3R_241() {
		return jj_scan_token(SEMICOLON);
	}

	private boolean jj_3_24() {
		if (jj_3R_107()) return true;
		if (jj_scan_token(RPAREN)) return true;
		return jj_3R_174();
	}

	private boolean jj_3_5() {
		if (jj_3R_87()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_88()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_scan_token(88)) {
			jj_scanpos = xsp;
			if (jj_scan_token(91)) {
				jj_scanpos = xsp;
				return jj_scan_token(87);
			}
		}
		return false;
	}

	private boolean jj_3R_423() {
		return jj_3R_100();
	}

	private boolean jj_3R_86() {
		return jj_3R_125();
	}

	private boolean jj_3R_154() {
		return jj_3R_123();
	}

	private boolean jj_3R_425() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_439()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3R_440()) jj_scanpos = xsp;
		return false;
	}

	private boolean jj_3_4() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_86()) jj_scanpos = xsp;
		if (jj_scan_token(IDENTIFIER)) return true;
		return jj_scan_token(LPAREN);
	}

	private boolean jj_3R_217() {
		return jj_3R_245();
	}

	private boolean jj_3R_104() {
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_154()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3_24()) {
			jj_scanpos = xsp;
			return jj_3R_155();
		}
		return false;
	}

	private boolean jj_3R_277() {
		return jj_3R_303();
	}

	private boolean jj_3R_422() {
		return jj_scan_token(IDENTIFIER);
	}

	private boolean jj_3R_424() {
		return jj_3R_438();
	}

	private boolean jj_3R_276() {
		return jj_3R_302();
	}

	private boolean jj_3R_271() {
		if (jj_scan_token(_DEFAULT)) return true;
		return jj_3R_116();
	}

	private boolean jj_3R_361() {
		if (jj_scan_token(TRY)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_424()) jj_scanpos = xsp;
		if (jj_3R_128()) return true;
		xsp = jj_scanpos;
		if (jj_3R_425()) {
			jj_scanpos = xsp;
			return jj_3R_426();
		}
		return false;
	}

	private boolean jj_3R_275() {
		return jj_3R_301();
	}

	private boolean jj_3R_274() {
		return jj_3R_300();
	}

	private boolean jj_3R_273() {
		return jj_3R_299();
	}

	private boolean jj_3R_106() {
		return jj_scan_token(DECR);
	}

	private boolean jj_3_23() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_105()) {
			jj_scanpos = xsp;
			return jj_3R_106();
		}
		return false;
	}

	private boolean jj_3R_105() {
		return jj_scan_token(INCR);
	}

	private boolean jj_3R_272() {
		return jj_3R_298();
	}

	private boolean jj_3_22() {
		return jj_3R_104();
	}

	private boolean jj_3R_421() {
		return jj_scan_token(IDENTIFIER);
	}

	private boolean jj_3R_267() {
		if (jj_3R_294()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_23()) jj_scanpos = xsp;
		return false;
	}

	public ASTParserTokenManager token_source;
	JavaCharStream jj_input_stream;
	public Token token;
	public Token jj_nt;
	private int jj_ntk;
	private Token jj_scanpos, jj_lastpos;
	private int jj_la;
	private boolean jj_lookingAhead = false;
	private boolean jj_semLA;
	private int jj_gen;
	private final int[] jj_la1 = new int[175];
	private static int[] jj_la1_0;
	private static int[] jj_la1_1;
	private static int[] jj_la1_2;
	private static int[] jj_la1_3;
	private static int[] jj_la1_4;
	static {
		jj_la1_init_0();
		jj_la1_init_1();
		jj_la1_init_2();
		jj_la1_init_3();
		jj_la1_init_4();
	}

	private static void jj_la1_init_0() {
		jj_la1_0 = new int[] { 0x0, 0x48101000, 0x1, 0x0, 0x0, 0x0, 0x40001000, 0x8100000, 0x48101000, 0x100000, 0x0, 0x10000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x4a995000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10000000, 0x0, 0x0, 0x0, 0x4a995000, 0x800000, 0x8100000, 0x2094000, 0x4a995000, 0x0, 0x0, 0x0, 0x22094000, 0x22094000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x42095000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x22094000, 0x6359f000, 0x0, 0x2094000, 0x0, 0x0, 0x2094000, 0x0, 0x0, 0x0, 0x0, 0x2094000, 0x0, 0x0, 0x10000000, 0x10000000, 0x2094000, 0x2094000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x22094000, 0x0, 0x0, 0x22094000, 0x0, 0x0, 0x0, 0x2094000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x22094000, 0x62095000, 0x0, 0x0, 0x0, 0x20000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x20000000, 0x20000000, 0x22094000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x2094000, 0x0, 0x0, 0x0, 0x0, 0x2349e000, 0x0, 0x2349e000, 0x0, 0x22094000, 0x0, 0x0, 0x0, 0x0, 0x22094000, 0x820000, 0x820000, 0x4000000, 0x62095000, 0x22094000, 0x22094000, 0x62095000, 0x22094000, 0x0, 0x0, 0x0, 0x22094000, 0x0, 0x40000, 0x0, 0x80000000, 0x0, 0x0, 0x0, 0x22094000, 0x22094000, 0x0, 0x4a195000, 0xa194000, 0x4a195000, 0x800000 };
	}

	private static void jj_la1_init_1() {
		jj_la1_1 = new int[] { 0x20, 0x8899c500, 0x0, 0x0, 0x80000, 0x0, 0x8899c400, 0x100, 0x8899c500, 0x100, 0x0, 0x0, 0x10, 0x0, 0x0, 0x0, 0x0, 0x10, 0x0, 0x0, 0xc89dc781, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0xc89dc781, 0x0, 0x100, 0x40040281, 0xc89dc781, 0x0, 0x0, 0x0, 0x51241a81, 0x51241a81, 0x0, 0x0, 0x0, 0x4000000, 0x0, 0x0, 0x889dc681, 0x0, 0x0, 0x0, 0x0, 0x4000000, 0x0, 0x0, 0x51241a81, 0xfbffdf8b, 0x80000, 0x40281, 0x0, 0x0, 0x40281, 0x0, 0x0, 0x0, 0x0, 0x40281, 0x0, 0x0, 0x200000, 0x200000, 0x40281, 0x40040281, 0x0, 0x0, 0x0, 0x0, 0x800, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x40, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x51241a81, 0x0, 0x0, 0x51241a81, 0x0, 0x0, 0x0, 0x40281, 0x0, 0x0, 0x0, 0x800, 0x0, 0x0, 0x0, 0x51241a81, 0xd9bdde81, 0x0, 0x800, 0x0, 0x11201800, 0x0, 0x0, 0x0, 0x0, 0x1000800, 0x0, 0x10001000, 0x10000000, 0x51241a81, 0x0, 0x0, 0x0, 0x0, 0x0, 0x40281, 0x0, 0x0, 0x0, 0x0, 0x73e61a8b, 0x0, 0x73e61a8b, 0x0, 0x51241a81, 0x0, 0x800, 0x0, 0x0, 0x51241a81, 0x0, 0x0, 0x0, 0xd9bdde81, 0x51241a81, 0x51241a81, 0xd9bdde81, 0x51241a81, 0x0, 0x0, 0x0, 0x51241a81, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x51241a81, 0x51241a81, 0x0, 0x889dc781, 0x40381, 0x889dc781, 0x0 };
	}

	private static void jj_la1_init_2() {
		jj_la1_2 = new int[] { 0x0, 0x4800000, 0x0, 0x4000000, 0x0, 0x2000000, 0x4000000, 0x4000000, 0x4800000, 0x0, 0x10000000, 0x0, 0x0, 0x4000000, 0x1000000, 0x4000000, 0x1000000, 0x0, 0x4004000, 0x1000000, 0x14884000, 0x800000, 0x4000000, 0x20000, 0x80000, 0x4000000, 0x1000000, 0x4000000, 0x0, 0x4000000, 0x0, 0x4000000, 0x14884000, 0x0, 0x4000000, 0x10004000, 0x14804000, 0x1000000, 0x8000000, 0x200000, 0x600a7086, 0x600a7086, 0x1000000, 0x10000000, 0x200000, 0x0, 0x880000, 0x1000000, 0x4004000, 0x1000000, 0x1000000, 0x0, 0x10000000, 0x0, 0x10000000, 0x10000000, 0x10027086, 0x48a7087, 0x0, 0x0, 0x4000000, 0x4000000, 0x4000, 0x4000000, 0x1000000, 0x10000000, 0x4000000, 0x80004000, 0x4000000, 0x4000000, 0x0, 0x0, 0x0, 0x4000, 0x4000000, 0x1000000, 0x4000000, 0x10000000, 0x4000, 0x0, 0x8000000, 0x8000000, 0x80000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10000000, 0x10000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x60027086, 0x60000000, 0x60000000, 0x27086, 0x0, 0x4000000, 0x0, 0x4000, 0x10000000, 0x20000, 0x10000000, 0x4000, 0x2020000, 0x1000000, 0x1000000, 0x60027086, 0x64027086, 0x10000000, 0x4000, 0x20000, 0x23086, 0x4000, 0x2000000, 0x10000000, 0x20000, 0x0, 0x2200000, 0x3086, 0x0, 0x60027086, 0x1000000, 0x4000000, 0x4000000, 0x10000000, 0x4220000, 0x10004000, 0x4000000, 0x4000000, 0x200000, 0x200000, 0x8a7087, 0x0, 0x8a7087, 0x1000000, 0x600a7086, 0x10000000, 0x4000, 0x8000000, 0x8000000, 0x27086, 0x0, 0x0, 0x0, 0x64027086, 0x60027086, 0x60027086, 0x64827086, 0x60027086, 0x1000000, 0x4000, 0x4000, 0x60027086, 0x20000, 0x0, 0x0, 0x0, 0x4000000, 0x4000, 0x1000000, 0x640a7086, 0x640a7086, 0x1000000, 0x4804000, 0x4004000, 0x4804000, 0x0 };
	}

	private static void jj_la1_init_3() {
		jj_la1_3 = new int[] { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x2000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x780, 0x780, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x20000000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x180, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0xc0000000, 0xdffc0000, 0x1ffc0000, 0x0, 0x20, 0x40, 0x4000, 0x8000, 0x2000, 0x12, 0x12, 0x0, 0xc, 0xc, 0x20000, 0x600, 0x600, 0x11800, 0x11800, 0x600, 0x780, 0x0, 0x0, 0x0, 0x180, 0x0, 0x2000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x80000000, 0x0, 0x0, 0x780, 0x780, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x780, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x180, 0x1, 0x180, 0x0, 0x780, 0x0, 0x0, 0xdffc0180, 0xdffc0180, 0x100, 0x0, 0x0, 0x0, 0x780, 0x780, 0x780, 0x780, 0x780, 0x0, 0x0, 0x0, 0x780, 0x0, 0x0, 0x4000, 0x0, 0x0, 0x0, 0x0, 0x780, 0x780, 0x0, 0x0, 0x0, 0x0, 0x0 };
	}

	private static void jj_la1_init_4() {
		jj_la1_4 = new int[] { 0x0, 0x0, 0x8, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10, 0x10, 0x10, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x4, 0x4, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x10, 0x0, 0x10, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
	}

	private final JJCalls[] jj_2_rtns = new JJCalls[47];
	private boolean jj_rescan = false;
	private int jj_gc = 0;

	public ASTParser(java.io.InputStream stream) {
		this(stream, null);
	}

	public ASTParser(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream = new JavaCharStream(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source = new ASTParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 175; i++) jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	}

	public void ReInit(java.io.InputStream stream) {
		ReInit(stream, null);
	}

	public void ReInit(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream.ReInit(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 175; i++) jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	}

	public ASTParser(java.io.Reader stream) {
		jj_input_stream = new JavaCharStream(stream, 1, 1);
		token_source = new ASTParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 175; i++) jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	}

	public void ReInit(java.io.Reader stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 175; i++) jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	}

	public ASTParser(ASTParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 175; i++) jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	}

	public void ReInit(ASTParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 175; i++) jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
	}

	private Token jj_consume_token(int kind) throws ParseException {
		Token oldToken;
		if ((oldToken = token).next != null) token = token.next;

		else token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		if (token.kind == kind) {
			jj_gen++;
			if (++jj_gc > 100) {
				jj_gc = 0;
				for (int i = 0; i < jj_2_rtns.length; i++) {
					JJCalls c = jj_2_rtns[i];
					while (c != null) {
						if (c.gen < jj_gen) c.first = null;
						c = c.next;
					}
				}
			}
			return token;
		}
		token = oldToken;
		jj_kind = kind;
		throw generateParseException();
	}

	private static final class LookaheadSuccess extends java.lang.Error {
}

private final LookaheadSuccess jj_ls = new LookaheadSuccess();

private boolean jj_scan_token(int kind) {
	if (jj_scanpos == jj_lastpos) {
		jj_la--;
		if (jj_scanpos.next == null) {
			jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
		} else {
			jj_lastpos = jj_scanpos = jj_scanpos.next;
		}
	} else {
		jj_scanpos = jj_scanpos.next;
	}
	if (jj_rescan) {
		int i = 0;
		Token tok = token;
		while (tok != null && tok != jj_scanpos) {
			i++;
			tok = tok.next;
		}
		if (tok != null) jj_add_error_token(kind, i);
	}
	if (jj_scanpos.kind != kind) return true;
	if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	return false;
}

public final Token getNextToken() {
	if (token.next != null) token = token.next;

	else token = token.next = token_source.getNextToken();
	jj_ntk = -1;
	jj_gen++;
	return token;
}

public final Token getToken(int index) {
	Token t = jj_lookingAhead ? jj_scanpos : token;
	for (int i = 0; i < index; i++) {
		if (t.next != null) t = t.next;

		else t = t.next = token_source.getNextToken();
	}
	return t;
}

private int jj_ntk() {
	if ((jj_nt = token.next) == null) return (jj_ntk = (token.next = token_source.getNextToken()).kind);

	else return (jj_ntk = jj_nt.kind);
}

private final java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
private int[] jj_expentry;
private int jj_kind = -1;
private final int[] jj_lasttokens = new int[100];
private int jj_endpos;

private void jj_add_error_token(int kind, int pos) {
	if (pos >= 100) return;
	if (pos == jj_endpos + 1) {
		jj_lasttokens[jj_endpos++] = kind;
	} else if (jj_endpos != 0) {
		jj_expentry = new int[jj_endpos];
		for (int i = 0; i < jj_endpos; i++) {
			jj_expentry[i] = jj_lasttokens[i];
		}
		jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext(); ) {
			int[] oldentry = (int[]) (it.next());
			if (oldentry.length == jj_expentry.length) {
				for (int i = 0; i < jj_expentry.length; i++) {
					if (oldentry[i] != jj_expentry[i]) {
						continue jj_entries_loop;
					}
				}
				jj_expentries.add(jj_expentry);
				break jj_entries_loop;
			}
		}
		if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	}
}

public ParseException generateParseException() {
	jj_expentries.clear();
	boolean[] la1tokens = new boolean[133];
	if (jj_kind >= 0) {
		la1tokens[jj_kind] = true;
		jj_kind = -1;
	}
	for (int i = 0; i < 175; i++) {
		if (jj_la1[i] == jj_gen) {
			for (int j = 0; j < 32; j++) {
				if ((jj_la1_0[i] & (1 << j)) != 0) {
					la1tokens[j] = true;
				}
				if ((jj_la1_1[i] & (1 << j)) != 0) {
					la1tokens[32 + j] = true;
				}
				if ((jj_la1_2[i] & (1 << j)) != 0) {
					la1tokens[64 + j] = true;
				}
				if ((jj_la1_3[i] & (1 << j)) != 0) {
					la1tokens[96 + j] = true;
				}
				if ((jj_la1_4[i] & (1 << j)) != 0) {
					la1tokens[128 + j] = true;
				}
			}
		}
	}
	for (int i = 0; i < 133; i++) {
		if (la1tokens[i]) {
			jj_expentry = new int[1];
			jj_expentry[0] = i;
			jj_expentries.add(jj_expentry);
		}
	}
	jj_endpos = 0;
	jj_rescan_token();
	jj_add_error_token(0, 0);
	int[][] exptokseq = new int[jj_expentries.size()][];
	for (int i = 0; i < jj_expentries.size(); i++) {
		exptokseq[i] = jj_expentries.get(i);
	}
	return new ParseException(token, exptokseq, tokenImage);
}

public final void enable_tracing() {}

public final void disable_tracing() {}

private void jj_rescan_token() {
	jj_rescan = true;
	for (int i = 0; i < 47; i++) {
		try {
			JJCalls p = jj_2_rtns[i];
			do {
				if (p.gen > jj_gen) {
					jj_la = p.arg;
					jj_lastpos = jj_scanpos = p.first;
					switch(i) {
						case 0:
								jj_3_1();
								break;
						case 1:
								jj_3_2();
								break;
						case 2:
								jj_3_3();
								break;
						case 3:
								jj_3_4();
								break;
						case 4:
								jj_3_5();
								break;
						case 5:
								jj_3_6();
								break;
						case 6:
								jj_3_7();
								break;
						case 7:
								jj_3_8();
								break;
						case 8:
								jj_3_9();
								break;
						case 9:
								jj_3_10();
								break;
						case 10:
								jj_3_11();
								break;
						case 11:
								jj_3_12();
								break;
						case 12:
								jj_3_13();
								break;
						case 13:
								jj_3_14();
								break;
						case 14:
								jj_3_15();
								break;
						case 15:
								jj_3_16();
								break;
						case 16:
								jj_3_17();
								break;
						case 17:
								jj_3_18();
								break;
						case 18:
								jj_3_19();
								break;
						case 19:
								jj_3_20();
								break;
						case 20:
								jj_3_21();
								break;
						case 21:
								jj_3_22();
								break;
						case 22:
								jj_3_23();
								break;
						case 23:
								jj_3_24();
								break;
						case 24:
								jj_3_25();
								break;
						case 25:
								jj_3_26();
								break;
						case 26:
								jj_3_27();
								break;
						case 27:
								jj_3_28();
								break;
						case 28:
								jj_3_29();
								break;
						case 29:
								jj_3_30();
								break;
						case 30:
								jj_3_31();
								break;
						case 31:
								jj_3_32();
								break;
						case 32:
								jj_3_33();
								break;
						case 33:
								jj_3_34();
								break;
						case 34:
								jj_3_35();
								break;
						case 35:
								jj_3_36();
								break;
						case 36:
								jj_3_37();
								break;
						case 37:
								jj_3_38();
								break;
						case 38:
								jj_3_39();
								break;
						case 39:
								jj_3_40();
								break;
						case 40:
								jj_3_41();
								break;
						case 41:
								jj_3_42();
								break;
						case 42:
								jj_3_43();
								break;
						case 43:
								jj_3_44();
								break;
						case 44:
								jj_3_45();
								break;
						case 45:
								jj_3_46();
								break;
						case 46:
								jj_3_47();
								break;
					}
				}
				p = p.next;
			} while (p != null);
		} catch (LookaheadSuccess ls) {
		}
	}
	jj_rescan = false;
}

private void jj_save(int index, int xla) {
	JJCalls p = jj_2_rtns[index];
	while (p.gen > jj_gen) {
		if (p.next == null) {
			p = p.next = new JJCalls();
			break;
		}
		p = p.next;
	}
	p.gen = jj_gen + xla - jj_la;
	p.first = token;
	p.arg = xla;
}

static final class JJCalls {
	int gen;
	Token first;
	int arg;
	JJCalls next;
}

}
