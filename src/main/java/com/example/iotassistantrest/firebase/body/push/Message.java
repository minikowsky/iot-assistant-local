package com.example.iotassistantrest.firebase.body.push;

import lombok.Data;

@Data
public class Message {
    private MessageBody message;

    public Message message(MessageBody message) {
        this.message = message;
        return this;
    }
}
