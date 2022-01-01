package com.freedomcoder.apigen.iface;

import java.util.HashMap;

public class MethodDef extends SchemaDomElement {

    String name;
    HashMap<String, ContainerDef> params;
    HashMap<String, ResponseRef> responses = new HashMap<String, ResponseRef>();

    public MethodDef(SchemaDom c, String name) {
        super(c);
        this.name = name;
    }

    public HashMap<String, ResponseRef> getMethResponses() {
        return responses;
    }

    public void setMethResponses(HashMap<String, ResponseRef> responses) {
        this.responses = responses;
    }

    public void addResponse(String nxt, ResponseRef responseRef) {
        responses.put(nxt, responseRef);
    }

    public HashMap<String, ContainerDef> getParams() {
        return params;
    }

    public void setParams(HashMap<String, ContainerDef> params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
