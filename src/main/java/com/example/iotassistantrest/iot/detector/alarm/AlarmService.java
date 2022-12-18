package com.example.iotassistantrest.iot.detector.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository repository;

    public boolean isAlarmOn() {
        Optional<Alarm> alarm = repository.findById(1);
        if(alarm.isEmpty()) {
            return false;
        }
        return alarm.get().getMode().equals("ON");
    }
}
