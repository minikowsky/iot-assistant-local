package com.example.iotassistantrest.firebase.body.push;

import lombok.Data;

@Data
public class MessageBody {
    private String to;
    private Notification notification;
    private com.example.iotassistantrest.firebase.body.push.Data data;

    public MessageBody to(String to) {
        this.to = to;
        return this;
    }

    public MessageBody notification(Notification notification) {
        this.notification = notification;
        return this;
    }

    public MessageBody data(com.example.iotassistantrest.firebase.body.push.Data data) {
        this.data = data;
        return this;
    }
}
