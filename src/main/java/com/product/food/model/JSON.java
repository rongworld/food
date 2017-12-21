package com.product.food.model;

import org.json.JSONException;
import org.json.JSONObject;

public class JSON implements java.io.Serializable{
    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    private JSONObject jsonObject;

    public JSON(Integer code, String message) {
         jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setKeyAndValue(String key, Object value) {
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getJSON() {
        return jsonObject.toString().replace("\\","");
    }
}
