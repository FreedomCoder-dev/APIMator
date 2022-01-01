package org.walkmod.javalang.ast.body;

import java.util.Comparator;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.comparators.EmptyMemberDeclarationComparator;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.merger.MergeEngine;
import org.walkmod.merger.Mergeable;

public final class EmptyMemberDeclaration extends BodyDeclaration implements Mergeable<EmptyMemberDeclaration> {

	public EmptyMemberDeclaration() {}

	public EmptyMemberDeclaration(JavadocComment javaDoc) {
		super(null, javaDoc);
	}

	public EmptyMemberDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, JavadocComment javaDoc) {
		super(beginLine, beginColumn, endLine, endColumn, null, javaDoc);
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
	public Comparator<?> getIdentityComparator() {

		return new EmptyMemberDeclarationComparator();
	}

	@Override
	public void merge(EmptyMemberDeclaration t1, MergeEngine configuration) {
		super.merge(t1, configuration);
	}

	@Override
	public EmptyMemberDeclaration clone() throws CloneNotSupportedException {
		return new EmptyMemberDeclaration();
	}
}
