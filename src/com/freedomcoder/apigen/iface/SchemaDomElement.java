package com.freedomcoder.apigen.iface;

public abstract class SchemaDomElement {

    SchemaDom parent;

    public SchemaDomElement(SchemaDom parent) {
        this.parent = parent;
    }

    public SchemaDom getParent() {
        return parent;
    }

    public void setParent(SchemaDom parent) {
        this.parent = parent;
    }

    public TypeDef getTypeFromName(String name) {
        return new TypeDef(name);
    }
}
