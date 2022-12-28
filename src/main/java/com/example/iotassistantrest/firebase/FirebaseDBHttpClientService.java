package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.iot.sensor.Sensor;
import com.example.iotassistantrest.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
class FirebaseDBHttpClientService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseDBHttpClientService.class);
    public static final String FIREBASE_DATABASE_URL = "https://iot-assistant-81581-default-rtdb.europe-west1.firebasedatabase.app/localServers/%s/%s";
    public static final String FIREBASE_CURRENT = FIREBASE_DATABASE_URL + "/current.json";
    public static final String FIREBASE_HISTORICAL = FIREBASE_DATABASE_URL + "/historical/%s.json";

    private final String localServerId;

    FirebaseDBHttpClientService(@Value(value = "${local-server.id}") final String localServerId) {
        this.localServerId = localServerId;
        log.info("LocalServerId = " + this.localServerId);
    }

    void sendCurrentData(Sensor sensor) {
        String json = JSONUtils.objectToJson(sensor);
        try {
            String uri = String.format(
                    FIREBASE_CURRENT,
                    localServerId,
                    sensor.getId());

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

    void sendHistoricalData(Sensor sensor) {
        String json = JSONUtils.objectToJson(sensor);
        try {
            String uri = String.format(
                    FIREBASE_HISTORICAL,
                    localServerId,
                    sensor.getId(),
                    sensor.getTimestamp().getTime());
            log.info(uri);
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
}
