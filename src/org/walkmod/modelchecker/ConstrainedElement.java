package org.walkmod.modelchecker;

import java.util.List;

public interface ConstrainedElement {

	void setConstraints(List<Constraint> constraints);

	List<Constraint> getConstraints();

	boolean check();
}
