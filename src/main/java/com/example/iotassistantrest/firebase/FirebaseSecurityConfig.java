package com.example.iotassistantrest.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;


public class FirebaseSecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(FirebaseSecurityConfig.class);

    private static final Resource file = new ClassPathResource("keystore/iot-assistant-81581-firebase-adminsdk-zsdw8-7bd502512f.json");

    public static String getFirebaseToken() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(file.getInputStream()).createScoped(
                    Arrays.asList(
                            "https://www.googleapis.com/auth/firebase.messaging",
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
