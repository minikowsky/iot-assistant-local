package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.firebase.body.data.Switch;
import com.example.iotassistantrest.iot.config.Lang;
import com.example.iotassistantrest.iot.config.Message;
import com.example.iotassistantrest.iot.config.Unit;
import com.example.iotassistantrest.firebase.body.data.Config;
import com.example.iotassistantrest.iot.detector.DetectorType;
import com.example.iotassistantrest.iot.sensor.MeasurementType;
import com.example.iotassistantrest.iot.sensor.Sensor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FirebaseService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseService.class);
    final FirebaseDBHttpClientService dbService;
    final FirebaseFCMHttpClientService fcmService;

    public void updateConfig(String localIP, String ssid) {
        dbService.sendServerConfig(new Config().localIP(localIP).ssid(ssid));
    }

    public Config getConfig() {
        return dbService.getConfig();
    }

    public Map<String,Switch> findAllSwitches() {
        return dbService.getAllSwitches();
    }

    public void updateSwitch(Switch data) {
        dbService.sendUpdateSwitch(data);
    }

    public void deleteSwitch(Switch data) {
        dbService.sendDeleteSwitch(data);
    }

    public void updateValues(Sensor sensor) {
        log.info("send actual data");
        dbService.sendCurrentData(sensor);

        if(sensor.getTimestamp().toLocalDateTime().getMinute()==0) {
            log.info("send historical data, time" + sensor.getTimestamp().toLocalDateTime());
            dbService.sendHistoricalData(sensor);
       }
    }

    public void pushDetection(DetectorType type, String location) {
        Map<Lang,String> messages = switch(type) {
            case MOTION -> Message.getMessage(Message.MOTION_DETECTED, location);
            default -> null;
        };
        if(messages != null) {
            fcmService.push(messages, null, null);
            log.info("Push detection: " + messages.get(Lang.EN) + " - has been send");
        } else {
            log.error("There is no messages!");
        }

    }

    public void pushLevelExceeded(Long sensorId, MeasurementType type, Double value) {
        Map<Lang,String> messages = switch(type){
            case PM1 -> Message.getMessage(Message.HIGH_LEVEL_PM1, value, Unit.MICROGRAMS_PER_CUBIC_METER.getSymbol());
            case PM25 -> Message.getMessage(Message.HIGH_LEVEL_PM25, value, Unit.MICROGRAMS_PER_CUBIC_METER.getSymbol());
            case PM10 -> Message.getMessage(Message.HIGH_LEVEL_PM10, value, Unit.MICROGRAMS_PER_CUBIC_METER.getSymbol());
            case CO2 -> Message.getMessage(Message.HIGH_LEVEL_C02, value, Unit.PARTS_PER_MILLION.getSymbol());
            default -> null;
        };
        if(messages!= null) {
            fcmService.push(messages, sensorId, type);
            log.info("Push level exceeded: " + messages.get(Lang.EN) + " - has been send");
        } else {
            log.error("There is no messages!");
        }
    }
}
