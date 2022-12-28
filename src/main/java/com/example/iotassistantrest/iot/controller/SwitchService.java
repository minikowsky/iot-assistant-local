package com.example.iotassistantrest.iot.controller;

import com.example.iotassistantrest.alarm.AlarmService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SwitchService {
    private static final Logger log = LoggerFactory.getLogger(SwitchService.class);
    private final SonoffService serviceSonoff;
    private final AlarmService serviceAlarm;

    public void switchLight(String devAddr, String mode) {
        if(Objects.equals(mode, "on") || Objects.equals(mode, "off")) {
            serviceSonoff.switchLight(devAddr, mode);
        } else {
            log.error("Unknown mode");
        }
    }

    public String findDevice(String id) {
        log.info("Sonoff id : " + id);
        String addr = serviceSonoff.findDevice(id);
        log.info("Addr" + addr);
        return addr;
    }
    public void switchAlarm(String mode) {
        if(Objects.equals(mode, "on") || Objects.equals(mode, "off")) {
            serviceAlarm.switchAlarm(mode);
        } else {
            log.error("Unknown alarm mode");
        }
    }
    //TODO: Delete
    public boolean getAlarm() {
        return serviceAlarm.isAlarmOn();
    }


}
