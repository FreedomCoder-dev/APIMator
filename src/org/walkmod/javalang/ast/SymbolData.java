package org.walkmod.javalang.ast;

import java.util.List;

public interface SymbolData {

	String getName();

	Class<?> getClazz();

	List<Class<?>> getBoundClasses();

	int getArrayCount();

	boolean isTemplateVariable();

	<T extends SymbolData> List<T> getParameterizedTypes();

	SymbolData merge(SymbolData other);

	List<Class<?>> getLowerBoundClasses();
}
