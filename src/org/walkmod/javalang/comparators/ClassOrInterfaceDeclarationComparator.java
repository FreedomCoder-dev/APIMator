package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.ClassOrInterfaceDeclaration;

public class ClassOrInterfaceDeclarationComparator implements Comparator<ClassOrInterfaceDeclaration> {

	@Override
	public int compare(ClassOrInterfaceDeclaration o1, ClassOrInterfaceDeclaration o2) {
		return o1.getName().compareTo(o2.getName());
	}
}
