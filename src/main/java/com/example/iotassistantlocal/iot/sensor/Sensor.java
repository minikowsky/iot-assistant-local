package com.example.iotassistantlocal.iot.sensor;

import java.util.Map;

public class Sensor {
    private Long id;
    private Map<String, Double> values;
    private String timestamp;
    private SensorType type;

    public Long getId() {
        return id;
    }

    public SensorType getType() {
        return type;
    }

    public Map<String, Double> getValues() {
        return values;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Sensor id(Long id) {
        this.id = id;
        return this;
    }

    public Sensor timestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Sensor type(SensorType type) {
        this.type = type;
        return this;
    }

    public Sensor values(Map<String, Double> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", values=" + values +
                ", timestamp='" + timestamp + '\'' +
                ", type=" + type +
                '}';
    }
}
