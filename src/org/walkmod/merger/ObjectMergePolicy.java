package org.walkmod.merger;

import java.util.Comparator;
import java.util.List;

public abstract class ObjectMergePolicy<T extends IdentificableNode> implements MergePolicy<T> {

	public void apply(T remoteObject, List<T> localList, List<T> resultList) {
		Comparator<?> comparator = remoteObject.getIdentityComparator();
		Object localObject = CollectionUtil.findObject(localList, remoteObject, comparator);
		this.apply((T) localObject, remoteObject, resultList);
	}

	public void apply(List<T> localList, List<T> remoteList, @SuppressWarnings("rawtypes") List resultList) {
		if (remoteList != null) {
			for (Object remoteObject : remoteList) {
				this.apply((T) remoteObject, localList, resultList);
			}
		}
		if (localList != null) {
			for (T localObject : localList) {
				Comparator<?> comparator = localObject.getIdentityComparator();
				@SuppressWarnings("unchecked") T remoteObject = (T) CollectionUtil.findObject(remoteList, localObject, comparator);
				if (remoteObject == null) {
					resultList.add(localObject);

				}
			}
		} else {
			resultList.addAll(localList);
		}

	}

	public abstract void apply(T localObject, T remoteObject, List<T> resultList);
}
