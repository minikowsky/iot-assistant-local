package com.example.iotassistantrest.config;

public enum Limit {
    CO2(100.00),
    PM1 ( 100.00),
    PM25(100.00),
    PM10(100.00);

    private final Double value;
    Limit(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

}
