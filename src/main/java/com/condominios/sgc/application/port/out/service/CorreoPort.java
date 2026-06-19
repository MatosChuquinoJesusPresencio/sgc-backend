package com.condominios.sgc.application.port.out.service;

import com.condominios.sgc.domain.shared.value_objects.Correo;

public interface CorreoPort {
    void enviarBienvenida(Correo destino, String nombre, String contrasena);
    void enviarVerificacion(Correo destino, String token);
    void enviarRestablecimiento(Correo destino, String token);
}
