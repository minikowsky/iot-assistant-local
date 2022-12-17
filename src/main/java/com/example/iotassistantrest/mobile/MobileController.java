package com.example.iotassistantrest.mobile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
