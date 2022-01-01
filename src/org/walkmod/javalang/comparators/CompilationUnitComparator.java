package org.walkmod.javalang.comparators;

import java.util.Collection;
import java.util.Comparator;

import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.PackageDeclaration;
import org.walkmod.javalang.ast.body.ModifierSet;
import org.walkmod.javalang.ast.body.TypeDeclaration;

public class CompilationUnitComparator implements Comparator<CompilationUnit> {

	@Override
	public int compare(CompilationUnit cu1, CompilationUnit cu2) {
		if (cu1 != null && cu2 != null) {
			PackageDeclaration pd1 = cu1.getPackage();
			PackageDeclaration pd2 = cu2.getPackage();
			boolean samePackage = true;
			if (pd1 != pd2) {
				if (pd1 != null && pd2 != null) {
					samePackage = pd1.getName().equals(pd2.getName());
				} else {
					samePackage = false;
				}
			}
			if (samePackage) {

				Collection<TypeDeclaration> types1 = cu1.getTypes();
				TypeDeclaration mainType = null;
				if (types1 != null) {
					for (TypeDeclaration td : types1) {
						if (ModifierSet.isPublic(td.getModifiers())) {
							mainType = td;
						}
					}
				}
				if (mainType != null) {
					Collection<TypeDeclaration> types2 = cu2.getTypes();
					if (types2 != null) {
						for (TypeDeclaration td : types2) {
							if (td.getName().equals(mainType.getName())) {
								return 0;
							}
						}
					}
				}
				return 1;
			}
		}
		if (cu1 != cu2) {
			return 1;
		}
		return 0;
	}
}
