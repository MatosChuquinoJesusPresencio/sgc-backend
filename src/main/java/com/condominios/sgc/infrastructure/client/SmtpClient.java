package com.condominios.sgc.infrastructure.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SmtpClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String from;

    public SmtpClient(RestTemplate restTemplate, String apiKey, String from) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.from = from;
    }

    @Async
    public void enviar(String destinatario, String asunto, String cuerpo) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = new LinkedHashMap<String, Object>();
        body.put("from", from);
        body.put("to", List.of(destinatario));
        body.put("subject", asunto);
        body.put("text", cuerpo);

        restTemplate.postForEntity(
            "https://api.resend.com/emails",
            new HttpEntity<>(body, headers),
            Map.class);
    }
}
