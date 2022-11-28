package com.example.iotassistantrest.iot.sensor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Map;
import java.util.stream.Collectors;

public class Sensor {
    @NotNull
    private Long id;
    @NotNull
    @Valid
    private Map<MeasurementType, Measurement> values;

    private Timestamp timestamp;
    @NotNull
    private String location;
    @NotNull
    @Valid
    private SensorType sensorType;

    public Sensor() {
        this.timestamp = new Timestamp(System.nanoTime());
    }

    public Long getId() {
        return id;
    }

    public SensorType getType() {
        return sensorType;
    }

    public Map<MeasurementType, Measurement> getValues() {
        return values;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public SensorType getSensorType() {
        return sensorType;
    }
    public Sensor id(Long id) {
        this.id = id;
        return this;
    }

    public Sensor timestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Sensor sensorType(SensorType sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    public Sensor location(String location) {
        this.location = location;
        return this;
    }
    public Sensor values(Map<MeasurementType, Measurement> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        String valuesToString = values.keySet().stream()
                .map(key -> key + "=" + values.get(key))
                .collect(Collectors.joining(", ","{","}"));
        return "Sensor{" +
                "\nid=" + id +
                "\n, values=\n" + valuesToString +
                "\n, timestamp='" + timestamp + '\'' +
                "\n, type=" + sensorType +
                "\n}\n";
    }


}
