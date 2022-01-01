package org.walkmod.javalang.comparators;

import java.util.Comparator;

import org.walkmod.javalang.ast.body.VariableDeclarator;

public class VariableDeclaratorComparator implements Comparator<VariableDeclarator> {

	@Override
	public int compare(VariableDeclarator local, VariableDeclarator remote) {
		if (local == null && remote == null) {
			return 0;
		}
		if (local == null || remote == null) {
			return -1;
		} else {
			return local.getId().getName().compareTo(remote.getId().getName());
		}
	}
}
