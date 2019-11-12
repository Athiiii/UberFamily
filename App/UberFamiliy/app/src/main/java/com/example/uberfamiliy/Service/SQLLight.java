package com.example.uberfamiliy.Service;

import android.content.Context;

import com.example.uberfamiliy.model.User;
import com.orm.SugarContext;

import java.util.List;

public class SQLLight {
    private static SQLLight instance;
    private static Context context;
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

    public Long getId(Context context) {
        if (id == null) {
            User user = getFirstUser(context);
            id = user.getId();
        }
        return id;
    }

    public User getFirstUser(Context context) {
        SugarContext.init(context);
        if (user == null) {
            List<User> users = User.listAll(User.class);
            if (users != null && users.size() > 0) {
                this.user = users.get(0);
                System.out.println(user.getId());
                System.out.println(user.getUsername());
            }
        }
        return user;
    }

    public void deleteAllFields(Context context) {
        SugarContext.init(context);
        System.out.println("DELETE");
        User.deleteAll(User.class);
    }

    public void saveUser(Context context, User user) {
        SugarContext.init(context);
        user.save();
    }
}
