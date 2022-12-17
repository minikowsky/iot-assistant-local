package com.example.iotassistantrest.iot.sensor;

import com.example.iotassistantrest.iot.Device;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Sensor extends Device {
    @NotNull
    @Valid
    private Map<MeasurementType, Measurement> values;

    @NotNull
    @Valid
    private SensorType sensorType;

    public Sensor() {

    }

    public Sensor sensorType(SensorType sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    public Sensor values(Map<MeasurementType, Measurement> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        String valuesToString = values.keySet().stream()
                .map(key -> key + "=" + values.get(key))
                .collect(Collectors.joining(", ","\t{\n\t\t","\n\t}"));
        return "\nSensor {\n" +
                "\tid=" + super.getId() + "\n" +
                "\tvalues=\n" + valuesToString  + "\n" +
                "\ttimestamp='" + super.getTimestamp() + "'\n" +
                "\tlocation=" + super.getLocation() + "\n" +
                "\tsensorType=" + sensorType + "\n" +
                "}";
    }


}
