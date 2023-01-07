package com.example.iotassistantrest.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Configuration
public class FirebaseSecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(FirebaseSecurityConfig.class);
    final Resource file;

    public FirebaseSecurityConfig(
            @Value("classpath:keystore/iot-assistant-81581-firebase-adminsdk-zsdw8-5f4cdc1b26.json") Resource file) {
        this.file = file;
    }

    @Bean(name = "firebaseToken")
    public String firebaseToken() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(file.getInputStream()).createScoped(
                    Arrays.asList("https://www.googleapis.com/auth/firebase.messaging",
                    "https://www.googleapis.com/auth/userinfo.email",
                    "https://www.googleapis.com/auth/firebase.database")
            );
            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
