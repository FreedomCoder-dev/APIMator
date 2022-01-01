package org.walkmod.javalang.ast;

public interface SymbolDataAware<T extends SymbolData> {

	T getSymbolData();

	void setSymbolData(T symbolData);
}
