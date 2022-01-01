package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class UnaryExpr extends Expression {

	public enum Operator {

		positive, negative, preIncrement, preDecrement, not, inverse, posIncrement, posDecrement}

	private Expression expr;

	private Operator op;

	public UnaryExpr() {}

	public UnaryExpr(Expression expr, Operator op) {
		setExpr(expr);
		this.op = op;
	}

	public UnaryExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression expr, Operator op) {
		super(beginLine, beginColumn, endLine, endColumn);
		setExpr(expr);
		this.op = op;
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
		List<Node> children = new LinkedList<Node>();
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

	public Expression getExpr() {
		return expr;
	}

	public Operator getOperator() {
		return op;
	}

	public void setExpr(Expression expr) {
		if (this.expr != null) {
			updateReferences(this.expr);
		}
		this.expr = expr;
		setAsParentNodeOf(expr);
	}

	public void setOperator(Operator op) {
		this.op = op;
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
	public UnaryExpr clone() throws CloneNotSupportedException {
		return new UnaryExpr(clone(expr), op);
	}

}
