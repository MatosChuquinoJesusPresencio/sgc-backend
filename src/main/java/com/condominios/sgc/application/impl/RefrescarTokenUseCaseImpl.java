package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.SesionCompleta;
import com.condominios.sgc.application.usecase.RefrescarTokenUseCase;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class RefrescarTokenUseCaseImpl implements RefrescarTokenUseCase {

    private final AutenticacionPort autenticacionPort;
    private final UsuarioPort usuarioPort;

    public RefrescarTokenUseCaseImpl(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort) {
        this.autenticacionPort = autenticacionPort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public SesionCompleta ejecutar(String refreshToken) {
        var sesion = autenticacionPort.refrescarToken(refreshToken);
        var usuario = usuarioPort.findById(sesion.usuario().id())
            .orElseThrow(AutenticacionException::usuarioNoRegistrado);
        return new SesionCompleta(sesion, usuario);
    }
}
