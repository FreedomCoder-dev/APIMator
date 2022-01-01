package org.walkmod.javalang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.walkmod.javalang.ast.CompilationUnit;

public final class JavaParser {

	private static ASTParser parser;

	private static boolean cacheParser = true;

	private JavaParser() {}

	public static void setCacheParser(boolean value) {
		cacheParser = value;
		if (!value) {
			parser = null;
		}
	}

	public static CompilationUnit parse(InputStream in, String encoding) throws ParseException {
		if (cacheParser) {
			if (parser == null) {
				parser = new ASTParser(in, encoding);
			} else {
				parser.reset(in, encoding);
			}
			return parser.CompilationUnit();
		}
		return new ASTParser(in, encoding).CompilationUnit();
	}

	public static CompilationUnit parse(InputStream in) throws ParseException {
		return parse(in, null);
	}

	public static CompilationUnit parse(File file, String encoding) throws ParseException, IOException {
		FileInputStream in = new FileInputStream(file);
		try {
			return parse(in, encoding);
		} finally {
			in.close();
		}
	}

	public static CompilationUnit parse(File file) throws ParseException, IOException {
		return parse(file, null);
	}
}
