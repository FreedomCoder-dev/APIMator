package com.freedomcoder.fide.gradle.models;

import java.io.File;
import java.util.HashMap;

public class GradleProject {
    public File root;
    public HashMap<String, GradleModule> modules = new HashMap<String, GradleModule>();

    public GradleProject(File root) {
        this.root = root;
    }

    public void addModule(GradleModule m) {
        modules.put(m.getRoot().getName(), m);
    }

    public void resolveDownloadMavenDependencies() {
        for (GradleModule module : modules.values()) {
            module.resolveDepsWithDownload();
        }
    }
}
