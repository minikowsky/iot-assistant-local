package com.example.iotassistantlocal.firebase;

import com.example.iotassistantlocal.iot.sensor.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseService.class);
    public void pushAlertMotion() {
        //TODO: push alert to FCM - motion detected
    }

    public void updateValues(Sensor sensor) {
        //TODO: update values in stats object(object that has all values) and is send periodically by scheduler
    }

    public void pushCO2Exceeded(Double co2) {
        //TODO: push alert to FCM - CO2 level exceeded
    }

    public void pushPM25Exceeded(Double pm25) {
        //TODO: push alert to FCM - PM25 level exceeded
    }

    public void pushPM10Exceeded(Double pm10) {
        //TODO: push alert to FCM - PM10 level exceeded
    }
}
