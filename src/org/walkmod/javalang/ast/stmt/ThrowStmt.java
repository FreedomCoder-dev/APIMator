package org.walkmod.javalang.ast.stmt;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.Expression;

public final class ThrowStmt extends Statement {

	private Expression expr;

	public ThrowStmt() {}

	public ThrowStmt(Expression expr) {
		setExpr(expr);
	}

	public ThrowStmt(int beginLine, int beginColumn, int endLine, int endColumn, Expression expr) {
		super(beginLine, beginColumn, endLine, endColumn);
		setExpr(expr);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (expr == child) {
				expr = null;
				result = true;
			}
		}
		if (result) {
			updateReferences(child);
		}
		return result;
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
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (expr != null) {
			children.add(expr);
		}
		return children;
	}

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		if (this.expr != null) {
			updateReferences(this.expr);
		}
		this.expr = expr;
		setAsParentNodeOf(expr);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == expr) {
			setExpr((Expression) newChild);
			updated = true;
		}

		return updated;
	}

	@Override
	public ThrowStmt clone() throws CloneNotSupportedException {
		return new ThrowStmt(clone(expr));
	}
}
