package org.walkmod.javalang.ast;

import java.lang.reflect.Constructor;

public interface ConstructorSymbolData extends SymbolData {
	Constructor<?> getConstructor();
}
