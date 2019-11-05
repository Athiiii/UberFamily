package com.example.uberfamiliy.communicate;

import com.example.uberfamiliy.bean.User;

public interface ConnectToServer {
    boolean pickMeUp(int id);
    String register(User user);
}
