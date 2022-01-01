package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class DoubleLiteralExpr extends StringLiteralExpr {

	public DoubleLiteralExpr() {}

	public DoubleLiteralExpr(String value) {
		super(value);
	}

	public DoubleLiteralExpr(int beginLine, int beginColumn, int endLine, int endColumn, String value) {
		super(beginLine, beginColumn, endLine, endColumn, value);
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
	public DoubleLiteralExpr clone() throws CloneNotSupportedException {
		return new DoubleLiteralExpr(getValue());
	}
}
