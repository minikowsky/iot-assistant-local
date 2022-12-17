package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.config.Message;
import com.example.iotassistantrest.config.Unit;
import com.example.iotassistantrest.iot.detector.DetectorType;
import com.example.iotassistantrest.iot.sensor.MeasurementType;
import com.example.iotassistantrest.iot.sensor.Sensor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class FirebaseService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseService.class);
    final FirebaseDBHttpClientService dbService;
    final FirebaseFCMHttpClientService fcmService;

    public void updateValues(Sensor sensor) {
        log.info("send actual data");
        dbService.sendUpdateRequest(sensor);

        if(sensor.getTimestamp().toLocalDateTime().getMinute()==0) {
            log.info("send historical data, time" + sensor.getTimestamp().toLocalDateTime());
            dbService.sendPostRequest(sensor);
        }
    }

    public void pushDetection(DetectorType type, String location) {
        String message = switch(type) {
            case MOTION -> Message.getMessage(Message.MOTION_DETECTED, location);
            default -> "Default message - detector type unknown";
        };
        fcmService.push(message);
        log.info("Push detection: " + message + " - has been send");
    }

    public void pushLevelExceeded(Double value, MeasurementType type) {
        String message = switch(type){
            case PM1 -> Message.getMessage(Message.HIGH_LEVEL_PM1, value, Unit.MICROGRAMS_PER_CUBIC_METER.getSymbol());
            case PM25 -> Message.getMessage(Message.HIGH_LEVEL_PM25, value, Unit.MICROGRAMS_PER_CUBIC_METER.getSymbol());
            case PM10 -> Message.getMessage(Message.HIGH_LEVEL_PM10, value, Unit.MICROGRAMS_PER_CUBIC_METER.getSymbol());
            case CO2 -> Message.getMessage(Message.HIGH_LEVEL_C02, value, Unit.PARTS_PER_MILLION.getSymbol());
            default -> "Default message - measurement type unknown";
        };
        fcmService.push(message);
        log.info("Push level exceeded: " + message + " - has been send");
    }
}
