package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.EnumConstantDeclaration;

public class EnumConstantDeclarationComparator implements Comparator<EnumConstantDeclaration> {

	@Override
	public int compare(EnumConstantDeclaration o1, EnumConstantDeclaration o2) {
		if (o1.getName() == null || o2.getName() == null) {
			throw new IllegalArgumentException("EnumConstantDeclaration must have a name in order to be compared " + o1 + "-" + o2);
		}
		return o1.getName().compareTo(o2.getName());
	}
}
