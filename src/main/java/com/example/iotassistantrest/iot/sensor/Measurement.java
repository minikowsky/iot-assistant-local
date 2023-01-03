package com.example.iotassistantrest.iot.sensor;

import com.example.iotassistantrest.iot.config.Unit;

public class Measurement {
    private Unit unit;

    private Double value;

    public Unit getUnit() {
        return unit;
    }

    public Double getValue() {
        return value;
    }

    public Measurement unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public Measurement value(Double value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "unit='" + unit + '\'' +
                ", value=" + value +
                '}';
    }
}
