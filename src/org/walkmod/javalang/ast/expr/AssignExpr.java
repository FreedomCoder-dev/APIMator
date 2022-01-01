package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class AssignExpr extends Expression {

	public enum Operator {

		assign, plus, minus, star, slash, and, or, xor, rem, lShift, rSignedShift, rUnsignedShift}

	private Expression target;

	private Expression value;

	private Operator op;

	public AssignExpr() {}

	public AssignExpr(Expression target, Expression value, Operator op) {
		setTarget(target);
		setValue(value);
		setOperator(op);
	}

	public AssignExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression target, Expression value, Operator op) {
		super(beginLine, beginColumn, endLine, endColumn);
		setTarget(target);
		setValue(value);
		setOperator(op);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (target != null) {
				if (target == child) {
					target = null;
					result = true;
				}
			}
			if (!result) {
				if (value != null) {
					if (value == child) {
						value = null;
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
		if (target != null) {
			children.add(target);
		}
		if (value != null) {
			children.add(value);
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

	public Operator getOperator() {
		return op;
	}

	public Expression getTarget() {
		return target;
	}

	public Expression getValue() {
		return value;
	}

	public void setOperator(Operator op) {
		this.op = op;
	}

	public void setTarget(Expression target) {
		if (this.target != null) {
			updateReferences(this.target);
		}
		this.target = target;
		setAsParentNodeOf(target);
	}

	public void setValue(Expression value) {
		if (this.value != null) {
			updateReferences(this.value);
		}
		this.value = value;
		setAsParentNodeOf(value);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (target == oldChild) {
			setTarget((Expression) newChild);
			updated = true;
		}
		if (!updated) {
			if (value == oldChild) {
				setValue((Expression) newChild);
				updated = true;
			}
		}

		return updated;
	}

	@Override
	public AssignExpr clone() throws CloneNotSupportedException {

		return new AssignExpr(clone(getTarget()), clone(getValue()), getOperator());
	}
}
