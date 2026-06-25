package com.condominios.sgc.infrastructure.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;

import com.condominios.sgc.application.port.out.service.CorreoServicePort;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class CorreoServiceAdapter implements CorreoServicePort {

    private final RestTemplate restTemplate;
    private final SpringTemplateEngine templateEngine;
    private final String apiKey;
    private final String from;
    private final String baseUrl;

    public CorreoServiceAdapter(
            RestTemplate restTemplate,
            SpringTemplateEngine templateEngine,
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from}") String from,
            @Value("${app.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.templateEngine = templateEngine;
        this.apiKey = apiKey;
        this.from = from;
        this.baseUrl = baseUrl;
    }

    @Override
    public void enviarBienvenida(String destino, String nombreCompleto, String contrasena) {
        var context = new Context();
        context.setVariable("nombre", nombreCompleto);
        context.setVariable("contrasena", contrasena);
        String html = templateEngine.process("email/bienvenida", context);
        enviarCorreo(destino, "Bienvenido al Sistema de Gesti\u00f3n de Condominios", html);
    }

    @Override
    public void enviarRestablecimiento(String destino, String nombreCompleto, String token) {
        var context = new Context();
        context.setVariable("nombre", nombreCompleto);
        context.setVariable("enlace", baseUrl + "/reset-password?token=" + token);
        String html = templateEngine.process("email/reseteo-contrasena", context);
        enviarCorreo(destino, "Restablece tu contrase\u00f1a", html);
    }

    @Override
    public void enviarVerificacion(String destino, String nombreCompleto, String token) {
        var context = new Context();
        context.setVariable("nombre", nombreCompleto);
        context.setVariable("enlace", baseUrl + "/api/auth/verificar-email?token=" + token);
        String html = templateEngine.process("email/verificacion", context);
        enviarCorreo(destino, "Verifica tu correo electr\u00f3nico", html);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    private void enviarCorreo(String destino, String asunto, String html) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, String> body = new HashMap<>();
        body.put("from", Objects.requireNonNullElse(from, ""));
        body.put("to", Objects.requireNonNullElse(destino, ""));
        body.put("subject", Objects.requireNonNullElse(asunto, ""));
        body.put("html", Objects.requireNonNullElse(html, ""));

        restTemplate.postForEntity(
            "https://api.resend.com/emails",
            new HttpEntity<>(body, headers),
            String.class
        );
    }
}
