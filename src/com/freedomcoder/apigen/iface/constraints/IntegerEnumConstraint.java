package com.freedomcoder.apigen.iface.constraints;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class IntegerEnumConstraint extends Constraint {
    ArrayList<Integer> enums = new ArrayList<Integer>();
    ArrayList<String> enumNames = new ArrayList<String>();

    public IntegerEnumConstraint(JSONArray names) {
        for (int i = 0; i < names.length(); i++) {
            try {
                enums.add(names.getInt(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public IntegerEnumConstraint(JSONArray ids, JSONArray names) {
        for (int i = 0; i < ids.length(); i++) {
            try {
                enums.add(ids.getInt(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                enumNames.add(names.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Integer> getEnum() {
        return enums;
    }

    public void setEnum(ArrayList<Integer> enums) {
        this.enums = enums;
    }

    public ArrayList<String> getEnumNames() {
        return enumNames;
    }

    public void setEnumNames(ArrayList<String> enumNames) {
        this.enumNames = enumNames;
    }

}
