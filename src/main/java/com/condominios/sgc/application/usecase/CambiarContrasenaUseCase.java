package com.condominios.sgc.application.usecase;

public interface CambiarContrasenaUseCase {
    void ejecutar(String email, String currentPassword, String newPassword);
}
