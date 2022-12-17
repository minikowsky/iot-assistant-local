package com.example.iotassistantrest.firebase.body;

import lombok.Data;

@Data
public class Notification {
    private String body;
    private String title;

    public Notification body(String body) {
        this.body = body;
        return this;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }
}
