package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.EnumConstantDeclaration;

public class EnumConstantComparator implements Comparator<EnumConstantDeclaration> {

	@Override
	public int compare(EnumConstantDeclaration o1, EnumConstantDeclaration o2) {
		if (o1.getName().equals(o2.getName())) {
			return 0;
		}
		return -1;
	}
}
