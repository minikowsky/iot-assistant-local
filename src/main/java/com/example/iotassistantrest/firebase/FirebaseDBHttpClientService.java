package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.firebase.body.data.Config;
import com.example.iotassistantrest.firebase.body.data.Device;
import com.example.iotassistantrest.firebase.body.data.Switch;
import com.example.iotassistantrest.iot.sensor.Sensor;
import com.example.iotassistantrest.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class FirebaseDBHttpClientService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseDBHttpClientService.class);
    private static final String FIREBASE_DATABASE_URL = "https://iot-assistant-81581-default-rtdb.europe-west1.firebasedatabase.app/localServers/%s";
    private static final String FIREBASE_CONFIG = FIREBASE_DATABASE_URL + "/config.json";
    private static final String FIREBASE_SWITCH = FIREBASE_DATABASE_URL + "/switch/%s.json";
    private static final String FIREBASE_SWITCH_GET = FIREBASE_DATABASE_URL + "/switch.json";
    private static final String FIREBASE_CURRENT = FIREBASE_DATABASE_URL + "/sensors/%s.json";
    private static final String FIREBASE_HISTORICAL = FIREBASE_DATABASE_URL + "/sensors/%s/historical/%s.json";

    private static final String METHOD_PATCH = "PATCH";
    private static final String METHOD_DELETE = "DELETE";

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

        send(uri, json, METHOD_PATCH);
    }

    void sendHistoricalData(Sensor sensor) {
        String uri = String.format(
                FIREBASE_HISTORICAL,
                localServerId,
                sensor.getId(),
                sensor.getTimestamp().getTime());

        String json = JSONUtils.objectToJson(sensor.getValues());

        send(uri, json, METHOD_PATCH);
    }

    void sendServerConfig(Config config) {
        String uri = String.format(
                FIREBASE_CONFIG,
                localServerId);

        String json = JSONUtils.objectToJson(config);

        send(uri, json, METHOD_PATCH);
    }

    public Config getConfig() {
        String uri = String.format(
                FIREBASE_CONFIG,
                localServerId);


        var response = get(uri);
        if(response != null) {
            log.info(response.body());
            return JSONUtils.jsonToConfig(response.body());
        }
        return null;
    }

    void sendUpdateSwitch(Switch data) {
        String uri = String.format(
                FIREBASE_SWITCH,
                localServerId,
                data.getId());

        String json = JSONUtils.objectToJson(data);

        send(uri, json, METHOD_PATCH);
    }

    void sendDeleteSwitch(Switch data) {
        String uri = String.format(
                FIREBASE_SWITCH,
                localServerId,
                data.getId());

        String json = JSONUtils.objectToJson(data);

        send(uri, json, METHOD_DELETE);
    }

    Map<String,Switch> getAllSwitches() {
        String uri = String.format(
                FIREBASE_SWITCH_GET,
                localServerId);


        var response = get(uri);
        if(response != null) {
            return JSONUtils.jsonToSwitches(response.body());
        }
        return null;
    }

    private void send(String uri, String body, String method) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .headers("Content-Type","application/json",
                            "Authorization", "Bearer "+ FirebaseSecurityConfig.getFirebaseToken())
                    .uri(new URI(uri))
                    .method(method, HttpRequest.BodyPublishers.ofString(body))
                    .build();

            httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(log::debug)
                    .join();
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private HttpResponse<String> get(String uri) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .headers("Content-Type","application/json",
                            "Authorization", "Bearer "+ FirebaseSecurityConfig.getFirebaseToken())
                    .uri(new URI(uri))
                    .GET().build();

            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
