package com.example.uberfamiliy.model;

import androidx.annotation.Nullable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

public class User extends SugarRecord implements Serializable {
    private Long userId;
    private String username;
    private String password;
    private boolean remembered;
    @Ignore
    private String fullName;
    @Ignore
    private boolean isDriver;
    @Ignore
    private String image;

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getUserId() == ((User) obj).getUserId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isRemembered() {
        return remembered;
    }

    public void setRemembered(boolean remembered) {
        this.remembered = remembered;
    }

    @Override
    public String toString() {
        return getUsername();
    }
    
}
