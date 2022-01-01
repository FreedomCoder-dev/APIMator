package org.walkmod.javalang.ast;

import java.util.List;
import java.util.Map;

public interface ScopeAware {

	Map<String, SymbolDefinition> getVariableDefinitions();

	Map<String, SymbolDefinition> getTypeDefinitions();

	Map<String, List<SymbolDefinition>> getMethodDefinitions();
}
