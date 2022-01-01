package org.walkmod.javalang.ast.expr;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.SymbolData;
import org.walkmod.javalang.ast.SymbolDataAware;

public abstract class Expression extends Node implements SymbolDataAware<SymbolData> {

	private SymbolData symbolData;

	public Expression() {}

	public Expression(int beginLine, int beginColumn, int endLine, int endColumn) {
		super(beginLine, beginColumn, endLine, endColumn);
	}

	@Override
	public SymbolData getSymbolData() {
		return symbolData;
	}

	@Override
	public void setSymbolData(SymbolData symbolData) {
		this.symbolData = symbolData;
	}

	@Override
	public abstract Expression clone() throws CloneNotSupportedException;

	@Override
	public List<Node> getChildren() {
		return new LinkedList<Node>();
	}

}
