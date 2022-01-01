package com.freedomcoder.apigen.iface.containers;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.SchemaDom;
import com.freedomcoder.apigen.iface.constraints.Constraint;

import java.util.ArrayList;

public class IntegerContainer extends ContainerDef {

    public final static String[] javaNames = new String[]{"byte", "short", "", "int", "", "", "", "long"};
    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    int defaultValue;
    int power = 4;

    public IntegerContainer(SchemaDom c) {
        super(c);
        setType(getTypeFromName("integer"));
    }

    public String getPoweredName() {
        return javaNames[power - 1];
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }
}
