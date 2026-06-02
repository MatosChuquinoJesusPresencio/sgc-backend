package com.condominios.sgc.application.usecase;

public interface EnviarCorreoBienvenidaUseCase {
    void ejecutar(String to, String nombres, String contrasena);
}
