package org.walkmod.javalang.ast;

public interface SymbolReference extends ScopeAware {

	SymbolDefinition getSymbolDefinition();

	void setSymbolDefinition(SymbolDefinition symbolDefinition);
}
