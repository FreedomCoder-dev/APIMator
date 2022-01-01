package org.walkmod.javalang.ast.type;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.AnnotationExpr;

public final class PrimitiveType extends Type {

	public enum Primitive {
		Boolean, Char, Byte, Short, Int, Long, Float, Double}

	private Primitive type;

	public PrimitiveType() {}

	public PrimitiveType(Primitive type) {
		this.type = type;
	}

	public PrimitiveType(int beginLine, int beginColumn, int endLine, int endColumn, Primitive type) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.type = type;
	}

	public PrimitiveType(int beginLine, int beginColumn, int endLine, int endColumn, Primitive type, List<AnnotationExpr> annotations) {
		super(beginLine, beginColumn, endLine, endColumn, annotations);
		this.type = type;
	}

	public static PrimitiveType Int() {
		return new PrimitiveType(Primitive.Int);
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

	public Primitive getType() {
		return type;
	}

	public void setType(Primitive type) {
		this.type = type;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return super.replaceChildNode(oldChild, newChild);
	}

	@Override
	public PrimitiveType clone() throws CloneNotSupportedException {
		return new PrimitiveType(type);
	}
}
