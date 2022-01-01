package com.freedomcoder.appmaster.interfaces;

import java.io.File;

public class DexLoader {
    public File dex, cachedir;
    public String mainclass;

    public File getCachedir() {
        return cachedir;
    }

    public void setCachedir(File cachedir) {
        this.cachedir = cachedir;
    }

    public File getDex() {
        return dex;
    }

    public void setDex(File dex) {
        this.dex = dex;
    }

    public String getMainclass() {
        return mainclass;
    }

    public void setMainclass(String mainclass) {
        this.mainclass = mainclass;
    }

    public ClassLoader load() {
        try {
            Class<?> dexcloader = Class.forName("dalvik.system.DexClassLoader");
            cachedir.mkdirs();
            //java.lang.String dexPath, java.lang.String optimizedDirectory, java.lang.String librarySearchPath, java.lang.ClassLoader parent) {}
            try {
                return (ClassLoader) dexcloader.getConstructor(java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.ClassLoader.class).newInstance(
                        dex.getAbsolutePath(), cachedir.getAbsolutePath(), null, getClass().getClassLoader());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object load(String mainclass) {
        System.out.println("load " + mainclass);
        try {
            ClassLoader ld = load();
            Class c = ld.loadClass(mainclass);
            System.out.println("loaded " + c.getCanonicalName());
            return c.newInstance();
        } catch (Exception e) {
            System.out.println("Fail to load " + e);
            e.printStackTrace();
        }
        return null;
    }

    public Class loadClass(String mainclass) {
        System.out.println("load " + mainclass);
        try {
            ClassLoader ld = load();
            Class c = ld.loadClass(mainclass);
            System.out.println("loaded " + c.getCanonicalName());
            return c;
        } catch (Exception e) {
            System.out.println("Fail to load " + e);
            e.printStackTrace();
        }
        return null;
    }
}
