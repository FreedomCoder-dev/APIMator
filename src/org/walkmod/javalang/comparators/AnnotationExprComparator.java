package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.expr.AnnotationExpr;

public class AnnotationExprComparator implements Comparator<AnnotationExpr> {

	@Override
	public int compare(AnnotationExpr an1, AnnotationExpr an2) {
		if (an1.getName().toString().equals(an2.getName().toString())) {
			return 0;
		}
		return -1;
	}
}
