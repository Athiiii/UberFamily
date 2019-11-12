package com.example.uberfamiliy.DBConnection;

import com.example.uberfamiliy.model.ChatMessage;
import com.example.uberfamiliy.model.Request;
import com.example.uberfamiliy.model.User;

import java.util.List;

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

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    @Override
    public void getFriends(Long userId, CallAPIResponse callAPIResponse) {
        String query = "userId=" + userId;
        connect(callAPIResponse, GET, "api/Friend?" + query);
    }

    @Override
    public void getApprovedFriends(Long userId, CallAPIResponse callAPIResponse) {
        String query = "userId=" + userId;
        connect(callAPIResponse, GET, "api/User/approvedFriends?" + query);
    }

    @Override
    public void getAllFriends(Long userId, CallAPIResponse callAPIResponse) {
        String query = "userId=" + userId;
        connect(callAPIResponse, GET, "api/User/allFriends?" + query);
    }

    @Override
    public void sendFriendRequest(Long userId, Long friendId, CallAPIResponse callAPIResponse) {
        String query = "userId=" + userId + "&friendId=" + friendId;
        connect(callAPIResponse, POST, "api/Friend", query);

        /*if (response != null) {
            try {
                JSONObject object = response.getJSONObject(0);
                friend = new Friend();
                friend.setApproved(object.getInt("approved") == 1);
                friend.setUserId(object.getLong("id"));
                friend.setFirstFriend(object.getLong("firstFriend"));
                friend.setSecondFriend(object.getLong("secondFriend"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return friend;*/
    }

    @Override
    public void acceptFriendship(boolean accepted, Long friendshipId, CallAPIResponse callAPIResponse) {
        int approved = accepted ? 1 : 0;
        String query = "friendshipId=" + friendshipId + "&approved=" + approved;
        connect(callAPIResponse, PUT, "api/Friend", query);
    }

    @Override
    public void removeFriend(Long friendshipId, CallAPIResponse callAPIResponse) {
        String query = "friendId=" + friendshipId;
        connect(callAPIResponse, DELETE, "api/Friend", query);
    }

    @Override
    public void getMessages(Long requestId, CallAPIResponse callAPIResponse) {
        String query = "requestId=" + requestId;
        connect(callAPIResponse, GET, "api/Message?" + query);
    }

    @Override
    public void sendMessage(ChatMessage message, CallAPIResponse callAPIResponse) {
        String query = "writer=" + message.getWriter() + "&requestId=" + message.getRequestId()
                + "&message=" + message.getMessage();
        connect(callAPIResponse, POST, "api/Message", query);
    }

    @Override
    public void getRequests(CallAPIResponse callAPIResponse) {
        List<Request> requests = null;
        connect(callAPIResponse, GET, "api/Request");

        /*if (response != null) {
            requests = new ArrayList<>();
            for (int i = 0; i < response.length(); ++i) {
                try {
                    Request request = new Request();
                    JSONObject object = response.getJSONObject(i);
                    request.setUserId(object.getLong("id"));
                    request.setRequester(object.getLong("requester"));

                    Object driver = object.getString("driver");
                    if (driver != null && !driver.equals("null"))
                        request.setDriver(object.getLong("driver"));

                    request.setOpen(object.getInt("open") == 1);
                    request.setAdress(object.getString("adress"));
                    requests.add(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return requests;*/
    }

    @Override
    public void getOpenRequest(CallAPIResponse callAPIResponse) {
        List<Request> requests = null;
        connect(callAPIResponse, GET, "api/Request/open");

        /*if (response != null) {
            requests = new ArrayList<>();
            try {
                for (int i = 0; i < response.length(); ++i) {
                    Request request = new Request();
                    JSONObject object = response.getJSONObject(i);
                    request.setUserId(object.getLong("id"));
                    request.setRequester(object.getLong("requester"));

                    Object driver = object.getString("driver");
                    if (driver != null && !driver.equals("null"))
                        request.setDriver(object.getLong("driver"));

                    request.setOpen(object.getInt("open") == 1);
                    request.setAdress(object.getString("adress"));
                    requests.add(request);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return requests;*/
    }

    @Override
    public void closeRequest(Long requestId, CallAPIResponse callAPIResponse) {
        String query = "requestId" + requestId;
        connect(callAPIResponse, PUT, "api/Request", query);
    }

    @Override
    public void createRequest(Long userId, String address, CallAPIResponse callAPIResponse) {
        String query = "userId=" + userId + "&adress=" + address;
        connect(callAPIResponse, POST, "api/Request", query);
    }

    @Override
    public void acceptRequest(Long requestId, Long userId, CallAPIResponse callAPIResponse) {
        Request request = null;
        String query = "requestId=" + requestId + "&userId=" + userId;
        connect(callAPIResponse, POST, "api/Request/driver", query);

        /*if (response != null) {
            try {
                JSONObject object = response.getJSONObject(0);
                request = new Request();
                request.setAdress(object.getString("adress"));
                request.setDriver(object.getLong("driver"));
                request.setUserId(object.getLong("id"));
                request.setRequester(object.getLong("id"));
                request.setOpen(object.getInt("open") == 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return request;*/
    }

    @Override
    public void deleteRequest(Long requestId, CallAPIResponse callAPIResponse) {
        String query = "requestId=" + requestId;
        connect(callAPIResponse, DELETE, "api/Request", query);
    }

    @Override
    public void registerUser(User user, CallAPIResponse callAPIResponse) {
        int driver = 0;
        if (user.isDriver()) {
            driver = 1;
        }
        String query = "fullname=" + user.getFullName() + "&username=" + user.getUsername()
                + "&password=" + user.getPassword() + "&isDriver=" + driver + "&picture=" + user.getImage() + "";
        connect(callAPIResponse, POST, "api/User", query);
    }

    @Override
    public void verifyUser(String username, String password, CallAPIResponse callAPIResponse) {
        String query = "username=" + username + "&password=" + password;
        connect(callAPIResponse, POST, "api/User/verify", query);
    }

    @Override
    public void getUsers(CallAPIResponse callAPIResponse) {
        List<User> users = null;

        connect(callAPIResponse, GET, "api/User");
        /*if (jsonObjects != null) {
            users = new ArrayList<>();
            try {
                for (int i = 0; i < jsonObjects.length(); ++i) {
                    User user = new User();
                    JSONObject object = jsonObjects.getJSONObject(i);
                    user.setUserId(object.getInt("id"));
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
        return users;*/
    }

    @Override
    public void deleteUser(Long userId, CallAPIResponse callAPIResponse) {
        String query = "userId=" + userId;
        connect(callAPIResponse, DELETE, "api/User", query);
    }

    private void connect(CallAPIResponse callAPIResponse, String type, String apiDomain, String body) {
        new CallAPI(body, type, callAPIResponse).execute(domain + apiDomain);
    }

    private void connect(CallAPIResponse callAPIResponse, String type, String apiDomain) {
        connect(callAPIResponse, type, apiDomain, null);
    }
}
