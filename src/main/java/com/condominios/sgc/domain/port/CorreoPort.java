package com.condominios.sgc.domain.port;

public interface CorreoPort {

    void enviarVerificacionCorreo(String destinatario, String token);

    void enviarRestablecimientoContrasena(String destinatario, String token);

    void enviarBienvenida(String destinatario, String nombres, String password);
}
