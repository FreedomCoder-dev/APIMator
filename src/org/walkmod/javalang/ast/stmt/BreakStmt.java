package org.walkmod.javalang.ast.stmt;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public final class BreakStmt extends Statement {

	private String id;

	public BreakStmt() {}

	public BreakStmt(String id) {
		this.id = id;
	}

	public BreakStmt(int beginLine, int beginColumn, int endLine, int endColumn, String id) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.id = id;
	}

	@Override
	public boolean removeChild(Node child) {
		return false;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public BreakStmt clone() throws CloneNotSupportedException {
		return new BreakStmt(id);
	}

}
