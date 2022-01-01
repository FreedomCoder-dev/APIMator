package org.walkmod.javalang.ast;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class LineComment extends Comment {

	public LineComment() {}

	public LineComment(String content) {
		super(content);
	}

	public LineComment(int beginLine, int beginColumn, int endLine, int endColumn, String content) {
		super(beginLine, beginColumn, endLine, endColumn, content);
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
	public LineComment clone() throws CloneNotSupportedException {
		return new LineComment(getContent());
	}
}
