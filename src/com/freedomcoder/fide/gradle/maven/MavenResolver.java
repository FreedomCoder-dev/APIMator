package com.freedomcoder.fide.gradle.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* renamed from: pe */
public class MavenResolver {

    /* renamed from: DW */
    private final Map<String, GradleParser.MavenModule> modules = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: j6 */
    public Map<GradleParser.MavenModule, String> resolvedModulePaths = new HashMap();

    /* renamed from: DW */
    private static String getParentDirName(String str) {
        String[] split = str.split("/");
        return split[split.length - 2];
    }

    /* access modifiers changed from: private */
    /* renamed from: Zo */
    public static String getSdcardMavenRepo() {
        return FileUtils.getSdcard() + "/AIDE/maven";
    }

    /* renamed from: j6 */
    public static String getRemoteMetadataPath(GradleParser.Repository mVar, GradleParser.MavenModule gVar) {
        return mVar.name + ("/" + gVar.group.replace('.', '/') + "/" + gVar.id) + "/maven-metadata.xml";
    }

    /* renamed from: DW */
    public static String getLocalMetadataPath(GradleParser.Repository mVar, GradleParser.MavenModule gVar) {
        return getSdcardMavenRepo() + ("/" + gVar.group.replace('.', '/') + "/" + gVar.id) + "/maven-metadata.xml";
    }

    /* renamed from: j6 */
    public static String getRemoteModulePath(GradleParser.Repository mVar, GradleParser.MavenModule gVar, String str, String str2) {
        return mVar.name + (("/" + gVar.group.replace('.', '/') + "/" + gVar.id) + "/" + str + "/" + gVar.id + "-" + str) + str2;
    }

    /* renamed from: DW */
    public static String getLocalModulePath(GradleParser.Repository mVar, GradleParser.MavenModule gVar, String str, String str2) {
        return getSdcardMavenRepo() + (("/" + gVar.group.replace('.', '/') + "/" + gVar.id) + "/" + str + "/" + gVar.id + "-" + str) + str2;
    }

    /* renamed from: j6 */
    public void clearModules() {
        this.modules.clear();
    }

    public boolean resolveWithDownload(GradleParser.MavenModule module, List<String> result) {
        boolean lastResult = false;
        HashSet<String> nodup = new HashSet<String>();
        do {
            if (lastResult) {
                System.out.println("Found unresolved dependency " + module + " - downloading.");
                lastResult = MavenCloner.cloneModule(this, module);
                System.out.println((lastResult ? "Succeded" : "Failed") + " to download module " + module);
//				Main.reprint(modules.keySet());
                if (lastResult) resolvedModulePaths.remove(module);
            } else lastResult = true;
            List<String> resolveDependencies = resolveDependencies(null, module);
            nodup.addAll(resolveDependencies);
        } while (lastResult && (module = getFirstUnresolvedModule()) != null);
        result.addAll(nodup);
        return lastResult;
    }

    /* renamed from: FH */
    private GradleParser.MavenModule getOrPutThis(GradleParser.MavenModule gVar) {
        if (!this.modules.containsKey(gVar.moduleName())) {
            this.modules.put(gVar.moduleName(), gVar);
        }
        return this.modules.get(gVar.moduleName());
    }

    /* renamed from: j6 */
    public void mo22809j6(GradleParser.MavenModule gVar) {
        m40886j6(gVar, 3);
    }

    /* renamed from: j6 */
    private void m40886j6(GradleParser.MavenModule module, int depth) {
        String Hw = getModulePath(null, module);
        if (Hw != null) {
            String DW = getParentDirName(Hw);
            if (!this.modules.containsKey(module.moduleName()) || ArtifactVersion.compare(DW, this.modules.get(module.moduleName()).version) > 0) {
                this.modules.put(module.moduleName(), new GradleParser.MavenModule(module, DW));
            }
            if (depth > 0) {
                for (GradleParser.MavenModule next : new PomParser().getSingletone(getPomFilename(Hw)).dependencies) {
                    if (!isAndroidAll(next)) {
                        m40886j6(next, depth - 1);
                    }
                }
            }
        }
    }

