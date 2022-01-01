package com.freedomcoder.apigen.config;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.ContextWrapper;
import com.freedomcoder.apigen.iface.LangContext;
import com.freedomcoder.apigen.iface.TypeDef;
import com.freedomcoder.apigen.parser.FileUtils;
import com.freedomcoder.apigen.parser.ParseProcessor;
import com.freedomcoder.apigen.parser.apilang.ApilangLexer;
import com.freedomcoder.apigen.parser.apilang.SourcePrinter;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

public class TranspilingModule extends LangContext {
    public HashMap<String, Class<? extends ParseProcessor>> parsers = new HashMap<String, Class<? extends ParseProcessor>>();
    public HashMap<String, Class<? extends ContextWrapper>> generators = new HashMap<String, Class<? extends ContextWrapper>>();
    public HashMap<String, JSONObject> settings = new HashMap<String, JSONObject>();
    File path;
    File baseconfig;
    File projectdir;

    public TranspilingModule(File path) {
        this.path = path;
    }

    private static JSONObject deepMerge(JSONObject source, JSONObject target, boolean mergeArrays) throws JSONException {
        Iterator<String> keyz = source.keys();
        while (keyz.hasNext()) {
            String key = keyz.next();
            Object value = source.get(key);
            if (!target.has(key)) {

                target.put(key, value);
            } else {

                if (value instanceof JSONObject) {
                    JSONObject valueJson = (JSONObject) value;
                    deepMerge(valueJson, target.getJSONObject(key), mergeArrays);
                } else if (value instanceof JSONArray && mergeArrays) {
                    JSONArray vl = (JSONArray) value;
                    JSONArray jSONArray = target.getJSONArray(key);
                    for (int i = 0; i < vl.length(); i++) {
                        jSONArray.put(vl.get(i));
                    }
                } else {
                    target.put(key, value);
                }
            }
        }
        return target;
    }

