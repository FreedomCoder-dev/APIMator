package org.walkmod.javalang.ast.expr;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class NullLiteralExpr extends LiteralExpr {

	public NullLiteralExpr() {}

	public NullLiteralExpr(int beginLine, int beginColumn, int endLine, int endColumn) {
		super(beginLine, beginColumn, endLine, endColumn);
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
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public NullLiteralExpr clone() throws CloneNotSupportedException {
		return new NullLiteralExpr();
	}

}
