package com.example.iotassistantrest.mobile;

import com.example.iotassistantrest.iot.detector.alarm.Alarm;
import com.example.iotassistantrest.iot.detector.alarm.AlarmRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SwitchService {
    private static final Logger log = LoggerFactory.getLogger(SwitchService.class);
    private final AlarmRepository repository;

    public void switchOnLight(Long id) {
        //TODO:
    }

    public void switchOffLight(Long id) {
        //TODO:
    }

    public void switchAlarm(String mode) {
        if (mode.equals("ON")) {
            Optional<Alarm> alarm = repository.findById(1);
            if(alarm.isPresent()) {
                alarm.get().setMode("ON");
                repository.save(alarm.get());
            } else  {
                log.error("There is no alarm mode in db");
            }
        } else if (mode.equals("OFF")) {
            Optional<Alarm> alarm = repository.findById(1);
            if(alarm.isPresent()) {
                alarm.get().setMode("OFF");
                repository.save(alarm.get());
            } else  {
                log.error("There is no alarm mode in db");
            }
        } else {
            log.error("Unknown alarm mode");
        }
    }
    //TODO: Delete
    public Optional<Alarm> getAlarm() {
        return repository.findById(1);
    }
}
