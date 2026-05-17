package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.VerificarCorreoUseCase;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class VerificarCorreoUseCaseImpl implements VerificarCorreoUseCase {

    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;

    public VerificarCorreoUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public UsuarioModel ejecutar(String usuarioId) {
        var usuario = usuarioPort.findById(usuarioId)
            .orElseThrow(UsuarioException::noEncontrado);

        if (usuario.getCorreoPendiente() == null) {
            throw UsuarioException.correoPendienteNoEncontrado();
        }

        var supabaseUser = autenticacionPort.obtenerUsuarioSupabase(usuarioId);
        String emailEnSupabase = (String) supabaseUser.get("email");

        if (!usuario.getCorreoPendiente().equals(emailEnSupabase)) {
            throw UsuarioException.correoNoVerificado();
        }

        usuario.confirmarCorreo();

        return usuarioPort.save(usuario);
    }
}
