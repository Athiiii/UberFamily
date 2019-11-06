package com.example.uberfamiliy.model;

import java.util.ArrayList;
import java.util.List;

public class CreateUsersForTesting {
    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setUsername("severin");
        user.setPassword("1234");
        user.setFullName("Severin Hersche");
        user.setDriver(false);
        user.setId(2);

        User user1 = new User();
        user1.setUsername("athi");
        user1.setPassword("1234");
        user1.setFullName("Athi Theivi");
        user1.setDriver(true);
        user1.setId(3);

        User user2 = new User();
        user2.setUsername("umui");
        user2.setPassword("1234");
        user2.setFullName("umut Ötzi");
        user2.setDriver(true);
        user2.setId(4);

        User user3 = new User();
        user3.setUsername("JEanimani");
        user3.setPassword("1234");
        user3.setFullName("JEan Schaad");
        user3.setDriver(true);
        user3.setId(5);


        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

    public User getUser(boolean driver) {
        User user = new User();
        user.setUsername("severin");
        user.setPassword("1234");
        user.setFullName("Severin Hersche");
        user.setDriver(driver);
        user.setId(1);
        return user;
    }
}
