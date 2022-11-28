package com.example.iotassistantrest.iot.sensor;

import com.example.iotassistantrest.config.Limit;
import com.example.iotassistantrest.firebase.FirebaseService;
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
        switch (sensor.getType()) {
            case SMOG -> checkSMOGExceeded(sensor);
            case CO2 -> checkCO2Exceeded(sensor);
            default -> log.error("Unknown sensor type " + sensor.getType());
        };
    }

    private void checkCO2Exceeded(Sensor sensor) {
        final Measurement measurement = sensor.getValues().get(MeasurementType.CO2);
        if(measurement.getValue() > Limit.CO2.getValue()) {
            firebaseService.pushCO2Exceeded(measurement.getValue());
        }
    }

    private void checkSMOGExceeded(Sensor sensor) {
        Double pm1Value = sensor.getValues().get(MeasurementType.PM1).getValue();
        if(pm1Value > Limit.PM1.getValue()) {
            firebaseService.pushPM1Exceeded(pm1Value);
        }
        Double pm25Value = sensor.getValues().get(MeasurementType.PM25).getValue();
        if(pm25Value > Limit.PM25.getValue()) {
            firebaseService.pushPM25Exceeded(pm25Value);
        }
        Double pm10Value = sensor.getValues().get(MeasurementType.PM10).getValue();
        if(pm10Value > Limit.PM10.getValue()) {
            firebaseService.pushPM10Exceeded(pm10Value);
        }
    }
}
