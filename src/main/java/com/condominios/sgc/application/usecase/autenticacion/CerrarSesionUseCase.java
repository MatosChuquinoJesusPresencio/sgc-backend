package com.condominios.sgc.application.usecase.autenticacion;

public interface CerrarSesionUseCase {
    void ejecutar(String accessToken, String refreshToken);
}
