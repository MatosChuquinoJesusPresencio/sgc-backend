package com.condominios.sgc.application.port.out.service;

public interface CorreoServicePort {
    void enviarBienvenida(String destinatario, String nombre, String contrasenaTemp);
    void enviarVerificacion(String destinatario, String token);
    void enviarResetContrasena(String destinatario, String token);
}
