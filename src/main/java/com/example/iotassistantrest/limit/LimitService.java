package com.example.iotassistantrest.limit;

import com.example.iotassistantrest.iot.config.Limit;
import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.iot.sensor.MeasurementType;
import com.example.iotassistantrest.iot.sensor.Sensor;
import com.example.iotassistantrest.limit.lastpush.LastPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LimitService {
    private static final Logger log = LoggerFactory.getLogger(LimitService.class);

    private final FirebaseService firebaseService;
    private final LastPushService lastPushService;

    private final long pushPM1;
    private final long pushPM25;
    private final long pushPM10;
    private final long pushCO2;

    public LimitService(FirebaseService firebaseService,
                        LastPushService lastPushService,
                        @Value(value = "${push.level-exceeded.pm1}") long pushPM1,
                        @Value(value = "${push.level-exceeded.pm25}") long pushPM25,
                        @Value(value = "${push.level-exceeded.pm10}") long pushPM10,
                        @Value(value = "${push.level-exceeded.co2}") long pushCO2) {
        this.firebaseService = firebaseService;
        this.lastPushService = lastPushService;
        this.pushPM1 = pushPM1;
        this.pushPM25 = pushPM25;
        this.pushPM10 = pushPM10;
        this.pushCO2 = pushCO2;
    }

    public void checkLimitExceeded(final Sensor sensor) {
        switch (sensor.getSensorType()) {
            case SMOG -> checkSMOGExceeded(sensor);
            case CO2 -> checkCO2Exceeded(sensor);
            default -> log.error("Unknown sensor type " + sensor.getSensorType());
        }
    }

    private void checkCO2Exceeded(Sensor sensor) {
        Double value = sensor.getValues().get(MeasurementType.CO2).getValue();
        if (value > Limit.CO2.getValue()) {
            if (lastPushService.shouldPush(MeasurementType.CO2.toString(), pushCO2)) {
                firebaseService.pushLevelExceeded(sensor.getId(), MeasurementType.CO2, value);
            }
        }
    }

    private void checkSMOGExceeded(Sensor sensor) {
        Double pm1Value = sensor.getValues().get(MeasurementType.PM1).getValue();
        if (pm1Value > Limit.PM1.getValue()) {
            if (lastPushService.shouldPush(MeasurementType.PM1.toString(), pushPM1)) {
                firebaseService.pushLevelExceeded(sensor.getId(), MeasurementType.PM1,pm1Value);
            }
        }
        Double pm25Value = sensor.getValues().get(MeasurementType.PM25).getValue();
        if (pm25Value > Limit.PM25.getValue()) {
            if (lastPushService.shouldPush(MeasurementType.PM25.toString(), pushPM25)) {
                firebaseService.pushLevelExceeded(sensor.getId(), MeasurementType.PM25, pm25Value);
            }
        }
        Double pm10Value = sensor.getValues().get(MeasurementType.PM10).getValue();
        if (pm10Value > Limit.PM10.getValue()) {
            if (lastPushService.shouldPush(MeasurementType.PM10.toString(), pushPM10)) {
                firebaseService.pushLevelExceeded(sensor.getId(), MeasurementType.PM10, pm10Value);
            }
        }
    }
}


