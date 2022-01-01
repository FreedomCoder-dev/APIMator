package org.walkmod.javalang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.body.BodyDeclaration;
import org.walkmod.javalang.ast.body.InitializerDeclaration;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.expr.NameExpr;
import org.walkmod.javalang.ast.expr.ThisExpr;
import org.walkmod.javalang.ast.stmt.BlockStmt;
import org.walkmod.javalang.ast.stmt.Statement;
import org.walkmod.javalang.ast.type.PrimitiveType;
import org.walkmod.javalang.ast.type.PrimitiveType.Primitive;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.ast.type.VoidType;

public class ASTManager {

	public static final PrimitiveType BYTE_TYPE = new PrimitiveType(Primitive.Byte);

	public static final PrimitiveType SHORT_TYPE = new PrimitiveType(Primitive.Short);

	public static final PrimitiveType INT_TYPE = new PrimitiveType(Primitive.Int);

	public static final PrimitiveType LONG_TYPE = new PrimitiveType(Primitive.Long);

	public static final PrimitiveType FLOAT_TYPE = new PrimitiveType(Primitive.Float);

	public static final PrimitiveType DOUBLE_TYPE = new PrimitiveType(Primitive.Double);

	public static final PrimitiveType BOOLEAN_TYPE = new PrimitiveType(Primitive.Boolean);

	public static final PrimitiveType CHAR_TYPE = new PrimitiveType(Primitive.Char);

	public static final VoidType VOID_TYPE = new VoidType();

	public static final ThisExpr THIS = new ThisExpr();

	public static CompilationUnit parse(File file) throws ParseException, IOException {
		return parse(file, "UTF-8");
	}

	public static CompilationUnit parse(File file, String encoding) throws ParseException, IOException {
		Reader reader = new InputStreamReader(new FileInputStream(file), encoding);
		CompilationUnit cu = parse(reader);
		cu.setURI(file.toURI());
		return cu;
	}

	public static CompilationUnit parse(Reader reader) throws ParseException, IOException {
		ASTParser astParser = new ASTParser(reader);
		astParser.jj_input_stream.setTabSize(1);
		CompilationUnit cu = null;
		try {
			cu = astParser.CompilationUnit();
		} finally {
			reader.close();
		}
		return cu;
	}

	public static CompilationUnit parse(String code) throws ParseException {

		return parse(code, false);
	}

	public static CompilationUnit parse(String code, boolean withoutLocation) throws ParseException {

		ASTParser astParser = null;
		StringReader sr = new StringReader(code);

		if (!withoutLocation) {
			astParser = new ASTParser(sr);
			astParser.jj_input_stream.setTabSize(1);
		} else {
			JavaCharStream stream = new JavaCharStream(sr, 1, 1);
			CleanerTokenManager ctm = new CleanerTokenManager(stream);
			astParser = new ASTParser(ctm);
		}

		CompilationUnit cu = null;
		try {
			cu = astParser.CompilationUnit();
		} finally {
			sr.close();
		}
		return cu;
	}

	public static Node parse(Class<?> clazz, String text) throws ParseException {
		return parse(clazz, text, true);
	}

	public static Node parse(Class<?> clazz, String text, boolean withoutLocation) throws ParseException {

		if (text == null || clazz == null) {
			return null;
		}

		ASTParser astParser = null;
		StringReader sr = new StringReader(text);

		if (!withoutLocation) {
			astParser = new ASTParser(sr);
			astParser.jj_input_stream.setTabSize(1);
		} else {
			JavaCharStream stream = new JavaCharStream(sr, 1, 1);
			CleanerTokenManager ctm = new CleanerTokenManager(stream);
			astParser = new ASTParser(ctm);
		}

		Node result = null;
		if (clazz.equals(Type.class)) {
			text = text.replace("$", ".");
			result = astParser.Type();
		} else if (clazz.equals(NameExpr.class)) {
			result = astParser.Name();
		} else if (clazz.equals(BlockStmt.class)) {
			result = astParser.Block();
		} else if (BodyDeclaration.class.isAssignableFrom(clazz)) {
			if (InitializerDeclaration.class.isAssignableFrom(clazz)) {
				result = astParser.ClassOrInterfaceBodyDeclaration(false);
			} else {
				result = astParser.ClassOrInterfaceBodyDeclaration(true);
			}
		} else if (Expression.class.isAssignableFrom(clazz)) {
			result = astParser.Expression();
		} else if (Statement.class.isAssignableFrom(clazz)) {
			result = astParser.BlockStatement();
		} else {
			Method method = null;
			try {
				method = astParser.getClass().getMethod(clazz.getSimpleName());
			} catch (Exception e) {
				throw new ParseException("The " + clazz.getSimpleName() + " cannot be parseable");
			}
			try {

				try {
					result = (Node) method.invoke(astParser);

				} catch (IllegalAccessException e) {
					throw new ParseException("The " + clazz.getSimpleName() + " cannot be parseable");
				} catch (IllegalArgumentException e) {
					throw new ParseException("The " + clazz.getSimpleName() + " cannot be parseable");
				} catch (InvocationTargetException e) {
					throw (ParseException) (e.getTargetException());
				}
			} finally {
				sr.close();
			}
		}
		return result;
	}
}
