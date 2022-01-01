package org.walkmod.javalang.ast.body;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class EmptyTypeDeclaration extends TypeDeclaration {

	public EmptyTypeDeclaration() {}

	public EmptyTypeDeclaration(JavadocComment javaDoc) {
		super(null, javaDoc, 0, null, null);
	}

	public EmptyTypeDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc) {
		super(beginLine, beginColumn, endLine, endColumn, null, javaDoc, 0, null, null);
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
	public EmptyTypeDeclaration clone() throws CloneNotSupportedException {
		return new EmptyTypeDeclaration(clone(getJavaDoc()));
	}
}
