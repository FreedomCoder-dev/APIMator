package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.AnnotationMemberDeclaration;

public class AnnotationMemberDeclarationComparator implements Comparator<AnnotationMemberDeclaration> {

	@Override
	public int compare(AnnotationMemberDeclaration an1, AnnotationMemberDeclaration an2) {
		if (an1.getName() == null || an2.getName() == null) {
			throw new IllegalArgumentException("Annotation member must have a name in order to compare them" + an1 + "-" + an2);
		}
		return an1.getName().compareTo(an2.getName());
	}
}
