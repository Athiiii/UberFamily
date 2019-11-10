package com.example.uberfamiliy.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Friend extends SugarRecord implements Serializable {
    private Long id;
    private Long firstFriend;
    private Long secondFriend;
    private boolean approved;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstFriend() {
        return firstFriend;
    }

    public void setFirstFriend(Long firstFriend) {
        this.firstFriend = firstFriend;
    }

    public Long getSecondFriend() {
        return secondFriend;
    }

    public void setSecondFriend(Long secondFriend) {
        this.secondFriend = secondFriend;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
