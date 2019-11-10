package com.example.uberfamiliy.Test;

import com.example.uberfamiliy.DBConnection.*;

import org.junit.Assert;
import org.junit.Test;

public class ConnectToDBTest {
    IConnectToDB connectToDB = ConnectToDB.getInstance();

    @Test
    public void getFriends() {
    }

    @Test
    public void sendFriendRequest() {
    }

    @Test
    public void acceptFriendship() {
    }

    @Test
    public void removeFriend() {
    }

    @Test
    public void getMessages() {
    }

    @Test
    public void sendMessage() {
    }

    @Test
    public void getRequests() {
    }

    @Test
    public void getOpenRequest() {
    }

    @Test
    public void closeRequest() {
    }

    @Test
    public void createRequest() {
    }

    @Test
    public void acceptRequest() {
    }

    @Test
    public void createUser() {
    }

    @Test
    public void verifyUser() {
        Assert.assertNotNull(connectToDB.verifyUser("Athi", "Athi"));
    }

    @Test
    public void getUsers() {
        Assert.assertNotNull(connectToDB.getUsers());
    }
}