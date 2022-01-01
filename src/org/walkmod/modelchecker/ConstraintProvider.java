package org.walkmod.modelchecker;

public interface ConstraintProvider<T> {

	Constraint<?> getConstraint(T model);
}
