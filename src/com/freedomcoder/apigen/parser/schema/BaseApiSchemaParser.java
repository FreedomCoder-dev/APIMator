package com.freedomcoder.apigen.parser.schema;

import com.freedomcoder.apigen.iface.*;
import com.freedomcoder.apigen.iface.constraints.*;
import com.freedomcoder.apigen.iface.containers.*;
import com.freedomcoder.apigen.parser.FileUtils;
import com.freedomcoder.apigen.parser.ParseProcessor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class BaseApiSchemaParser extends ParseProcessor<SchemaDom> {

    File objectsFile, methodsFile, errorsFile, responsesFile;
    SchemaDom ctx;
    public BaseApiSchemaParser(LangContext ctx) {
        super(ctx);
    }

    @Override
    public void setSettings(JSONObject settings) {
        try {
            File workdir = new File(getHomeDir(), settings.getString("rel.dir"));
            this.objectsFile = new File(workdir, settings.getString("objectsFilename"));
            this.methodsFile = new File(workdir, settings.getString("methodsFilename"));
            this.errorsFile = new File(workdir, settings.getString("errorsFilename"));
            this.responsesFile = new File(workdir, settings.getString("responsesFilename"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public SchemaDom parse() {
        SchemaDom dom = new SchemaDom();
        ctx = dom;
        try {
            parseObjects(dom);
            parseMethods(dom);
            parseResponses(dom);
            parseErrors(dom);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dom;
    }

    void parseObjects(SchemaDom dom) throws JSONException {
        System.out.println("### Parsing objects.");
        System.out.println("Parse json file " + objectsFile);
        JSONObject base = FileUtils.parseJsonFile(objectsFile);
        if (base == null) return;
        HashMap<String, ObjectDef> ret = new HashMap<String, ObjectDef>();
        base = base.getJSONObject("definitions");
        Iterator<String> keys = base.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            System.out.print("Parsing " + next + "... ");
            JSONObject curr = base.getJSONObject(next);

            ContainerDef parseContainer = parseContainer(curr);
            if (parseContainer instanceof ObjectContainer) {
                ((ObjectContainer) parseContainer).setName(next);
            }
            ret.put(next, new ObjectDef(dom, next).setParams(parseContainer));

            System.out.println("SUCCESS");
        }
        dom.setObjects(ret);
    }

    void parseErrors(SchemaDom cont) throws JSONException {
        System.out.println("### Parsing errors.");
        System.out.println("Parse json file " + errorsFile);
        JSONObject base = FileUtils.parseJsonFile(errorsFile);
        if (base == null) return;
        HashMap<String, ErrorDef> ret = new HashMap<String, ErrorDef>();
        base = base.getJSONObject("errors");
        Iterator<String> keys = base.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            System.out.print("Parsing " + next + "... ");
            JSONObject curr = base.getJSONObject(next);

            ErrorDef def = new ErrorDef(ctx);
            if (curr.has("code")) def.setCode(curr.getInt("code"));
            if (curr.has("description")) def.setDescription(curr.getString("description"));

            ret.put(next, def);
            System.out.println("SUCCESS");
        }
        cont.setErrors(ret);
    }

    void parseResponses(SchemaDom cont) throws JSONException {
        System.out.println("### Parsing responses.");
        System.out.println("Parse json file " + responsesFile);
        JSONObject base = FileUtils.parseJsonFile(responsesFile);
        if (base == null) return;
        HashMap<String, ResponseDef> ret = new HashMap<String, ResponseDef>();
        base = base.getJSONObject("definitions");
        Iterator<String> keys = base.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            System.out.print("Parsing response " + next + "... ");
            JSONObject curr = base.getJSONObject(next);

            ret.put(next, new ResponseDef(ctx, next).setParams(parseContainer(curr)));

            System.out.println("SUCCESS");
        }
        cont.setResponses(ret);
    }

    void parseMethods(SchemaDom cont) throws JSONException {
        System.out.println("### Parsing methods.");
        System.out.println("Parse json file " + methodsFile);
        JSONObject base = FileUtils.parseJsonFile(methodsFile);
        if (base == null) return;
        HashMap<String, MethodDef> ret = new HashMap<String, MethodDef>();
        JSONArray meth = base.getJSONArray("methods");
        for (int i = 0; i < meth.length(); i++) {
            JSONObject curr = meth.getJSONObject(i);

            String next = curr.getString("name");
            System.out.print("Parsing method " + next + "... ");

            MethodDef method = new MethodDef(ctx, next);
            if (curr.has("parameters")) method.setParams(parseContainerArray(curr.getJSONArray("parameters")));
            if (curr.has("responses")) {
                JSONObject resp = curr.getJSONObject("responses");
                Iterator<String> keys = resp.keys();
                while (keys.hasNext()) {
                    String nxt = keys.next();
                    JSONObject re = resp.getJSONObject(nxt);
                    method.addResponse(nxt, new ResponseRef(ctx, re.getString("$ref")));
                }
            } else
                System.out.println("FUCK\nFUCK\nFUCK\nFUCK\nFUCK\nFUCK\nFUCK\nFUCK\nFUCK\nFUCK\nFUCK\n  response for method " + next + " is MISSING!");
            ret.put(next, method);

            System.out.println("SUCCESS");
        }
        cont.setMethods(ret);
    }

    HashMap<String, ContainerDef> parseContainerArray(JSONArray array) throws JSONException {
        HashMap<String, ContainerDef> ret = new HashMap<String, ContainerDef>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject curr = array.getJSONObject(i);

            String next = curr.getString("name");

            ret.put(next, parseContainer(curr));

        }
        return ret;
    }

    ContainerDef parseContainer(JSONObject curr) throws JSONException {
        String description = "";
        boolean withoutRefs = false, required = false;
        if (curr.has("description")) {
            description = curr.getString("description");
        }
        if (curr.has("withoutRefs") && curr.getBoolean("withoutRefs")) {
            withoutRefs = true;
        }
        JSONArray arr = null;
        if (curr.has("required")) {
            Object get = curr.get("required");
            if (get instanceof Boolean && (Boolean) get) {
                required = true;
            }
            if (get instanceof JSONArray) {
                arr = (JSONArray) get;
            }
        }
        ContainerDef ret = null;
        if (curr.has("$ref")) {
            ret = new ObjectRef(ctx, curr.getString("$ref"));
        } else switch (curr.getString("type")) {

            case "object":
                ret = parseObjectContainer(curr);
                ObjectContainer objj = (ObjectContainer) ret;
                if (arr != null) for (int i = 0; i < arr.length(); i++) {
                    objj.setRequired(arr.getString(i), true);
                }
                break;
            case "array":
                ret = parseArrayContainer(curr);
                break;
            case "boolean":
                ret = parseBool(curr);
                break;
            case "integer":
                ret = parseInt(curr);
                break;
            case "number":
                ret = parseNumber(curr);
                break;
            case "string":
                ret = parseString(curr);
        }
        if (ret != null && !description.isEmpty()) ret.setDescription(description);
        if (ret != null && withoutRefs) ret.setWithoutRefs(true);
        if (ret != null && required) ret.setRequired(true);

        return ret;
    }

    private ContainerDef parseString(JSONObject curr) throws JSONException {
        if (!curr.getString("type").equals("string")) {
            System.out.println("FAIL: is not a string");
            return null;
        }

        StringContainer objectContainer = new StringContainer(ctx);
        Iterator<String> keys = curr.keys();
        while (keys.hasNext()) {
            String next = keys.next();

            if (next.equals("default")) {
                objectContainer.setDefaultValue(curr.getString("default"));
            } else if (next.equals("enum")) {
                if (curr.has("enumNames"))
                    objectContainer.getConstraints().add(new StringEnumConstraint(curr.getJSONArray("enum"), curr.getJSONArray("enumNames")));

                else objectContainer.getConstraints().add(new StringEnumConstraint(curr.getJSONArray("enum")));
            } else if (next.equals("minLength")) {
                objectContainer.getConstraints().add(new MinLengthStringConstraint(curr.getInt("minLength")));
            } else if (next.equals("maxLength")) {
                objectContainer.getConstraints().add(new MaxLengthStringConstraint(curr.getInt("maxLength")));
            } else if (next.equals("format")) {
                objectContainer.getConstraints().add(new FormatConstraint(curr.getString("format")));
            }

        }
        return objectContainer;
    }

    NumberContainer parseNumber(JSONObject curr) throws JSONException {
        if (!curr.getString("type").equals("number")) {
            System.out.println("FAIL: is not a number");
            return null;
        }

        NumberContainer objectContainer = new NumberContainer(ctx);
        Iterator<String> keys = curr.keys();
        while (keys.hasNext()) {
            String next = keys.next();

            if (next.equals("maximum")) {
                objectContainer.getConstraints().add(new MaxNumberConstraint(curr.getInt("maximum")));
            } else if (next.equals("minimum")) {
                objectContainer.getConstraints().add(new MinNumberConstraint(curr.getInt("minimum")));
            } else if (next.equals("default") && !curr.isNull("default")) {
                objectContainer.setDefaultValue(curr.getInt("default"));
            } else if (next.equals("enum")) {
                if (curr.has("enumNames"))
                    objectContainer.getConstraints().add(new IntegerEnumConstraint(curr.getJSONArray("enum"), curr.getJSONArray("enumNames")));
                else objectContainer.getConstraints().add(new IntegerEnumConstraint(curr.getJSONArray("enum")));
            }

        }
        return objectContainer;
    }

    IntegerContainer parseInt(JSONObject curr) throws JSONException {
        if (!curr.getString("type").equals("integer")) {
            System.out.println("FAIL: is not an integer");
            return null;
        }

        IntegerContainer objectContainer = new IntegerContainer(ctx);
        Iterator<String> keys = curr.keys();
        while (keys.hasNext()) {
            String next = keys.next();

            if (next.equals("maximum")) {
                objectContainer.getConstraints().add(new MaxNumberConstraint(curr.getInt("maximum")));
            } else if (next.equals("minimum")) {
                objectContainer.getConstraints().add(new MinNumberConstraint(curr.getInt("minimum")));
            } else if (next.equals("default") && !curr.isNull("default")) {
                objectContainer.setDefaultValue(curr.getInt("default"));
            } else if (next.equals("enum")) {
                if (curr.has("enumNames"))
                    objectContainer.getConstraints().add(new IntegerEnumConstraint(curr.getJSONArray("enum"), curr.getJSONArray("enumNames")));
                else objectContainer.getConstraints().add(new IntegerEnumConstraint(curr.getJSONArray("enum")));
            }

        }
        return objectContainer;
    }

    BooleanContainer parseBool(JSONObject curr) throws JSONException {
        if (!curr.getString("type").equals("boolean")) {
            System.out.println("FAIL: is not an integer");
            return null;
        }

        BooleanContainer objectContainer = new BooleanContainer(ctx);
        Iterator<String> keys = curr.keys();
        while (keys.hasNext()) {
            String next = keys.next();

            if (next.equals("default") && !curr.isNull("default")) {
                Object get = curr.get("default");
                if (get == Boolean.TRUE || ((Integer) get) == 1)
                    objectContainer.setDefaultValue(get == Boolean.TRUE || ((Integer) get) == 1 || ((get == Boolean.FALSE || ((Integer) get) == 0) ? false : null));
            }

        }
        return objectContainer;
    }

    ArrayContainer parseArrayContainer(JSONObject curr) throws JSONException {

        if (!curr.getString("type").equals("array")) {
            System.out.println("FAIL: is not an array");
            return null;
        }

        ArrayContainer objectContainer = new ArrayContainer(ctx);
        if (curr.has("items")) {
            curr = curr.getJSONObject("items");
            if (curr.has("allOf")) {
                JSONArray curr2 = curr.getJSONArray("allOf");

                for (int i = 0; i < curr2.length(); i++) {
                    curr = curr2.getJSONObject(i);
                    if (curr.has("$ref")) {
                        objectContainer.getMergeFrom().add(new ObjectRef(ctx, curr.getString("$ref")));
                    } else System.out.println(curr);
                }
            } else objectContainer.setTypes(parseContainer(curr));
        }
        return objectContainer;
    }

    ObjectContainer parseObjectContainer(JSONObject curr) throws JSONException {

        if (!curr.getString("type").equals("object")) {
            System.out.println("FAIL: is not an object");
            return null;
        }
        ObjectContainer objectContainer = new ObjectContainer(ctx);

        if (curr.has("properties")) {
            curr = curr.getJSONObject("properties");

            HashMap<String, ContainerDef> properties = objectContainer.getProperties();

            Iterator<String> keys = curr.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                JSONObject prop = curr.getJSONObject(next);
                ContainerDef parseContainer = parseContainer(prop);
                if (parseContainer instanceof ObjectContainer) {
                    ((ObjectContainer) parseContainer).setName(next);
                }
                properties.put(next, parseContainer);
            }
        } else if (curr.has("allOf")) {
            JSONArray curr2 = curr.getJSONArray("allOf");

            HashMap<String, ContainerDef> properties = objectContainer.getProperties();
            for (int i = 0; i < curr2.length(); i++) {
                curr = curr2.getJSONObject(i);
                if (curr.has("properties")) {
                    curr = curr.getJSONObject("properties");
                    Iterator<String> keys = curr.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        JSONObject prop = curr.getJSONObject(next);
                        ContainerDef parseContainer = parseContainer(prop);
                        if (parseContainer instanceof ObjectContainer) {
                            ((ObjectContainer) parseContainer).setName(next);
                        }
                        properties.put(next, parseContainer);
                    }
                } else if (curr.has("$ref")) {
                    objectContainer.getMergeFrom().add(new ObjectRef(ctx, curr.getString("$ref")));
                } else System.out.println(curr);
            }

        } else {
            return new UndefinedObject(ctx);
        }
        return objectContainer;
    }
}
