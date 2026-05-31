package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.usecase.CambiarContrasenaUseCase;
import com.condominios.sgc.application.usecase.CerrarSesionUseCase;
import com.condominios.sgc.application.usecase.IniciarSesionUseCase;
import com.condominios.sgc.application.usecase.RefrescarTokenUseCase;
import com.condominios.sgc.domain.auxiliar.LoginCompleta;

@Service
public class AutenticacionService {

    private final IniciarSesionUseCase iniciarSesionUseCase;
    private final CerrarSesionUseCase cerrarSesionUseCase;
    private final RefrescarTokenUseCase refrescarTokenUseCase;
    private final CambiarContrasenaUseCase cambiarContrasenaUseCase;

    public AutenticacionService(
            IniciarSesionUseCase iniciarSesionUseCase,
            CerrarSesionUseCase cerrarSesionUseCase,
            RefrescarTokenUseCase refrescarTokenUseCase,
            CambiarContrasenaUseCase cambiarContrasenaUseCase) {
        this.iniciarSesionUseCase = iniciarSesionUseCase;
        this.cerrarSesionUseCase = cerrarSesionUseCase;
        this.refrescarTokenUseCase = refrescarTokenUseCase;
        this.cambiarContrasenaUseCase = cambiarContrasenaUseCase;
    }

    public LoginCompleta iniciarSesion(String correo, String contrasena) {
        return iniciarSesionUseCase.ejecutar(correo, contrasena);
    }

    public void cerrarSesion(String accessToken) {
        cerrarSesionUseCase.ejecutar(accessToken);
    }

    public LoginCompleta refrescarToken(String refreshToken) {
        return refrescarTokenUseCase.ejecutar(refreshToken);
    }

    public void cambiarContrasena(Long userId, String currentPassword, String newPassword) {
        cambiarContrasenaUseCase.ejecutar(userId, currentPassword, newPassword);
    }
}
