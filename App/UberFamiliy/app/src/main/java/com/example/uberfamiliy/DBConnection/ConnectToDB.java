package com.example.uberfamiliy.DBConnection;

import com.example.uberfamiliy.model.ChatMessage;
import com.example.uberfamiliy.model.Friend;
import com.example.uberfamiliy.model.Request;
import com.example.uberfamiliy.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ConnectToDB implements IConnectToDB {

    private static IConnectToDB connectToAPI;
    private static String domain = "https://memecreator.io/";

    // Request Types
    private static final String DELETE = "DELETE";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";

    public static IConnectToDB getInstance() {
        if (connectToAPI == null) {
            connectToAPI = new ConnectToDB();
        }
        return connectToAPI;
    }

    private ConnectToDB() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    @Override
    public List<Friend> getFriends(Long userId) {
        return null;
    }

    @Override
    public Friend sendFriendRequest(Long userId, Long friendId) {
        return null;
    }

    @Override
    public boolean acceptFriendship(boolean accepted, Long friendshipId) {
        return false;
    }

    @Override
    public boolean removeFriend(Long friendshipId) {
        return false;
    }

    @Override
    public List<ChatMessage> getMessages(Long requestId) {
        return null;
    }

    @Override
    public boolean sendMessage(ChatMessage message) {
        return false;
    }

    @Override
    public List<Request> getRequests() {
        return null;
    }

    @Override
    public List<Request> getOpenRequest() {
        return null;
    }

    @Override
    public boolean closeRequest(Long requestId) {
        return false;
    }

    @Override
    public Request createRequest(Long userId, String adress) {
        return null;
    }

    @Override
    public Request acceptRequest(Long requestId, Long userId) {
        return null;
    }

    @Override
    public boolean createUser(User user) {
        
        return false;
    }

    @Override
    public User verifyUser(String username, String password) {
        User user = null;
        JSONArray jsonObject = connect(POST, "api/User/verify", "username=" + username + "&password=" + password + "");
        if(jsonObject != null) {
            try {
                JSONObject object = jsonObject.getJSONObject(0);
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
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = null;

        JSONArray jsonObjects = connect(GET, "api/User");
        if(jsonObjects != null) {
            users = new ArrayList<>();
            try {
                for (int i = 0; i < jsonObjects.length(); ++i) {
                    User user = new User();
                    JSONObject object = jsonObjects.getJSONObject(i);
                    user.setId(object.getInt("id"));
                    user.setFullName(object.getString("fullname"));
                    user.setUsername(object.getString("username"));
                    user.setPassword(object.getString("password"));
                    user.setDriver(object.getInt("isDriver") == 1);
                    user.setImage(object.getString("picture"));
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    private JSONArray connect(String type, String apiDomain, String body) {
        JSONArray output = null;
        BufferedReader br = null;
        String response = "";

        try {
            URL url = new URL(domain + apiDomain);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(type);
            conn.setRequestProperty("Accept", "application/json");

            if(body != null) {
                byte[] postData = body.getBytes( StandardCharsets.UTF_8 );
                conn.setRequestProperty( "Content-Length", Integer.toString( postData.length ));
                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                    wr.write( postData );
                }
            }
            if(conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = br.lines().collect(Collectors.joining());
                output = new JSONArray(response);
            }
            conn.disconnect();
        } catch (JSONException je) {
            try {
                output = new JSONArray("[" + response + "]");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return output;
    }

    private JSONArray connect(String type, String apiDomain) {
        return connect(type, apiDomain, null);
    }
}
