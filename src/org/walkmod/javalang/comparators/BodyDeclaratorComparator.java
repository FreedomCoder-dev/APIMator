package org.walkmod.javalang.comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.walkmod.javalang.ast.body.BodyDeclaration;

public class BodyDeclaratorComparator implements Comparator<BodyDeclaration> {

	private final String[] order;

	@SuppressWarnings("rawtypes")
	private final Map<String, Comparator> comparator = new HashMap<String, Comparator>();

	public BodyDeclaratorComparator() {
		order = new String[] { "FieldDeclaration", "EnumConstantDeclaration", "InitializerDeclaration", "ConstructorDeclaration", "AnnotationMemberDeclaration", "MethodDeclaration", "TypeDeclaration", "EmptyMemberDeclaration" };
		comparator.put("FieldDeclaration", new FieldDeclarationComparator());
		comparator.put("MethodDeclaration", new MethodDeclarationComparator());
		comparator.put("EnumConstantDeclaration", new EnumConstantComparator());
		comparator.put("AnnotationMemberDeclaration", new AnnotationMemberDeclarationComparator());
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(BodyDeclaration o1, BodyDeclaration o2) {
		Integer o1_position = Arrays.binarySearch(order, o1.getClass().getSimpleName());
		Integer o2_position = Arrays.binarySearch(order, o2.getClass().getSimpleName());
		if (o1_position < 0) {
			return 0;
		} else if (o1_position == o2_position) {
			if (comparator.containsKey(order[o1_position])) {
				return comparator.get(order[o1_position]).compare(o1, o2);
			} else {
				return 0;
			}
		} else {
			return o1_position.compareTo(o2_position);
		}
	}
}
