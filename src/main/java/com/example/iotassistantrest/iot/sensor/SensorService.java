package com.example.iotassistantrest.iot.sensor;

import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.limit.LimitService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorService {
    private static final Logger log = LoggerFactory.getLogger(SensorService.class);
    private final FirebaseService firebaseService;
    private final LimitService limitService;

    public void process(final Sensor sensor) {
        switch (sensor.getSensorType()) {
            case SMOG, CO2 -> processCheckLimit(sensor); // measurements that have to be checked if limit has been exceeded
            default -> processNormal(sensor); //humidity, temperature - just stats
        }
    }

    private void processNormal(Sensor sensor) {
        log.debug("Process normal: " + sensor.toString());
        firebaseService.updateValues(sensor);
    }

    private void processCheckLimit(Sensor sensor) {
        log.debug("Process check limit!");
        limitService.checkLimitExceeded(sensor);
        processNormal(sensor);
    }
}
