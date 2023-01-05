package com.example.iotassistantrest.api;

import com.example.iotassistantrest.iot.controller.SwitchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobile") //req: 192.168.0.99:8181/api/iot
@RequiredArgsConstructor
public class MobileController {
    private final SwitchService service;

    @PostMapping(value = "/light/{id}/{mode}")
    public HttpStatus switchLight(@PathVariable("id") String id, @PathVariable("mode") String mode) {
        String devAddr = service.findDevice(id);
        if(devAddr == null) {
            return HttpStatus.BAD_REQUEST;
        }
        try {
            service.switchLight(devAddr, mode.toLowerCase());
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @PostMapping(value = "/alarm/{mode}")
    public void switchAlarm(@PathVariable("mode") String mode) {
        service.switchAlarm(mode.toLowerCase());
    }

    @GetMapping(value = "/alarm")
    public boolean getAlarm() {
        return service.getAlarm();
    }
}
