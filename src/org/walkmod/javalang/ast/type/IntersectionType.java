package org.walkmod.javalang.ast.type;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class IntersectionType extends Type {

	private List<ReferenceType> bounds;

	public IntersectionType() {

	}

	public IntersectionType(List<ReferenceType> bounds) {
		setBounds(bounds);
	}

	public IntersectionType(int beginLine, int beginColumn, int endLine, int endColumn, List<ReferenceType> bounds) {
		super(beginLine, beginColumn, endLine, endColumn, null);
		setBounds(bounds);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (bounds != null) {
				if (child instanceof ReferenceType) {
					List<ReferenceType> boundsAux = new LinkedList<ReferenceType>(bounds);
					result = boundsAux.remove(child);
					bounds = boundsAux;
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
		if (bounds != null) {
			children.addAll(bounds);
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

	public List<ReferenceType> getBounds() {
		return bounds;
	}

	public void setBounds(List<ReferenceType> bounds) {
		this.bounds = bounds;
		setAsParentNodeOf(bounds);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return super.replaceChildNode(oldChild, newChild);
	}

	@Override
	public IntersectionType clone() throws CloneNotSupportedException {
		return new IntersectionType(clone(getBounds()));
	}
}
