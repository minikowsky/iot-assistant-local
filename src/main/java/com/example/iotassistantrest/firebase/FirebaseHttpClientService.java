package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.iot.sensor.Sensor;
import com.example.iotassistantrest.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

@Service
public class FirebaseHttpClientService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseHttpClientService.class);
    public static final String FIREBASE_DATABASE_URL = "https://iot-assistant-81581-default-rtdb.europe-west1.firebasedatabase.app/localServers/%s/%s";
    public static final String FIREBASE_CURRENT = FIREBASE_DATABASE_URL + "/current.json";
    public static final String FIREBASE_HISTORICAL = FIREBASE_DATABASE_URL + "/historical.json";
    public static final String FIREBASE_DELETE_HISTORICAL = FIREBASE_DATABASE_URL + "/historical/%s.json";

    //private final String localServerId = System.getProperty("localServerId");
    private final Properties properties = new Properties();

    public FirebaseHttpClientService() {

        try(InputStream input = FirebaseHttpClientService.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    void sendUpdateRequest(Sensor sensor) {
        String json = JSONUtils.objectToJson(sensor);
        String localServerId = properties.getProperty("localServerId");
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

    void sendPostRequest(Sensor sensor) {
        String json = JSONUtils.objectToJson(sensor);
        String localServerId = properties.getProperty("localServerId");
        try {
            String uri = String.format(
                    FIREBASE_HISTORICAL,
                    localServerId,
                    sensor.getId());

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .header("Content-Type","application/json")
                    .uri(new URI(uri))
                    .POST( HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(log::debug)
                    .join();
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    void sendDeleteRequest(Sensor sensor) {
        Long sensorId = sensor.getId();
        String localServerId = properties.getProperty("localServerId");
        try {
            String uri = String.format(
                    FIREBASE_DELETE_HISTORICAL,
                    localServerId,
                    sensor.getId(),
                    sensorId);

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .DELETE()
                    .build();

            httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }
}
