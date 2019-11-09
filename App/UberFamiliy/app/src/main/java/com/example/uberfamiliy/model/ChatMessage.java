package com.example.uberfamiliy.model;

import com.orm.SugarRecord;

import java.io.Serializable;

public class ChatMessage extends SugarRecord implements Serializable {
    private Long id;
    private Long writer;
    private Long requestId;
    private String message;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getWriter() {
        return writer;
    }

    public void setWriter(Long writer) {
        this.writer = writer;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
