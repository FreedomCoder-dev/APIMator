package org.walkmod.javalang.ast.type;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.AnnotationExpr;

public final class ReferenceType extends Type {

	private Type type;

	private int arrayCount;

	private List<List<AnnotationExpr>> arraysAnnotations;

	public ReferenceType() {}

	public ReferenceType(Type type) {
		setType(type);
	}

	public ReferenceType(Type type, int arrayCount) {
		setType(type);
		this.arrayCount = arrayCount;
	}

	public ReferenceType(Type type, int arrayCount, List<AnnotationExpr> annotations) {
		super(annotations);
		setType(type);
		this.arrayCount = arrayCount;
	}

	public ReferenceType(Type type, int arrayCount, List<AnnotationExpr> annotations, List<List<AnnotationExpr>> arraysAnnotations) {
		super(annotations);
		setType(type);
		this.arrayCount = arrayCount;
		setArraysAnnotations(arraysAnnotations);
	}

	public ReferenceType(int beginLine, int beginColumn, int endLine, int endColumn, Type type, int arrayCount) {
		super(beginLine, beginColumn, endLine, endColumn);
		setType(type);
		this.arrayCount = arrayCount;
	}

	public ReferenceType(int beginLine, int beginColumn, int endLine, int endColumn, Type type, int arrayCount, List<AnnotationExpr> annotations, List<List<AnnotationExpr>> arraysAnnotations) {
		super(beginLine, beginColumn, endLine, endColumn, annotations);
		setType(type);
		this.arrayCount = arrayCount;
		setArraysAnnotations(arraysAnnotations);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (child == type) {
				type = null;
				result = true;
			}
			if (arraysAnnotations != null) {
				if (child instanceof AnnotationExpr) {
					List<List<AnnotationExpr>> arraysAnnotationsAux = new LinkedList<List<AnnotationExpr>>();
					Iterator<List<AnnotationExpr>> it = arraysAnnotations.iterator();
					while (it.hasNext()) {
						List<AnnotationExpr> next = it.next();
						List<AnnotationExpr> nextAux = new LinkedList<AnnotationExpr>(next);
						result = result || nextAux.remove(child);
						arraysAnnotationsAux.add(nextAux);
					}
					arraysAnnotations = arraysAnnotationsAux;
				}
			}
		}
		if (result) {
			updateReferences(child);
		}
		return result;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (type != null) {
			children.add(type);
		}
		if (arraysAnnotations != null) {
			for (List<AnnotationExpr> annList : arraysAnnotations) {
				if (annList != null) children.addAll(annList);
			}
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

	public int getArrayCount() {
		return arrayCount;
	}

	public Type getType() {
		return type;
	}

	public void setArrayCount(int arrayCount) {
		this.arrayCount = arrayCount;
	}

	public void setType(Type type) {
		if (this.type != null) {
			updateReferences(this.type);
		}
		this.type = type;
		setAsParentNodeOf(type);
	}

	public List<List<AnnotationExpr>> getArraysAnnotations() {
		return arraysAnnotations;
	}

	public void setArraysAnnotations(List<List<AnnotationExpr>> arraysAnnotations) {
		this.arraysAnnotations = arraysAnnotations;
		if (arraysAnnotations != null) {
			for (List<AnnotationExpr> ann : arraysAnnotations) {
				setAsParentNodeOf(ann);
			}
		}
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = super.replaceChildNode(oldChild, newChild);
		if (oldChild == type) {
			setType((ClassOrInterfaceType) newChild);
			updated = true;
		}

		if (!updated) {
			if (arraysAnnotations != null) {
				List<List<AnnotationExpr>> auxArraysAnn = new LinkedList<List<AnnotationExpr>>(arraysAnnotations);
				for (List<AnnotationExpr> list : auxArraysAnn) {
					updated = updated || replaceChildNodeInList(oldChild, newChild, list);
				}
				if (updated) {
					arraysAnnotations = auxArraysAnn;
				}
			}
		}
		return updated;
	}

	@Override
	public ReferenceType clone() throws CloneNotSupportedException {
		List<List<AnnotationExpr>> copy = null;
		if (arraysAnnotations != null) {
			copy = new LinkedList<List<AnnotationExpr>>();
			for (List<AnnotationExpr> item : arraysAnnotations) {
				copy.add(clone(item));
			}
		}
		return new ReferenceType(clone(type), arrayCount, clone(getAnnotations()), copy);
	}

}
