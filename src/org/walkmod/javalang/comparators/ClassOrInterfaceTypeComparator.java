package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.type.ClassOrInterfaceType;

public class ClassOrInterfaceTypeComparator implements Comparator<ClassOrInterfaceType> {

	@Override
	public int compare(ClassOrInterfaceType o1, ClassOrInterfaceType o2) {
		if (o1 == null && o2 == null) {
			return 0;
		}
		if (o1 == null && o2 != null) {
			return 1;
		}
		if (o1 != null && o2 == null) {
			return -1;
		}

		int scope = compare(o1.getScope(), o2.getScope());
		if (scope == 0) {
			return o1.getName().compareTo(o2.getName());
		}
		return scope;
	}
}
