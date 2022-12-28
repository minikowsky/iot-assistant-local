package com.example.iotassistantrest.iot.detector;

import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.alarm.AlarmService;
import com.example.iotassistantrest.iot.sensor.SensorService;
import com.example.iotassistantrest.limit.lastpush.LastPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class DetectorService {
    private static final Logger log = LoggerFactory.getLogger(SensorService.class);
    private final LastPushService lastPushService;
    private final FirebaseService firebaseService;
    private final AlarmService alarmService;

    private final long pushMotion;

    public DetectorService(LastPushService lastPushService,
                           FirebaseService firebaseService,
                           AlarmService alarmService, @Value(value = "${push.detection.motion}") long pushMotion) {
        this.lastPushService = lastPushService;
        this.firebaseService = firebaseService;
        this.alarmService = alarmService;
        this.pushMotion = pushMotion;
    }

    public void process(Detector detector) {
        switch (detector.getDetectorType()) {
            case MOTION -> processMotion(detector.getDetectorType(), detector.getLocation()); // check if need to send alert
            default -> log.error("Detector type unknown");
        }
    }

    private void processMotion(DetectorType type, String location) {
        log.debug("ProcessMotion - motion detected!");
        if(alarmService.isAlarmOn()){
            if(lastPushService.shouldPush(type.toString(), pushMotion)) {
                firebaseService.pushDetection(type, location);
            }
        }
    }
}
