package org.walkmod.javalang.comparators;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.walkmod.javalang.ast.body.ConstructorDeclaration;
import org.walkmod.javalang.ast.body.Parameter;

public class ConstructorDeclarationComparator implements Comparator<ConstructorDeclaration> {

	@Override
	public int compare(ConstructorDeclaration o1, ConstructorDeclaration o2) {
		if (o1.getName().equals(o2.getName())) {
			List<Parameter> params1 = o1.getParameters();
			List<Parameter> params2 = o2.getParameters();
			if ((params1 == null || params1.size() == 0) && (params2 == null || params2.size() == 0)) {
				return 0;
			} else if (params1 != null && params2 != null) {
				if (params1.size() == params2.size()) {
					Iterator<Parameter> itprms = o1.getParameters().iterator();
					Iterator<Parameter> itprms2 = o2.getParameters().iterator();
					boolean equalParams = false;
					while (itprms.hasNext() && itprms2.hasNext() && !equalParams) {
						equalParams = itprms.next().getType().toString().equals(itprms2.next().getType().toString());
					}
					if (equalParams) return 0;

					else return new Integer(params1.size()).compareTo(params2.size());
				} else {
					return new Integer(params1.size()).compareTo(params2.size());
				}
			}
		}
		return o1.getName().compareTo(o2.getName());
	}
}
