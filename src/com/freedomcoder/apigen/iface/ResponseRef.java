package com.freedomcoder.apigen.iface;

public class ResponseRef extends SchemaDomElement {

    String name;

    public ResponseRef(SchemaDom c, String name) {
        super(c);
        this.name = name;
    }

    public ResponseRef(SchemaDom c) {
        super(c);
    }

    public ResponseRef(SchemaDom c, ResponseDef from) {
        super(c);
        this.name = from.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
