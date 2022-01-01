package com.freedomcoder.apigen.parser.apilang.compiler;

import com.freedomcoder.apigen.iface.LangContext;
import com.freedomcoder.apigen.iface.SchemaDom;
import com.freedomcoder.apigen.parser.ParseProcessor;
import com.freedomcoder.apigen.parser.apilang.ApilangLexer;
import com.freedomcoder.apigen.parser.apilang.ApilangParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class ApiParseProcessor extends ParseProcessor<SchemaDom> {

    File workdir, mainfile;
    int identlog = 0;

    public ApiParseProcessor(LangContext ctx) {
        super(ctx);

    }

    public void parseLocalFile(String name, SchemaDom dom) throws IOException {
        parseFile(new File(workdir, name), dom);
    }

    @Override
    public SchemaDom parse() {
        SchemaDom dom = new SchemaDom();
        log("Parsing active stage");
        identlog++;
        long time = System.currentTimeMillis();
        try {
            parseFile(mainfile, dom);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long worked = System.currentTimeMillis() - time;
        identlog--;
        log("Active stage finished");
        log("Spent " + (worked / 1000) + "s " + (worked % 1000) + "ms");
        return dom;
    }

    public String align(int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) sb.append("  ");
        return sb.toString();
    }

    public void parseFile(File f, SchemaDom dom) throws IOException {
        log("> Parsing file  " + f.getName());
        identlog++;
        ApilangLexer lexer = new ApilangLexer(new ANTLRFileStream(f.getAbsolutePath()));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        ApilangParser parser = new ApilangParser(commonTokenStream);
        ParseTree tree = parser.parse();
        log("# Built AST for " + f.getName() + " Preparing schema...");
        ApiParsingVisitor visitor = new ApiParsingVisitor(this, dom);
        visitor.visit(tree);
        identlog--;
        log("< Parsing ends  " + f.getName());
    }

    @Override
    public void setSettings(JSONObject settings) {
        try {
            if (settings.has("fullpath")) {
                File file = new File(settings.getString("fullpath"));
                workdir = file.getParentFile();
                mainfile = file;
            } else {
                workdir = new File(getHomeDir(), settings.getString("rel.dir"));
                mainfile = new File(workdir, settings.getString("file"));
            }
            log("Parser is ready");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String log(String l) {
        System.out.println("[ApiParseProcessor] " + align(identlog) + l);
        return l;
    }
}
