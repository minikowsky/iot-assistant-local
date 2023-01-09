package com.example.iotassistantrest.firebase.body.push;

import com.example.iotassistantrest.iot.sensor.MeasurementType;

@lombok.Data
public class Data {
    private String sensorId;
    private String serverId;
    private String measurementType;

    public Data sensorId(String sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public Data serverId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public Data measurementType(String measurementType) {
        this.measurementType = measurementType;
        return this;
    }
}
