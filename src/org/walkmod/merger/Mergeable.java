package org.walkmod.merger;

public interface Mergeable<T> extends IdentificableNode {

	void merge(T t1, MergeEngine configuration);
}
