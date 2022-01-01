package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class LongLiteralMinValueExpr extends LongLiteralExpr {

	public LongLiteralMinValueExpr() {
		super(MIN_VALUE);
	}

	public LongLiteralMinValueExpr(int beginLine, int beginColumn, int endLine, int endColumn) {
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
	public LongLiteralMinValueExpr clone() throws CloneNotSupportedException {
		return new LongLiteralMinValueExpr();
	}
}
