package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.ast.Node;

public abstract class LiteralExpr extends Expression {

	public LiteralExpr() {}

	public LiteralExpr(int beginLine, int beginColumn, int endLine, int endColumn) {
		super(beginLine, beginColumn, endLine, endColumn);
	}

	@Override
	public abstract LiteralExpr clone() throws CloneNotSupportedException;

	@Override
	public boolean removeChild(Node child) {
		return false;
	}
}
