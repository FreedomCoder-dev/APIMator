package org.walkmod.javalang.ast;

import java.lang.reflect.Field;

public interface FieldSymbolData extends SymbolData {

	Field getField();
}
