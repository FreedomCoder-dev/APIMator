package com.freedomcoder.fide.gradle;

import com.freedomcoder.fide.gradle.models.GradleModule;
import com.freedomcoder.fide.gradle.models.GradleProject;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;


public class Main {
    public static void main(String[] args) {
        GradleResolver resolver = new GradleResolver();
        /*
         MavenResolver resolver = new MavenResolver();
         String v = "org.simplejavamail:simple-java-mail:6.5.2";
         //  v = "org.apache.poi:poi-ooxml-lite:3.12";
         //  v = "org.apache.poi:poi:3.12";
         // v = "com.incesoft.tools:ince-excel:1.0.0";
         //  v = "ch.rabanti:nanoxlsx4j:1.2.8";
         //v = "org.spdx:spdx-spreadsheet-store:0.0.6";
         //      String v = "com.squareup.tools.build:maven-archeologist:0.0.3.1";
         //  MavenCloner.repos.add(new GradleParser.Repository("http://192.168.1.244:8000/artifactory/incerepo"));
         GradleParser.MavenModule module = new GradleParser.MavenModule(v);
         System.out.println("Module " + module);
         System.out.println("Building dependency tree");
         ArrayList<String> arrayList = new ArrayList<String>();
         resolver.resolveWithDownload(module, arrayList);
         System.out.println(
         resolver.getModulePath(module)
         );
         System.out.println("-----Module dependencies-----");
         reprint(
         arrayList
         );
         String outdir = "/storage/emulated/0/AIDE/out";

         FileUtil.delete(outdir);
         for (String f:arrayList) {
         File ff= new File(f);
         FileUtil.copyFile(ff, new File(outdir, ff.getName()));
         }
         System.out.println("Copied!");
         //MavenCloner.cloneModule(resolver, jetpack, module);

         //MavenModule.parse("com.squareup.tools.build:maven-archeologist:0.0.3.1");
         */
        System.out.println("Loading modules");
        File prgt = new File(
                "/storage/emulated/0/_Work/Projects/New/sftooll"
                //"/storage/emulated/0/_Work/Projects/Current/ApkMaster"
        );
        GradleProject prg = GradleResolver.resolve(prgt);
        prg.resolveDownloadMavenDependencies();
        HashSet<GradleModule> mdls = GradleResolver.resolveDependentModules(prg.modules.get("app"));
        for (GradleModule module : mdls) {
            System.out.println("----- Module: " + module.name + " -----");
            System.out.println("‐-------------------------------------");
            System.out.println("|RootDir     " + module.getRoot());
            System.out.println("|GradleFile  " + module.getGradleFile());
            System.out.println("|Sources     " + module.getSources());
            System.out.println("|Manifest    " + module.getManifest());
            System.out.println("|JavaSources " + module.getJavaSources());
            System.out.println("|Resources   " + module.getResources());
            System.out.println("‐-------------------------------------");
            System.out.println("Used modules: ");
            reprint(module.deps);
            System.out.println("Used maven modules:");
            reprint(module.resolvedDeps);
        }
    }

    public static void reprint(Collection<String> resolveDependencies) {
        for (String a : resolveDependencies) {
            System.out.println(a);
        }
    }


}
