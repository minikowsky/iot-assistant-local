package com.example.iotassistantrest.firebase.body.data;

import com.example.iotassistantrest.iot.sensor.Measurement;
import com.example.iotassistantrest.iot.sensor.MeasurementType;
import com.example.iotassistantrest.iot.sensor.Sensor;
import com.example.iotassistantrest.iot.sensor.SensorType;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
public class Device {
    private Long id;
    private String location;
    private SensorType sensorType;
    private Current current;

    Device(Long id, String location, SensorType sensorType) {
        this.id = id;
        this.location = location;
        this.sensorType = sensorType;
    }

    public static Device current(Sensor sensor) {
        Device device = new Device(sensor.getId(),sensor.getLocation(), sensor.getSensorType());
        device.setCurrent(new Current(sensor.getValues(), sensor.getTimestamp()));
        return device;
    }

    @Data
    static class Current {
        private Map<MeasurementType, Measurement> values;
        private Timestamp timestamp;

        public Current(Map<MeasurementType, Measurement> values, Timestamp timestamp) {
            this.values = values;
            this.timestamp = timestamp;
        }
    }

}
