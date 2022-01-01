package com.freedomcoder.appmaster.interfaces;

import com.freedomcoder.apigen.config.FileUtil;
import com.freedomcoder.apigen.parser.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class NativeRun {
    public File outerFs, innerFs;
    public boolean verbose = true;

    public File getOuterFs() {
        return outerFs;
    }

    public void setOuterFs(File outerFs) {
        this.outerFs = outerFs;
    }

    public File getInnerFs() {
        return innerFs;
    }

    public void setInnerFs(File innerFs) {
        this.innerFs = innerFs;
    }

    public void verbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void prepare() {
        System.out.println("from " + outerFs + " " + innerFs);
        if (!innerFs.exists()) {
            FileUtil.copyFile(outerFs, innerFs);
        }
        innerFs.setExecutable(true);
    }

    public boolean start(String args) {
        Process nativeProc;

        try {
            nativeProc = Runtime.getRuntime().exec(innerFs + " " + args);

            StreamUtils.transfer(nativeProc.getInputStream(), verbose ? System.out : new NullOutputStream());
            StreamUtils.transfer(nativeProc.getErrorStream(), verbose ? System.out : new NullOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            int code = nativeProc.waitFor();

            if (code == 0) {
                System.err.println("[NATIVE] Run succeeded.");
                return true;
            } else {
                System.err.println("[NATIVE] Run failed.");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected static class NullOutputStream extends OutputStream {
        public NullOutputStream() {
        }

        @Override
        public void write(int i) throws IOException {
        }
    }
}
