package com.example.iotassistantrest.limit;

import com.example.iotassistantrest.config.Limit;
import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.iot.sensor.Measurement;
import com.example.iotassistantrest.iot.sensor.MeasurementType;
import com.example.iotassistantrest.iot.sensor.Sensor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitService {
    private static final Logger log = LoggerFactory.getLogger(LimitService.class);

    private final FirebaseService firebaseService;
    public void checkLimitExceeded(final Sensor sensor) {
        switch (sensor.getSensorType()) {
            case SMOG -> checkSMOGExceeded(sensor);
            case CO2 -> checkCO2Exceeded(sensor);
            default -> log.error("Unknown sensor type " + sensor.getSensorType());
        };
    }

    private void checkCO2Exceeded(Sensor sensor) {
        final Measurement measurement = sensor.getValues().get(MeasurementType.CO2);
        if(measurement.getValue() > Limit.CO2.getValue()) {
            firebaseService.pushLevelExceeded(measurement.getValue(), MeasurementType.CO2);
        }
    }

    private void checkSMOGExceeded(Sensor sensor) {
        Double pm1Value = sensor.getValues().get(MeasurementType.PM1).getValue();
        if(pm1Value > Limit.PM1.getValue()) {
            firebaseService.pushLevelExceeded(pm1Value, MeasurementType.PM1);
        }
        Double pm25Value = sensor.getValues().get(MeasurementType.PM25).getValue();
        if(pm25Value > Limit.PM25.getValue()) {
            firebaseService.pushLevelExceeded(pm25Value, MeasurementType.PM25);
        }
        Double pm10Value = sensor.getValues().get(MeasurementType.PM10).getValue();
        if(pm10Value > Limit.PM10.getValue()) {
            firebaseService.pushLevelExceeded(pm10Value, MeasurementType.PM10);
        }
    }
}
