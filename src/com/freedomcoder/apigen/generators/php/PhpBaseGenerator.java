package com.freedomcoder.apigen.generators.php;

import com.freedomcoder.apigen.generators.GeneratorAccepts;
import com.freedomcoder.apigen.iface.ContextWrapper;
import com.freedomcoder.apigen.iface.LangContext;
import com.freedomcoder.apigen.iface.SchemaDom;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class PhpBaseGenerator extends ContextWrapper implements GeneratorAccepts<SchemaDom> {

    File outdir;

    public PhpBaseGenerator(LangContext ctx) {
        super(ctx);
    }

    @Override
    public void setSettings(JSONObject settings) {
        try {
            outdir = new File(getHomeDir(), settings.getString("rel.dir"));
        } catch (JSONException e) {
        }
    }

    @Override
    public boolean generate(SchemaDom from) {
        log("starting...");

        return false;
    }

    public String log(String l) {
        System.out.println("[PhpBaseGenerator] " + l);
        return l;
    }
}
