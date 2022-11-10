package com.example.iotassistantlocal.iot.sensor;

import com.example.iotassistantlocal.config.Limit;
import com.example.iotassistantlocal.firebase.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitService {
    private static final Logger log = LoggerFactory.getLogger(LimitService.class);
    private static final String CO2 = "CO2";
    private static final String PM25 = "PM25";
    private static final String PM10 = "PM10";

    private final FirebaseService firebaseService;
    public void checkLimitExceeded(final Sensor sensor) {
        switch (sensor.getType()) {
            case SMOG -> checkSMOGExceeded(sensor);
            case CO2 -> checkCO2Exceeded(sensor);
            default -> log.error("Unknown sensor type " + sensor.getType());
        };
    }

    private void checkCO2Exceeded(Sensor sensor) {
        if(sensor.getValues().get(CO2) > Limit.CO2) {
            firebaseService.pushCO2Exceeded(sensor.getValues().get(CO2));
        }
    }

    private void checkSMOGExceeded(Sensor sensor) {
        if(sensor.getValues().get(PM25) > Limit.PM25) {
            firebaseService.pushPM25Exceeded(sensor.getValues().get(PM25));
        }
        if(sensor.getValues().get(PM10) > Limit.PM10) {
            firebaseService.pushPM10Exceeded(sensor.getValues().get(PM10));
        }
    }
}
