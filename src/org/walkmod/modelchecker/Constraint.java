package org.walkmod.modelchecker;

public interface Constraint<T> {

	boolean isConstrained(T o);

}
