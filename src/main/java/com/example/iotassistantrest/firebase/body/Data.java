package com.example.iotassistantrest.firebase.body;
@lombok.Data
public class Data {
    private Long sensorId;

    public Data sensorId(Long sensorId) {
        this.sensorId = sensorId;
        return this;
    }
}
