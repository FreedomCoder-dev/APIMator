package com.freedomcoder.apigen.iface;

public class ResponseDef extends ResponseRef {

    ContainerDef params;

    public ResponseDef(SchemaDom c, String name) {
        super(c);
        this.name = name;
    }

    public ContainerDef getParams() {
        return params;
    }

    public ResponseDef setParams(ContainerDef params) {
        this.params = params;
        return this;
    }
}
