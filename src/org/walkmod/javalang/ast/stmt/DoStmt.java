package org.walkmod.javalang.ast.stmt;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.Expression;

public final class DoStmt extends Statement {

	private Statement body;

	private Expression condition;

	public DoStmt() {}

	public DoStmt(Statement body, Expression condition) {
		setBody(body);
		setCondition(condition);
	}

	public DoStmt(int beginLine, int beginColumn, int endLine, int endColumn, Statement body, Expression condition) {
		super(beginLine, beginColumn, endLine, endColumn);
		setBody(body);
		setCondition(condition);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (body == child) {
				body = null;
				result = true;
			}

			if (!result) {
				if (condition == child) {
					condition = null;
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
		if (body != null) {
			children.add(body);
		}
		if (condition != null) {
			children.add(condition);
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

	public Statement getBody() {
		return body;
	}

	public Expression getCondition() {
		return condition;
	}

	public void setBody(Statement body) {
		if (this.body != null) {
			updateReferences(this.body);
		}
		this.body = body;
		setAsParentNodeOf(body);
	}

	public void setCondition(Expression condition) {
		if (this.condition != null) {
			updateReferences(this.condition);
		}
		this.condition = condition;
		setAsParentNodeOf(condition);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == body) {
			setBody((Statement) newChild);
			updated = true;
		}
		if (!updated) {
			if (oldChild == condition) {
				setCondition((Expression) newChild);
				updated = true;
			}
		}
		return updated;
	}

	@Override
	public DoStmt clone() throws CloneNotSupportedException {
		return new DoStmt(clone(body), clone(condition));
	}
}
