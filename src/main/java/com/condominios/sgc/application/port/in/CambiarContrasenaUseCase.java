package com.condominios.sgc.application.port.in;

public interface CambiarContrasenaUseCase {
    void ejecutar(String contrasenaActual, String nuevaContrasena);
}
