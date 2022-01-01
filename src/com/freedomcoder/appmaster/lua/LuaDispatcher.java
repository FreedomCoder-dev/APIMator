package com.freedomcoder.appmaster.lua;

import com.freedomcoder.apigen.parser.apilang.compiler.ApiParseProcessor;
import com.freedomcoder.appmaster.interfaces.Args;
import com.freedomcoder.appmaster.lua.libs.ModulesLib;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.freedomcoder.appmaster.lua.libs.ModulesLib.jtl;

public class LuaDispatcher implements ResourceFinder {
    public static File privateDir;
    public File baseDir;
    String scriptName;
    Globals globals;

    public LuaDispatcher(File script) {
        this.baseDir = script.getParentFile();
        this.scriptName = script.getName();
        globals = JsePlatform.standardGlobals();
        globals.finder = this;
        ModulesLib modulesLib = new ModulesLib();
        modulesLib.setPathlocal(baseDir);

        modulesLib.setPathprivate(privateDir);
        globals.load(modulesLib);
        LuaDispatcher.print print = new print();
        globals.set("log", print);
        globals.set("print", print);
        globals.set("args", new args());
    }

    public static void main(String[] args) {

        new ApiParseProcessor(null);

        if (args != null && args.length > 2 && args[0].equals("lua")) {
            //System.out.println(Arrays.toString(args));
            privateDir = new File(args[2]);
            System.out.println(privateDir + " " + args[2]);
            new LuaDispatcher(new File(args[1])).run();
        } else
            System.out.println("Unknown input file.");
    }

    public void run() {
        try {
            LuaValue script = globals.loadfile(scriptName);
            script.call();
        } catch (LuaError err) {
            System.out.print("[ERROR] ");
            err.printStackTrace();
        }
    }

    @Override
    public InputStream findResource(String p1) {
        try {
            return new FileInputStream(new File(baseDir, p1));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    static final class args extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue p1) {
            return jtl(new Args(p1));
        }
    }

    final class print extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            LuaValue tostring = globals.get("tostring");
            globals.STDOUT.print("[LUA] ");
            for (int i = 1, n = args.narg(); i <= n; i++) {
                if (i > 1) globals.STDOUT.print('\t');
                LuaString s = tostring.call(args.arg(i)).strvalue();
                globals.STDOUT.print(s.tojstring());
            }
            globals.STDOUT.print('\n');
            return NONE;
        }
    }

}

