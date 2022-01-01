package org.walkmod.javalang.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.visitors.VoidVisitorAdapter;
import org.walkmod.javalang.ast.expr.FieldAccessExpr;
import org.walkmod.javalang.ast.expr.NameExpr;
import org.walkmod.javalang.ast.expr.ThisExpr;

public class Refactorization {

	public boolean refactorVariable(SymbolDefinition n, final String newName) {
		Map<String, SymbolDefinition> scope = n.getVariableDefinitions();

		if (!scope.containsKey(newName)) {

			if (n.getUsages() != null) {
				List<SymbolReference> usages = new LinkedList<SymbolReference>(n.getUsages());

				VoidVisitorAdapter<?> visitor = new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(NameExpr nexpr, Object ctx) {
						Map<String, SymbolDefinition> innerScope = nexpr.getVariableDefinitions();
						if (innerScope.containsKey(newName)) {
							nexpr.getParentNode().replaceChildNode(nexpr, new FieldAccessExpr(new ThisExpr(), newName));
						} else {
							nexpr.getParentNode().replaceChildNode(nexpr, new NameExpr(newName));
						}
					}

					@Override
					public void visit(FieldAccessExpr nexpr, Object ctx) {
						nexpr.getParentNode().replaceChildNode(nexpr, new FieldAccessExpr(nexpr.getScope(), nexpr.getTypeArgs(), newName));
					}
				};

				for (SymbolReference usage : usages) {
					Node aux = (Node) usage;

					aux.accept(visitor, null);

				}
			}

			return true;
		}
		return false;
	}

}