    public GradleParser.MavenModule getFirstUnresolvedModule() {
        for (GradleParser.MavenModule m : resolvedModulePaths.keySet()) {
            if (resolvedModulePaths.get(m).length() == 0) {
                return m;
            }
        }
        return null;
    }

    /* renamed from: DW */
    public void cleanModulesPathsCache() {
        this.resolvedModulePaths.clear();
    }

    /* renamed from: j6 */
    public boolean isExistInLocalRepo(Map<String, String> map, GradleParser.MavenModule gVar) {
        return getModulePath(map, getOrPutThis(gVar)) != null;
    }

    /* renamed from: DW */
    public String getModulePath(GradleParser.MavenModule gVar) {
        return getModulePath(null, gVar);
    }

    /* renamed from: DW */
    public List<String> resolveDependencies(Map<String, String> map, GradleParser.MavenModule gVar) {
        return resolveDependencies(map, getModulePath(map, getOrPutThis(gVar)));
    }

    /* renamed from: j6 */
    public List<String> resolveByPath(String str) {
        return resolveDependencies(null, str);
    }

    /* renamed from: FH */
    public List<GradleParser.MavenModule> mo22805FH(Map<String, String> map, GradleParser.MavenModule gVar) {
        ArrayList arrayList = new ArrayList();
        m40887j6(map, gVar, (List<GradleParser.MavenModule>) arrayList, 3);
        return arrayList;
    }

    /* renamed from: j6 */
    private void m40887j6(Map<String, String> map, GradleParser.MavenModule gVar, List<GradleParser.MavenModule> list, int i) {
        String Hw = getModulePath(map, getOrPutThis(gVar));
        if (Hw == null) {
            list.add(gVar);
        } else if (i > 0) {
            for (GradleParser.MavenModule next : new PomParser().getSingletone(getPomFilename(Hw)).dependencies) {
                if (!isAndroidAll(next)) {
                    m40887j6(map, next, list, i - 1);
                }
            }
        }
    }

