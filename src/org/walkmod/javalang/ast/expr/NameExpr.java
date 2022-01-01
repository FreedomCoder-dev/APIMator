package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.ScopeAwareUtil;
import org.walkmod.javalang.ast.SymbolDefinition;
import org.walkmod.javalang.ast.SymbolReference;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class NameExpr extends Expression implements SymbolReference {

	private String name;

	private SymbolDefinition symbolDefinition;

	public NameExpr() {}

	public NameExpr(String name) {
		this.name = name;
	}

	public NameExpr(int beginLine, int beginColumn, int endLine, int endColumn, String name) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.name = name;
	}

	@Override
	public boolean removeChild(Node child) {
		return false;
	}

	@Override
	public List<Node> getChildren() {
		return new LinkedList<Node>();
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

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	@Override
	public SymbolDefinition getSymbolDefinition() {
		return symbolDefinition;
	}

	@Override
	public void setSymbolDefinition(SymbolDefinition symbolDefinition) {
		this.symbolDefinition = symbolDefinition;
	}

	@Override
	public boolean replaceChildNode(Node oldChild, Node newChild) {
		return false;
	}

	@Override
	public NameExpr clone() throws CloneNotSupportedException {
		return new NameExpr(name);
	}

	@Override
	public Map<String, SymbolDefinition> getVariableDefinitions() {
		return ScopeAwareUtil.getVariableDefinitions(this);
	}

	@Override
	public Map<String, List<SymbolDefinition>> getMethodDefinitions() {
		return ScopeAwareUtil.getMethodDefinitions(NameExpr.this);
	}

	@Override
	public Map<String, SymbolDefinition> getTypeDefinitions() {
		return ScopeAwareUtil.getTypeDefinitions(NameExpr.this);
	}

}
