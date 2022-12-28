package com.example.iotassistantrest.iot.controller;

import com.example.iotassistantrest.utils.JSONUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SonoffService {
    private static final Logger log = LoggerFactory.getLogger(SonoffService.class);
    private static final String SONOFF_URL = "http://%s:8081/zeroconf/switch";

    private final SonoffRepository repository;
    public String findDevice(String id) {
        Optional<Sonoff> sonoff = repository.findById(id);
        return sonoff.map(Sonoff::getDeviceLocalAddr).orElse(null);
    }

    public void switchLight(String devAddr, String mode) {
        String json = "{\"data\":{\"switch\":\"" + mode + "\"}}";
        log.info(json);
        try {
            String uri = String.format(
                    SONOFF_URL,
                    devAddr);
            log.info(uri);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .header("Content-Type","application/json")
                    .uri(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
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
