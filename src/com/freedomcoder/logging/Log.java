package com.freedomcoder.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Log {
    public static void i(String tag, String text, Exception e) {
        i(tag + " " + text + " " + getEText(e));
    }

    public static void i(String tag, String text) {
        i(tag + " " + text);
    }

    public static void i(String str) {
        System.out.println(str);
    }

    public static void e(String tag, String text, Exception e) {
        e(tag + " " + text + " " + getEText(e));
    }

    public static void e(String tag, String text) {
        e(tag + " " + text);
    }

    public static void e(String str) {
        System.out.println(str);
    }

    public static String getEText(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

