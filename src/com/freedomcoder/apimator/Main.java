package com.freedomcoder.apimator;

import com.freedomcoder.appmaster.lua.LuaDispatcher;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            File file = new File(args.length > 0 ? args[0] : System.getenv("SCRIPT") != null ? System.getenv("SCRIPT") : "buildapi.lua");
            if (!file.isFile()) {
                System.err.println("File " + file + " not found, pass it's name to argument");
                System.exit(0);
            }
            String[] argsNew = new String[]{"lua", file.getAbsolutePath(), "~/.apkmaster/"};
            LuaDispatcher.main(argsNew);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
