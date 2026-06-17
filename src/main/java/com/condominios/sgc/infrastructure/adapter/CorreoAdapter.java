package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.infrastructure.client.ResendClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class CorreoAdapter implements CorreoPort {

    private final ResendClient resendClient;
    private final SpringTemplateEngine templateEngine;
    private final String baseUrl;

    public CorreoAdapter(ResendClient resendClient,
                         SpringTemplateEngine templateEngine,
                         @Value("${app.base-url}") String baseUrl) {
        this.resendClient = resendClient;
        this.templateEngine = templateEngine;
        this.baseUrl = baseUrl;
    }

    @Override
    public void enviarEmailVerificacion(String destinatario, String nombre, String token) {
        Context context = new Context();
        context.setVariable("nombre", nombre);
        context.setVariable("enlace", baseUrl + "/verificar-correo?token=" + token);
        String html = templateEngine.process("email/verificacion", context);
        resendClient.enviar(destinatario, "Verifica tu correo electrónico", html);
    }

    @Override
    public void enviarReseteoContrasena(String destinatario, String nombre, String token) {
        Context context = new Context();
        context.setVariable("nombre", nombre);
        context.setVariable("enlace", baseUrl + "/restablecer-contrasena?token=" + token);
        String html = templateEngine.process("email/reseteo-contrasena", context);
        resendClient.enviar(destinatario, "Restablece tu contraseña", html);
    }

    @Override
    public void enviarBienvenida(String destinatario, String nombre, String contrasena) {
        Context context = new Context();
        context.setVariable("nombre", nombre);
        context.setVariable("contrasena", contrasena);
        String html = templateEngine.process("email/bienvenida", context);
        resendClient.enviar(destinatario, "¡Bienvenido!", html);
    }
}
