package com.freedomcoder.apigen.iface;

public class ObjectRef extends ContainerDef {

    String name;
    ContainerDef container;

    public ObjectRef(SchemaDom ctx, String name) {
        super(ctx);
        this.name = name;
        setType(getTypeFromName("reference"));
    }

    public ContainerDef getContainer() {
        return container;
    }

    public void setContainer(ContainerDef container) {
        this.container = container;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
