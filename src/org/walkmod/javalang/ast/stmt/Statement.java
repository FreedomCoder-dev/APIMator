package org.walkmod.javalang.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.SymbolData;
import org.walkmod.javalang.ast.SymbolDataAware;

public abstract class Statement extends Node implements SymbolDataAware<SymbolData> {

	private SymbolData symbolData;

	public Statement() {}

	public Statement(int beginLine, int beginColumn, int endLine, int endColumn) {
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
	public abstract Statement clone() throws CloneNotSupportedException;

	@Override
	public List<Node> getChildren() {
		return new LinkedList<Node>();
	}
}
