package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class LongLiteralExpr extends StringLiteralExpr {

	private static final String UNSIGNED_MIN_VALUE = "9223372036854775808";

	protected static final String MIN_VALUE = "-" + UNSIGNED_MIN_VALUE + "L";

	public LongLiteralExpr() {}

	public LongLiteralExpr(String value) {
		super(value);
	}

	public LongLiteralExpr(int beginLine, int beginColumn, int endLine, int endColumn, String value) {
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
		return value != null && value.length() == 20 && value.startsWith(UNSIGNED_MIN_VALUE) 
		&& (value.charAt(19) == 'L' || value.charAt(19) == 'l');
	}

	public LongLiteralExpr clone() throws CloneNotSupportedException {
		return new LongLiteralExpr(value);
	}
}
