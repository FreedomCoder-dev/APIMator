package com.freedomcoder.astutils;

import org.walkmod.javalang.ast.CompilationUnit;

public class JAST {

    public static CompilationUnit makeUnit() {
        return new CompilationUnit();
    }

    public static SourceDirManager makeManager(String src) {
        return new SourceDirManager(src);
    }
}
