package org.walkmod.javalang.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;
import org.walkmod.javalang.ast.expr.VariableDeclarationExpr;

public final class TryStmt extends Statement {

	private List<VariableDeclarationExpr> resources;

	private BlockStmt tryBlock;

	private List<CatchClause> catchs;

	private BlockStmt finallyBlock;

	public TryStmt() {}

	public TryStmt(final BlockStmt tryBlock, final List<CatchClause> catchs, final BlockStmt finallyBlock) {
		setTryBlock(tryBlock);
		setCatchs(catchs);
		setFinallyBlock(finallyBlock);
	}

	public TryStmt(final int beginLine, final int beginColumn, final int endLine, final int endColumn, List<VariableDeclarationExpr> resources, final BlockStmt tryBlock, final List<CatchClause> catchs, final BlockStmt finallyBlock) {
		super(beginLine, beginColumn, endLine, endColumn);
		setResources(resources);
		setTryBlock(tryBlock);
		setCatchs(catchs);
		setFinallyBlock(finallyBlock);
	}

	@Override
	public boolean removeChild(Node child) {
		boolean result = false;
		if (child != null) {
			if (tryBlock == child) {
				tryBlock = null;
				result = true;
			}
			if (!result) {
				if (finallyBlock == child) {
					finallyBlock = null;
					result = true;
				}
			}
			if (!result) {
				if (resources != null) {
					if (child instanceof VariableDeclarationExpr) {
						List<VariableDeclarationExpr> resourcesAux = new LinkedList<VariableDeclarationExpr>(resources);
						result = resourcesAux.remove(child);
						resources = resourcesAux;
					}
				}
			}
			if (!result) {
				if (catchs != null) {
					if (child instanceof CatchClause) {
						List<CatchClause> catchsAux = new LinkedList<CatchClause>(catchs);
						result = catchsAux.remove(child);
						catchs = catchsAux;
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
		if (resources != null) {
			children.addAll(resources);
		}
		if (tryBlock != null) {
			children.add(tryBlock);
		}
		if (catchs != null) {
			children.addAll(catchs);
		}
		if (finallyBlock != null) {
			children.add(finallyBlock);
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

	public List<CatchClause> getCatchs() {
		return catchs;
	}

	public BlockStmt getFinallyBlock() {
		return finallyBlock;
	}

	public BlockStmt getTryBlock() {
		return tryBlock;
	}

	public List<VariableDeclarationExpr> getResources() {
		return resources;
	}

	public void setCatchs(List<CatchClause> catchs) {
		this.catchs = catchs;
		setAsParentNodeOf(catchs);

	}

	public void setFinallyBlock(BlockStmt finallyBlock) {
		if (this.finallyBlock != null) {
			updateReferences(this.finallyBlock);
		}
		this.finallyBlock = finallyBlock;
		setAsParentNodeOf(finallyBlock);

	}

	public void setTryBlock(BlockStmt tryBlock) {
		if (this.tryBlock != null) {
			updateReferences(this.tryBlock);
		}
		this.tryBlock = tryBlock;
		setAsParentNodeOf(tryBlock);

	}

	public void setResources(List<VariableDeclarationExpr> resources) {
		this.resources = resources;
		setAsParentNodeOf(resources);
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		boolean updated = false;
		if (oldChild == tryBlock) {
			setTryBlock((BlockStmt) newChild);
			updated = true;
		}
		if (!updated) {
			if (oldChild == finallyBlock) {
				setFinallyBlock((BlockStmt) newChild);
				updated = true;
			}
			if (!updated && catchs != null) {

				List<CatchClause> auxCatchs = new LinkedList<CatchClause>(catchs);

				updated = replaceChildNodeInList(oldChild, newChild, auxCatchs);

				if (updated) {
					catchs = auxCatchs;
				}

			}
			if (!updated && resources != null) {

				List<VariableDeclarationExpr> auxResources = new LinkedList<VariableDeclarationExpr>(resources);

				updated = replaceChildNodeInList(oldChild, newChild, auxResources);

				if (updated) {
					resources = auxResources;
				}
			}
		}

		return updated;
	}

	@Override
	public TryStmt clone() throws CloneNotSupportedException {
		return new TryStmt(clone(tryBlock), clone(catchs), clone(finallyBlock));
	}
}
