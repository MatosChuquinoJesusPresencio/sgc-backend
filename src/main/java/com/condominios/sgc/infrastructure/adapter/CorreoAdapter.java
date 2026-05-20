package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.infrastructure.client.SmtpClient;

public class CorreoAdapter implements CorreoPort {

    private final SmtpClient smtpClient;
    private final String baseUrl;

    public CorreoAdapter(SmtpClient smtpClient, String baseUrl) {
        this.smtpClient = smtpClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public void enviarVerificacionCorreo(String destinatario, String token) {
        var cuerpo = String.format("""
            Has solicitado cambiar tu correo electrónico.

            Para verificar tu nuevo correo, haz clic en el siguiente enlace:
            %s/api/auth/verificar-email?token=%s

            Si no solicitaste este cambio, ignora este mensaje.

            El enlace expirará en 1 hora.
            """, baseUrl, token);
        smtpClient.enviar(destinatario, "Verifica tu nuevo correo electrónico", cuerpo);
    }

    @Override
    public void enviarBienvenida(String destinatario, String nombres, String password) {
        var cuerpo = String.format("""
            Hola %s,

            Tu cuenta ha sido creada exitosamente en el Sistema de Gestión de Condominios.

            Tus credenciales de acceso son:
              Correo: %s
              Contraseña: %s

            Recomendamos cambiar tu contraseña después de iniciar sesión.

            Saludos,
            El equipo SGC
            """, nombres, destinatario, password);
        smtpClient.enviar(destinatario, "Bienvenido al Sistema de Gestión de Condominios", cuerpo);
    }

    @Override
    public void enviarRestablecimientoContrasena(String destinatario, String token) {
        var cuerpo = String.format("""
            Has solicitado restablecer tu contraseña.

            Tu token de restablecimiento es: %s

            Para restablecer tu contraseña, usa el siguiente comando:
            curl -X POST %s/api/auth/reset-password \\
              -H "Content-Type: application/json" \\
              -d '{"token": "%s", "password": "TU_NUEVA_CONTRASENA"}'

            Si no solicitaste esto, ignora este mensaje.

            El token expirará en 1 hora.
            """, token, baseUrl, token);
        smtpClient.enviar(destinatario, "Restablece tu contraseña", cuerpo);
    }
}
