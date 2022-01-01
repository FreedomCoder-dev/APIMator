package com.freedomcoder.fide.gradle.models;

import com.freedomcoder.fide.gradle.maven.GradleParser;
import com.freedomcoder.fide.gradle.maven.MavenResolver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class GradleModule {
    public GradleProject parent;
    public String name;
    public File root;
    public ArrayList<String> deps = new ArrayList<String>();
    public HashSet<GradleParser.MavenModule> mavenModules = new HashSet<GradleParser.MavenModule>();
    public HashSet<String> resolvedDeps = new HashSet<String>();
    public MavenResolver maven = new MavenResolver();

    public GradleModule(GradleProject parent, String name, File root) {
        this.parent = parent;
        this.name = name;
        this.root = root;
    }

    public void addModule(GradleParser.MavenModule module) {
        mavenModules.add(module);
    }

    public void resolveDepsWithDownload() {
        ArrayList<String> container = new ArrayList<String>();
        for (GradleParser.MavenModule mod : mavenModules) {
            maven.resolveWithDownload(mod, container);
            resolvedDeps.addAll(container);
            container.clear();
        }
    }

    public File getRoot() {
        return root;
    }

    public File getGradleFile() {
        return new File(root, "build.gradle");
    }

    public File getSources() {
        return new File(root, "src");
    }

    public File getJavaSources() {
        return new File(getSources(), "main/java");
    }

    public File getResources() {
        return new File(getSources(), "main/res");
    }

    public File getManifest() {
        return new File(getSources(), "main/AndroidManifest.xml");
    }
}
