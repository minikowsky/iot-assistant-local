package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.firebase.body.push.Message;
import com.example.iotassistantrest.iot.config.Lang;
import com.example.iotassistantrest.firebase.body.push.Data;
import com.example.iotassistantrest.firebase.body.push.MessageBody;
import com.example.iotassistantrest.firebase.body.push.Notification;
import com.example.iotassistantrest.iot.sensor.MeasurementType;
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
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
class FirebaseFCMHttpClientService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseFCMHttpClientService.class);

    private static final String FCM_URL = "https://fcm.googleapis.com/v1/projects/iot-assistant-81581/messages:send";

    private final String serverId;

    FirebaseFCMHttpClientService(@Value(value = "${local-server.id}") final String serverId) {
        this.serverId = serverId;
        log.info("LocalServerId = " + this.serverId);
    }

    void push(Map<Lang,String> messages, Long sensorId, MeasurementType type) {
        pushMessage(messages.get(Lang.EN), Lang.EN.toString(), sensorId, type);
        pushMessage(messages.get(Lang.PL), Lang.PL.toString(), sensorId, type);
    }

    void pushMessage(String message, String lang, Long sensorId, MeasurementType measurementType) {
        String json = JSONUtils.objectToJson(getMessage(lang, message, sensorId.toString(), measurementType.toString()));
        log.info(json);
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .headers("Content-Type","application/json; charset=UTF-8",
                            "Authorization", "Bearer " + FirebaseSecurityConfig.getFirebaseToken())
                    .uri(new URI(FCM_URL))
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

    private Message getMessage(String lang, String message, String sensorId, String measurementType) {
        return new Message().message(new MessageBody()
                .topic(serverId + "_" + lang)
                .notification(new Notification()
                        .body(message)
                        .title("IOT Assistant"))
                .data(new Data()
                        .sensorId(sensorId)
                        .serverId(serverId)
                        .measurementType(measurementType)));
    }
}
