package com.example.uberfamiliy.DBConnection;

import com.example.uberfamiliy.bean.User;

public interface ConnectToDB {
    boolean register(User user);
    int getId();
}
