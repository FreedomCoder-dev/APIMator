package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.TypeDeclaration;

public class TypeDeclarationNameComparator implements Comparator<TypeDeclaration> {

	@Override
	public int compare(TypeDeclaration o1, TypeDeclaration o2) {
		if (o1.getName() == null || o2.getName() == null) {
			throw new IllegalArgumentException(o1.getClass().getName() + " must have a name. " + o1 + "-" + o2);
		}
		return o1.getName().compareTo(o2.getName());
	}
}
