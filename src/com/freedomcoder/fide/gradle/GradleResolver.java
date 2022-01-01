package com.freedomcoder.fide.gradle;

import com.freedomcoder.fide.gradle.maven.GradleParser;
import com.freedomcoder.fide.gradle.models.GradleModule;
import com.freedomcoder.fide.gradle.models.GradleProject;

import java.io.*;
import java.util.HashSet;

public class GradleResolver {
    public static GradleProject resolve(String base) {
        return resolve(new File(base));
    }

    public static GradleProject resolve(File base) {
        GradleProject prg = new GradleProject(base);
        File[] sub = base.listFiles();
        for (File f : sub) {
            if (f.isDirectory() && new File(f, "build.gradle").exists()) {
                prg.addModule(resolveModule(prg, f));
            }
        }
        return prg;
    }

    public static HashSet<GradleModule> resolveDependentModules(GradleModule target) {
        HashSet<GradleModule> mdls = new HashSet<GradleModule>();

        mdls.add(target);
        GradleProject prg = target.parent;
        for (String mdl : target.deps) {
            mdls.addAll(resolveDependentModules(prg.modules.get(mdl)));
        }

        return mdls;
    }

    public static GradleModule resolveModule(GradleProject prg, File base) {
        GradleModule mdl = new GradleModule(prg, base.getName(), base);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(mdl.getGradleFile()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    String[] tokens = line.split(" ");
                    switch (tokens[0]) {
                        case "compile":
                        case "api":
                        case "implementation":
                            if (tokens[1].startsWith("project")) {
                                mdl.deps.add(tokens[1].split("'")[1].substring(1));
                            } else if (tokens[1].startsWith("\"")) {
                                String mavendep = tokens[1].substring(1, tokens[1].lastIndexOf('"'));
                                mdl.addModule(new GradleParser.MavenModule(mavendep));
                                System.out.println("Found Maven Dependency " + mavendep);
                            } else if (tokens[1].startsWith("'")) {
                                String mavendep = tokens[1].substring(1, tokens[1].lastIndexOf('\''));
                                mdl.addModule(new GradleParser.MavenModule(mavendep));
                                System.out.println("Found Maven Dependency " + mavendep);
                            }
                            break;
                    }
                }
            } catch (IOException e) {
            }
        } catch (FileNotFoundException e) {
            return null;
        }


        return mdl;
    }
}
