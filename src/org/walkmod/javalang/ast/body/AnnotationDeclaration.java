package org.walkmod.javalang.ast.body;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class AnnotationDeclaration extends TypeDeclaration {

	public AnnotationDeclaration() {}

	public AnnotationDeclaration(int modifiers, String name) {
		super(modifiers, name);
	}

	public AnnotationDeclaration(JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, String name, List<BodyDeclaration> members) {
		super(annotations, javaDoc, modifiers, name, members);
	}

	public AnnotationDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc, int modifiers, List<AnnotationExpr> annotations, String name, List<BodyDeclaration> members) {
		super(beginLine, beginColumn, endLine, endColumn, annotations, javaDoc, modifiers, name, members);
	}

	@Override
	public List<Node> getChildren() {
		return super.getChildren();
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
	public AnnotationDeclaration clone() throws CloneNotSupportedException {
		return new AnnotationDeclaration(getModifiers(), getName());
	}

}
