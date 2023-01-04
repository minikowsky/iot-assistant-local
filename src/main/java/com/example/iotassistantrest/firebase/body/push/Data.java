package com.example.iotassistantrest.firebase.body.push;
@lombok.Data
public class Data {
    private Long sensorId;
    private String serverId;

    public Data sensorId(Long sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public Data serverId(String serverId) {
        this.serverId = serverId;
        return this;
    }
}
