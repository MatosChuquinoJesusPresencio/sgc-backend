package com.condominios.sgc.infrastructure.client;

import com.condominios.sgc.domain.exception.CorreoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Component
public class ResendClient {

    private final String apiKey;
    private final String from;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ResendClient(
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from}") String from) {
        this.apiKey = apiKey;
        this.from = from;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void enviar(String destinatario, String asunto, String html) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "from", from,
                "to", List.of(destinatario),
                "subject", asunto,
                "html", html);

        String json;
        try {
            json = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw CorreoException.errorInterno("serializando payload", e);
        }

        try {
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.resend.com/emails", request, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw CorreoException.errorEnvio(destinatario,
                        "código " + response.getStatusCode());
            }
        } catch (CorreoException e) {
            throw e;
        } catch (Exception e) {
            throw CorreoException.errorEnvio(destinatario, e);
        }
    }
}
