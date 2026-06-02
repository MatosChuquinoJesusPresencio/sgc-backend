package com.condominios.sgc.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ResendClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String from;

    public ResendClient(
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from}") String from) {
        this.apiKey = apiKey;
        this.from = from;
        this.restTemplate = new RestTemplate();
    }

    public void sendEmail(String to, String subject, String html) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = Map.of(
            "from", from,
            "to", new String[]{to},
            "subject", subject,
            "html", html
        );

        var request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("https://api.resend.com/emails", request, String.class);
    }
}
