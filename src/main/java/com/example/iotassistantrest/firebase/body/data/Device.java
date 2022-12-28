package com.example.iotassistantrest.firebase.body.data;

import com.example.iotassistantrest.iot.sensor.Sensor;
import com.example.iotassistantrest.iot.sensor.SensorType;
import lombok.Data;

@Data
public class Device {
    private String location;
    private SensorType sensorType;
    private Current current;

    private Device () {}

    Device(String location, SensorType sensorType) {
        this.location = location;
        this.sensorType = sensorType;
    }

    public static Device current(Sensor sensor) {
        Device device = new Device(sensor.getLocation(), sensor.getSensorType());
        device.setCurrent(new Current(sensor.getValues(), sensor.getTimestamp()));
        return device;
    }

}
