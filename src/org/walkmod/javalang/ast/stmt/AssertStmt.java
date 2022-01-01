package org.walkmod.javalang.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.Expression;

public final class AssertStmt extends Statement {

	private Expression check;

	private Expression msg;

	public AssertStmt() {}

	public AssertStmt(Expression check) {
		setCheck(check);
	}

	public AssertStmt(Expression check, Expression msg) {
		setCheck(check);
		setMessage(msg);
	}

	public AssertStmt(int beginLine, int beginColumn, int endLine, int endColumn, Expression check, Expression msg) {
		super(beginLine, beginColumn, endLine, endColumn);
		setCheck(check);
		setMessage(msg);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (check == child) {
				check = null;
				result = true;
			}

			if (!result) {
				if (msg == child) {
					msg = null;
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
		if (check != null) {
			children.add(check);
		}
		if (msg != null) {
			children.add(msg);
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

	public Expression getCheck() {
		return check;
	}

	public Expression getMessage() {
		return msg;
	}

	public void setCheck(Expression check) {
		if (this.check != null) {
			updateReferences(this.check);
		}
		this.check = check;
		setAsParentNodeOf(check);
	}

	public void setMessage(Expression msg) {
		if (this.msg != null) {
			updateReferences(this.msg);
		}
		this.msg = msg;
		setAsParentNodeOf(msg);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == check) {
			setCheck((Expression) newChild);
			updated = true;
		}
		if (!updated) {
			if (oldChild == msg) {
				setMessage((Expression) newChild);
				updated = true;
			}
		}
		return updated;
	}

	@Override
	public AssertStmt clone() throws CloneNotSupportedException {
		return new AssertStmt(clone(check), clone(msg));
	}
}
