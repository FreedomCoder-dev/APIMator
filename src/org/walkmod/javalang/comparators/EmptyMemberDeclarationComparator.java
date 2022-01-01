package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.EmptyMemberDeclaration;

public class EmptyMemberDeclarationComparator implements Comparator<EmptyMemberDeclaration> {

	@Override
	public int compare(EmptyMemberDeclaration o1, EmptyMemberDeclaration o2) {
		return 0;
	}
}
