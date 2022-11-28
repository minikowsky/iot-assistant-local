package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.iot.sensor.Sensor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseService.class);
    final FirebaseHttpClientService httpClientService;
    public void pushAlertMotion() {
        //TODO: push alert to FCM - motion detected
    }

    public void updateValues(Sensor sensor) {
        //TODO: update values in stats object(object that has all values) and send periodically by scheduler ??
        httpClientService.sendUpdateRequest(sensor);
    }

    public void pushCO2Exceeded(Double co2) {
        //TODO: push alert to FCM - CO2 level exceeded
    }

    public void pushPM1Exceeded(Double pm1) {
        //TODO: push alert to FCM - PM25 level exceeded
    }

    public void pushPM25Exceeded(Double pm25) {
        //TODO: push alert to FCM - PM25 level exceeded
    }

    public void pushPM10Exceeded(Double pm10) {
        //TODO: push alert to FCM - PM10 level exceeded
    }
}
