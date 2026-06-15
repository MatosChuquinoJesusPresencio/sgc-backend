package com.condominios.sgc.domain.port;

public interface CorreoPort {
    void enviarEmailVerificacion(String destinatario, String nombre, String token);
    void enviarReseteoContrasena(String destinatario, String nombre, String token);
    void enviarBienvenida(String destinatario, String nombre, String contrasena);
}
