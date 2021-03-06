package org.walkmod.javalang.ast.expr;

import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;

public final class SingleMemberAnnotationExpr extends AnnotationExpr {

	private Expression memberValue;

	public SingleMemberAnnotationExpr() {}

	public SingleMemberAnnotationExpr(NameExpr name, Expression memberValue) {
		setName(name);
		setMemberValue(memberValue);
	}

	public SingleMemberAnnotationExpr(int beginLine, int beginColumn, int endLine, int endColumn, NameExpr name, Expression memberValue) {
		super(beginLine, beginColumn, endLine, endColumn);
		setName(name);
		setMemberValue(memberValue);
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (memberValue != null) {
			children.add(memberValue);
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

	public Expression getMemberValue() {
		return memberValue;
	}

	public void setMemberValue(Expression memberValue) {
		this.memberValue = memberValue;
		setAsParentNodeOf(memberValue);
	}

	@Override
	public void merge(AnnotationExpr t1, MergeEngine configuration) {

	}

	@Override
	public SingleMemberAnnotationExpr clone() throws CloneNotSupportedException {

		return new SingleMemberAnnotationExpr(clone(name), clone(memberValue));
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(SingleMemberAnnotationExpr.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(SingleMemberAnnotationExpr.this);
	}

}
