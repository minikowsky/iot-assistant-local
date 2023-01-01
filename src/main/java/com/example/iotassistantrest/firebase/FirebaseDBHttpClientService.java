package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.firebase.body.data.Config;
import com.example.iotassistantrest.firebase.body.data.Device;
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
    private static final String FIREBASE_DATABASE_URL = "https://iot-assistant-81581-default-rtdb.europe-west1.firebasedatabase.app/localServers/%s";
    private static final String FIREBASE_CONFIG = FIREBASE_DATABASE_URL + "/config.json";
    private static final String FIREBASE_CURRENT = FIREBASE_DATABASE_URL + "/sensors/%s.json";
    private static final String FIREBASE_HISTORICAL = FIREBASE_DATABASE_URL + "/sensors/%s/historical/%s.json";

    private final String localServerId;

    FirebaseDBHttpClientService(@Value(value = "${local-server.id}") final String localServerId) {
        this.localServerId = localServerId;
        log.info("LocalServerId = " + this.localServerId);
    }

    void sendCurrentData(Sensor sensor) {
        String uri = String.format(
                FIREBASE_CURRENT,
                localServerId,
                sensor.getId());

        String json = JSONUtils.objectToJson(Device.current(sensor));

        send(uri, json);
    }

    void sendHistoricalData(Sensor sensor) {
        String uri = String.format(
                FIREBASE_HISTORICAL,
                localServerId,
                sensor.getId(),
                sensor.getTimestamp().getTime());

        String json = JSONUtils.objectToJson(sensor.getValues());

        send(uri, json);
    }

    void sendServerConfig(Config config) {
        String uri = String.format(
                FIREBASE_CONFIG,
                localServerId);

        String json = JSONUtils.objectToJson(config);

        send(uri, json);
    }

    private void send(String uri, String body) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .uri(new URI(uri))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
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
