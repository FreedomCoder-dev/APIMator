package com.freedomcoder.apigen.config;

import com.freedomcoder.apigen.iface.ContextWrapper;
import com.freedomcoder.apigen.parser.ParseProcessor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ProjectConfiguration {

    public JSONObject obj;
    public ProcessingDispatcher disp;
    public HashMap<String, JSONObject> settings = new HashMap<String, JSONObject>();
    ParseProcessor parser;
    String parserName;
    ArrayList<ContextWrapper> generators;
    ArrayList<String> generatorsNames;

    public ProjectConfiguration(JSONObject obj, ProcessingDispatcher disp) throws JSONException {
        this.obj = obj;
        this.disp = disp;

        settings = (HashMap<String, JSONObject>) disp.settings.clone();
        settings:
        {
            JSONObject setts = obj.getJSONObject("settings");
            Iterator<String> keys = setts.keys();
            while (keys.hasNext()) {
                String name = keys.next();
                if (settings.containsKey(name)) {
                    JSONObject target = settings.get(name);
                    JSONObject replace = setts.getJSONObject(name);
                    deepMerge(replace, target, true);
                } else settings.put(name, setts.getJSONObject(name));
            }
        }
        try {
            parserName = obj.getString("parser");
            parser = disp.makeParser(parserName);
            parser.setSettings(settings.get(parserName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        generators:
        {
            try {
                JSONArray gens = obj.getJSONArray("generators");
                generators = new ArrayList<ContextWrapper>(gens.length());
                generatorsNames = new ArrayList<String>(gens.length());
                for (int i = 0; i < gens.length(); i++) {
                    try {
                        String string = gens.getString(i);
                        ContextWrapper makeGenerator = disp.makeGenerator(string);
                        makeGenerator.setSettings(settings.get(string));
                        generators.add(makeGenerator);
                        generatorsNames.add(string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public static JSONObject deepMerge(JSONObject source, JSONObject target, boolean mergeArrays) throws JSONException {
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

    public ArrayList<String> getGeneratorsNames() {
        return generatorsNames;
    }

    public void setGeneratorsNames(ArrayList<String> generatorsNames) {
        this.generatorsNames = generatorsNames;
    }

    public String getParserName() {
        return parserName;
    }

    public void setParserName(String parserName) {
        this.parserName = parserName;
    }

    public ParseProcessor getParser() {
        return parser;
    }

    public void setParser(ParseProcessor parser) {
        this.parser = parser;
    }

    public ArrayList<ContextWrapper> getGenerators() {
        return generators;
    }

    public void setGenerators(ArrayList<ContextWrapper> generators) {
        this.generators = generators;
    }

}
