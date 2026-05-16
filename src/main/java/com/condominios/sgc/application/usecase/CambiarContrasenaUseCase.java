package com.condominios.sgc.application.usecase;

public interface CambiarContrasenaUseCase {
    void ejecutar(String accessToken, String nuevaContrasena);
}
