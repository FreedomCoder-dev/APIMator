package org.walkmod.javalang.ast.body;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.Refactorizable;
import org.walkmod.javalang.ast.Refactorization;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class Parameter extends BaseParameter implements Refactorizable {

	private Type type;

	private boolean isVarArgs;

	public Parameter() {}

	public Parameter(Type type, VariableDeclaratorId id) {
		super(id);
		setType(type);
	}

	public Parameter(int modifiers, Type type, VariableDeclaratorId id) {
		super(modifiers, id);
		setType(type);
	}

	public Parameter(int beginLine, int beginColumn, int endLine, int endColumn, int modifiers, List<AnnotationExpr> annotations, Type type, boolean isVarArgs, VariableDeclaratorId id) {
		super(beginLine, beginColumn, endLine, endColumn, modifiers, annotations, id);
		setType(type);
		setVarArgs(isVarArgs);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			result = super.removeChild(child);
			if (!result) {
				if (type != null) {
					if (type == child) {
						type = null;
						result = true;
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
	public List<Node> getChildren() {
		List<Node> children = super.getChildren();
		if (type != null) {
			children.add(type);
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

	public Type getType() {
		return type;
	}

	public boolean isVarArgs() {
		return isVarArgs;
	}

	public void setType(Type type) {
		this.type = type;
		setAsParentNodeOf(type);
	}

	public void setVarArgs(boolean isVarArgs) {
		this.isVarArgs = isVarArgs;
	}

	@Override
	public boolean rename(String newName) {
		Refactorization refactorization = new Refactorization();
		if (refactorization.refactorVariable(this, newName)) {
			replaceChildNode(getId(), new VariableDeclaratorId(newName));
			return true;
		}
		return false;
	}

	@Override
	public Parameter clone() throws CloneNotSupportedException {
		return new Parameter(getModifiers(), clone(getType()), clone(getId()));
	}
}
