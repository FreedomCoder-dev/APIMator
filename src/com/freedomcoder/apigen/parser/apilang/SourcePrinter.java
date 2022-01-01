package com.freedomcoder.apigen.parser.apilang;

public class SourcePrinter {

    private final StringBuilder buf = new StringBuilder();
    public boolean indented = false;
    private int level = 0;
    private String indentationString = "  ";
    private char indentationChar = '	';

    public void indent() {
        level++;
    }

    public void indent(int level) {
        this.level = level;
    }

    public void setIndentationChar(char indentationChar) {
        this.indentationChar = indentationChar;
    }

    public void setSize(int size) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            buffer.append(indentationChar);
        }
        indentationString = buffer.toString();
    }

    public void unindent() {
        level--;
    }

    private void makeIndent() {
        for (int i = 0; i < level; i++) {
            buf.append(indentationString);
        }
    }

    public void print(String arg) {
        if (!indented) {
            makeIndent();
            indented = true;
        }
        buf.append(arg);
    }

    public void printJavadocText(String arg) {
        String[] parts = arg.split("\n");
        StringBuffer aux = new StringBuffer();
        for (int i = 0; i < parts.length; i++) {
            String line = parts[i].replaceAll("^\\s+", "");

            aux.append(line);

            if (i + 1 < parts.length) {
                aux.append("\n ");
            }
        }
        print(aux.toString());
    }

    public void println(String arg) {
        print(arg);
        println();
    }

    public void println() {
        buf.append("\n");
        indented = false;
    }

    public String getSource() {
        return buf.toString();
    }

    @Override
    public String toString() {
        return getSource();
    }
}
