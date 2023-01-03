package com.example.iotassistantrest.firebase.body.push;
@lombok.Data
public class Data {
    private Long sensorId;
    private String localServerId;

    public Data sensorId(Long sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public Data localServerId(String localServerId) {
        this.localServerId = localServerId;
        return this;
    }
}
