package com.example.iotassistantrest.api;

import com.example.iotassistantrest.iot.detector.Detector;
import com.example.iotassistantrest.iot.detector.DetectorService;
import com.example.iotassistantrest.iot.sensor.Sensor;
import com.example.iotassistantrest.iot.sensor.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/iot")
@RequiredArgsConstructor
public class IOTController {
    private final SensorService sensorService;
    private final DetectorService detectorService;

    @PostMapping("sensor")
    public HttpStatus postData(@RequestBody @Valid final Sensor sensor) {
        try {
            sensorService.process(sensor);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("detector")
    public HttpStatus handleDetection(@RequestBody @Valid final Detector detector) {
        try {
            detectorService.process(detector);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
