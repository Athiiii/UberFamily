package com.example.uberfamiliy.Helper;

import com.example.uberfamiliy.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConvertJSONToObject {
    private static ConvertJSONToObject instance;

    public static ConvertJSONToObject getInstance() {
        if (instance == null) {
            instance = new ConvertJSONToObject();
        }
        return instance;
    }

    public User convertJsonToUser(String output) {
        User user = null;
        if (output != null) {
            JSONArray jsonArray = getJsonArray(output);

            if (jsonArray != null) {
                try {
                    JSONObject object = jsonArray.getJSONObject(0);
                    user = new User();
                    user.setId(object.getInt("id"));
                    user.setFullName(object.getString("fullname"));
                    user.setUsername(object.getString("username"));
                    user.setPassword(object.getString("password"));
                    user.setDriver(object.getInt("isDriver") == 1);
                    user.setImage(object.getString("picture"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }


    private JSONArray getJsonArray(String output) {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(output);

        } catch (JSONException e) {
            try {
                jsonArray = new JSONArray("[" + output + "]");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonArray;
    }
}
