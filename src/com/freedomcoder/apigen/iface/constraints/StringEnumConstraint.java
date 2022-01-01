package com.freedomcoder.apigen.iface.constraints;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class StringEnumConstraint extends Constraint {
    ArrayList<String> enums = new ArrayList<String>();
    ArrayList<String> enumNames = new ArrayList<String>();

    public StringEnumConstraint(JSONArray names) {
        for (int i = 0; i < names.length(); i++) {
            try {
                enums.add(names.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public StringEnumConstraint(JSONArray ids, JSONArray names) {
        for (int i = 0; i < ids.length(); i++) {
            try {
                enums.add(ids.getString(i));
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

    public ArrayList<String> getEnumNames() {
        return enumNames;
    }

    public void setEnumNames(ArrayList<String> enumNames) {
        this.enumNames = enumNames;
    }

    public ArrayList<String> getEnum() {
        return enums;
    }

    public void setEnum(ArrayList<String> enums) {
        this.enums = enums;
    }


}
