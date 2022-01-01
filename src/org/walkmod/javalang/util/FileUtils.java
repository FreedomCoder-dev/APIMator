package org.walkmod.javalang.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.JavaParser;
import org.walkmod.javalang.ParseException;
import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.PackageDeclaration;
import org.walkmod.javalang.ast.body.TypeDeclaration;

public class FileUtils {

	public static CompilationUnit getCompilationUnit(File outputDirectory, PackageDeclaration pd, TypeDeclaration td) throws ParseException, IOException {
		File sourceFile = getSourceFile(outputDirectory, pd, td);
		if (sourceFile.exists()) {
			return JavaParser.parse(sourceFile);
		} else {
			CompilationUnit result = new CompilationUnit();
			result.setPackage(pd);
			return result;
		}
	}

	public static File getSourceFile(File outputDirectory, PackageDeclaration pd, TypeDeclaration td) {
		String pdName = ".";
		if (pd != null) {
			pdName = pd.getName().toString().replace('.', '/');
		}
		return new File(outputDirectory, pdName + "//" + td.getName() + ".java");
	}

	public static File getSourceFile(File outputDirectory, PackageDeclaration pd, String clazzName) {
		String pdName = ".";
		if (pd != null) {
			pdName = pd.getName().toString().replace('.', '/');
		}
		return new File(outputDirectory, pdName + "//" + clazzName + ".java");
	}

	public static String normalizeName(String name) {
		return name.replaceAll("::", ".");
	}

	public static String resolveFile(String qualifiedName) {
		return qualifiedName.replaceAll("::", "/");
	}

	public static void createSourceFile(File parent, File source) throws Exception {
		File owner = source.getParentFile();
		if (!owner.exists()) {
			owner.mkdirs();
		}
		source.createNewFile();
	}

	public static List<String> fileToLines(String filename) throws Exception {
		List<String> lines = new LinkedList<String>();
		String line = "";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		try {
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			in.close();
		}
		return lines;
	}

	public static String fileToString(String fileName) throws Exception {
		StringBuffer result = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		char[] buffer = new char[1000];
		int readedChars = 0;
		try {
			while ((readedChars = in.read(buffer)) > 0) {
				result.append(buffer, 0, readedChars);

			}
		} finally {
			in.close();
		}
		return result.toString();
	}
}