    public void loadConfig(String path) {
        baseconfig = new File(System.getProperty("user.home"), path);
        log("Starting...");
        JSONObject cfg = FileUtils.parseJsonFile(baseconfig);
        try {
            log("Scanning modules...");
            log("cfg " + baseconfig + " " + baseconfig.exists());
            modules:
            {
                JSONObject mod = cfg.getJSONObject("modules");
                parsers:
                {
                    JSONObject parsrs = mod.getJSONObject("parsers");
                    Iterator<String> keys = parsrs.keys();
                    while (keys.hasNext()) {
                        String name = keys.next();
                        try {
                            parsers.put(name, loadParser(parsrs.getJSONObject(name)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                generators:
                {
                    JSONObject geners = mod.getJSONObject("generators");
                    Iterator<String> keys = geners.keys();
                    while (keys.hasNext()) {
                        String name = keys.next();
                        try {
                            generators.put(name, loadGenerator(geners.getJSONObject(name)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            log("Scanning settings...");
            settings:
            {
                JSONObject setts = cfg.getJSONObject("settings");
                Iterator<String> keys = setts.keys();
                while (keys.hasNext()) {
                    String name = keys.next();
                    settings.put(name, setts.getJSONObject(name));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applySettings(LuaTable table) {
        try {
            JSONObject setts = tableToJSONObject(table);
            Iterator<String> keys = setts.keys();
            while (keys.hasNext()) {
                String name = keys.next();
                if (settings.containsKey(name)) {
                    JSONObject target = settings.get(name);
                    JSONObject replace = setts.getJSONObject(name);
                    deepMerge(replace, target, true);
                } else settings.put(name, setts.getJSONObject(name));
            }
        } catch (JSONException e) {
            log("FAIL " + e);
        }
    }

    public void beautify(String file) throws IOException {
        String completePath = processPath(file);

        String dataString = com.freedomcoder.fide.gradle.utils.StreamUtils.convertStreamToString(new FileInputStream(completePath));
        dataString = format(dataString);

        com.freedomcoder.fide.gradle.utils.StreamUtils.transferAndClose(new ByteArrayInputStream(dataString.getBytes(StandardCharsets.UTF_8)), new FileOutputStream(completePath));

    }

    private String format(String text) {
        ApilangLexer lexer = new ApilangLexer(new ANTLRInputStream(text));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        commonTokenStream.fill();
        SourcePrinter p = new SourcePrinter();
        int prevtype = -1, type = -1;
        for (int index = 0; index < commonTokenStream.size(); index++) {
            Token token = commonTokenStream.get(index);

            prevtype = type;
            type = token.getType();
            //l("Token " + token.getText() + " @ " + token.getLine() + ":" + token.getCharPositionInLine());

            if (type != Parser.EOF) {
                if (type != ApilangLexer.DESCRIPTION && prevtype == ApilangLexer.SEMI) {
                    p.println();
                }
                if (type != ApilangLexer.RBRACK && prevtype == ApilangLexer.CBRACE) {
                    p.println();
                }

                if (type == ApilangLexer.OBRACE || type == ApilangLexer.OPAR) {

                    p.println(((p.indented && prevtype != ApilangLexer.LBRACK) ? " " : "") + token.getText());
                    p.indent();
                } else if (type == ApilangLexer.CBRACE || type == ApilangLexer.CPAR) {
                    p.unindent();
                    if (p.indented) p.println();
                    p.print(token.getText());
                } else if (type == ApilangLexer.DESCRIPTION || type == ApilangLexer.COMMENT || type == ApilangLexer.LINE_COMMENT) {
                    p.println((p.indented ? " " : "") + token.getText());
                } else if (type == ApilangLexer.SEMI || type == ApilangLexer.GT || type == ApilangLexer.ARRBRACK || type == ApilangLexer.RBRACK || type == ApilangLexer.COMMA) {
                    p.print(token.getText());
                } else p.print((p.indented && prevtype != ApilangLexer.LT ? " " : "") + token.getText());
            }
        }

        return p.getSource();
    }

    public Object parse(String parserName) {
        try {
            //parserName = obj.getString("parser");
            ParseProcessor parser = makeParser(parserName);
            parser.setSettings(settings.get(parserName));
            return parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object parse(String parserName, String file) {
        try {
            //parserName = obj.getString("parser");
            ParseProcessor parser = makeParser(parserName);

            JSONObject get = settings.get(parserName);
            get.put("fullpath", processPath(file));
            parser.setSettings(get);

            return parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generate(Object parserResult, String name) {
        try {
            ContextWrapper wrap = makeGenerator(name);
            wrap.setSettings(settings.get(name));
            log("Starting generator \"" + name + "\" (" + wrap.getClass().getName() + ")");
            boolean started = false;
            for (Method m : wrap.getClass().getMethods()) {

                if (m.getName().equals("generate")
                        && m.getParameterCount() == 1
                        && m.getParameterTypes()[0].isAssignableFrom(parserResult.getClass())) {
                    try {
                        started = true;
                        m.invoke(wrap, parserResult);
                        break;
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!started)
                log("Failed to start generator: no applicable method for processing result (" + parserResult.getClass().getSimpleName() + ") found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generate(Object parserResult, String name, String dir) {
        try {
            dir = processPath(dir);
            ContextWrapper wrap = makeGenerator(name);
            JSONObject get = settings.get(name);
            get.put("rel.dir", dir);
            wrap.setSettings(get);
            log("Starting generator \"" + name + "\" (" + wrap.getClass().getName() + ")");
            boolean started = false;
            for (Method m : wrap.getClass().getMethods()) {

                if (m.getName().equals("generate")
                        && m.getParameterCount() == 1
                        && m.getParameterTypes()[0].isAssignableFrom(parserResult.getClass())) {
                    try {
                        started = true;
                        m.invoke(wrap, parserResult);
                        break;
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!started)
                log("Failed to start generator: no applicable method for processing result (" + parserResult.getClass().getSimpleName() + ") found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path.toString();
    }

    public void setPath(String path) {
        this.path = new File(path);
    }

    private String processPath(String old) {
        return (old.length() > 0 && old.charAt(0) == File.pathSeparatorChar) ? (old) : (new File(path, old).getAbsolutePath());
    }

    private JSONObject tableToJSONObject(LuaTable t) throws JSONException {
        JSONObject result = new JSONObject();
        LuaValue[] keys = t.keys();
        for (LuaValue key : keys) {
//			log(key.checkstring());
            String keyname = key.checkjstring();
            LuaValue value = t.get(key);
            Object val = null;
            switch (value.type()) {
                case LuaValue.TINT:
                    val = value.checkint();
                    break;
                case LuaValue.TTABLE:
                    val = tableToJSONObject(value.checktable());
                    break;
                case LuaValue.TNUMBER:
                    val = value.islong() ? ((Object) value.tolong()) : ((Object) value.todouble());
                    break;
                case LuaValue.TSTRING:
                    val = value.checkjstring();
                    break;
                case LuaValue.TBOOLEAN:
                    val = value.toboolean();
                    break;
            }
            if (val != null) {
                result.put(keyname, val);
            }
        }
        return result;
    }

    private ContextWrapper makeGenerator(String name) throws InstantiationException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        Class<? extends ContextWrapper> clz = generators.get(name);
        if (clz != null) for (Constructor<?> c : clz.getConstructors()) {
            if (c.getParameterCount() == 1) {
                return (ContextWrapper) c.newInstance(this);
            }
        }
        return null;
    }

    private ParseProcessor makeParser(String name) throws InstantiationException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        Class<? extends ParseProcessor> clz = parsers.get(name);
        if (clz != null) for (Constructor<?> c : clz.getConstructors()) {
            if (c.getParameterCount() == 1) {
                return (ParseProcessor) c.newInstance(this);
            }
        }
        return null;
    }

    private Class<? extends ContextWrapper> loadGenerator(JSONObject from) throws JSONException, ClassNotFoundException {
        return (Class<? extends ContextWrapper>) Class.forName(from.getString("class"));
    }

    private Class<? extends ParseProcessor> loadParser(JSONObject from) throws JSONException, ClassNotFoundException {
        return (Class<? extends ParseProcessor>) Class.forName(from.getString("class"));
    }

    private <T> T log(T text) {
        System.out.println("[TModule] " + text);
        return text;
    }

    @Override
    public File getHomeDir() {
        return path;
    }

    @Override
    public void build() {

    }

    @Override
    public TypeDef getTypeFromName(String name) {
        return null;
    }

    @Override
    public ContainerDef getContainerFromName(String name) {
        return null;
    }

}
