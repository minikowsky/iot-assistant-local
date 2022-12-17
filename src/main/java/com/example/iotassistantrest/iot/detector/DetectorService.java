package com.example.iotassistantrest.iot.detector;

import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.iot.sensor.SensorService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DetectorService {
    private static final Logger log = LoggerFactory.getLogger(SensorService.class);
    private final FirebaseService firebaseService;
    public void process(Detector detector) {
        switch (detector.getDetectorType()) {
            case MOTION -> processMotion(detector.getDetectorType(), detector.getLocation()); // check if need to send alert
            default -> log.error("Detector type unknown");
        }
    }

    private void processMotion(DetectorType type, String location) {
        log.debug("ProcessMotion - motion detected!");
        firebaseService.pushDetection(type, location);
    }

}
