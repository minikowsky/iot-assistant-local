package com.example.iotassistantrest.config;

public class Message {
    public static String HIGH_LEVEL_C02 = "High C02 level in the air! The measurement was %s %s";
    public static String HIGH_LEVEL_PM1 = "High PM1 level in the air! The measurement was %s %s";
    public static String HIGH_LEVEL_PM25 = "High PM2,5 level in the air! The measurement was %s %s";
    public static String HIGH_LEVEL_PM10 = "High PM10 level in the air! The measurement was %s %s";
    public static String MOTION_DETECTED = "Motion detected in %s!";

    public static String getMessage(String message, Object... args) {
        return String.format(message,args);
    }
}
