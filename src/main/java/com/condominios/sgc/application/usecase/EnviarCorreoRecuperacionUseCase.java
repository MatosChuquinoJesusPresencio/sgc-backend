package com.condominios.sgc.application.usecase;

public interface EnviarCorreoRecuperacionUseCase {
    void ejecutar(String to, String token);
}
