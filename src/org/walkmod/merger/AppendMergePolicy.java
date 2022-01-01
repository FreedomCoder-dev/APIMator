package org.walkmod.merger;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class AppendMergePolicy<T extends IdentificableNode> extends ObjectMergePolicy<T> implements MergeEngineAware {

	private MergeEngine mergeConfiguration;

	@SuppressWarnings("unchecked")
	@Override
	public void apply(T localObject, T remoteObject, List<T> resultList) {
		if (localObject == null) {
			resultList.add(remoteObject);
		} else {
			if (localObject instanceof Mergeable) {
				((Mergeable) localObject).merge(remoteObject, mergeConfiguration);
			}
			resultList.add(localObject);
		}
	}

	@Override
	public void setMergeEngine(MergeEngine mergeConfiguration) {
		this.mergeConfiguration = mergeConfiguration;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MergeEngine getMergeEngine() {
		return mergeConfiguration;
	}

	@Override
	public T apply(T localObject, T remoteObject) {
		if (localObject == null) {
			return remoteObject;
		} else {
			if (localObject instanceof Mergeable) {
				((Mergeable) localObject).merge(remoteObject, mergeConfiguration);
			}
			return localObject;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void apply(List<T> localList, List<T> remoteList, 
	@SuppressWarnings("rawtypes") List resultList) {
		if (remoteList != null) {

			List<T> updatedLocalObjects = new LinkedList<T>();

			List<T> addedRemoteObjects = new LinkedList<T>();
			for (T remoteObject : remoteList) {
				Comparator<?> comparator = remoteObject.getIdentityComparator();
				@SuppressWarnings("unchecked") T localObject = (T) CollectionUtil.findObject(localList, remoteObject, comparator);
				if (localObject != null) {
					this.apply(localObject, remoteObject, updatedLocalObjects);
				} else {
					this.apply(localObject, remoteObject, addedRemoteObjects);
				}
			}
			if (localList != null) {
				for (T local : localList) {

					resultList.add(local);
				}
			}
			for (T remoteAdded : addedRemoteObjects) {
				resultList.add(remoteAdded);
			}
		}
		else {
			resultList.addAll(localList);
		}

	}

}
