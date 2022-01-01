package org.walkmod.javalang.ast.stmt;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class LabeledStmt extends Statement {

	private String label;

	private Statement stmt;

	public LabeledStmt() {}

	public LabeledStmt(String label, Statement stmt) {
		this.label = label;
		setStmt(stmt);
	}

	public LabeledStmt(int beginLine, int beginColumn, int endLine, int endColumn, String label, Statement stmt) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.label = label;
		setStmt(stmt);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (stmt == child) {
				stmt = null;
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
		List<Node> children = super.getChildren();
		if (stmt != null) {
			children.add(stmt);
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

	public String getLabel() {
		return label;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setStmt(Statement stmt) {
		if (this.stmt != null) {
			updateReferences(this.stmt);
		}
		this.stmt = stmt;
		setAsParentNodeOf(stmt);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == stmt) {
			setStmt((Statement) newChild);
			updated = true;
		}

		return updated;
	}

	@Override
	public LabeledStmt clone() throws CloneNotSupportedException {
		return new LabeledStmt(label, clone(stmt));
	}
}
