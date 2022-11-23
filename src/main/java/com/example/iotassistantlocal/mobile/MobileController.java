package com.example.iotassistantlocal.mobile;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mobile") //req: 192.168.0.99:8181/api/iot
public class MobileController {

    @PostMapping(value = "/light/{id}/on")
    public void switchOnLight(@PathVariable("id") Long id) {

    }

    @PostMapping(value = "/light/{id}/off")
    public void switchOffLight(@PathVariable("id") Long id) {

    }
}
