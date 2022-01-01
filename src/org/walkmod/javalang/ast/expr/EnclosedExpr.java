package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class EnclosedExpr extends Expression {

	private Expression inner;

	public EnclosedExpr() {}

	public EnclosedExpr(Expression inner) {
		setInner(inner);
	}

	public EnclosedExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression inner) {
		super(beginLine, beginColumn, endLine, endColumn);
		setInner(inner);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (inner == child) {
				inner = null;
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
		if (inner != null) {
			children.add(inner);
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

	public Expression getInner() {
		return inner;
	}

	public void setInner(Expression inner) {
		if (this.inner != null) {
			updateReferences(this.inner);
		}
		this.inner = inner;
		setAsParentNodeOf(inner);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;

		if (oldChild == inner) {
			setInner((Expression) newChild);
			updated = true;
		}

		return updated;
	}

	@Override
	public EnclosedExpr clone() throws CloneNotSupportedException {

		return new EnclosedExpr(clone(inner));
	}

}
