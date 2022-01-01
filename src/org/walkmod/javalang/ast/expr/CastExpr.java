package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class CastExpr extends Expression {

	private Type type;

	private Expression expr;

	public CastExpr() {}

	public CastExpr(Type type, Expression expr) {
		setType(type);
		setExpr(expr);
	}

	public CastExpr(int beginLine, int beginColumn, int endLine, int endColumn, Type type, Expression expr) {
		super(beginLine, beginColumn, endLine, endColumn);
		setType(type);
		setExpr(expr);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (type != null) {
				if (type == child) {
					type = null;
					result = true;
				}
			}
			if (!result) {
				if (expr != null) {
					if (expr == child) {
						expr = null;
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
		if (type != null) {
			children.add(type);
		}
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
		if (type == oldChild) {
			setType((Type) newChild);
			updated = true;
		}
		if (!updated) {
			if (expr == oldChild) {
				setExpr((Expression) newChild);
				updated = true;
			}
		}
		return updated;
	}

	@Override
	public CastExpr clone() throws CloneNotSupportedException {

		return new CastExpr(clone(getType()), clone(getExpr()));
	}

}
