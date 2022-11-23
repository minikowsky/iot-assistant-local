package com.example.iotassistantlocal.firebase;

import com.example.iotassistantlocal.iot.sensor.Sensor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class FirebaseService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseService.class);
    public void pushAlertMotion() {
        //TODO: push alert to FCM - motion detected
    }

    public void updateValues(Sensor sensor) {
        //TODO: update values in stats object(object that has all values) and send periodically by scheduler ??
        ObjectMapper jsonMapper = new JsonMapper();
        String json = "";
        try {
            json = jsonMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(sensor);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return;
        }
        try {
            String uri = String.format("https://iot-assistant-81581-default-rtdb.europe-west1.firebasedatabase.app/localServers/test_from_local/%s/.json",sensor.getId());
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .header("Content-Type","application/json")
                    .uri(new URI(uri))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(log::debug)
                    .join();
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    public void pushCO2Exceeded(Double co2) {
        //TODO: push alert to FCM - CO2 level exceeded
    }

    public void pushPM1Exceeded(Double pm1) {
        //TODO: push alert to FCM - PM25 level exceeded
    }

    public void pushPM25Exceeded(Double pm25) {
        //TODO: push alert to FCM - PM25 level exceeded
    }

    public void pushPM10Exceeded(Double pm10) {
        //TODO: push alert to FCM - PM10 level exceeded
    }
}
