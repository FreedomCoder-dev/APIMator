package com.freedomcoder.apigen.iface;

import org.json.JSONObject;

import java.io.File;

public abstract class ContextWrapper extends LangContext {

    LangContext mBase;

    public ContextWrapper(LangContext base) {
        mBase = base;
    }

    public LangContext getMBase() {
        return mBase;
    }

    public void setMBase(LangContext mBase) {
        this.mBase = mBase;
    }

    @Override
    public void build() {
        mBase.build();
    }

    public abstract void setSettings(JSONObject settings);

    @Override
    public TypeDef getTypeFromName(String name) {
        return mBase.getTypeFromName(name);
    }

    @Override
    public String getApiName() {
        return mBase.getApiName();
    }

    @Override
    public void setApiName(String apiName) {
        mBase.setApiName(apiName);
    }

    @Override
    public File getHomeDir() {
        return mBase.getHomeDir();
    }

    @Override
    public void setHomeDir(File dir) {
        mBase.setHomeDir(dir);
    }

    @Override
    public ContainerDef getContainerFromName(String name) {
        return mBase.getContainerFromName(name);
    }

    protected void attachBaseContext(LangContext base) {
        if (mBase != null) {
            throw new IllegalStateException("Base context already set");
        }
        mBase = base;
    }

}
