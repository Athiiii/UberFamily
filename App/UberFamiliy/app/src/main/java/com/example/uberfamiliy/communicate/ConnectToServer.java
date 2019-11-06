package com.example.uberfamiliy.communicate;

import com.example.uberfamiliy.model.User;

import java.util.List;

public interface ConnectToServer {
    boolean pickMeUp(int id);

    User register(User user);

    User login(String username, String password);

    User verifyUser();

    boolean PickMe();

    List<User> getAllUsers();

    boolean addFriend(long id, User friend);
}
