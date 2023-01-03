package com.example.iotassistantrest.firebase.body.data;

import lombok.Data;

@Data
public class Config {
    private String localIP;
    private String ssid;

    public Config localIP(String localIP) {
        this.localIP = localIP;
        return this;
    }

    public Config ssid(String ssid) {
        this.ssid = ssid;
        return this;
    }
}
