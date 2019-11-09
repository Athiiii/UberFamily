package com.example.uberfamiliy.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Request extends SugarRecord implements Serializable {
    private Long id;
    private Long requester;
    private Long driver;
    private byte open;
    private String adress;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequester() {
        return requester;
    }

    public void setRequester(Long requester) {
        this.requester = requester;
    }

    public Long getDriver() {
        return driver;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
    }

    public byte getOpen() {
        return open;
    }

    public void setOpen(byte open) {
        this.open = open;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
