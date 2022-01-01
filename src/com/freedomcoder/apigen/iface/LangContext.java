package com.freedomcoder.apigen.iface;

import java.io.File;

public abstract class LangContext {

    File homeDir;
    String apiName;

    public File getHomeDir() {
        return homeDir;
    }

    public void setHomeDir(File dir) {
        homeDir = dir;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public abstract void build();

    public abstract TypeDef getTypeFromName(String name);

    public abstract ContainerDef getContainerFromName(String name);

}
