package com.example.iotassistantrest.iot.detector;

import com.example.iotassistantrest.iot.Device;
import lombok.Getter;

@Getter
public class Detector extends Device {
    private DetectorType detectorType;


    public Detector() {
    }

    public Detector detectorType(DetectorType detectorType) {
        this.detectorType = detectorType;
        return this;
    }

    @Override
    public String toString() {
        return "Detector{\n" +
                "\tid=" + super.getId() + "\n" +
                "\tlocation=" + super.getLocation() + "\n" +
                "\ttimestamp=" + super.getTimestamp() + "\n" +
                "\tdetectorType=" + detectorType + "\n" +
                '}';
    }
}
