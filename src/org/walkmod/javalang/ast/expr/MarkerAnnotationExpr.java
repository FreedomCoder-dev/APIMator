package org.walkmod.javalang.ast.expr;

import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;

public final class MarkerAnnotationExpr extends AnnotationExpr {

	public MarkerAnnotationExpr() {}

	public MarkerAnnotationExpr(NameExpr name) {
		this.name = name;
	}

	public MarkerAnnotationExpr(int beginLine, int beginColumn, int endLine, int endColumn, NameExpr name) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.name = name;
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

	@Override
	public void merge(AnnotationExpr t1, MergeEngine configuration) {

	}

	@Override
	public MarkerAnnotationExpr clone() throws CloneNotSupportedException {
		return new MarkerAnnotationExpr(clone(name));
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(MarkerAnnotationExpr.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(MarkerAnnotationExpr.this);
	}
}
