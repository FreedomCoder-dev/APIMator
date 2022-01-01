package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.body.VariableDeclarator;

public final class VariableDeclarationExpr extends Expression {

	private int modifiers;

	private List<AnnotationExpr> annotations;

	private Type type;

	private List<VariableDeclarator> vars;

	public VariableDeclarationExpr() {}

	public VariableDeclarationExpr(Type type, List<VariableDeclarator> vars) {
		setType(type);
		setVars(vars);
	}

	public VariableDeclarationExpr(int modifiers, Type type, List<VariableDeclarator> vars) {
		this.modifiers = modifiers;
		setType(type);
		setVars(vars);
	}

	public VariableDeclarationExpr(int beginLine, int beginColumn, int endLine, int endColumn, int modifiers, List<AnnotationExpr> annotations, Type type, List<VariableDeclarator> vars) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.modifiers = modifiers;
		setAnnotations(annotations);
		setType(type);
		setVars(vars);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (annotations != null) {
				if (child instanceof AnnotationExpr) {
					List<AnnotationExpr> annotationsAux = new LinkedList<AnnotationExpr>(annotations);
					result = annotationsAux.remove(child);
					annotations = annotationsAux;
				}
			}
			if (!result) {
				if (type == child) {
					type = null;
					result = true;
				}
			}
			if (!result) {
				if (vars != null) {
					if (child instanceof VariableDeclarator) {
						List<VariableDeclarator> varsAux = new LinkedList<VariableDeclarator>(vars);
						result = varsAux.remove(child);
						vars = varsAux;
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
		List<Node> children = new LinkedList<Node>();
		if (annotations != null) {
			children.addAll(annotations);
		}
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

	public List<AnnotationExpr> getAnnotations() {
		return annotations;
	}

	public int getModifiers() {
		return modifiers;
	}

	public Type getType() {
		return type;
	}

	public List<VariableDeclarator> getVars() {
		return vars;
	}

	public void setAnnotations(List<AnnotationExpr> annotations) {
		this.annotations = annotations;
		setAsParentNodeOf(annotations);
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public void setType(Type type) {
		this.type = type;
		setAsParentNodeOf(type);
	}

	public void setVars(List<VariableDeclarator> vars) {
		this.vars = vars;
		setAsParentNodeOf(vars);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == type) {
			type = (Type) newChild;
			updated = true;
		}
		if (!updated && annotations != null) {
			List<AnnotationExpr> auxAnnotations = new LinkedList<AnnotationExpr>(annotations);
			updated = replaceChildNodeInList(oldChild, newChild, auxAnnotations);
			if (updated) {
				annotations = auxAnnotations;
			}

		}
		if (!updated && vars != null) {
			List<VariableDeclarator> auxVars = new LinkedList<VariableDeclarator>(vars);

			updated = replaceChildNodeInList(oldChild, newChild, auxVars);

			if (updated) {
				vars = auxVars;
			}
		}
		return updated;
	}

	@Override
	public VariableDeclarationExpr clone() throws CloneNotSupportedException {
		return new VariableDeclarationExpr(clone(type), clone(vars));
	}

}
