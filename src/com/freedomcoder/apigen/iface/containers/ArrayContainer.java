package com.freedomcoder.apigen.iface.containers;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.ObjectRef;
import com.freedomcoder.apigen.iface.SchemaDom;

import java.util.ArrayList;

public class ArrayContainer extends ContainerDef {

    ContainerDef types;
    ArrayList<ObjectRef> mergeFrom = new ArrayList<ObjectRef>();

    public ArrayContainer(SchemaDom context) {
        super(context);
        setType(getTypeFromName("array"));
    }

    public ArrayList<ObjectRef> getMergeFrom() {
        return mergeFrom;
    }

    public void setMergeFrom(ArrayList<ObjectRef> mergeFrom) {
        this.mergeFrom = mergeFrom;
    }

    public ContainerDef getTypes() {
        return types;
    }

    public void setTypes(ContainerDef types) {
        this.types = types;
    }
}
