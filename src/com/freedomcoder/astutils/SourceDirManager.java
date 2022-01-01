package com.freedomcoder.astutils;

import org.walkmod.javalang.JavaParser;
import org.walkmod.javalang.ParseException;
import org.walkmod.javalang.ast.CompilationUnit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class SourceDirManager {

    String src;

    public SourceDirManager(String src) {
        this.src = src;
    }

    public int saveFile(CompilationUnit unit) {
        File fldr = new File(src, unit.getPackage().getName().toString().replace(".", "/"));
        fldr.mkdirs();
        int i = 0;
        File code = new File(fldr, unit.getMainclass().getName() + ".java");
        try {
            PrintWriter wr = new PrintWriter(new FileOutputStream(code));
            wr.append(unit.getPrettySource('	', 0, 1));
            wr.flush();
            wr.close();
            i = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public CompilationUnit loadFile(String unit) throws IOException, ParseException {
        File f = new File(src, unit.replace(".", "/"));

        return JavaParser.parse(f);
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
