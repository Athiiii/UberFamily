package com.example.uberfamiliy.Service;

import com.example.uberfamiliy.model.User;

import java.util.List;

public class SQLLight {
    private static SQLLight instance;
    private Long id;
    private User user;

    private SQLLight() {

    }

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
        if (user == null) {
            List<User> users = User.listAll(User.class);
            if (users != null && users.size() > 0) {
                this.user = users.get(0);
            }
        }
        return user;
    }

    public void deleteAllFields() {
        User.deleteAll(User.class);
    }
}
