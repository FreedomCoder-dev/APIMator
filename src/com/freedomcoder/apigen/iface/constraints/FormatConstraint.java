package com.freedomcoder.apigen.iface.constraints;

public class FormatConstraint extends Constraint {

    String format;

    public FormatConstraint(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
