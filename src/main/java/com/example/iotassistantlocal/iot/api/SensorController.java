package com.example.iotassistantlocal.iot.api;

import com.example.iotassistantlocal.iot.sensor.Sensor;
import com.example.iotassistantlocal.iot.sensor.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/iot") //req: 192.168.0.99:8181/api/iot
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping
    public HttpStatus postData(@RequestBody final Sensor sensor) {
        sensorService.process(sensor);
        return HttpStatus.ACCEPTED;
    }
}
