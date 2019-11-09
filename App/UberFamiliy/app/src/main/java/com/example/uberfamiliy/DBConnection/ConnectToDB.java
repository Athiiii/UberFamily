package com.example.uberfamiliy.DBConnection;

import com.example.uberfamiliy.model.User;

public interface ConnectToDB {
    // Friend API


    boolean register(User user);
    int getId();
}
