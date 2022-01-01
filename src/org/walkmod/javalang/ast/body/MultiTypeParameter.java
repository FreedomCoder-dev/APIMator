package org.walkmod.javalang.ast.body;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class MultiTypeParameter extends BaseParameter {

	private List<Type> types;

	public MultiTypeParameter() {}

	public MultiTypeParameter(int modifiers, List<AnnotationExpr> annotations, List<Type> types, VariableDeclaratorId id) {
		super(modifiers, annotations, id);
		setTypes(types);
	}

	public MultiTypeParameter(int beginLine, int beginColumn, int endLine, int endColumn, int modifiers, List<AnnotationExpr> annotations, List<Type> types, VariableDeclaratorId id) {
		super(beginLine, beginColumn, endLine, endColumn, modifiers, annotations, id);
		setTypes(types);
	}

	@Override
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (types != null) {
			children.addAll(types);
		}
		return children;
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			result = super.removeChild(child);
			if (!result) {
				if (child instanceof Type) {
					if (types != null) {
						List<Type> typesAux = new LinkedList<Type>(types);
						result = typesAux.remove(child);
						types = typesAux;
					}
				}
			}
		}
		if (result) {
			updateReferences(child);
		}
		return result;
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

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
		setAsParentNodeOf(types);
	}

	@Override
	public MultiTypeParameter clone() throws CloneNotSupportedException {
		return new MultiTypeParameter(getModifiers(), clone(getAnnotations()), clone(getTypes()), clone(getId()));
	}
}
