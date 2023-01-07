package com.example.iotassistantrest.admin.config;

import com.example.iotassistantrest.utils.JSONUtils;
import com.google.api.client.json.webtoken.JsonWebToken;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtParser;
import io.jsonwebtoken.impl.JwtMap;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class MobileTokenFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MobileTokenFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String auth = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(auth);
        Jwt jwt = new DefaultJwtParser().parse(auth);
        String kid = jwt.getHeader().get("kid").toString();

        String[] chunks = auth.split(".//");
        SignatureAlgorithm sa = SignatureAlgorithm.RS256;
        String tokenWithoutSignature = chunks[0] + '.' + chunks[1];
        String signature = chunks[2];

        SecretKeySpec secretKeySpec = new SecretKeySpec(getKey(kid), sa.getJcaName());
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa,secretKeySpec);
        if(!validator.isValid(tokenWithoutSignature, signature)) {
            log.error("Token is invalid!!");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(request, response);
    }

    private byte[] getKey(String key) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com"))
                    .GET().build();

            var response =  httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Map<String,String > map = JSONUtils.jsonToMap(String.valueOf(response));
            return map.get(key).getBytes();
        } catch (Exception e) {
            log.error("Error getting firebase key to verify signature");
            throw new RuntimeException();
        }
    }
}
