package com.freedomcoder.apigen.generators.php;

import java.io.PrintWriter;

public class PhpSourcePrinter extends PrintWriter {

    public PhpSourcePrinter(java.io.Writer out) {
        super(out);
    }

    public PhpSourcePrinter(java.io.OutputStream out) {
        super(out);
    }

    public PhpSourcePrinter(java.lang.String fileName) throws java.io.FileNotFoundException {
        super(fileName);
    }

    public PhpSourcePrinter(java.io.File file) throws java.io.FileNotFoundException {
        super(file);
    }

    public void printHeader() {
        println("<?php");
    }

    public void printClassStart(String name) {
        println("class " + name);
        println("{");
    }

    public void printGetter(String name, boolean isboolean) {
        println("public function " + getterName(isboolean, name));
        println("{");
        println("return $" + name + ";");
        println("}");
    }

    public void printField(String name) {
        println("public $" + name + ";");
    }

    public String getterName(boolean isBoolean, String from) {
        if (isBoolean) {
            return "is" + capitalizeFirstLetter(from);
        } else {
            return "get" + capitalizeFirstLetter(from);
        }
    }

    public String setterName(boolean isBoolean, String from) {
        return "set" + capitalizeFirstLetter(from);
    }

    public String lowercaseFirstLetter(String src) {
        char[] arr = src.toCharArray();
        arr[0] = Character.toLowerCase(arr[0]);
        return new String(arr);
    }

    public String capitalizeFirstLetter(String src) {
        char[] arr = src.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public void printClassEnd(String name) {
        println("}");
    }

    public void printTerminator() {
        print("?>");
    }
}
