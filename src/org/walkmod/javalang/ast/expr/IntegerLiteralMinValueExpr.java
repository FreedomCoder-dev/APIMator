package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class IntegerLiteralMinValueExpr extends IntegerLiteralExpr {

	public IntegerLiteralMinValueExpr() {
		super(MIN_VALUE);
	}

	public IntegerLiteralMinValueExpr(int beginLine, int beginColumn, int endLine, int endColumn) {
		super(beginLine, beginColumn, endLine, endColumn, MIN_VALUE);
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
	public IntegerLiteralMinValueExpr clone() throws CloneNotSupportedException {
		return new IntegerLiteralMinValueExpr();
	}
}
