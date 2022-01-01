package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class IntegerLiteralExpr extends StringLiteralExpr {

	private static final String UNSIGNED_MIN_VALUE = "2147483648";

	protected static final String MIN_VALUE = "-" + UNSIGNED_MIN_VALUE;

	public IntegerLiteralExpr() {}

	public IntegerLiteralExpr(String value) {
		super(value);
	}

	public IntegerLiteralExpr(long value) {
		super(Long.toString(value));
	}

	public IntegerLiteralExpr(int beginLine, int beginColumn, int endLine, int endColumn, String value) {
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

	public final boolean isMinValue() {
		return value != null && value.length() == 10 && value.equals(UNSIGNED_MIN_VALUE);
	}

	@Override
	public IntegerLiteralExpr clone() throws CloneNotSupportedException {
		return new IntegerLiteralExpr(value);
	}
}
