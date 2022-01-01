package org.walkmod.merger;

import java.util.List;

public class AssignMergePolicy<T> extends TypeMergePolicy<T> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void apply(List<T> localList, List<T> remoteList, List resultList) {
		if (remoteList != null) {
			resultList.addAll(remoteList);
		}
	}

	@Override
	public T apply(T localObject, T remoteObject) {
		return remoteObject;
	}
}
