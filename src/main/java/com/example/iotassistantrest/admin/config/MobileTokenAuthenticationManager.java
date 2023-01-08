package com.example.iotassistantrest.admin.config;

import com.example.iotassistantrest.utils.JSONUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;

public class MobileTokenAuthenticationManager implements AuthenticationManager {
    private static final Logger log = LoggerFactory.getLogger(MobileTokenFilter.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication auth = (JwtAuthentication) authentication;
        MobileToken token = auth.getMobileToken();
        String kid = token.getKID();
        String[] chunks = token.getValue().split("\\.");

        String tokenWithoutSignature = chunks[0] + '.' + chunks[1];
        String signature = chunks[2];

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(getKey(kid)));
            PublicKey pk = certificate.getPublicKey();

            DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(SignatureAlgorithm.RS256, pk);

            if(!validator.isValid(tokenWithoutSignature, signature)) {
                log.error("Token is invalid!!");
                throw new BadCredentialsException("Invalid token");
            }
        } catch (NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
        return authentication;
    }



    private byte[] getKey(String key) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com"))
                    .GET().build();

            var response =  httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Map<String,String > map = JSONUtils.jsonToMap(response.body());
            return map.get(key).getBytes();
        } catch (Exception e) {
            log.error("Error getting firebase key to verify signature");
            throw new RuntimeException();
        }
    }
}
