package com.freedomcoder.apigen.iface;

public class ObjectDef extends ObjectRef {

    ContainerDef params;

    public ObjectDef(SchemaDom c, String name) {
        super(c, name);
    }

    public ContainerDef getParams() {
        return params;
    }

    public ObjectDef setParams(ContainerDef params) {
        this.params = params;
        return this;
    }
}
