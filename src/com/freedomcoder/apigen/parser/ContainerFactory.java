package com.freedomcoder.apigen.parser;

import com.freedomcoder.apigen.iface.ContainerDef;
import com.freedomcoder.apigen.iface.ObjectRef;
import com.freedomcoder.apigen.iface.SchemaDom;
import com.freedomcoder.apigen.iface.containers.BooleanContainer;
import com.freedomcoder.apigen.iface.containers.IntegerContainer;
import com.freedomcoder.apigen.iface.containers.NumberContainer;
import com.freedomcoder.apigen.iface.containers.StringContainer;

public class ContainerFactory {

    public static ContainerDef makeContainer(SchemaDom dom, String type) {
        switch (type) {

            case "int64":
            case "int32":
            case "int16":
            case "int8":
            case "int":
                IntegerContainer integerContainer = new IntegerContainer(dom);
                if (type.length() > 3) {
                    // System.out.println(type.substring(3));
                    integerContainer.setPower(Integer.parseInt(type.substring(3)) / 8);
                    // System.out.println(type.substring(3) +" - "+integerContainer.getPower()+" - "+integerContainer.getPoweredName());
                }
                return integerContainer;
            case "string":
                return new StringContainer(dom);
            case "float":
            case "double":
                return new NumberContainer(dom);
            case "boolean":
            case "bool":
                return new BooleanContainer(dom);
            default:
                ObjectRef objectRef = new ObjectRef(dom, "objects.json#/definitions/" + type);
                return objectRef;
        }
    }
}
