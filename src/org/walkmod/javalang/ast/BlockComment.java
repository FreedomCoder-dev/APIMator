package org.walkmod.javalang.ast;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class BlockComment extends Comment {

	public BlockComment() {}

	public BlockComment(String content) {
		super(content);
	}

	public BlockComment(int beginLine, int beginColumn, int endLine, int endColumn, String content) {
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
	public BlockComment clone() throws CloneNotSupportedException {
		return new BlockComment(getContent());
	}
}
