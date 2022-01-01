package com.freedomcoder.apigen.iface;

import java.util.HashMap;

public class SchemaDom {

    HashMap<String, ErrorDef> errors = new HashMap<String, ErrorDef>();
    HashMap<String, ObjectDef> objects = new HashMap<String, ObjectDef>();
    HashMap<String, MethodDef> methods = new HashMap<String, MethodDef>();
    HashMap<String, ResponseDef> responses = new HashMap<String, ResponseDef>();

    public HashMap<String, ErrorDef> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, ErrorDef> errors) {
        this.errors = errors;
    }

    public HashMap<String, ObjectDef> getObjects() {
        return objects;
    }

    public void setObjects(HashMap<String, ObjectDef> objects) {
        this.objects = objects;
    }

    public HashMap<String, MethodDef> getMethods() {
        return methods;
    }

    public void setMethods(HashMap<String, MethodDef> methods) {
        this.methods = methods;
    }

    public HashMap<String, ResponseDef> getResponses() {
        return responses;
    }

    public void setResponses(HashMap<String, ResponseDef> responses) {
        this.responses = responses;
    }
}
