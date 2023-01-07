package com.example.iotassistantrest.firebase.body.push;

import com.example.iotassistantrest.iot.sensor.MeasurementType;

@lombok.Data
public class Data {
    private Long sensorId;
    private String serverId;
    private MeasurementType measurementType;

    public Data sensorId(Long sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public Data serverId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public Data measurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
        return this;
    }
}
