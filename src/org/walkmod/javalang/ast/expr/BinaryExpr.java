package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class BinaryExpr extends Expression {

	public enum Operator {

		or, and, binOr, binAnd, xor, equals, notEquals, less, greater, lessEquals, greaterEquals, lShift, rSignedShift, rUnsignedShift, plus, minus, times, divide, remainder}

	private Expression left;

	private Expression right;

	private Operator op;

	public BinaryExpr() {}

	public BinaryExpr(Expression left, Expression right, Operator op) {
		setLeft(left);
		setRight(right);
		setOperator(op);
	}

	public BinaryExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression left, Expression right, Operator op) {
		super(beginLine, beginColumn, endLine, endColumn);
		setLeft(left);
		setRight(right);
		setOperator(op);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (left != null) {
				if (left == child) {
					left = null;
					result = true;
				}
			}
			if (!result) {
				if (right != null) {
					if (right == child) {
						right = null;
						result = true;
					}
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
		List<Node> children = new LinkedList<Node>();
		if (left != null) {
			children.add(left);
		}
		if (right != null) {
			children.add(right);
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

	public Expression getLeft() {
		return left;
	}

	public Operator getOperator() {
		return op;
	}

	public Expression getRight() {
		return right;
	}

	public void setLeft(Expression left) {
		if (this.left != null) {
			updateReferences(this.left);
		}
		this.left = left;
		setAsParentNodeOf(left);
	}

	public void setOperator(Operator op) {
		this.op = op;
	}

	public void setRight(Expression right) {
		if (this.right != null) {
			updateReferences(this.right);
		}
		this.right = right;
		setAsParentNodeOf(right);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (left == oldChild) {
			setLeft((Expression) newChild);
			updated = true;
		}
		if (right == oldChild) {
			setRight((Expression) newChild);
			updated = true;
		}
		return updated;
	}

	@Override
	public BinaryExpr clone() throws CloneNotSupportedException {
		return new BinaryExpr(clone(getLeft()), clone(getRight()), getOperator());
	}

}
