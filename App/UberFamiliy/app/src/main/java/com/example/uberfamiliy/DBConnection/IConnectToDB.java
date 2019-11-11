package com.example.uberfamiliy.DBConnection;

import com.example.uberfamiliy.model.ChatMessage;
import com.example.uberfamiliy.model.User;

public interface IConnectToDB {
    // Friend API
    void getFriends(Long userId, CallAPIResponse callAPIResponse);

    void sendFriendRequest(Long userId, Long friendId, CallAPIResponse callAPIResponse);

    void acceptFriendship(boolean accepted, Long friendshipId, CallAPIResponse callAPIResponse);

    void removeFriend(Long friendshipId, CallAPIResponse callAPIResponse);

    // Chat message API
    void getMessages(Long requestId, CallAPIResponse callAPIResponse);

    void sendMessage(ChatMessage message, CallAPIResponse callAPIResponse);

    // Request API
    void getRequests(CallAPIResponse callAPIResponse);

    void getOpenRequest(CallAPIResponse callAPIResponse);

    void closeRequest(Long requestId, CallAPIResponse callAPIResponse);

    void createRequest(Long userId, CallAPIResponse callAPIResponse, String adress);

    void acceptRequest(Long requestId, CallAPIResponse callAPIResponse, Long userId);

    void deleteRequest(Long requestId, CallAPIResponse callAPIResponse);

    void registerUser(User user, CallAPIResponse callAPIResponse);

    void verifyUser(String username, String password, CallAPIResponse callAPIResponse);

    void getUsers(CallAPIResponse callAPIResponse);

    void deleteUser(Long userId, CallAPIResponse callAPIResponse);
}
