package org.walkmod.javalang.ast.body;

import org.walkmod.javalang.ast.Comment;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class JavadocComment extends Comment {

	public JavadocComment() {}

	public JavadocComment(String content) {
		super(content);
	}

	public JavadocComment(int beginLine, int beginColumn, int endLine, int endColumn, String content) {
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
	public JavadocComment clone() throws CloneNotSupportedException {
		return new JavadocComment(getContent());
	}
}
