package com.example.iotassistantrest.firebase.body.push;

import lombok.Data;

@Data
public class MessageBody {
    private String topic;
    private Notification notification;
    private com.example.iotassistantrest.firebase.body.push.Data data;

    public MessageBody topic(String topic) {
        this.topic = topic;
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
