package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class InstanceOfExpr extends Expression {

	private Expression expr;

	private Type type;

	public InstanceOfExpr() {}

	public InstanceOfExpr(Expression expr, Type type) {
		setExpr(expr);
		setType(type);
	}

	public InstanceOfExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression expr, Type type) {
		super(beginLine, beginColumn, endLine, endColumn);
		setExpr(expr);
		setType(type);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (expr == child) {
				expr = null;
				result = true;
			}

			if (!result) {
				if (type == child) {
					type = null;
					result = true;
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
		if (expr != null) {
			children.add(expr);
		}
		if (type != null) {
			children.add(type);
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

	public Type getType() {
		return type;
	}

	public void setExpr(Expression expr) {
		if (this.expr != null) {
			updateReferences(this.expr);
		}
		this.expr = expr;
		setAsParentNodeOf(expr);
	}

	public void setType(Type type) {
		if (this.type != null) {
			updateReferences(this.type);
		}
		this.type = type;
		setAsParentNodeOf(type);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;

		if (oldChild == expr) {
			setExpr((Expression) newChild);
			updated = true;
		}
		if (!updated) {
			if (type == oldChild) {
				setType((Type) newChild);
				updated = true;
			}
		}

		return updated;
	}

	@Override
	public InstanceOfExpr clone() throws CloneNotSupportedException {

		return new InstanceOfExpr(clone(expr), clone(type));
	}

}
