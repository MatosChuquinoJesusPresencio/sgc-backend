package com.condominios.sgc.infrastructure.client;

import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.infrastructure.config.properties.AppProperties;
import com.condominios.sgc.infrastructure.config.properties.ResendProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Map;

@Component
public class ResendCorreoAdapter implements CorreoServicePort {

    private final RestTemplate restTemplate;
    private final SpringTemplateEngine templateEngine;
    private final ResendProperties resendProps;
    private final AppProperties appProps;

    public ResendCorreoAdapter(RestTemplate restTemplate,
                                SpringTemplateEngine templateEngine,
                                ResendProperties resendProps,
                                AppProperties appProps) {
        this.restTemplate = restTemplate;
        this.templateEngine = templateEngine;
        this.resendProps = resendProps;
        this.appProps = appProps;
    }

    @Override
    public void enviarBienvenida(String destinatario, String nombre, String contrasenaTemp) {
        var context = new Context();
        context.setVariable("nombre", nombre);
        context.setVariable("contrasena", contrasenaTemp);
        var html = templateEngine.process("email/bienvenida", context);
        enviar(destinatario, "Bienvenido al Sistema de Gestión de Condominios", html);
    }

    @Override
    public void enviarVerificacion(String destinatario, String token) {
        var enlace = appProps.getBaseUrl() + "/verificar-correo?token=" + token;
        var context = new Context();
        context.setVariable("nombre", destinatario);
        context.setVariable("enlace", enlace);
        var html = templateEngine.process("email/verificacion", context);
        enviar(destinatario, "Verifica tu correo electrónico", html);
    }

    @Override
    public void enviarResetContrasena(String destinatario, String token) {
        var enlace = appProps.getBaseUrl() + "/restablecer-contrasena?token=" + token;
        var context = new Context();
        context.setVariable("nombre", destinatario);
        context.setVariable("enlace", enlace);
        var html = templateEngine.process("email/reseteo-contrasena", context);
        enviar(destinatario, "Restablece tu contraseña", html);
    }

    private void enviar(String para, String asunto, String html) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(resendProps.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = Map.of(
                "from", resendProps.getFrom(),
                "to", List.of(para),
                "subject", asunto,
                "html", html
        );

        restTemplate.postForEntity(
                "https://api.resend.com/emails",
                new HttpEntity<>(body, headers),
                String.class);
    }
}
