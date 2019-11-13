package com.example.uberfamiliy.Service;

import com.example.uberfamiliy.model.ChatMessage;
import com.example.uberfamiliy.model.Request;
import com.example.uberfamiliy.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts JSON to Objects
 */
public class ConvertJSON {
    private static ConvertJSON instance;

    private ConvertJSON() {

    }

    public static ConvertJSON getInstance() {
        if (instance == null) {
            instance = new ConvertJSON();
        }
        return instance;
    }

    public User toUser(String json) {
        User user = null;
        if (json != null) {
            JSONArray jsonArray = getJsonArray(json);

            if (jsonArray != null) {
                try {
                    JSONObject object = jsonArray.getJSONObject(0);
                    user = new User();
                    user.setUserId(object.getInt("id"));
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

    public List<User> toUsers(String json) {
        List<User> users = new ArrayList<>();
        if (json != null) {
            JSONArray jsonArray = getJsonArray(json);
            for (int i = 0; i < jsonArray.length(); ++i) {
                if (jsonArray != null) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        User user = new User();
                        user.setUserId(object.getInt("id"));
                        user.setFullName(object.getString("fullname"));
                        user.setUsername(object.getString("username"));
                        user.setPassword(object.getString("password"));
                        user.setDriver(object.getInt("isDriver") == 1);
                        user.setImage(object.getString("picture"));
                        users.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return users;
    }

    public List<User> toFriends(String json) {
        List<User> friends = new ArrayList<>();
        if (json != null) {
            JSONArray jsonArray = getJsonArray(json);
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    User friend = new User();
                    friend.setUserId(object.getInt("id"));
                    friend.setUsername(object.getString("username"));
                    friends.add(friend);
                    friend.setIp(object.getString("ipAddress"));
                    String port = object.getString("port");
                    if (!port.equals("null"))
                        friend.setPort(Integer.getInteger(port));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return friends;
    }

    public List<ChatMessage> toMessage(String json) {
        List<ChatMessage> messages = new ArrayList<>();
        if (json != null) {
            JSONArray jsonArray = getJsonArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); ++i) {
                    try {
                        ChatMessage message = new ChatMessage();
                        JSONObject object = jsonArray.getJSONObject(i);
                        message.setId(object.getLong("id"));
                        message.setWriter(object.getLong("writer"));
                        message.setRequestId(object.getLong("requestId"));
                        message.setMessage(object.getString("message"));
                        messages.add(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return messages;
    }

    public Request toRequest(String json) {
        Request request = null;
        if (json != null) {
            JSONArray jsonArray = getJsonArray(json);
            if (jsonArray != null) {
                try {
                    JSONObject object = jsonArray.getJSONObject(0);
                    request = new Request();
                    request.setAdress(object.getString("adress"));
                    request.setDriver(object.getLong("driver"));
                    request.setId(object.getLong("id"));
                    request.setRequester(object.getLong("id"));
                    request.setOpen(object.getInt("open") == 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return request;
    }

    public List<Request> toRequests(String json) {
        List<Request> requestList = new ArrayList<>();
        JSONArray responses = null;
        try {
            responses = new JSONArray(json);

            for (int i = 0; i < responses.length(); ++i) {
                Request request = new Request();
                JSONObject object = responses.getJSONObject(i);
                request.setId(object.getLong("id"));
                request.setRequester(object.getLong("requester"));

                String driver = object.getString("driver");
                if (!driver.equals("null"))
                    request.setDriver(object.getLong("driver"));

                request.setOpen(object.getInt("open") == 1);
                request.setAdress(object.getString("adress"));
                requestList.add(request);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestList;
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
