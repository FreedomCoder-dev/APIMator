package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class ArrayCreationExpr extends Expression {

	private Type type;

	private int arrayCount;

	private ArrayInitializerExpr initializer;

	private List<Expression> dimensions;

	private List<List<AnnotationExpr>> arraysAnnotations;

	public ArrayCreationExpr() {}

	public ArrayCreationExpr(Type type, int arrayCount, ArrayInitializerExpr initializer) {
		setType(type);
		this.arrayCount = arrayCount;
		setInitializer(initializer);
		this.dimensions = null;
	}

	public ArrayCreationExpr(Type type, int arrayCount, ArrayInitializerExpr initializer, List<List<AnnotationExpr>> arraysAnnotations) {
		setType(type);
		this.arrayCount = arrayCount;
		setInitializer(initializer);
		this.dimensions = null;
		setArraysAnnotations(arraysAnnotations);
	}

	public ArrayCreationExpr(int beginLine, int beginColumn, int endLine, int endColumn, Type type, int arrayCount, ArrayInitializerExpr initializer) {
		super(beginLine, beginColumn, endLine, endColumn);
		setType(type);
		this.arrayCount = arrayCount;
		setInitializer(initializer);
		this.dimensions = null;
	}

	public ArrayCreationExpr(Type type, List<Expression> dimensions, int arrayCount) {
		setType(type);
		this.arrayCount = arrayCount;
		setDimensions(dimensions);
		this.initializer = null;
	}

	public ArrayCreationExpr(Type type, List<Expression> dimensions, int arrayCount, List<List<AnnotationExpr>> arraysAnnotations) {
		setType(type);
		this.arrayCount = arrayCount;
		setDimensions(dimensions);
		this.initializer = null;
		setArraysAnnotations(arraysAnnotations);
	}

	public ArrayCreationExpr(int beginLine, int beginColumn, int endLine, int endColumn, Type type, List<Expression> dimensions, int arrayCount) {
		super(beginLine, beginColumn, endLine, endColumn);
		setType(type);
		this.arrayCount = arrayCount;
		setDimensions(dimensions);
		this.initializer = null;
	}

	public ArrayCreationExpr(int beginLine, int beginColumn, int endLine, int endColumn, Type type, List<Expression> dimensions, int arrayCount, List<List<AnnotationExpr>> arraysAnnotations) {
		super(beginLine, beginColumn, endLine, endColumn);
		setType(type);
		this.arrayCount = arrayCount;
		setDimensions(dimensions);
		this.initializer = null;
		setArraysAnnotations(arraysAnnotations);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (type == child) {
				type = null;
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
		if (type != null) {
			children.add(type);
		}
		if (initializer != null) {
			children.add(initializer);
		}
		if (dimensions != null) {
			children.addAll(dimensions);
		}
		if (arraysAnnotations != null) {
			for (List<AnnotationExpr> l : arraysAnnotations) {
				if (l != null) children.addAll(l);
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

	public List<Expression> getDimensions() {
		return dimensions;
	}

	public ArrayInitializerExpr getInitializer() {
		return initializer;
	}

	public Type getType() {
		return type;
	}

	public void setArrayCount(int arrayCount) {
		this.arrayCount = arrayCount;
	}

	public void setDimensions(List<Expression> dimensions) {
		this.dimensions = dimensions;
		setAsParentNodeOf(dimensions);
	}

	public void setInitializer(ArrayInitializerExpr initializer) {
		if (this.initializer != null) {
			updateReferences(this.initializer);
		}
		this.initializer = initializer;
		setAsParentNodeOf(initializer);
	}

	public void setType(Type type) {
		if (this.type != null) {
			updateReferences(this.type);
		}
		this.type = type;
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
		boolean updated = false;

		if (type == oldChild) {
			setType((Type) newChild);
			updated = true;
		}
		if (!updated) {
			if (initializer == oldChild) {
				setInitializer((ArrayInitializerExpr) newChild);
				updated = true;
			}
		}
		if (!updated && dimensions != null) {
			List<Expression> auxDimensions = new LinkedList<Expression>(dimensions);
			updated = this.replaceChildNodeInList(oldChild, newChild, auxDimensions);
			if (updated) {
				dimensions = auxDimensions;
			}
		}
		if (!updated && arraysAnnotations != null) {
			List<List<AnnotationExpr>> auxArraysAnn = new LinkedList<List<AnnotationExpr>>(arraysAnnotations);
			updated = replaceChildNodeInList(oldChild, newChild, auxArraysAnn);
			if (updated) {
				arraysAnnotations = auxArraysAnn;
			}
		}
		return updated;
	}

	@Override
	public ArrayCreationExpr clone() throws CloneNotSupportedException {
		List<List<AnnotationExpr>> copy = null;
		if (arraysAnnotations != null) {
			copy = new LinkedList<List<AnnotationExpr>>();
			for (List<AnnotationExpr> item : arraysAnnotations) {
				copy.add(clone(item));
			}
		}
		return new ArrayCreationExpr(clone(getType()), clone(dimensions), arrayCount, copy);
	}
}
