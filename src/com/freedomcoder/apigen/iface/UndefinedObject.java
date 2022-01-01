package com.freedomcoder.apigen.iface;

import com.freedomcoder.apigen.iface.containers.ObjectContainer;

public class UndefinedObject extends ObjectContainer {

    public UndefinedObject(SchemaDom c) {
        super(c);
        setType(getTypeFromName("undefinedobject"));
    }
}
