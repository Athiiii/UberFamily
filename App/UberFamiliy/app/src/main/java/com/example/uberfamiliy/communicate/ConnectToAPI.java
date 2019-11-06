package com.example.uberfamiliy.communicate;

import com.example.uberfamiliy.model.CreateUsersForTesting;
import com.example.uberfamiliy.model.User;

import java.util.List;

public class ConnectToAPI implements ConnectToServer {
    private static ConnectToAPI connectToAPI;

    public static ConnectToServer getInstance() {
        if (connectToAPI == null) {
            connectToAPI = new ConnectToAPI();
        }
        return connectToAPI;
    }

    @Override
    public boolean pickMeUp(int id) {
        return false;
    }

    @Override
    public User register(User user) {
        return null;
    }

    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public User verifyUser() {
        return new User();
    }

    @Override
    public boolean PickMe() {
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return CreateUsersForTesting.getUsers();
    }

    @Override
    public boolean addFriend(long id, User friend) {

        return false;
    }
}
