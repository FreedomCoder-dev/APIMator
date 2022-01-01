package org.walkmod.javalang.ast.stmt;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.ast.expr.VariableDeclarationExpr;

public final class ForeachStmt extends Statement {

	private VariableDeclarationExpr var;

	private Expression iterable;

	private Statement body;

	public ForeachStmt() {}

	public ForeachStmt(VariableDeclarationExpr var, Expression iterable, Statement body) {
		setVariable(var);
		setIterable(iterable);
		setBody(body);
	}

	public ForeachStmt(int beginLine, int beginColumn, int endLine, int endColumn, VariableDeclarationExpr var, Expression iterable, Statement body) {
		super(beginLine, beginColumn, endLine, endColumn);
		setVariable(var);
		setIterable(iterable);
		setBody(body);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (var == child) {
				var = null;
				result = true;
			}
			if (!result) {
				if (iterable == child) {
					iterable = null;
					result = true;
				}
			}
			if (!result) {
				if (body == child) {
					body = null;
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
		if (var != null) {
			children.add(var);
		}
		if (iterable != null) {
			children.add(iterable);
		}
		if (body != null) {
			children.add(body);
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

	public Expression getIterable() {
		return iterable;
	}

	public VariableDeclarationExpr getVariable() {
		return var;
	}

	public void setBody(Statement body) {
		if (this.body != null) {
			updateReferences(this.body);
		}
		this.body = body;
		setAsParentNodeOf(body);
	}

	public void setIterable(Expression iterable) {
		if (this.iterable != null) {
			updateReferences(this.iterable);
		}
		this.iterable = iterable;
		setAsParentNodeOf(iterable);
	}

	public void setVariable(VariableDeclarationExpr var) {
		this.var = var;
		setAsParentNodeOf(var);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == iterable) {
			setIterable((Expression) newChild);
			updated = true;
		}
		if (!updated) {
			if (oldChild == var) {
				setVariable((VariableDeclarationExpr) newChild);
				updated = true;
			}
			if (!updated) {
				if (oldChild == body) {
					setBody((Statement) newChild);
					updated = true;
				}
			}
		}
		return updated;
	}

	@Override
	public ForeachStmt clone() throws CloneNotSupportedException {
		return new ForeachStmt(clone(var), clone(iterable), clone(body));
	}
}
