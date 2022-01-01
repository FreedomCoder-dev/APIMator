package com.freedomcoder.apigen.iface.containers;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.SchemaDom;
import com.freedomcoder.apigen.iface.constraints.Constraint;

import java.util.ArrayList;

public class BooleanContainer extends ContainerDef {

    public Boolean defaultValue;
    ArrayList<Constraint> constraints = new ArrayList<Constraint>();

    public BooleanContainer(SchemaDom c) {
        super(c);
        setType(getTypeFromName("boolean"));
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }
}
