package org.walkmod.merger;

public interface MergeEngineAware {

	void setMergeEngine(MergeEngine mergeEngine);

	MergeEngine getMergeEngine();
}
