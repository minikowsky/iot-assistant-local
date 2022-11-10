package com.example.iotassistantlocal.config;

public class Message {
    public static String HIGH_LEVEL_C02 = "High C02 level in the air! The measurement was %s µg/m3";
    public static String HIGH_LEVEL_SMOG = "High C02 level in the air! The measurement was %s µg/m3";
    public static String HIGH_LEVEL_TEMPERATURE = "High C02 level in the air! The measurement was %s µg/m3";
    public static String HIGH_LEVEL_HUMIDITY = "High C02 level in the air! The measurement was %s µg/m3";
    public static String MOTION_DETECTED = "Motion detected!";

    public static String getMessage(String message, Object... args) {
        return String.format(message,args);
    }
}
