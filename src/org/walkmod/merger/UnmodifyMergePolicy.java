package org.walkmod.merger;

import java.util.List;

public class UnmodifyMergePolicy<T> extends TypeMergePolicy<T> {

	@SuppressWarnings("unchecked")
	@Override
	public void apply(List<T> localList, List<T> remoteList, 
	@SuppressWarnings("rawtypes") List resultList) {
		if (localList != null) {
			resultList.addAll(localList);
		}
	}

	@Override
	public T apply(T localObject, T remoteObject) {
		return localObject;
	}
}
