package com.example.iotassistantrest.firebase.body.data;

import com.example.iotassistantrest.iot.sensor.Measurement;
import com.example.iotassistantrest.iot.sensor.MeasurementType;
import com.example.iotassistantrest.iot.sensor.SensorType;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Map;
@Data
public class Current {
    private Map<MeasurementType, Measurement> values;
    private Timestamp timestamp;

    public Current(Map<MeasurementType, Measurement> values, Timestamp timestamp) {
        this.values = values;
        this.timestamp = timestamp;
    }
}
