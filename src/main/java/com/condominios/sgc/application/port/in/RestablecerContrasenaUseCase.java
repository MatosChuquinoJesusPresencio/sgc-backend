package com.condominios.sgc.application.port.in;

public interface RestablecerContrasenaUseCase {
    void ejecutar(String tokenRefresco, String contrasena);
}
