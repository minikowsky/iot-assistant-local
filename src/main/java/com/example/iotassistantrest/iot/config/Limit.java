package com.example.iotassistantrest.iot.config;

public enum Limit {
    CO2(1500.00),
    PM1 ( 70.00),
    PM25(85.00),
    PM10(140.00);

    private final Double value;
    Limit(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

}
