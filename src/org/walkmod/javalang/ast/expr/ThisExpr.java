package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class ThisExpr extends Expression {

	private Expression classExpr;

	public ThisExpr() {}

	public ThisExpr(Expression classExpr) {
		setClassExpr(classExpr);
	}

	public ThisExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression classExpr) {
		super(beginLine, beginColumn, endLine, endColumn);
		setClassExpr(classExpr);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (classExpr == child) {
				classExpr = null;
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
		List<Node> children = new LinkedList<Node>();
		if (classExpr != null) {
			children.add(classExpr);
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

	public Expression getClassExpr() {
		return classExpr;
	}

	public void setClassExpr(Expression classExpr) {
		if (this.classExpr != null) {
			updateReferences(this.classExpr);
		}
		this.classExpr = classExpr;
		setAsParentNodeOf(classExpr);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == classExpr) {
			setClassExpr((Expression) newChild);
			updated = true;
		}
		return updated;
	}

	@Override
	public ThisExpr clone() throws CloneNotSupportedException {
		return new ThisExpr(clone(classExpr));
	}

}
