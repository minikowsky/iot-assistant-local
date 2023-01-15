package com.example.iotassistantrest.alarm;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private static final Logger log = LoggerFactory.getLogger(AlarmService.class);
    private final AlarmRepository repository;


    public boolean isAlarmOn() {
        Optional<Alarm> alarm = repository.findById(1);
        if(alarm.isEmpty()) {
            Alarm newAlarm = new Alarm().id(1).mode("off"); // if db is empty
            repository.save(newAlarm);
            return false;
        }
        return alarm.get().getMode().equals("on");
    }

    public void switchAlarm(String mode) {
        Optional<Alarm> alarm = repository.findById(1);
        if(alarm.isPresent()) {
            alarm.get().setMode(mode);
            repository.save(alarm.get());
        } else  {
            log.error("There is no alarm mode in db");
        }
    }
}
