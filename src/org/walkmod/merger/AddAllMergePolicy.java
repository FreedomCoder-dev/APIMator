package org.walkmod.merger;

import java.util.List;

public class AddAllMergePolicy<T> extends TypeMergePolicy<T> {

	@SuppressWarnings("unchecked")
	@Override
	public void apply(List<T> localList, List<T> remoteList, 
	@SuppressWarnings("rawtypes") List resultList) {
		if (localList != null) {
			resultList.addAll(localList);
		}
		if (remoteList != null) {
			resultList.addAll(remoteList);
		}
	}

	@Override
	public T apply(T localObject, T remoteObject) {
		return localObject;
	}
}