    /* renamed from: j6 */
    private List<String> resolveDependencies(Map<String, String> map, String str) {
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            resolveDependencies(map, str, (List<String>) arrayList, 3);
        }
        return arrayList;
    }

    /* renamed from: j6 */
    private void resolveDependencies(Map<String, String> map, String path, List<String> result, int i) {
        String currpath;
        if (!result.contains(path)) {
            result.add(path);
            if (i > 0) {
                for (GradleParser.MavenModule next : new PomParser().getSingletone(getPomFilename(path)).dependencies) {
                    System.out.println(" dbg solve " + next);
                    if (!isAndroidAll(next) && (currpath = getModulePath(map, getOrPutThis(next))) != null) {
                        resolveDependencies(map, currpath, result, i - 1);
                    }
                }
            }
        }
    }

    /* renamed from: Hw */
    private boolean isAndroidAll(GradleParser.MavenModule gVar) {
        return gVar.id.contains("android-all");
    }

    /* renamed from: v5 */
    private List<String> getRepositories() {
        ArrayList list = new ArrayList();
        list.add("/storage/emulated/0/AIDE/maven");
        return list;
    }

    /* renamed from: Hw */
    private String getModulePath(Map<String, String> map, GradleParser.MavenModule module) {
        if (map != null) {
            for (String next : map.keySet()) {
                String filepath = getAarFilepathVersioned(next, module.group, module.id, module.version);
                if (filepath != null) {
                    String name = new File(filepath).getName();
                    String str = map.get(next) + "/" + name.substring(0, name.length() - 4) + ".exploded.aar";
                    extractAar(filepath, str);
                    return str;
                }
            }
        }
        if (this.resolvedModulePaths.containsKey(module)) {
            String str2 = this.resolvedModulePaths.get(module);
            if (str2.length() == 0) {
                return null;
            }
            return str2;
        }
        String dir = resolveInRepos(module);
        if (dir == null) {
            this.resolvedModulePaths.put(module, "");
        } else {
            this.resolvedModulePaths.put(module, dir);
        }
        return dir;
    }

    /* renamed from: v5 */
    private String resolveInRepos(GradleParser.MavenModule gVar) {
        for (String DW : getRepositories()) {
//			System.out.println(DW);
            String DW2 = getLocalRepoFilepath(DW, gVar.group, gVar.id, gVar.version);
//            System.out.println(DW2);
            if (DW2 != null) {
                return DW2;
            }
        }
        return null;
    }

    /* renamed from: j6 */
    private String getAarFilepathVersioned(String basedir, String str2, String moduleid, String str4) {
        String str5 = basedir + "/" + moduleid + ".aar";
        if (new File(str5).exists()) {
            return str5;
        }
        File[] listFiles = new File(basedir).listFiles();
        if (listFiles == null) {
            return null;
        }
        for (File file : listFiles) {
            if (file.getName().startsWith(moduleid + "-") && file.getName().endsWith(".aar") && MetadataParser.versionMatch(file.getName().substring(moduleid.length() + 1, file.getName().length() - 4), str4)) {
                return file.getPath();
            }
        }
        return null;
    }

    /* renamed from: DW */
    private String getLocalRepoFilepath(String repopath, String group, String id, String version) {
        String j6;
        String path = repopath + "/" + group.replace(".", "/") + "/" + id;
//       System.out.println(path);
        if (!FileUtils.isDir(path) || (j6 = getMatchingVersion(path, version)) == null) {
            return null;
        }
        String str6 = path + "/" + j6 + "/" + id + "-" + j6;
//        System.out.println(str6);
        if (new File(str6 + ".jar").isFile()) {
            return str6 + ".jar";
        }
        if (new File(str6 + ".aar").isDirectory()) {
            return str6 + ".aar";
        }
        File file = new File(str6 + ".exploded.aar");
//		System.out.println(file.getParentFile());
        if (file.isDirectory()) {
            return str6 + ".exploded.aar";
        }
        if (!new File(str6 + ".aar").isFile()) {
            return null;
        }
        extractAar(str6 + ".aar", str6 + ".exploded.aar");
        return str6 + ".exploded.aar";
    }

    /* renamed from: j6 */
    private String getMatchingVersion(String str, String str2) {
        String ver;
        String str3 = str + "/maven-metadata.xml";
        if (FileUtils.isExistingFileNotJar(str3) && (ver = new MetadataParser().getSingletone(str3).getMatchingVersion(str2)) != null) {
            return ver;
        }
        try {
            ArrayList arrayList = new ArrayList();
            for (String Zo : FileUtils.listFiles(str)) {
                arrayList.add(FileUtils.getFileName(Zo));
            }
            return MetadataParser.sortFindVersion((ArrayList<String>) arrayList, str2);
        } catch (Exception e) {

        }
        return null;
    }

    /* renamed from: DW */
    private void extractAar(String str, String str2) {
        if (!isContainOlderFiles(str, str2)) {
            try {
                FileUtils.unzip(new FileInputStream(str), str2, true);
                // C1954e.m15851j6("Extracted AAR " + str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: FH */
    private boolean isContainOlderFiles(String str, String dir) {
        if (!new File(dir).isDirectory()) {
            return false;
        }
        File[] listFiles = new File(dir).listFiles();
        if (listFiles == null) {
            return true;
        }
        long lastModified = new File(str).lastModified();
        for (File file : listFiles) {
            if (file.isFile() && file.lastModified() < lastModified) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: FH */
    private String getPomFilename(String str) {
        if (str.endsWith(".exploded.aar")) {
            return str.substring(0, str.length() - 13) + ".pom";
        }
        return str.substring(0, str.length() - 4) + ".pom";
    }

    /* renamed from: FH */
    public void refreshRepos() {
        //refresh
        new Runnable() {
            public void run() {
                try {
                    MavenResolver.this.resolvedModulePaths.clear();
                    FileUtils.delete(MavenResolver.getSdcardMavenRepo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
