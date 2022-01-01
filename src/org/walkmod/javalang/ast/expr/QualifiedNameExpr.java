package org.walkmod.javalang.ast.expr;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class QualifiedNameExpr extends NameExpr {

	private NameExpr qualifier;

	public QualifiedNameExpr() {}

	public QualifiedNameExpr(NameExpr scope, String name) {
		super(name);
		setQualifier(scope);
	}

	public QualifiedNameExpr(int beginLine, int beginColumn, int endLine, int endColumn, NameExpr scope, String name) {
		super(beginLine, beginColumn, endLine, endColumn, name);
		setQualifier(scope);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (qualifier == child) {
				qualifier = null;
				result = true;
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
		if (qualifier != null) {
			children.add(qualifier);
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

	public NameExpr getQualifier() {
		return qualifier;
	}

	public void setQualifier(NameExpr qualifier) {
		this.qualifier = qualifier;
		setAsParentNodeOf(qualifier);
	}

	@Override
	public QualifiedNameExpr clone() throws CloneNotSupportedException {
		return new QualifiedNameExpr(clone(getQualifier()), getName());
	}

}
