package com.example.iotassistantrest.firebase.body.data;

import lombok.Data;

@Data
public class Config {
    private String ip;
    private String ssid;

    public Config ip(String ip) {
        this.ip = ip;
        return this;
    }

    public Config ssid(String ssid) {
        this.ssid = ssid;
        return this;
    }
}
