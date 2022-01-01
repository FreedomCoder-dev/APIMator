package org.walkmod.javalang.ast.stmt;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.Expression;

public final class IfStmt extends Statement {

	private Expression condition;

	private Statement thenStmt;

	private Statement elseStmt;

	public IfStmt() {}

	public IfStmt(Expression condition, Statement thenStmt, Statement elseStmt) {
		setCondition(condition);
		setThenStmt(thenStmt);
		setElseStmt(elseStmt);
	}

	public IfStmt(int beginLine, int beginColumn, int endLine, int endColumn, Expression condition, Statement thenStmt, Statement elseStmt) {
		super(beginLine, beginColumn, endLine, endColumn);
		setCondition(condition);
		setThenStmt(thenStmt);
		setElseStmt(elseStmt);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (condition == child) {
				condition = null;
				result = true;
			}
			if (!result) {
				if (thenStmt == child) {
					thenStmt = null;
					result = true;
				}
			}
			if (!result) {
				if (elseStmt == child) {
					elseStmt = null;
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
		if (condition != null) {
			children.add(condition);
		}
		if (thenStmt != null) {
			children.add(thenStmt);
		}
		if (elseStmt != null) {
			children.add(elseStmt);
		}
		return children;
	}

	public BlockStmt makeThen() {
		BlockStmt blockStmt = new BlockStmt();
		setThenStmt(blockStmt);
		blockStmt.setPar(this);
		return blockStmt;
	}

	public BlockStmt makeElse() {
		BlockStmt blockStmt = new BlockStmt();
		setElseStmt(blockStmt);
		blockStmt.setPar(this);
		return blockStmt;
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

	public Expression getCondition() {
		return condition;
	}

	public Statement getElseStmt() {
		return elseStmt;
	}

	public Statement getThenStmt() {
		return thenStmt;
	}

	public void setCondition(Expression condition) {
		if (this.condition != null) {
			updateReferences(this.condition);
		}
		this.condition = condition;
		setAsParentNodeOf(condition);
	}

	public void setElseStmt(Statement elseStmt) {
		if (this.elseStmt != null) {
			updateReferences(this.elseStmt);
		}
		this.elseStmt = elseStmt;
		setAsParentNodeOf(elseStmt);
	}

	public void setThenStmt(Statement thenStmt) {
		if (this.thenStmt != null) {
			updateReferences(this.thenStmt);
		}
		this.thenStmt = thenStmt;
		setAsParentNodeOf(thenStmt);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == condition) {
			setCondition((Expression) newChild);
			updated = true;
		}
		if (!updated) {
			if (oldChild == thenStmt) {
				setThenStmt((Statement) newChild);
				updated = true;
			}
			if (!updated) {
				if (oldChild == elseStmt) {
					setElseStmt((Statement) newChild);
					updated = true;
				}
			}
		}
		return updated;
	}

	@Override
	public IfStmt clone() throws CloneNotSupportedException {
		return new IfStmt(clone(condition), clone(thenStmt), clone(elseStmt));
	}
}
