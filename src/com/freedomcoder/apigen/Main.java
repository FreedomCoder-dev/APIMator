package com.freedomcoder.apigen;

import org.walkmod.javalang.ast.Node;

import java.io.PrintWriter;

public class Main {


    static PrintWriter filew;

    public static void dumpAst(Node node, String ident) {

        String nodes = node.toString();
        if (nodes.length() > 0) {
            try {
                nodes = nodes.replaceAll("\n", ",");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                nodes = nodes.replaceAll("\r", ",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filew.println(ident + "[" + node.getClass().getSimpleName() + "] " + nodes);
        for (Node n : node.getChildren()) {
            dumpAst(n, ident + "  ");
        }
    }
}
