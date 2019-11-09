package com.example.uberfamiliy.DBConnection;

import com.example.uberfamiliy.model.ChatMessage;
import com.example.uberfamiliy.model.Friend;
import com.example.uberfamiliy.model.Request;
import com.example.uberfamiliy.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
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
    public User VerifyUser(String username, String password) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = null;

        JSONObject response = connect("GET", "api/User");

        return users;
    }

    private JSONObject connect(String type, String apiDomain) {
        JSONObject json = null;

        try {
            URL url = new URL(domain + apiDomain);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if(conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output = br.lines().collect(Collectors.joining());
                json = new JSONObject(output);
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return json;
    }
}
