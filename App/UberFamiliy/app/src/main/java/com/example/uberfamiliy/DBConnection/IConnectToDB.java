package com.example.uberfamiliy.DBConnection;

import com.example.uberfamiliy.model.ChatMessage;
import com.example.uberfamiliy.model.Friend;
import com.example.uberfamiliy.model.Request;
import com.example.uberfamiliy.model.User;

import java.util.List;

public interface IConnectToDB {
    // Friend API
    List<Friend> getFriends(Long userId);
    Friend sendFriendRequest(Long userId, Long friendId);
    boolean acceptFriendship(boolean accepted, Long friendshipId);
    boolean removeFriend(Long friendshipId);

    // Chat message API
    List<ChatMessage> getMessages(Long requestId);
    boolean sendMessage(ChatMessage message);

    // Request API
    List<Request> getRequests();
    List<Request> getOpenRequest();
    boolean closeRequest(Long requestId);
    Request createRequest(Long userId, String adress);
    Request acceptRequest(Long requestId, Long userId);

    boolean createUser(User user);
    User VerifyUser(String username, String password);
    List<User> getUsers();
}
