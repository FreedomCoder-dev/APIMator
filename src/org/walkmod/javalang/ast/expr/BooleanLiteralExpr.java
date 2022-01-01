package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class BooleanLiteralExpr extends LiteralExpr {

	private boolean value;

	public BooleanLiteralExpr() {}

	public BooleanLiteralExpr(boolean value) {
		this.value = value;
	}

	public BooleanLiteralExpr(int beginLine, int beginColumn, int endLine, int endColumn, boolean value) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.value = value;
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

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public BooleanLiteralExpr clone() throws CloneNotSupportedException {
		return new BooleanLiteralExpr(value);
	}

}
