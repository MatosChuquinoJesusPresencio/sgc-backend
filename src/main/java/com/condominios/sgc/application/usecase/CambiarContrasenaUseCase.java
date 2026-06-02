package com.condominios.sgc.application.usecase;

public interface CambiarContrasenaUseCase {
    void ejecutar(Long userId, String currentPassword, String newPassword);
}
