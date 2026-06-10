package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.infrastructure.client.ResendClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorreoAdapter implements CorreoPort {

    private final ResendClient resendClient;
    private final String baseUrl;

    public CorreoAdapter(ResendClient resendClient, @Value("${app.base-url}") String baseUrl) {
        this.resendClient = resendClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public void sendVerificationEmail(String recipient, String token) {
        var link = baseUrl + "/verificar-email?token=" + token;
        var html = "<h1>Verifica tu correo</h1>"
                 + "<p>Haz clic en el siguiente enlace para verificar tu direccion de correo:</p>"
                 + "<a href=\"" + link + "\">" + link + "</a>";
        resendClient.sendEmail(recipient, "Verifica tu correo electronico", html);
    }

    @Override
    public void sendPasswordResetEmail(String recipient, String token) {
        var link = baseUrl + "/reset-password?token=" + token;
        var html = "<h1>Restablece tu contrasena</h1>"
                 + "<p>Haz clic en el siguiente enlace para restablecer tu contrasena:</p>"
                 + "<a href=\"" + link + "\">" + link + "</a>";
        resendClient.sendEmail(recipient, "Restablece tu contrasena", html);
    }

    @Override
    public void sendWelcomeEmail(String recipient, String names, String password) {
        var html = "<h1>Bienvenido al Sistema de Gestion de Condominios</h1>"
                 + "<p>Hola " + names + ", tu cuenta ha sido creada exitosamente.</p>"
                 + "<p>Tu contrasena temporal es: <strong>" + password + "</strong></p>"
                 + "<p>Por favor cambia tu contrasena al iniciar sesion.</p>";
        resendClient.sendEmail(recipient, "Bienvenido al Sistema de Gestion de Condominios", html);
    }
}
