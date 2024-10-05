package com.Employee;

import java.util.List;
import java.util.Map;

public class JSONArray {
    private List<Map<String, String>> list;

    public JSONArray(List<Map<String, String>> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder("[");
        for (Map<String, String> map : list) {
            if (json.length() > 1) json.append(",");
            json.append(new JSONObject().toString());
        }
        json.append("]");
        return json.toString();
    }
}
