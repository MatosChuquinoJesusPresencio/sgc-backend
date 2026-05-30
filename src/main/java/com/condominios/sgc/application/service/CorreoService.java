package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.usecase.EnviarCorreoBienvenidaUseCase;
import com.condominios.sgc.application.usecase.EnviarCorreoRecuperacionUseCase;
import com.condominios.sgc.application.usecase.EnviarCorreoVerificacionUseCase;

@Service
public class CorreoService {

    private final EnviarCorreoBienvenidaUseCase enviarCorreoBienvenidaUseCase;
    private final EnviarCorreoVerificacionUseCase enviarCorreoVerificacionUseCase;
    private final EnviarCorreoRecuperacionUseCase enviarCorreoRecuperacionUseCase;

    public CorreoService(
            EnviarCorreoBienvenidaUseCase enviarCorreoBienvenidaUseCase,
            EnviarCorreoVerificacionUseCase enviarCorreoVerificacionUseCase,
            EnviarCorreoRecuperacionUseCase enviarCorreoRecuperacionUseCase) {
        this.enviarCorreoBienvenidaUseCase = enviarCorreoBienvenidaUseCase;
        this.enviarCorreoVerificacionUseCase = enviarCorreoVerificacionUseCase;
        this.enviarCorreoRecuperacionUseCase = enviarCorreoRecuperacionUseCase;
    }

    public void enviarBienvenida(String to, String nombres, String contrasena) {
        enviarCorreoBienvenidaUseCase.ejecutar(to, nombres, contrasena);
    }

    public void enviarVerificacion(String to, String token) {
        enviarCorreoVerificacionUseCase.ejecutar(to, token);
    }

    public void enviarRecuperacion(String to, String token) {
        enviarCorreoRecuperacionUseCase.ejecutar(to, token);
    }
}
