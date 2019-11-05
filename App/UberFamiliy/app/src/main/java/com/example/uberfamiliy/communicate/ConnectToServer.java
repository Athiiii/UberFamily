package com.example.uberfamiliy.communicate;

import com.example.uberfamiliy.bean.User;

public interface ConnectToServer {
    boolean pickMeUp(int id);
    int register(User user);
}
