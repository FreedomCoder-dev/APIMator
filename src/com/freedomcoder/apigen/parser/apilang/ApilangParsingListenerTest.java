package com.freedomcoder.apigen.parser.apilang;

import org.antlr.v4.runtime.tree.TerminalNode;

public class ApilangParsingListenerTest extends ApilangBaseListener {

    public SourcePrinter p = new SourcePrinter();

    @Override
    public void enterImportDeclaration(ApilangParser.ImportDeclarationContext ctx) {
        p.println("import file " + ctx.STRING());
        super.enterImportDeclaration(ctx);
    }

    @Override
    public void enterApiDeclaration(ApilangParser.ApiDeclarationContext ctx) {
        p.println("api " + ctx.ID() + " {");
        p.indent();
        super.enterApiDeclaration(ctx);
    }

    @Override
    public void exitApiDeclaration(ApilangParser.ApiDeclarationContext ctx) {

        p.unindent();
        p.println("} end api " + ctx.ID() + "");

        super.exitApiDeclaration(ctx);
    }

    @Override
    public void enterApiGroupDeclaration(ApilangParser.ApiGroupDeclarationContext ctx) {
        p.println("group " + ctx.ID() + " {");
        p.indent();
        super.enterApiGroupDeclaration(ctx);
    }

    @Override
    public void exitApiGroupDeclaration(ApilangParser.ApiGroupDeclarationContext ctx) {

        p.unindent();
        p.println("} end group " + ctx.ID() + "");

        super.exitApiGroupDeclaration(ctx);
    }

    @Override
    public void enterApiMethodDeclaration(ApilangParser.ApiMethodDeclarationContext ctx) {
        p.println("method " + ctx.ID() + " {");
        p.indent();
        super.enterApiMethodDeclaration(ctx);
    }

    @Override
    public void exitApiMethodDeclaration(ApilangParser.ApiMethodDeclarationContext ctx) {

        p.unindent();
        p.println("} end method " + ctx.ID() + "");

        super.exitApiMethodDeclaration(ctx);
    }

    @Override
    public void enterObjectDeclaration(ApilangParser.ObjectDeclarationContext ctx) {

        p.println("obj " + ctx.ID() + " {");
        p.indent();
        super.enterObjectDeclaration(ctx);
    }

    @Override
    public void exitObjectDeclaration(ApilangParser.ObjectDeclarationContext ctx) {

        p.unindent();
        p.println("} end obj " + ctx.ID() + "");
        super.exitObjectDeclaration(ctx);
    }

    @Override
    public void enterInlineObjectDeclaration(ApilangParser.InlineObjectDeclarationContext ctx) {
        ApilangParser.InlineTypeNameContext inlineTypeName = ctx.inlineTypeName();
        TerminalNode iD = null;
        if (inlineTypeName != null) iD = inlineTypeName.ID();
        p.println("obj inline <" + iD + ">" + ctx.ID() + " {");
        p.indent();

    }

    @Override
    public void exitInlineObjectDeclaration(ApilangParser.InlineObjectDeclarationContext ctx) {

        p.unindent();
        p.println("} end obj " + ctx.ID() + "");

    }

    @Override
    public void enterFieldDeclaration(ApilangParser.FieldDeclarationContext ctx) {
        p.println("field " + ctx.ID());

        super.enterFieldDeclaration(ctx);
    }

    @Override
    public void enterFieldModifier(ApilangParser.FieldModifierContext ctx) {
        p.println("fieldmod " + ctx.toString());

        super.enterFieldModifier(ctx);
    }

    @Override
    public void enterIdAtom(ApilangParser.IdAtomContext ctx) {
        System.out.println(ctx.getText());
        super.enterIdAtom(ctx);
    }

    private static class SourcePrinter {

        private final StringBuilder buf = new StringBuilder();
        private int level = 0;
        private String indentationString = "    ";
        private char indentationChar = ' ';
        private boolean indented = false;

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
}
