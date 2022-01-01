package com.freedomcoder.appmaster.interfaces;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Args {
    List<String> args = new LinkedList<String>();

    public Args(LuaValue val) {
        if (val.istable())
            for (int i = 0; i < val.length(); i++)
                args.add(val.get(i).toString());
    }

    public void add(String[] elem) {
        args.addAll(Arrays.asList(elem));
    }

    public void add(String elem) {
        args.add(elem);
    }

    public String[] get() {
        return args.toArray(new String[0]);
    }

    public LuaTable getTable() {
        LuaTable t = LuaTable.tableOf(0, 30);
        int i = 0;
        for (String arg : args) {
            t.insert(i++, LuaValue.valueOf(arg));
        }
        return t;
    }
}
