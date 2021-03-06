package org.walkmod.javalang.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.ConstructorSymbolData;
import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.type.Type;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.Expression;

public final class ExplicitConstructorInvocationStmt extends Statement {

	private List<Type> typeArgs;

	private boolean isThis;

	private Expression expr;

	private List<Expression> args;

	public ExplicitConstructorInvocationStmt() {}

	public ExplicitConstructorInvocationStmt(boolean isThis, Expression expr, List<Expression> args) {
		this.isThis = isThis;
		setExpr(expr);
		setArgs(args);
	}

	public ExplicitConstructorInvocationStmt(int beginLine, int beginColumn, int endLine, int endColumn, List<Type> typeArgs, boolean isThis, Expression expr, List<Expression> args) {
		super(beginLine, beginColumn, endLine, endColumn);
		setTypeArgs(typeArgs);
		this.isThis = isThis;
		setExpr(expr);
		setArgs(args);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;

		if (child != null) {
			if (expr == child) {
				expr = null;
				result = true;
			}
			if (!result) {
				if (typeArgs != null) {
					if (child instanceof Type) {
						List<Type> typeArgsAux = new LinkedList<Type>(typeArgs);
						result = typeArgsAux.remove(child);
						typeArgs = typeArgsAux;
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
		List<Node> children = super.getChildren();
		if (typeArgs != null) {
			children.addAll(typeArgs);
		}
		if (expr != null) {
			children.add(expr);
		}
		if (args != null) {
			children.addAll(args);
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

	public List<Expression> getArgs() {
		return args;
	}

	public Expression getExpr() {
		return expr;
	}

	@Override
	public ConstructorSymbolData getSymbolData() {
		return (ConstructorSymbolData) super.getSymbolData();
	}

	public List<Type> getTypeArgs() {
		return typeArgs;
	}

	public boolean isThis() {
		return isThis;
	}

	public void setArgs(List<Expression> args) {
		this.args = args;
		setAsParentNodeOf(args);
	}

	public void setExpr(Expression expr) {
		if (this.expr != null) {
			updateReferences(this.expr);
		}
		this.expr = expr;
		setAsParentNodeOf(expr);
	}

	public void setThis(boolean isThis) {
		this.isThis = isThis;
	}

	public void setTypeArgs(List<Type> typeArgs) {
		this.typeArgs = typeArgs;
		setAsParentNodeOf(typeArgs);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == expr) {
			setExpr((Expression) newChild);
			updated = true;
		}
		if (!updated && args != null) {

			List<Expression> auxArgs = new LinkedList<Expression>(args);

			updated = replaceChildNodeInList(oldChild, newChild, auxArgs);

			if (updated) {
				args = auxArgs;
			}

		}
		if (!updated && typeArgs != null) {

			List<Type> auxTypeArgs = new LinkedList<Type>(typeArgs);
			updated = replaceChildNodeInList(oldChild, newChild, auxTypeArgs);

			if (updated) {
				typeArgs = auxTypeArgs;
			}
		}
		return updated;
	}

	@Override
	public ExplicitConstructorInvocationStmt clone() throws CloneNotSupportedException {
		return new ExplicitConstructorInvocationStmt(isThis, clone(expr), clone(args));
	}
}
