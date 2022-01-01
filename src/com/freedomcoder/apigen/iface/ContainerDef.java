package com.freedomcoder.apigen.iface;

public abstract class ContainerDef extends SchemaDomElement {

    TypeDef type;
    EnumerationDef values;
    FormatDef format;
    boolean required;
    String description;
    boolean withoutRefs;

    public ContainerDef(SchemaDom context) {
        super(context);
    }

    public boolean isWithoutRefs() {
        return withoutRefs;
    }

    public void setWithoutRefs(boolean withoutRefs) {
        this.withoutRefs = withoutRefs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public FormatDef getFormat() {
        return format;
    }

    public void setFormat(FormatDef format) {
        this.format = format;
    }

    public EnumerationDef getValues() {
        return values;
    }

    public void setValues(EnumerationDef values) {
        this.values = values;
    }

    public TypeDef getType() {
        return type;
    }

    public void setType(TypeDef type) {
        this.type = type;
    }
}
