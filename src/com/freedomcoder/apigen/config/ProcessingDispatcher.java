package com.freedomcoder.apigen.config;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.ContextWrapper;
import com.freedomcoder.apigen.iface.LangContext;
import com.freedomcoder.apigen.iface.TypeDef;
import com.freedomcoder.apigen.parser.FileUtils;
import com.freedomcoder.apigen.parser.ParseProcessor;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ProcessingDispatcher extends LangContext {

    public HashMap<String, Class<? extends ParseProcessor>> parsers = new HashMap<String, Class<? extends ParseProcessor>>();
    public HashMap<String, Class<? extends ContextWrapper>> generators = new HashMap<String, Class<? extends ContextWrapper>>();
    public HashMap<String, JSONObject> settings = new HashMap<String, JSONObject>();
    File baseconfig;
    File projectdir;

    public void run(File config) {
        baseconfig = config;
        log("Starting...");
        JSONObject cfg = FileUtils.parseJsonFile(baseconfig);
        try {
            log("Scanning modules...");
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
            log("Scanning settongs...");
            settings:
            {
                JSONObject setts = cfg.getJSONObject("settings");
                Iterator<String> keys = setts.keys();
                while (keys.hasNext()) {
                    String name = keys.next();
                    settings.put(name, setts.getJSONObject(name));
                }
            }
            current_project:
            {
                JSONObject general = cfg.getJSONObject("general");
                String prj = general.getString("currentProject");
                load:
                {
                    projectdir = new File(baseconfig.getParentFile(), prj);
                    File projectconfig = new File(projectdir, "projectconfig.json");
                    JSONObject prjconf = FileUtils.parseJsonFile(projectconfig);
                    log("Scanning project configuration...");
                    ProjectConfiguration conf = new ProjectConfiguration(prjconf, this);

                    log("    ----Parsing stage----     ");
                    log("Starting parser \"" + conf.getParserName() + "\" (" + conf.getParser().getClass().getName() + ")");
                    ParseProcessor parser = conf.getParser();
                    Object parserResult = parser.parse();
                    log("   ----Generation stage----   ");
                    ArrayList<ContextWrapper> generators = conf.getGenerators();
                    ArrayList<String> generatorsNames = conf.getGeneratorsNames();
                    for (int i = 0; i < generators.size(); i++) {
                        ContextWrapper wrap = generators.get(i);
                        log("Starting generator \"" + generatorsNames.get(i) + "\" (" + wrap.getClass().getName() + ")");
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
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public <T> T log(T text) {
        System.out.println("[Dispatcher] " + text);
        return text;
    }

    public ContextWrapper makeGenerator(String name) throws InstantiationException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        Class<? extends ContextWrapper> clz = generators.get(name);
        if (clz != null) for (Constructor<?> c : clz.getConstructors()) {
            if (c.getParameterCount() == 1) {
                return (ContextWrapper) c.newInstance(this);
            }
        }
        return null;
    }

    public ParseProcessor makeParser(String name) throws InstantiationException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
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

    public Class<? extends ParseProcessor> loadParser(JSONObject from) throws JSONException, ClassNotFoundException {
        return (Class<? extends ParseProcessor>) Class.forName(from.getString("class"));
    }

    @Override
    public File getHomeDir() {
        return projectdir;
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
