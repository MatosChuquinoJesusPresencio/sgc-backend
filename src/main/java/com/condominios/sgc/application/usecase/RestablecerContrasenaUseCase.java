package com.condominios.sgc.application.usecase;

public interface RestablecerContrasenaUseCase {
    void ejecutar(String token, String nuevaContrasena);
}
