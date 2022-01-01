package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;

public final class NormalAnnotationExpr extends AnnotationExpr {

	private List<MemberValuePair> pairs;

	public NormalAnnotationExpr() {}

	public NormalAnnotationExpr(NameExpr name, List<MemberValuePair> pairs) {
		setName(name);
		setPairs(pairs);
	}

	public NormalAnnotationExpr(int beginLine, int beginColumn, int endLine, int endColumn, NameExpr name, List<MemberValuePair> pairs) {
		super(beginLine, beginColumn, endLine, endColumn);
		setName(name);
		setPairs(pairs);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			result = super.removeChild(child);
			if (!result) {
				if (pairs != null) {
					List<MemberValuePair> pairsAux = new LinkedList<MemberValuePair>(pairs);
					result = pairsAux.remove(child);
					pairs = pairsAux;
				}
			}
		}
		if (result) {
			updateReferences(child);
		}
		return result;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (pairs != null) {
			children.addAll(pairs);
		}

		return children;
	}

	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
		if (!check()) {
			return null;
		}
		return v.visit(this, arg);
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg) {
		if (check()) {
			v.visit(this, arg);
		}
	}

	public List<MemberValuePair> getPairs() {
		return pairs;
	}

	public void setPairs(List<MemberValuePair> pairs) {
		this.pairs = pairs;
		setAsParentNodeOf(pairs);
	}

	@Override
	public void merge(AnnotationExpr t1, MergeEngine configuration) {

		List<MemberValuePair> pairsList = new LinkedList<MemberValuePair>();
		configuration.apply(getPairs(), ((NormalAnnotationExpr) t1).getPairs(), pairsList, MemberValuePair.class);
		if (!pairsList.isEmpty()) {
			setPairs(pairsList);
		} else {
			setPairs(null);
		}

	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (pairs != null) {
			List<MemberValuePair> auxPairs = new LinkedList<MemberValuePair>(pairs);
			updated = replaceChildNodeInList(oldChild, newChild, auxPairs);
			if (updated) {
				pairs = auxPairs;
			}
		}

		return updated;
	}

	@Override
	public NormalAnnotationExpr clone() throws CloneNotSupportedException {

		return new NormalAnnotationExpr(clone(name), clone(pairs));
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(NormalAnnotationExpr.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(NormalAnnotationExpr.this);
	}

}
