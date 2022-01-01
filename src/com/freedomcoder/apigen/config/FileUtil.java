package com.freedomcoder.apigen.config;

import com.freedomcoder.logging.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {
    public static String test(String test) {
        return "abc " + test;
    }

    public static String parentdir(String dir) {
        return new File(dir).getParent();
    }

    public static boolean copydir(String from, String to) {
        return copydir(new File(from), new File(to));
    }

    public static boolean copydir(File from, String to) {
        return copydir(from, new File(to));
    }

    public static boolean copydir(String from, File to) {
        return copydir(new File(from), to);
    }

    public static boolean copydir(File from, File to) {
        to.mkdir();
        boolean operationsuccess = true;
        for (File f : from.listFiles()) {
            File file = new File(to, f.getName());
            if (f.isDirectory())
                operationsuccess &= copydir(f, file);
            else if (!file.exists())
                operationsuccess &= copyFile(f, file);
        }
        return operationsuccess;
    }


    public static boolean delete(String from) {
        Log.i("FileUtil", "Deleting " + from);
        return delete(new File(from));
    }

    public static boolean delete(File from) {
        boolean operationsuccess = true;
        if (!from.isDirectory()) operationsuccess = from.delete();
        else {
            for (File f : from.listFiles()) {
                // Log.i("FileUtil", "Deleting " + f);
                if (f.isDirectory())
                    operationsuccess &= delete(f);
                else
                    operationsuccess &= f.delete();
            }
            from.delete();
        }
        return operationsuccess;
    }


    public static boolean copyFile(String from, String to) {
        return copyFile(new File(from), new File(to));
    }

    public static boolean copyFile(File from, String to) {
        return copyFile(from, new File(to));
    }

    public static boolean copyFile(String from, File to) {
        return copyFile(new File(from), to);
    }

    public static boolean copyFile(File in, File out) {
        File parentFile = out.getParentFile();
        if (!parentFile.exists()) parentFile.mkdirs();
        boolean operationsuccess = false;
        try {
            FileChannel inChannel = new
                    FileInputStream(in).getChannel();

            FileChannel outChannel = new
                    FileOutputStream(out).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(),
                        outChannel);
                operationsuccess = true;
            } catch (IOException e) {
                throw e;
            } finally {
                if (inChannel != null) inChannel.close();
                if (outChannel != null) outChannel.close();
            }
        } catch (IOException e) {
        }
        return operationsuccess;
    }

}
