package com.freedomcoder.apigen.iface.containers;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.ObjectDef;
import com.freedomcoder.apigen.iface.ObjectRef;
import com.freedomcoder.apigen.iface.SchemaDom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ObjectContainer extends ContainerDef {

    HashMap<String, ContainerDef> properties = new HashMap<String, ContainerDef>();
    HashSet<String> required = new HashSet<String>();
    ArrayList<ObjectRef> mergeFrom = new ArrayList<ObjectRef>();
    String name;

    public ObjectContainer(SchemaDom c) {
        super(c);
        setType(getTypeFromName("object"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ObjectRef> getMergeFrom() {
        return mergeFrom;
    }

    public void setMergeFrom(ArrayList<ObjectRef> mergeFrom) {
        this.mergeFrom = mergeFrom;
    }

    public HashSet<String> getRequired() {
        return required;
    }

    public void setRequired(HashSet<String> required) {
        this.required = required;
    }

    public void setRequired(String name, boolean enable) {
        if (enable) {
            required.add(name);
            ContainerDef container = properties.get(name);
            if (container != null) {
                container.setRequired(true);
            }
        } else {
            required.remove(name);
            ContainerDef container = properties.get(name);
            if (container != null) {
                container.setRequired(false);
            }
        }
    }

    public ObjectDef resolveReference(ObjectRef reference) {
        String ref = reference.getName();
        if (ref.startsWith("objects.json#/definitions")) {
            String substring = ref.substring(26);

            return getParent().getObjects().get(substring);
        }

        System.out.println("FAILED TO RESOLVE LINK " + ref);
        return null;
    }

    public HashMap<String, ContainerDef> getProperties() {
        HashMap<String, ContainerDef> props = null;
        if (mergeFrom != null && getMergeFrom().size() > 0) {
            props = new HashMap<String, ContainerDef>(properties);
            for (ObjectRef ref : getMergeFrom()) {
                ObjectDef def = resolveReference(ref);
                if (def == null) System.out.println(ref.getName());
                ContainerDef params = def.getParams();
                if (params instanceof ObjectContainer) {
                    HashMap<String, ContainerDef> propert = ((ObjectContainer) params).getProperties();
                    if (propert != null) props.putAll(propert);
                }
            }
        } else props = properties;
        return props;
    }

    public void setProperties(HashMap<String, ContainerDef> properties) {
        this.properties = properties;
    }
}
