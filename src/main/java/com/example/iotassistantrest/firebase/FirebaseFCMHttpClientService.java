package com.example.iotassistantrest.firebase;

import com.example.iotassistantrest.firebase.body.MessageBody;
import com.example.iotassistantrest.firebase.body.Notification;
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
class FirebaseFCMHttpClientService {
    private static final Logger log = LoggerFactory.getLogger(FirebaseFCMHttpClientService.class);

    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String KEY = "AAAA2dWLpSQ:APA91bGJrOvLvOjkVlpfO7ytGEVD7nvZFt4xmwDXbbioAFVU1ZgbFddLGjX3eiUI6wPLGPoq-426n4IKgvFD_kcyj-peVAoIRPkx49DyH5XItVOI5jJz_SrV6pkoRfiBo72B5Gl6vPks";

    private final String localServerId;

    FirebaseFCMHttpClientService(@Value(value = "${local-server.id}") final String localServerId) {
        this.localServerId = localServerId;
        log.info("LocalServerId = " + this.localServerId);
    }
    void push(String message) {
        String json = JSONUtils.objectToJson(new MessageBody()
                                                    .to("/topics/"+localServerId)
                                                    .notification(new Notification()
                                                                        .body(message)
                                                                        .title("IOT Assistant alert")
                                                    ));
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .headers("Content-Type","application/json", "Authorization", "key="+KEY)
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
}
