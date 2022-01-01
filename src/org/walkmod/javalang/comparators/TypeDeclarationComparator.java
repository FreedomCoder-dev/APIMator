package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.TypeDeclaration;

public class TypeDeclarationComparator implements Comparator<TypeDeclaration> {

	@Override
	public int compare(TypeDeclaration t1, TypeDeclaration t2) {
		return t1.getName().compareTo(t2.getName());
	}
}
