package org.walkmod.javalang.ast.stmt;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.Expression;

public final class ExpressionStmt extends Statement {

	private Expression expr;

	public ExpressionStmt() {}

	public ExpressionStmt(Expression expr) {
		setExpression(expr);
	}

	public ExpressionStmt(int beginLine, int beginColumn, int endLine, int endColumn, Expression expr) {
		super(beginLine, beginColumn, endLine, endColumn);
		setExpression(expr);
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
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (expr != null) {
			children.add(expr);
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

	public Expression getExpression() {
		return expr;
	}

	public void setExpression(Expression expr) {
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
			setExpression((Expression) newChild);
			updated = true;
		}

		return updated;
	}

	@Override
	public ExpressionStmt clone() throws CloneNotSupportedException {
		return new ExpressionStmt(clone(expr));
	}
}
