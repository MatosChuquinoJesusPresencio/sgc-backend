package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.SesionCompleta;
import com.condominios.sgc.application.usecase.IniciarSesionUseCase;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class IniciarSesionUseCaseImpl implements IniciarSesionUseCase {

    private final AutenticacionPort autenticacionPort;
    private final UsuarioPort usuarioPort;

    public IniciarSesionUseCaseImpl(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort) {
        this.autenticacionPort = autenticacionPort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public SesionCompleta ejecutar(String email, String password) {
        var sesion = autenticacionPort.iniciarSesion(email, password);
        var usuario = usuarioPort.findById(sesion.usuario().id())
            .orElseThrow(AutenticacionException::usuarioNoRegistrado);
        return new SesionCompleta(sesion, usuario);
    }
}
