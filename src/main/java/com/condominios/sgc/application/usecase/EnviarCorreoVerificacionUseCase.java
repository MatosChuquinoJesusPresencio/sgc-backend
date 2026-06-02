package com.condominios.sgc.application.usecase;

public interface EnviarCorreoVerificacionUseCase {
    void ejecutar(String to, String token);
}
