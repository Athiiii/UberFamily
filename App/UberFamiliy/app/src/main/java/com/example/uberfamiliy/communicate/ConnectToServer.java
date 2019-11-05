package com.example.uberfamiliy.communicate;

import com.example.uberfamiliy.model.User;

public interface ConnectToServer {
    boolean pickMeUp(int id);

    User register(User user);

    User login(String username, String password);

    User verifyUser();

    boolean PickMe();
}
