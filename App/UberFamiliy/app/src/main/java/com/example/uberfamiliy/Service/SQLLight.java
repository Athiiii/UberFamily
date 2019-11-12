package com.example.uberfamiliy.Service;

import com.example.uberfamiliy.model.User;

import java.util.List;

public class SQLLight {
    private static SQLLight instance;
    private Long id;

    public static SQLLight getInstance() {
        if (instance == null) {
            instance = new SQLLight();
        }
        return instance;
    }

    public Long getId() {
        if (id == null) {
            User user = getFirstUser();
            id = user.getId();
        }
        return id;
    }

    public User getFirstUser() {
        List<User> users = User.listAll(User.class);
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }
        return user;
    }
}
