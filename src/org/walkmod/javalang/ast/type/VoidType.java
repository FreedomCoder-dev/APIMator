package org.walkmod.javalang.ast.type;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.AnnotationExpr;

public final class VoidType extends Type {

	public VoidType() {}

	public VoidType(int beginLine, int beginColumn, int endLine, int endColumn) {
		super(beginLine, beginColumn, endLine, endColumn);
	}

	public VoidType(int beginLine, int beginColumn, int endLine, int endColumn, List<AnnotationExpr> annotations) {
		super(beginLine, beginColumn, endLine, endColumn, annotations);
	}

	@Override
	public boolean removeChild(Node child) {
		return false;
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
	public VoidType clone() throws CloneNotSupportedException {
		return new VoidType();
	}

}
