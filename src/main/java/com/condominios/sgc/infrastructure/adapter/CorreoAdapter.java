package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.infrastructure.client.ResendClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CorreoAdapter implements CorreoPort {

    private final ResendClient resendClient;
    private final String baseUrl;

    public CorreoAdapter(ResendClient resendClient,
                         @Value("${app.base-url}") String baseUrl) {
        this.resendClient = resendClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public void enviarEmailVerificacion(String destinatario, String nombre, String token) {
        String asunto = "Verifica tu correo electrónico";
        String enlace = baseUrl + "/auth/verificar?token=" + token;
        String html = """
                <h2>Verifica tu correo electrónico</h2>
                <p>Hola <strong>%s</strong>,</p>
                <p>Haz clic en el siguiente enlace para verificar tu cuenta:</p>
                <p><a href="%s">Verificar correo</a></p>
                <p>Si no solicitaste esto, ignora este mensaje.</p>
                """.formatted(nombre, enlace);
        resendClient.enviar(destinatario, asunto, html);
    }

    @Override
    public void enviarReseteoContrasena(String destinatario, String nombre, String token) {
        String asunto = "Restablece tu contraseña";
        String enlace = baseUrl + "/auth/resetear?token=" + token;
        String html = """
                <h2>Restablece tu contraseña</h2>
                <p>Hola <strong>%s</strong>,</p>
                <p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>
                <p><a href="%s">Restablecer contraseña</a></p>
                <p>Si no solicitaste esto, ignora este mensaje.</p>
                """.formatted(nombre, enlace);
        resendClient.enviar(destinatario, asunto, html);
    }

    @Override
    public void enviarBienvenida(String destinatario, String nombre) {
        String asunto = "¡Bienvenido!";
        String html = """
                <h2>¡Bienvenido, %s!</h2>
                <p>Tu cuenta ha sido creada exitosamente.</p>
                <p>Ya puedes iniciar sesión y comenzar a usar la plataforma.</p>
                """.formatted(nombre);
        resendClient.enviar(destinatario, asunto, html);
    }
}
