package com.example.iotassistantrest.firebase.body;

import lombok.Data;

@Data
public class MessageBody {
    private String to;
    private Notification notification;

    public MessageBody to(String to) {
        this.to = to;
        return this;
    }

    public MessageBody notification(Notification notification) {
        this.notification = notification;
        return this;
    }
}
