package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class ArrayAccessExpr extends Expression {

	private Expression name;

	private Expression index;

	public ArrayAccessExpr() {}

	public ArrayAccessExpr(Expression name, Expression index) {
		setName(name);
		setIndex(index);
	}

	public ArrayAccessExpr(int beginLine, int beginColumn, int endLine, int endColumn, Expression name, Expression index) {
		super(beginLine, beginColumn, endLine, endColumn);
		setName(name);
		setIndex(index);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (name != null) {
				if (name == child) {
					name = null;
					result = true;
				}
			}
			if (!result) {
				if (index != null) {
					if (index == child) {
						index = null;
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
		if (name != null) {
			children.add(name);
		}
		if (index != null) {
			children.add(index);
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

	public Expression getIndex() {
		return index;
	}

	public Expression getName() {
		return name;
	}

	public void setIndex(Expression index) {
		if (this.index != null) {
			updateReferences(this.index);
		}
		this.index = index;
		setAsParentNodeOf(index);
	}

	public void setName(Expression name) {
		if (this.name != null) {
			updateReferences(this.name);
		}
		this.name = name;
		setAsParentNodeOf(name);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (index == oldChild) {
			setIndex((Expression) newChild);
			updated = true;
		}
		if (!updated) {
			if (name == oldChild) {
				setName((Expression) newChild);
			}
		}
		return updated;
	}

	@Override
	public ArrayAccessExpr clone() throws CloneNotSupportedException {
		return new ArrayAccessExpr(clone(name), clone(index));
	}
}
