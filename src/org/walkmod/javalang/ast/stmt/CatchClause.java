package org.walkmod.javalang.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.body.MultiTypeParameter;
import org.walkmod.javalang.ast.body.VariableDeclaratorId;
import org.walkmod.javalang.ast.expr.AnnotationExpr;

public final class CatchClause extends Node {

	private MultiTypeParameter except;

	private BlockStmt catchBlock;

	public CatchClause() {}

	public CatchClause(final MultiTypeParameter except, final BlockStmt catchBlock) {
		setExcept(except);
		setCatchBlock(catchBlock);
	}

	public CatchClause(int exceptModifier, List<AnnotationExpr> exceptAnnotations, List<Type> exceptTypes, VariableDeclaratorId exceptId, BlockStmt catchBlock) {
		this(new MultiTypeParameter(exceptModifier, exceptAnnotations, exceptTypes, exceptId), catchBlock);
	}

	public CatchClause(final int beginLine, final int beginColumn, final int endLine, final int endColumn, final int exceptModifier, final List<AnnotationExpr> exceptAnnotations, final List<Type> exceptTypes, final VariableDeclaratorId exceptId, final BlockStmt catchBlock) {
		super(beginLine, beginColumn, endLine, endColumn);
		setExcept(new MultiTypeParameter(beginLine, beginColumn, endLine, endColumn, exceptModifier, exceptAnnotations, exceptTypes, exceptId));
		setCatchBlock(catchBlock);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (except == child) {
				except = null;
				result = true;
			}

			if (!result) {
				if (catchBlock == child) {
					catchBlock = null;
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
		List<Node> children = new LinkedList<Node>();
		if (except != null) {
			children.add(except);
		}
		if (catchBlock != null) {
			children.add(catchBlock);
		}
		return children;
	}

	@Override
	public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {
		if (!check()) {
			return null;
		}
		return v.visit(this, arg);
	}

	@Override
	public <A> void accept(final VoidVisitor<A> v, final A arg) {
		if (check()) {
			v.visit(this, arg);
		}
	}

	public BlockStmt getCatchBlock() {
		return catchBlock;
	}

	public MultiTypeParameter getExcept() {
		return except;
	}

	public void setCatchBlock(final BlockStmt catchBlock) {
		if (this.catchBlock != null) {
			updateReferences(this.catchBlock);
		}
		this.catchBlock = catchBlock;
		setAsParentNodeOf(catchBlock);
	}

	public void setExcept(final MultiTypeParameter except) {
		if (this.except != null) {
			updateReferences(this.except);
		}
		this.except = except;
		setAsParentNodeOf(except);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == except) {
			setExcept((MultiTypeParameter) newChild);
			updated = true;
		}
		if (!updated) {
			if (oldChild == catchBlock) {
				setCatchBlock((BlockStmt) newChild);
				updated = true;
			}
		}
		return updated;
	}

	@Override
	public CatchClause clone() throws CloneNotSupportedException {
		return new CatchClause(clone(except), clone(catchBlock));
	}
}
