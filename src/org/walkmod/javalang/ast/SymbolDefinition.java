package org.walkmod.javalang.ast;

import java.util.List;

public interface SymbolDefinition extends ScopeAware {

	List<SymbolReference> getUsages();

	void setUsages(List<SymbolReference> usages);

	boolean addUsage(SymbolReference usage);

	List<SymbolReference> getBodyReferences();

	void setBodyReferences(List<SymbolReference> bodyReferences);

	boolean addBodyReference(SymbolReference bodyReference);

	int getScopeLevel();

	void setScopeLevel(int scopeLevel);

	String getSymbolName();

}
