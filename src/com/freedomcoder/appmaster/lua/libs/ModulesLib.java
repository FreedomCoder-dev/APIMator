package com.freedomcoder.appmaster.lua.libs;

import com.freedomcoder.apigen.config.FileUtil;
import com.freedomcoder.apigen.config.TranspilingModule;
import com.freedomcoder.appmaster.interfaces.DexLoader;
import com.freedomcoder.appmaster.interfaces.NativeRun;
import com.freedomcoder.fide.gradle.GradleResolver;
import com.freedomcoder.fide.gradle.models.GradleProject;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.io.File;
import java.util.Iterator;

public class ModulesLib extends TwoArgFunction {
    public static final String LIBNAME = "modules";
    public static ModulesLib LIB = null;
    public File pathlocal, pathdata, pathprivate;

    public static <T> T ltj(LuaValue p, Class<T> clz) {
        return (T) CoerceLuaToJava.coerce(p, clz);
    }

    public static LuaValue jtl(Object clz) {
        return CoerceJavaToLua.coerce(clz);
    }

    public File getPathprivate() {
        return pathprivate;
    }

    public void setPathprivate(File pathprivate) {
        this.pathprivate = pathprivate;
    }

    public File getPathlocal() {
        return pathlocal;
    }

    public void setPathlocal(File pathlocal) {
        this.pathlocal = pathlocal;
    }

    public File getPathdata() {
        return pathdata;
    }

    public void setPathdata(File pathdata) {
        this.pathdata = pathdata;
    }

    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaTable apigen = new LuaTable(0, 30);

        //apigen.set("test", new test());
        apigen.set("gradle", new gradle());
        apigen.set("dexload", new dexload());
        apigen.set("getiter", new getiter());
        apigen.set("shell", new shell());
        apigen.set("transpiler", new transpiler());
        apigen.set("files", new files());

        env.set(LIBNAME, apigen);
        if (!env.get("package").isnil()) env.get("package").get("loaded").set(LIBNAME, apigen);
        return apigen;
    }

    final class shell extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue p2) {
            String name = p2.checkjstring();
            NativeRun nativerun = new NativeRun();
            nativerun.setOuterFs(new File(pathdata + "/bin", name));
            System.out.println(pathprivate);
            nativerun.setInnerFs(new File(pathprivate + "/bin", name));
            nativerun.prepare();
            return jtl(nativerun);
        }

    }

    final class dexload extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue p2) {
            String name = p2.checkjstring();
            System.out.println(name);
            DexLoader dexLoader = new DexLoader();
            dexLoader.setCachedir(new File(pathdata + "/cache", name));
            dexLoader.setDex(new File(pathdata + "/jars", name));
            return jtl(dexLoader);
        }

    }

    final class gradle extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue p2) {
            String name = p2.checkjstring();
            GradleProject prg = GradleResolver.resolve(name);
            return jtl(prg);
        }

    }

    final class getiter extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue p2) {
            Object in = p2.checkuserdata();
            if (in instanceof Iterator)
                return jtl(in);
            else if (in instanceof Iterable)
                return jtl(((Iterable) in).iterator());
            else
                throw new LuaError("getiter: object is not iterable or iterator");
        }

    }

    final class transpiler extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue p1) {
            return jtl(new TranspilingModule(pathlocal));
        }

    }

    final class files extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue p1) {
            return jtl(new FileUtil());
        }

    }

}
