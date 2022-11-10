package com.example.iotassistantlocal.mobile;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mobile") //req: 192.168.0.99:8181/api/iot
public class MobileController {

    @PostMapping(value = "/light/on/{id}")
    public void switchOnLight(@PathVariable("id") Long id) {

    }

    @PostMapping(value = "/light/off/{id}")
    public void switchOffLight(@PathVariable("id") Long id) {

    }

}
