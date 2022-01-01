package com.freedomcoder.apigen.generators.javaserver;

import com.freedomcoder.apigen.generators.GeneratorAccepts;
import com.freedomcoder.apigen.iface.*;
import com.freedomcoder.apigen.iface.containers.ArrayContainer;
import com.freedomcoder.apigen.iface.containers.IntegerContainer;
import com.freedomcoder.apigen.iface.containers.ObjectContainer;
import com.freedomcoder.astutils.JAST;
import com.freedomcoder.astutils.SourceDirManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkmod.javalang.ASTManager;
import org.walkmod.javalang.ParseException;
import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.body.*;
import org.walkmod.javalang.ast.expr.IntegerLiteralExpr;
import org.walkmod.javalang.ast.expr.NameExpr;
import org.walkmod.javalang.ast.expr.VariableDeclarationExpr;
import org.walkmod.javalang.ast.stmt.BlockStmt;
import org.walkmod.javalang.ast.stmt.Statement;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.ast.type.VoidType;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaServerGenerator extends ContextWrapper implements GeneratorAccepts<SchemaDom> {

    public int methodcount = 0, linecount = 0;
    public int varid = 0;
    File outdir;
    SourceDirManager src;
    String basepackage;
    PrintWriter wr = new PrintWriter(System.out);
    SchemaDom dom;
    int antiloop = 10;
    boolean flagAddInit = false;

    public JavaServerGenerator(LangContext ctx) {
        super(ctx);
    }

    @Override
    public void setSettings(JSONObject settings) {
        try {
            outdir = new File(getHomeDir(), settings.getString("rel.dir"));
            src = JAST.makeManager(outdir.toString());
            basepackage = settings.getString("package");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean generate(SchemaDom from) {
        dom = from;
        log("starting...");
        long loadtime = System.currentTimeMillis();
        log("Output directory : " + outdir);
        log("Package          : " + basepackage);
        try {
            makeObjects();
            makeResponses();
            makeMethods();
            makeInitProvider();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log("Done in " + (System.currentTimeMillis() - loadtime) + "ms");
        return true;
    }

    @Override
    public void build() {

    }

    public void makeObjects() throws ParseException {
        flagAddInit = true;
        log("Generating objects");
        for (HashMap.Entry<String, ObjectDef> def : dom.getObjects().entrySet()) {
            CompilationUnit unit = new CompilationUnit();
            String capitaliseAll = capitaliseAll(def.getKey());
            unit.setPackage(basepackage + ".objects");
            unit.addImportAll(basepackage);
            unit.addImportAll("org.json");
            unit.addImportAll("java.util");
            ClassOrInterfaceDeclaration clz = unit.makeClass(capitaliseAll);
            clz.makeConstructor().makeBody();
            BlockStmt makeBody = clz.makeConstructor().addThrows("JSONException").addParameter("Object", "src").makeBody();
            methodcount++;
            makeJsonParser(clz, makeBody, def.getValue().getParams());
            makeBody = clz.makeMethod("toJson").setType("JSONObject").addThrows("JSONException").makeBody();
            makeJsonDumper(clz, makeBody, def.getValue().getParams());

            clz.sortMembers();
            linecount += src.saveFile(unit);
        }
        log("Done Objects");
        log("Functions count: " + methodcount);
        log("Lines count: " + linecount);
        flagAddInit = false;
    }

    public void makeResponses() throws ParseException {
        log("Generating responses");
        flagAddInit = true;
        for (HashMap.Entry<String, ResponseDef> def : dom.getResponses().entrySet()) {
            CompilationUnit unit = new CompilationUnit();
            String capitaliseAll = capitaliseAll(def.getKey());
            unit.setPackage(basepackage + ".responses");
            unit.addImportAll(basepackage);
            unit.addImportAll("org.json");
            unit.addImportAll("com.freedomcoder.servers.iface");
            unit.addImportAll("java.util");
            unit.addImportAll(basepackage + ".objects");
            ClassOrInterfaceDeclaration clz = unit.makeClass(capitaliseAll);
            clz.addImplements("IResponse");
            BlockStmt makeBody = clz.makeMethod("init").addThrows("JSONException").addParameter(new ClassOrInterfaceType("JSONObject"), "src").makeBody();
            methodcount++;
            makeJsonParser(clz, makeBody, def.getValue().getParams());
            makeBody = clz.makeMethod("toJson").override().setType("JSONObject").addThrows("JSONException").makeBody();
            makeJsonDumper(clz, makeBody, def.getValue().getParams());
            clz.sortMembers();
            linecount += src.saveFile(unit);
        }
        log("Done Responses");
        log("Functions count: " + methodcount);
        log("Lines count: " + linecount);
        flagAddInit = false;
    }

    public void makeRequest(MethodDef fo) throws ParseException {

        CompilationUnit unit = new CompilationUnit();
        String capitaliseAll = capitaliseAll(fo.getName().replace('.', '_')) + "Request";
        unit.setPackage(basepackage + ".requests");
        unit.addImportAll(basepackage);
        unit.addImportAll("org.json");
        unit.addImportAll("com.freedomcoder.servers.iface");
        unit.addImportAll("java.util");
        unit.addImportAll(basepackage + ".objects");
        ClassOrInterfaceDeclaration clz = unit.makeClass(capitaliseAll);
        clz.addExtends("BaseJsonRequest");
        BlockStmt makeBody = clz.makeMethod("init").override().addThrows("JSONException").addParameter(new ClassOrInterfaceType("JSONObject"), "src").makeBody();
        methodcount++;
        HashMap<String, ContainerDef> params = fo.getParams();
        ObjectContainer cdef = new ObjectContainer(dom);
        cdef.setProperties(params);
        makeJsonParser(clz, makeBody, cdef);
        makeBody = clz.makeMethod("toJson").setType("JSONObject").addThrows("JSONException").makeBody();
        makeJsonDumper(clz, makeBody, cdef);
        clz.sortMembers();
        linecount += src.saveFile(unit);

    }

    public void makeInitProvider() throws ParseException {

        CompilationUnit unit = new CompilationUnit();
        String capitaliseAll = "ApiInitProvider";
        unit.setPackage(basepackage);
        unit.addImportAll("com.freedomcoder.servers.iface");

        ClassOrInterfaceDeclaration clz = unit.makeClass(capitaliseAll);
        clz.addImplements("IInitProvider");
        BlockStmt makeBody = clz.makeMethod("init").addParameter(new ClassOrInterfaceType("IServerApiFactory"), "factory").override().makeBody();
        for (HashMap.Entry<String, MethodDef> def : dom.getMethods().entrySet()) {
            String key = def.getKey();
            String reqname = capitaliseAll(key.replace('.', '_'));
            int lastIndexOf = key.lastIndexOf('.');
            String methname = key.substring(0, lastIndexOf) + '.' + capitaliseAll(key.substring(lastIndexOf + 1));

            makeBody.addMethodCall("factory", "putMethod", "\"" + def.getKey() + "\"", "new " + basepackage + ".methods." + methname + "Method()", basepackage + ".requests." + reqname + "Request.class");
        }
        methodcount++;
        linecount += src.saveFile(unit);

    }

    public void makeMethods() throws ParseException {
        log("Generating methods");
        HashMap<String, ClassOrInterfaceDeclaration> selectors = new HashMap<String, ClassOrInterfaceDeclaration>();
        for (HashMap.Entry<String, MethodDef> def : dom.getMethods().entrySet()) {
            makeRequest(def.getValue());
            CompilationUnit unit = new CompilationUnit();
            String key = def.getKey();
            int lastIndexOf = key.lastIndexOf('.');
            String capitaliseAll = capitaliseAll(key.substring(lastIndexOf + 1)) + "Method";
            unit.setPackage(basepackage + ".methods" + (lastIndexOf > 0 ? "." + key.substring(0, lastIndexOf) : ""));
            unit.addImportAll(basepackage);
            unit.addImportAll("org.json");
            unit.addImport(basepackage.substring(0, basepackage.lastIndexOf('.')) + ".sessions.AppSession");
            unit.addImportAll("java.util");
            unit.addImportAll(basepackage + ".objects");
            unit.addImportAll(basepackage + ".requests");
            unit.addImportAll(basepackage + ".responses");
            ClassOrInterfaceDeclaration clz = unit.makeClass(capitaliseAll);
            MethodDef value = def.getValue();
            HashMap<String, ResponseRef> methResponses = value.getMethResponses();
            MethodDeclaration makeMethod = clz.makeMethod("execute");
            String reqname = capitaliseAll(value.getName().replace('.', '_')) + "Request";

            makeMethod.addParameter(new ClassOrInterfaceType(reqname), "request").addParameter(new ClassOrInterfaceType("AppSession"), "s");
            BlockStmt makeBody = makeMethod.makeBody();

            if (methResponses != null && methResponses.size() > 0) {
                String name = methResponses.get(methResponses.keySet().toArray(new String[]{})[0]).getName();

                String resp = capitaliseAll(name.substring(28));

                makeMethod.setType(resp);

                makeBody.addSourceDirectly(resp + " response = new " + resp + "();");

                clz.addExtends("BaseJsonMethod<" + capitaliseAll(def.getKey().replace('.', '_')) + "Request" + ", " + resp + ">");

                makeBody.addSourceDirectly("return response;");
            } else
                clz.addExtends("BaseJsonMethod<" + capitaliseAll(def.getKey().replace('.', '_')) + "Request" + ", ?>");

            makeMethod.override();

            clz.sortMembers();
            linecount += src.saveFile(unit);

        }

        log("Done Methods");
        log("Functions count: " + methodcount);
        log("Lines count: " + linecount);

    }

    private void makeMethodBuilder(ClassOrInterfaceDeclaration decl, String selectorpath, MethodDef apimeth) {
        int lastIndexOf = selectorpath.lastIndexOf('.');
        String name = "";
        if (lastIndexOf > 0) name = selectorpath.substring(lastIndexOf + 1);
        else if (selectorpath.length() > 0) name = selectorpath;
        String path = "BaseSelector";
        String buildername = name + "Builder";
        if (lastIndexOf > 0) path = selectorpath.substring(0, lastIndexOf);
        try {
            decl.makeMethod(name).setType(buildername).makeBody().addSourceDirectly("return new " + buildername + "(auth, \"" + selectorpath + "\");");
        } catch (ParseException e) {
        }

        boolean hasExtended = false;

        ClassOrInterfaceDeclaration makeClass = decl.makeClass(buildername);
        makeClass.addExtends("MethodBuilder").makeConstructor().setPrivate(true).addParameter("VkAuthInterface", "vkAuthInterface").addParameter("String", "route").makeBody().addMethodCall("", "super", "vkAuthInterface", "route");
        HashMap<String, ContainerDef> params = apimeth.getParams();
        if (params != null) for (HashMap.Entry<String, ContainerDef> entr : params.entrySet()) {
            ContainerDef def = entr.getValue();
            String settername = makeClass.setterName(false, capitaliseAll(entr.getKey()));
            try {
                BlockStmt makeBody = makeClass.makeMethod(settername).setType(buildername).addParameter(new ClassOrInterfaceType(getArgClassname(def)), "val").makeBody().addSourceDirectly("put(\"" + entr.getKey() + "\", val);").addSourceDirectly("return this;");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (HashMap.Entry<String, ResponseRef> entr : apimeth.getMethResponses().entrySet()) {
            String tag = entr.getKey().replace("response", "").replace("Response", "");
            if (tag.length() > 0) tag = makeClass.capitalizeFirstLetter(tag);
            String clz = basepackage + "." + getRefRoute(entr.getValue().getName());
            BlockStmt makeBody = makeClass.makeMethod("request" + tag).addThrows("IOException").addThrows("JSONException").addThrows("ApiException").setType(clz).makeBody().addReturn("new " + clz + "(super.request());");
        }
    }

    private ClassOrInterfaceDeclaration getSelector(HashMap<String, ClassOrInterfaceDeclaration> selectors, String name) {
        int indx = name.lastIndexOf('.');
        String selectorpath = name;
        if (indx > 0) {
            selectorpath = name.substring(0, indx);
        }

        ClassOrInterfaceDeclaration result;
        result = selectors.get(selectorpath);
        if (result == null) result = makeSelector(selectors, selectorpath);

        return result;
    }

    private ClassOrInterfaceDeclaration makeSelector(HashMap<String, ClassOrInterfaceDeclaration> selectors, String selectorpath) {
        CompilationUnit unit = new CompilationUnit();
        int lastIndexOf = selectorpath.lastIndexOf('.');
        String name = "BaseSelector";
        if (lastIndexOf > 0) name = selectorpath.substring(lastIndexOf + 1);
        else if (selectorpath.length() > 0) name = selectorpath;
        String path = "";
        if (lastIndexOf > 0) path = selectorpath.substring(0, lastIndexOf);

        unit.setPackage(basepackage + ".methods." + path);
        unit.addImportAll(basepackage);
        unit.addImportAll("org.json");
        unit.addImportAll("java.util");
        unit.addImport("java.io.IOException");
        unit.addImportAll(basepackage + ".responses");
        unit.addImportAll(basepackage + ".exceptions");
        unit.addImportAll(basepackage + ".objects");
        unit.addImportAll(basepackage + ".iface");
        unit.addImportAll(basepackage + ".utils");
        ClassOrInterfaceDeclaration result = unit.makeClass(name);
        result.addExtends("MethodSelector");
        try {

            BlockStmt makeBody = result.makeConstructor().addParameter("VkAuthInterface", "vkAuthInterface").makeBody();
            makeBody.addMethodCall("", "super", "vkAuthInterface");

        } catch (Exception e) {
            e.printStackTrace();
        }
        methodcount++;

        selectors.put(selectorpath, result);

        if (selectorpath != "BaseSelector") addToParentSelector(selectors, selectorpath);
        return result;
    }

    private void addToParentSelector(HashMap<String, ClassOrInterfaceDeclaration> selectors, String selectorpath) {

        if (selectorpath == "") return;
        int lastIndexOf = selectorpath.lastIndexOf('.');
        String name = "";
        if (lastIndexOf > 0) name = selectorpath.substring(lastIndexOf + 1);
        else if (selectorpath.length() > 0) name = selectorpath;
        String path = "BaseSelector";
        if (lastIndexOf > 0) path = selectorpath.substring(0, lastIndexOf);

        ClassOrInterfaceDeclaration parent = getSelector(selectors, path);
        parent.makeField(basepackage + ".methods." + selectorpath).addVariable(name).setFinal(true).setPublic(true);
        try {
            parent.getMainconstructor().getBlock().addSourceDirectly("this." + name + " = new " + basepackage + ".methods." + selectorpath + "(vkAuthInterface);");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void makeJsonParser(ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) throws ParseException {
        String name = from.getType().getName();
        switch (name) {

            case "object":
                to.addSourceDirectly("JSONObject obj = (JSONObject) src;");
                for (HashMap.Entry<String, ContainerDef> e : ((ObjectContainer) from).getProperties().entrySet()) {
                    try {
                        ContainerDef value = e.getValue();
                        if (value != null) try {
                            //System.out.println("Parser var " + e.getKey());

                            bindFieldToContainer(e.getKey(), e.getKey(), "obj", cdecl, to, value);
                        } catch (Exception ey) {
                            ey.printStackTrace();
                        }
                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }
                break;
            case "array":
                to.addSourceDirectly("JSONArray arr = (JSONArray) src;");
                bindFieldToJsonInt("value", "", "obj", cdecl, to, from);
                break;
            case "string":
            case "float":
            case "integer":
            case "number":
            case "boolean":
                String mvar = "value";
                String implClassname = "boolean";
                cdecl.addField(implClassname, flagAddInit, mvar);
                List<BodyDeclaration> types = cdecl.getMembers();
                if (types == null) types = new ArrayList<BodyDeclaration>();

                MethodDeclaration meth = new MethodDeclaration();
                meth.setModifiers(ModifierSet.PUBLIC);
                String terName = cdecl.getterName(implClassname.equalsIgnoreCase("boolean"), capitaliseAll(mvar));

                meth.setName(terName);
                meth.setType(implClassname);
                meth.makeBody().addReturn("this." + mvar);
                types.add(meth);
                terName = cdecl.setterName(implClassname.equalsIgnoreCase("boolean"), capitaliseAll(mvar));
                //if (terName.equals("etClass")) terName = "getClasz";
                meth = new MethodDeclaration();
                meth.setModifiers(ModifierSet.PUBLIC);
                meth.setName(terName);
                meth.setType(new VoidType());
                meth.addParameter(new ClassOrInterfaceType(implClassname), "in");
                meth.makeBody().addSourceDirectly("this." + mvar + " = in;");
                types.add(meth);
                cdecl.setMembers(types);
                methodcount++;
                to.addSourceDirectly(mvar + " = (" + implClassname + ") src;");
                break;
            default:
        }

    }

    public void makeJsonDumper(ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) throws ParseException {
        String name = from.getType().getName();
        switch (name) {

            case "object":
                to.addSourceDirectly("JSONObject obj = new JSONObject();");
                for (HashMap.Entry<String, ContainerDef> e : ((ObjectContainer) from).getProperties().entrySet()) {
                    try {
                        ContainerDef value = e.getValue();
                        if (value != null) {
                            //System.out.println("Dumper var " + e.getKey());

                            bindRawValToJSONObjectVar("obj", e.getKey(), value, to, true);
                        }
                    } catch (Exception er) {
                        er.printStackTrace();
                    }
                }
                to.addReturn("obj");
                break;
            case "array":
                to.addSourceDirectly("JSONArray arr = (JSONArray) src;");
                bindFieldToJsonInt("value", "", "obj", cdecl, to, from);
                break;
            case "string":
            case "integer":
            case "number":
            case "boolean":
                String mvar = "value";
                String implClassname = "boolean";
                cdecl.addField(implClassname, flagAddInit, mvar);
                List<BodyDeclaration> types = cdecl.getMembers();
                if (types == null) types = new ArrayList<BodyDeclaration>();

                MethodDeclaration meth = new MethodDeclaration();
                meth.setModifiers(ModifierSet.PUBLIC);
                String terName = cdecl.getterName(implClassname.equalsIgnoreCase("boolean"), capitaliseAll(mvar));

                meth.setName(terName);
                meth.setType(implClassname);
                meth.makeBody().addReturn("this." + mvar);
                types.add(meth);
                terName = cdecl.setterName(implClassname.equalsIgnoreCase("boolean"), capitaliseAll(mvar));
                //if (terName.equals("etClass")) terName = "getClasz";
                meth = new MethodDeclaration();
                meth.setModifiers(ModifierSet.PUBLIC);
                meth.setName(terName);
                meth.setType(new VoidType());
                meth.addParameter(new ClassOrInterfaceType(implClassname), "in");
                meth.makeBody().addSourceDirectly("this." + mvar + " = in;");
                types.add(meth);
                cdecl.setMembers(types);
                methodcount++;
                to.addSourceDirectly(mvar + " = (" + implClassname + ") src;");
                break;
            default:
        }

    }

    public void bindRawValToJSONObjectVar(String var, String field, ContainerDef cont, BlockStmt to, boolean isobj) throws ParseException {
        String fieldType = cont.getType().getName();
        String mvar = "this.m" + capitaliseAll(field);
        if (fieldType.equals("array")) {
            if (isobj && !cont.isRequired()) {
                to = to.makeIf(mvar + " != null").makeThen();
            }


            ArrayContainer ar = (ArrayContainer) cont;
            String varname = "tmpJArr" + varid++, varitemname = "tmpJArrI" + varid++;

            VariableDeclarationExpr variableDeclarationExpr = new VariableDeclarationExpr();
            variableDeclarationExpr.setType(new ClassOrInterfaceType(getArrDeclClassname(ar.getTypes())));
            ArrayList<VariableDeclarator> vars = new ArrayList<VariableDeclarator>();
            VariableDeclarator variableDeclarator = new VariableDeclarator();
            variableDeclarator.setId(new VariableDeclaratorId(varitemname));
            vars.add(variableDeclarator);
            variableDeclarationExpr.setVars(vars);

            to.addSourceDirectly("JSONArray " + varname + " = new JSONArray();");

            bindRawValToJSONObjectVar(varname, varitemname, ar.getTypes(), to.makeForEach(variableDeclarationExpr, new NameExpr((isobj ? mvar : field))), false);
            to.addSourceDirectly(var + ".put(" + (isobj ? "\"" + field + "\", " : "") + varname + ");");
        } else if (fieldType.equals("reference")) {
            String arrDeclClassname = getArrDeclClassname(cont);
            String src = var + ".put(" + (isobj ? "\"" + field + "\", " : "") + (isobj ? mvar : field) + ("JSONArray".equals(arrDeclClassname) || "JSONObject".equals(arrDeclClassname) ? ");" : ".toJson());");
            if (isobj && !cont.isRequired()) {
                to.makeIf(mvar + " != null").setThenStmt((Statement) ASTManager.parse(Statement.class, src));
            } else to.addSourceDirectly(src);
        } else
            to.addSourceDirectly(var + ".put(" + (isobj ? "\"" + field + "\", " : "") + (isobj ? mvar : field) + ");");
    }

    public void bindVarToArrayContainer(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) throws ParseException {
        switch (from.getType().getName()) {

            case "integer":
                bindVarToJsonAInt(var, ctr, json, cdecl, to, from);
                break;
            case "reference":
                bindVarToJsonARef(var, ctr, json, cdecl, to, from);
                break;
            case "boolean":
                bindVarToJsonABoolean(var, ctr, json, cdecl, to, from);
                break;
            case "string":
                bindVarToJsonAString(var, ctr, json, cdecl, to, from);
                break;
            case "object":
                log("VARIABLE BIND FAILED: OBJECT TYPE OF CONTAINER");
                break;
            case "array":
                bindVarToJsonAArray(var, ctr, json, getArrDeclClassname(from), cdecl, to, from);
                break;
            case "number":
            case "float":
                bindVarToJsonAFloat(var, ctr, json, cdecl, to, from);
                break;
            default:
                log("VARIABLE BIND FAILED: UNDEFINED TYPE OF CONTAINER");
        }
    }

    public void bindFieldsToContainer(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) throws ParseException {
        HashMap<String, ContainerDef> properties = ((ObjectContainer) from).getProperties();

        for (HashMap.Entry<String, ContainerDef> e : properties.entrySet()) {
            char[] arr = e.getKey().toCharArray();
            arr[0] = Character.toUpperCase(arr[0]);

            bindFieldToContainer(var + new String(arr), e.getKey(), json, cdecl, to, e.getValue());
        }
    }

    public void bindFieldToContainer(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) throws ParseException {
        if (from == null) return;
        varid++;
        if (!from.isRequired()) {
            to = to.makeIf(json + ".has(\"" + ctr + "\")").makeThen();
        }
        String vari = "tmp" + varid;
        String arrDeclClassname = getArrDeclClassname(from);
        String mvar = "m" + capitaliseAll(var);
        if (arrDeclClassname.contains("null")) log(" from " + from + " var " + var + " ctr " + ctr);
        if (from.getType().getName() != "object") {
            //System.out.println("Parser construction: field " + arrDeclClassname + " " + mvar);
            cdecl.addField(arrDeclClassname, flagAddInit, mvar);
            List<BodyDeclaration> types = cdecl.getMembers();
            if (types == null) types = new ArrayList<BodyDeclaration>();

            MethodDeclaration meth = new MethodDeclaration();
            meth.setModifiers(ModifierSet.PUBLIC);
            VariableDeclarator f = cdecl.getField(mvar);
            FieldDeclaration decl = (FieldDeclaration) f.getParentNode();
            String terName = cdecl.getterName(decl.getType().toString().equalsIgnoreCase("boolean"), capitaliseAll(var));
            if (terName.equals("getClass")) terName = "getClasz";
            meth.setName(terName);
            meth.setType(decl.getType());
            meth.makeBody().addReturn("this." + mvar);
            types.add(meth);
            terName = cdecl.setterName(decl.getType().toString().equalsIgnoreCase("boolean"), capitaliseAll(var));
            //if (terName.equals("etClass")) terName = "getClasz";
            meth = new MethodDeclaration();
            meth.setModifiers(ModifierSet.PUBLIC);
            meth.setName(terName);
            meth.setType(new VoidType());
            meth.addParameter(decl.getType(), "in");
            meth.makeBody().addSourceDirectly("this." + mvar + " = in;");
            types.add(meth);
            cdecl.setMembers(types);
            methodcount++;
            //System.out.println("Parser construction: var " + arrDeclClassname + " " + vari);
            to.addVar(arrDeclClassname, vari);
        }
        boolean isObj = false;
        String name = from.getType().getName();
        switch (name) {

            case "integer":
                bindVarToJsonInt(vari, ctr, json, cdecl, to, from);
                break;
            case "reference":
                if (arrDeclClassname.endsWith("Boolean")) {
                    bindVarToJsonBoolean(vari, ctr, json, cdecl, to, from, true);
                    break;
                }
                bindVarToJsonRef(vari, ctr, json, cdecl, to, from);
                break;
            case "boolean":
                bindVarToJsonBoolean(vari, ctr, json, cdecl, to, from, false);
                break;
            case "string":
                bindVarToJsonString(vari, ctr, json, cdecl, to, from);
                break;
            case "object":
                bindFieldsToContainer(var, ctr, json, cdecl, to, from);
                isObj = true;
                break;
            case "undefinedobject":
                bindVarToJsonUndef(vari, ctr, json, cdecl, to, from);
                break;
            case "array":
                bindVarToJsonArray(vari, ctr, json, arrDeclClassname, cdecl, to, from);
                break;
            case "number":
            case "float":
                bindVarToJsonFloat(vari, ctr, json, cdecl, to, from);
                break;
            default:
                log("VARIABLE BIND FAILED: UNDEFINED TYPE OF CONTAINER");
        }
        if (!isObj) try {

            to.addSourceDirectly("this." + mvar + " = " + vari + ";");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonAInt(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = (" + ((IntegerContainer) from).getPoweredName() + ")" + json + ".getLong(" + ctr + ");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonAFloat(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = (float)" + json + ".getDouble(" + ctr + ");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonABoolean(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = " + json + ".getBoolean(" + ctr + ");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonAString(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = " + json + ".getString(" + ctr + ");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonARef(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            String arrDeclClassname = getArrDeclClassname(from);
            if ("JSONArray".equals(arrDeclClassname) || "JSONObject".equals(arrDeclClassname))
                to.addSourceDirectly(var + " = " + json + ".get" + arrDeclClassname + "(" + ctr + ");");
            else
                to.addSourceDirectly(var + " = new " + arrDeclClassname + "(" + json + ".get(" + ctr + "));");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonAArray(String var, String ctr, String json, String type, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {

            varid++;
            to.addVar("int", "tmpJALen" + varid);
            to.addVar("JSONArray", "tmpJArr" + varid);
            to.addSourceDirectly("tmpJArr" + varid + " = " + json + ".getJSONArray(" + ctr + ");");
            to.addSourceDirectly("tmpJALen" + varid + " = tmpJArr" + varid + ".length();");
            to.addSourceDirectly(var + " = new " + type + "(tmpJALen" + varid + ");");
            ContainerDef types = ((ArrayContainer) from).getTypes();
            to.addVar(getArrDeclClassname(types), "tmpJAtmp" + varid);
            int gg = varid;
            BlockStmt forr = to.makeFor("i" + gg, new IntegerLiteralExpr(0), new NameExpr("tmpJALen" + varid));

            bindVarToArrayContainer("tmpJAtmp" + gg, "i" + gg, "tmpJArr" + gg, cdecl, forr, types);
            forr.addSourceDirectly(var + ".add(tmpJAtmp" + gg + ");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonInt(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = (" + ((IntegerContainer) from).getPoweredName() + ")" + json + ".getLong(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonFloat(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = (float)" + json + ".getDouble(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonBoolean(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from, boolean integer) {
        try {
            if (integer) to.addSourceDirectly(var + " = (" + json + ".getInt(\"" + ctr + "\") == 1) ? true : false;");

            else to.addSourceDirectly(var + " = " + json + ".getBoolean(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonUndef(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = " + json + ".getJSONObject(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonString(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            to.addSourceDirectly(var + " = " + json + ".getString(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonRef(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            String arrDeclClassname = getArrDeclClassname(from);
            if ("JSONArray".equals(arrDeclClassname) || "JSONObject".equals(arrDeclClassname))
                to.addSourceDirectly(var + " = " + json + ".get" + arrDeclClassname + "(\"" + ctr + "\");");
            else
                to.addSourceDirectly(var + " = new " + arrDeclClassname + "(" + json + ".get(\"" + ctr + "\"));");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindVarToJsonArray(String var, String ctr, String json, String type, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        try {
            varid++;
            to.addVar("int", "tmpJALen" + varid);
            to.addVar("JSONArray", "tmpJArr" + varid);
            to.addSourceDirectly("tmpJArr" + varid + " = " + json + ".getJSONArray(\"" + ctr + "\");");
            to.addSourceDirectly("tmpJALen" + varid + " = tmpJArr" + varid + ".length();");

            to.addSourceDirectly(var + " = new " + type + "(tmpJALen" + varid + ");");
            ContainerDef types = ((ArrayContainer) from).getTypes();
            to.addVar(getArrDeclClassname(types), "tmpJAtmp" + varid);
            int gg = varid;
            BlockStmt forr = to.makeFor("i" + gg, new IntegerLiteralExpr(0), new NameExpr("tmpJALen" + varid));
            bindVarToArrayContainer("tmpJAtmp" + gg, "i" + gg, "tmpJArr" + gg, cdecl, forr, types);
            forr.addSourceDirectly(var + ".add(tmpJAtmp" + gg + ");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindFieldToJsonInt(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        cdecl.addField("int", flagAddInit, var);
        try {
            cdecl.addGetter(var);
            cdecl.addSetter(var);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            to.addSourceDirectly("this." + var + " = " + json + ".getInt(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindFieldToJsonBoolean(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        cdecl.addField("boolean", flagAddInit, var);
        try {
            cdecl.addGetter(var);
            cdecl.addSetter(var);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            to.addSourceDirectly("this." + var + " = " + json + ".getBoolean(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindFieldToJsonString(String var, String ctr, String json, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        cdecl.addField("String", flagAddInit, var);
        try {
            cdecl.addGetter(var);
            cdecl.addSetter(var);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            to.addSourceDirectly("this." + var + " = " + json + ".getString(\"" + ctr + "\");");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void bindFieldToJsonArray(String var, String ctr, String json, String type, ClassOrInterfaceDeclaration cdecl, BlockStmt to, ContainerDef from) {
        cdecl.addField("ArrayList<" + type + ">", flagAddInit, var);

        try {
            cdecl.addGetter(var);
            cdecl.addSetter(var);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            to = to.makeBlock();

            to.addVar("int", "len");
            to.addVar("JSONArray", "arr");
            to.addSourceDirectly("arr = " + json + ".getJSONArray(\"" + ctr + "\");");
            to.addSourceDirectly("len = arr.length();");
            to.addSourceDirectly("this." + var + " = new ArrayList<" + type + ">(len);");
            to.addVar("ArrayList<" + type + ">", "list");
            BlockStmt forr = to.makeFor("i", new IntegerLiteralExpr(0), new NameExpr("len"));
            switch (type) {

                case "int":
                    forr.addSourceDirectly("list.add(arr.getInt(i));");
                    break;
                case "float":
                    forr.addSourceDirectly("list.add(arr.getDouble(i));");
                    break;
                case "boolean":
                    forr.addSourceDirectly("list.add(arr.getBoolean(i));");
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getArgClassname(ContainerDef forr) {
        switch (forr.getType().getName()) {

            case "integer":
                return "int";
            case "reference":
            case "object":
                return "String";
            case "array":
                return "String";
            case "number":
            case "float":
                return "float";
            case "string":
                return "String";
            default:
                return forr.getType().getName();
        }
    }

    public ObjectDef resolveReference(ObjectRef reference) {
        String ref = reference.getName();
        if (ref.startsWith("objects.json#/definitions")) {
            return dom.getObjects().get(ref.substring(26));
        } else log("FAILED TO RESOLVE LINK " + ref);
        return null;
    }

    /*
     public String getImplClassname(ContainerDef forr) {
     String name = forr.getType().getName();
     return getImplClassname(name);
     }

     public String getImplClassname(String name) {
     switch (name) {

     case "integer":
     return "int";
     case "reference":
     case "object":
     return "JSONObject";
     case "array":
     return "JSONArray";
     case "number":
     case "float":
     return "float";
     case "boolean":
     return "boolean";
     case "string":
     return "String";
     default:
     return name;
     }
     }
     */
    public String getArrDeclClassname(ContainerDef forr) throws ParseException {
        if (forr == null) throw new ParseException();
        switch (forr.getType().getName()) {

            case "integer":
                return ((IntegerContainer) forr).getPoweredName();
            case "reference":
                String refRoute = getRefRoute(((ObjectRef) forr).getName());
                if (refRoute.endsWith("Boolean") || refRoute.endsWith("BaseBoolInt")) return "boolean";
                if (refRoute.endsWith("JSONObject") || refRoute.endsWith("JObj")) return "JSONObject";
                if (refRoute.endsWith("JSONArray") || refRoute.endsWith("JArr")) return "JSONArray";
                return basepackage + "." + refRoute;
            case "object":
                return basepackage + "." + getObjectRoute(((ObjectContainer) forr).getName());
            case "array":
                return "ArrayList<" + getArrDeclClassNonPrimitive(((ArrayContainer) forr).getTypes()) + ">";
            case "number":
            case "float":
                return "float";
            case "string":
                return "String";
            case "undefinedobject":
                return "JSONObject";
            default:
                return forr.getType().getName();
        }
    }

    public String getArrDeclClassNonPrimitive(ContainerDef forr) throws ParseException {
        if (forr == null) throw new ParseException();

        switch (forr.getType().getName()) {

            case "integer":
                return "Integer";
            case "reference":
                String refRoute = getRefRoute(((ObjectRef) forr).getName());
                if (refRoute.endsWith("Boolean") || refRoute.endsWith("BaseBoolInt")) return "Boolean";
                return basepackage + "." + refRoute;
            case "object":
                return basepackage + "." + getObjectRoute(((ObjectContainer) forr).getName());
            case "array":
                return "ArrayList<" + getArrDeclClassNonPrimitive(((ArrayContainer) forr).getTypes()) + ">";
            case "number":
            case "float":
                return "Float";
            case "string":
                return "String";
            case "undefinedobject":
                return "JSONObject";
            default:
                return forr.getType().getName();
        }
    }

    public String getObjectRoute(String name) {
        return "objects." + capitaliseAll(name);
    }

    public String getResponseRoute(String name) {
        return "responses." + capitaliseAll(name);
    }

    public String getMethodRoute(String name) {
        return "method." + capitaliseAll(name);
    }

    public String getErrorRoute(String name) {
        return "errors." + capitaliseAll(name);
    }

    public String getRefRoute(String ref) {
        if (ref.startsWith("objects.json#/definitions")) {
            return getObjectRoute(ref.substring(26));
        } else if (ref.startsWith("methods.json#/definitions")) {
            return getMethodRoute(ref.substring(26));
        } else if (ref.startsWith("responses.json#/definitions")) {
            return getResponseRoute(ref.substring(28));
        } else if (ref.startsWith("errors.json#/definitions")) {
            return getErrorRoute(ref.substring(25));
        } else if (ref.startsWith("#/definitions")) {
            return "UnknownObject";
        } else return "UNDEFINED REFERENCE TYPE " + ref;
    }

    public String capitaliseAll(String name) {
        StringBuilder sb = new StringBuilder(name.length());
        char[] from = name.toCharArray();
        boolean cap = true;
        for (char src : from) {
            if (src == '_') {
                cap = true;
                continue;
            }
            if (cap) {
                sb.append(Character.toUpperCase(src));
                cap = false;
            } else {
                sb.append(src);
            }
        }
        return sb.toString();
    }

    public String log(String l) {
        System.out.println("[JavaServerGenerator] " + l);
        return l;
    }

    @Override
    public TypeDef getTypeFromName(String name) {
        return new TypeDef(name);
    }

    @Override
    public ContainerDef getContainerFromName(String name) {
        return null;
    }
}
