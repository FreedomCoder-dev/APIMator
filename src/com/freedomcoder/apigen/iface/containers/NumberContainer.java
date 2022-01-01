package com.freedomcoder.apigen.iface.containers;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.SchemaDom;
import com.freedomcoder.apigen.iface.constraints.Constraint;

import java.util.ArrayList;

public class NumberContainer extends ContainerDef {

    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    double defaultValue;

    public NumberContainer(SchemaDom c) {
        super(c);
        setType(getTypeFromName("number"));
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }
}
