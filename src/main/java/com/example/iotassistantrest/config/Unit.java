package com.example.iotassistantrest.config;

public enum Unit {
    CELSIUS("°C"),
    FAHRENHEIT("°F"),
    MICROGRAMS_PER_CUBIC_METER("µg/m³"),
    PERCENT("%"),
    PARTS_PER_MILLION("ppm");

    private final String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
