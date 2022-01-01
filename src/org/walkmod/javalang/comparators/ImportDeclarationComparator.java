package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.ImportDeclaration;

public class ImportDeclarationComparator implements Comparator<ImportDeclaration> {

	@Override
	public int compare(ImportDeclaration id1, ImportDeclaration id2) {
		return id1.getName().toString().compareTo(id2.getName().toString());
	}
}
