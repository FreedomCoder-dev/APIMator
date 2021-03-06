package org.walkmod.javalang.ast.type;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.AnnotationExpr;

public final class WildcardType extends Type {

	private ReferenceType ext;

	private ReferenceType sup;

	public WildcardType() {}

	public WildcardType(ReferenceType ext) {
		setExtends(ext);
	}

	public WildcardType(ReferenceType ext, ReferenceType sup) {
		setExtends(ext);
		setSuper(sup);
	}

	public WildcardType(int beginLine, int beginColumn, int endLine, int endColumn, ReferenceType ext, ReferenceType sup) {
		super(beginLine, beginColumn, endLine, endColumn);
		setExtends(ext);
		setSuper(sup);
	}

	public WildcardType(int beginLine, int beginColumn, int endLine, int endColumn, ReferenceType ext, ReferenceType sup, List<AnnotationExpr> annotations) {
		super(beginLine, beginColumn, endLine, endColumn, annotations);
		setExtends(ext);
		setSuper(sup);

	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (ext == child) {
				ext = null;
				result = true;
			}
			if (!result) {
				if (sup == child) {
					sup = null;
					result = true;
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
		if (ext != null) {
			children.add(ext);
		}
		if (sup != null) {
			children.add(sup);
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

	public ReferenceType getExtends() {
		return ext;
	}

	public ReferenceType getSuper() {
		return sup;
	}

	public void setExtends(ReferenceType ext) {
		if (this.ext != null) {
			updateReferences(this.ext);
		}
		this.ext = ext;
		setAsParentNodeOf(ext);
	}

	public void setSuper(ReferenceType sup) {
		if (this.sup != null) {
			updateReferences(this.sup);
		}
		this.sup = sup;
		setAsParentNodeOf(sup);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = super.replaceChildNode(oldChild, newChild);
		if (!updated) {
			if (ext == oldChild) {
				setExtends((ReferenceType) newChild);
				updated = true;
			}
			if (!updated) {
				if (sup == oldChild) {
					setSuper((ReferenceType) newChild);
				}
			}
		}
		return updated;
	}

	@Override
	public WildcardType clone() throws CloneNotSupportedException {
		return new WildcardType(clone(ext), clone(sup));
	}
}
