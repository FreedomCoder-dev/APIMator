package org.walkmod.merger;

import java.util.List;

public interface MergePolicy<T> {

	T apply(T localObject, T remoteObject);

	void apply(List<T> localList, List<T> remoteList,
               @SuppressWarnings("rawtypes") List resultList);
}
