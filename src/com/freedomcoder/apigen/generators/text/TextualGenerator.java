package com.freedomcoder.apigen.generators.text;

import com.freedomcoder.apigen.generators.GeneratorAccepts;
import com.freedomcoder.apigen.iface.*;
import com.freedomcoder.apigen.iface.containers.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

public class TextualGenerator extends ContextWrapper implements GeneratorAccepts<SchemaDom> {

    File workdir;

    public TextualGenerator(LangContext context) {
        super(context);
    }

    @Override
    public void setSettings(JSONObject settings) {
        try {
            workdir = new File(getHomeDir(), settings.getString("rel.dir"));
        } catch (JSONException e) {
        }
    }

    @Override
    public boolean generate(SchemaDom dom) {
        File tg = new File(workdir, "dump.txt");

        try {
            PrintWriter p = new PrintWriter(new FileWriter(tg));
            p.println("# FAPI Codegen scheme dump");
            p.println("# Project Name: " + getApiName());
            {
                p.println();
                p.println("***************************************************");
                p.println("# Dump interface objects");
                p.println("***************************************************");
                p.println();
                Set<HashMap.Entry<String, ObjectDef>> entrySet = dom.getObjects().entrySet();
                for (HashMap.Entry<String, ObjectDef> obj : entrySet) {
                    p.println();
                    p.print("OBJECT ");
                    p.print(obj.getKey());
                    p.println(": ");

                    ContainerDef params = obj.getValue().getParams();
                    p.print(getContainerString(params));
                    p.println();
                    p.print("#END ");
                    p.println(obj.getKey());
                }
            }
            p.flush();
            {
                p.println();
                p.println("***************************************************");
                p.println("# Dump interface methods");
                p.println("***************************************************");
                p.println();
                Set<HashMap.Entry<String, MethodDef>> entrySet = dom.getMethods().entrySet();
                for (HashMap.Entry<String, MethodDef> obj : entrySet) {
                    p.println();
                    p.print("METHOD ");
                    p.print(obj.getKey());
                    p.println(": ");

                    HashMap<String, ContainerDef> params = obj.getValue().getParams();
                    if (params != null) {
                        Set<HashMap.Entry<String, ContainerDef>> entrySet2 = params.entrySet();
                        for (HashMap.Entry<String, ContainerDef> obj2 : entrySet2) {
                            p.print(obj2.getKey());
                            p.print(':');
                            p.print(getContainerString(obj2.getValue()));
                            p.print(',');
                        }
                    }
                    p.println();
                    p.print("#END ");
                    p.println(obj.getKey());
                }
            }
            p.flush();
            {
                p.println();
                p.println("***************************************************");
                p.println("# Dump interface responses");
                p.println("***************************************************");
                p.println();
                Set<HashMap.Entry<String, ResponseDef>> entrySet = dom.getResponses().entrySet();
                for (HashMap.Entry<String, ResponseDef> obj : entrySet) {
                    p.println();
                    p.print("RESPONSE ");
                    p.print(obj.getKey());
                    p.println(": ");

                    ContainerDef params = obj.getValue().getParams();
                    p.print(getContainerString(params));
                    p.println();
                    p.print("#END ");
                    p.println(obj.getKey());
                }
            }
            p.flush();
            {
                p.println();
                p.println("***************************************************");
                p.println("# Dump interface errors");
                p.println("***************************************************");
                p.println();
                Set<HashMap.Entry<String, ErrorDef>> entrySet = dom.getErrors().entrySet();
                for (HashMap.Entry<String, ErrorDef> obj : entrySet) {

                    p.print("API ERROR ");
                    p.print(obj.getKey());
                    p.print(": ");

                    ErrorDef params = obj.getValue();
                    p.print(params.getCode());
                    p.print(",");
                    p.print(params.getDescription());
                    p.println();

                }
            }

            p.println();
            p.println("#DONE REPORT");
            p.flush();
            p.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getContainerString(ContainerDef def) {
        if (def instanceof ObjectContainer) {
            return getObjectContainerString((ObjectContainer) def);
        } else if (def instanceof ArrayContainer) {
            return getArrayContainerString((ArrayContainer) def);
        } else if (def instanceof IntegerContainer) {
            return getIntegerContainerString((IntegerContainer) def);
        } else if (def instanceof NumberContainer) {
            return getNumberContainerString((NumberContainer) def);
        } else if (def instanceof StringContainer) {
            return getStringContainerString((StringContainer) def);
        } else if (def instanceof ObjectRef) {
            return getRefString((ObjectRef) def);
        }
        return "null";
    }

    public String getRefString(ObjectRef obj) {
        return "[reference " + obj.getName() + "]";
    }

    public String getStringContainerString(StringContainer obj) {
        return "string";
    }

    public String getIntegerContainerString(IntegerContainer obj) {
        return "int";
    }

    public String getNumberContainerString(NumberContainer obj) {
        return "number";
    }

    public String getArrayContainerString(ArrayContainer obj) {
        return "array of " + obj.getTypes();
    }

    public String getObjectContainerString(ObjectContainer obj) {
        StringBuilder ret = new StringBuilder("object {\n");
        for (HashMap.Entry<String, ContainerDef> entry : obj.getProperties().entrySet()) {
            ret.append("	");
            ret.append(entry.getKey());
            ret.append(": ");
            ret.append(getContainerString(entry.getValue()).replaceAll("\n", "\n    "));
        }
        ret.append("}");
        return ret.toString();
    }

    @Override
    public TypeDef getTypeFromName(String name) {
        return new TypeDef(name);
    }

}
