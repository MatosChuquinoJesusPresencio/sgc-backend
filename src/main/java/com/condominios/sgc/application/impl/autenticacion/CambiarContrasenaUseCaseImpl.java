package com.condominios.sgc.application.impl.autenticacion;

import com.condominios.sgc.application.dto.command.CambiarContrasenaCommand;
import com.condominios.sgc.application.usecase.autenticacion.CambiarContrasenaUseCase;
import org.springframework.transaction.annotation.Transactional;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;

@Transactional
public class CambiarContrasenaUseCaseImpl implements CambiarContrasenaUseCase {
    private final UsuarioPort usuarioPort;
    private final AutenticacionPort autenticacionPort;

    public CambiarContrasenaUseCaseImpl(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        this.usuarioPort = usuarioPort;
        this.autenticacionPort = autenticacionPort;
    }

    @Override
    public void ejecutar(CambiarContrasenaCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(command.idUsuario())
            .orElseThrow(UsuarioException::noEncontrado);

        if (!autenticacionPort.verificarContrasena(command.contrasenaActual(), usuario.getContrasena())) {
            throw UsuarioException.credencialesInvalidas();
        }

        usuario.cambiarContrasena(autenticacionPort.hashContrasena(command.nuevaContrasena()));
        usuarioPort.guardar(usuario);
    }
}
