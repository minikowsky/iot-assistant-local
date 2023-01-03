package com.example.iotassistantrest.iot.config;

import java.util.Map;

public class Message {
    public static Map<Lang,String> HIGH_LEVEL_C02 = Map.of(
            Lang.EN,"High C02 level in the air! The measurement was %s %s",
            Lang.PL,"Wysoki poziom CO2 w powietrzu! Pomiar wyniósł %s %s");
    public static Map<Lang,String> HIGH_LEVEL_PM1 = Map.of(
            Lang.EN,"High PM1 level in the air! The measurement was %s %s",
            Lang.PL,"Wysoki poziom pyłu PM1 w powietrzu! Pomiar wyniósł %s %s");
    public static Map<Lang,String> HIGH_LEVEL_PM25 = Map.of(
            Lang.EN,"High PM2,5 level in the air! The measurement was %s %s",
            Lang.PL,"Wysoki poziom pyłu PM2,5 w powietrzu! Pomiar wyniósł %s %s");
    public static Map<Lang,String> HIGH_LEVEL_PM10 = Map.of(
            Lang.EN,"High PM10 level in the air! The measurement was %s %s",
            Lang.PL,"Wysoki poziom pyłu PM10 w powietrzu! Pomiar wyniósł %s %s");
    public static Map<Lang,String> MOTION_DETECTED = Map.of(
            Lang.EN,"Motion detected in %s!",
            Lang.PL,"Wykryto ruch w %s");

    public static Map<Lang,String> getMessage(Map<Lang,String> message, Object... args) {
        return Map.of(Lang.EN, String.format(message.get(Lang.EN),args),
                        Lang.PL, String.format(message.get(Lang.PL),args));
    }
}
