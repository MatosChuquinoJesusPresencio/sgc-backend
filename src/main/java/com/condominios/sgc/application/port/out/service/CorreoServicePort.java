package com.condominios.sgc.application.port.out.service;

public interface CorreoServicePort {
    void enviarBienvenida(String destino, String nombreCompleto, String contrasena);
    void enviarRestablecimiento(String destino, String nombreCompleto, String token);
    void enviarVerificacion(String destino, String nombreCompleto, String token);
}
