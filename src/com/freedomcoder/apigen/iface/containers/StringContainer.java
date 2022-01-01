package com.freedomcoder.apigen.iface.containers;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.SchemaDom;
import com.freedomcoder.apigen.iface.constraints.Constraint;

import java.util.ArrayList;

public class StringContainer extends ContainerDef {

    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    String defaultValue;

    public StringContainer(SchemaDom c) {
        super(c);
        setType(getTypeFromName("string"));
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }
}
