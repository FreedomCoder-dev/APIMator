package com.freedomcoder.apigen.iface;

public class ErrorDef extends SchemaDomElement {

    String name;
    int code;
    String description;

    public ErrorDef(SchemaDom c) {
        super(c);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
