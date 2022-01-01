package org.walkmod.javalang.comparators;

import java.util.Comparator;
import java.util.List;

import org.walkmod.javalang.ast.body.FieldDeclaration;
import org.walkmod.javalang.ast.body.VariableDeclarator;

public class FieldDeclarationComparator implements Comparator<FieldDeclaration> {

	@Override
	public int compare(FieldDeclaration fd1, FieldDeclaration fd2) {
		if (fd1.getType().equals(fd2.getType())) {
			List<VariableDeclarator> vds = fd1.getVariables();
			List<VariableDeclarator> vds2 = fd2.getVariables();
			if (vds == null || vds.size() == 0 || vds2 == null || vds2.size() == 0) {
				return -1;
			}
			if (vds.size() == vds2.size()) {
				if (vds.containsAll(vds2)) {
					return 0;
				}
			}
		}
		return -1;
	}
}
