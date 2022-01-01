package org.walkmod.merger;

import java.util.List;

public class OverrideMergePolicy<T extends IdentificableNode> extends ObjectMergePolicy<T> {

	@Override
	public void apply(T localObject, T remoteObject, List<T> resultList) {
		if (remoteObject != null) {
			resultList.add(remoteObject);
		}
	}

	@Override
	public T apply(T localObject, T remoteObject) {
		return remoteObject;
	}
}
