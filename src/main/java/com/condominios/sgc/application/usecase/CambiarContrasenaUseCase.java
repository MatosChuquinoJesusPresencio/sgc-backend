package com.condominios.sgc.application.usecase;

public interface CambiarContrasenaUseCase {
    void ejecutar(String userId, String currentPassword, String newPassword);
}
