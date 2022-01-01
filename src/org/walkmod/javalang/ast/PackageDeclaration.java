package org.walkmod.javalang.ast;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.expr.NameExpr;

public final class PackageDeclaration extends Node {

	private List<AnnotationExpr> annotations;

	private NameExpr name;

	public PackageDeclaration() {}

	public PackageDeclaration(NameExpr name) {
		setName(name);
	}

	public PackageDeclaration(List<AnnotationExpr> annotations, NameExpr name) {
		setAnnotations(annotations);
		setName(name);
	}

	public PackageDeclaration(int beginLine, int beginColumn, int endLine, int endColumn, List<AnnotationExpr> annotations, NameExpr name) {
		super(beginLine, beginColumn, endLine, endColumn);
		setAnnotations(annotations);
		setName(name);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (child instanceof AnnotationExpr) {
				List<AnnotationExpr> annotationAux = new LinkedList<AnnotationExpr>(annotations);
				result = annotationAux.remove(child);
				annotations = annotationAux;
			} else if (child == name && name != null) {
				name = (NameExpr) child;
				result = true;
			}
		}
		if (result) {
			updateReferences(child);
		}
		return result;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = new LinkedList<Node>();
		if (annotations != null) {
			children.addAll(annotations);
		}
		if (name != null) {
			children.add(name);
		}
		return children;
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

	public List<AnnotationExpr> getAnnotations() {
		return annotations;
	}

	public NameExpr getName() {
		return name;
	}

	public void setAnnotations(List<AnnotationExpr> annotations) {
		this.annotations = annotations;
		setAsParentNodeOf(annotations);
	}

	public void setName(NameExpr name) {
		String namee = name.getName();
		if (namee.endsWith(".")) name = new NameExpr(namee.substring(0, namee.length() - 1));
		if (this.name != null) {
			updateReferences(this.name);
		}
		this.name = name;
		setAsParentNodeOf(name);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		if (name == oldChild) {
			setName((NameExpr) newChild);
			return true;
		}
		return false;
	}

	@Override
	public PackageDeclaration clone() throws CloneNotSupportedException {
		return new PackageDeclaration(clone(annotations), clone(name));
	}
}
