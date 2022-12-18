package com.example.iotassistantrest.mobile;

import com.example.iotassistantrest.iot.detector.alarm.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/mobile") //req: 192.168.0.99:8181/api/iot
@RequiredArgsConstructor
public class MobileController {

    private final SwitchService service;

    @PostMapping(value = "/light/{id}/on")
    public void switchOnLight(@PathVariable("id") Long id) {
        service.switchOnLight(id);
    }

    @PostMapping(value = "/light/{id}/off")
    public void switchOffLight(@PathVariable("id") Long id) {
        service.switchOffLight(id);
    }

    @PostMapping(value = "/alarm/{mode}")
    public void switchAlarm(@PathVariable("mode") String mode) {
        service.switchAlarm(mode);
    }

    @GetMapping(value = "/alarm")
    public Optional<Alarm> switchAlarm() {
        return service.getAlarm();
    }
}
