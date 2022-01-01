package org.walkmod.merger;

import java.util.Comparator;

public interface IdentificableNode {

	Comparator<?> getIdentityComparator();
}
