package com.example.iotassistantrest.iot.controller;

import com.example.iotassistantrest.alarm.AlarmService;
import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.firebase.body.data.Switch;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SwitchService {
    private static final Logger log = LoggerFactory.getLogger(SwitchService.class);
    private final SonoffService sonoffService;
    private final AlarmService alarmService;

    private final FirebaseService firebaseService;

    public void switchLight(String id, String mode) {
        Switch s = findDevice(id);
        if(s.getLocalIP() == null) {
            log.error("Device id unknown");
        }
        if(Objects.equals(mode, "on") || Objects.equals(mode, "off")) {
            s.setMode(mode);
            sonoffService.switchLight(s.getLocation(), mode);
            firebaseService.updateSwitch(s);
        } else {
            log.error("Unknown mode");
        }
    }

    public Switch findDevice(String id) {
        log.info("Sonoff id : " + id);
        Map<String, Switch> map  = firebaseService.findAllSwitches();
        return map.get(id);
    }
    public void switchAlarm(String mode) {
        if(Objects.equals(mode, "on") || Objects.equals(mode, "off")) {
            alarmService.switchAlarm(mode);
        } else {
            log.error("Unknown alarm mode");
        }
    }
    //TODO: Delete
    public boolean getAlarm() {
        return alarmService.isAlarmOn();
    }


}
