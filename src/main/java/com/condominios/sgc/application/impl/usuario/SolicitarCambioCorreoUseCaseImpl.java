package com.condominios.sgc.application.impl.usuario;

import com.condominios.sgc.application.dto.command.SolicitarCambioCorreoCommand;
import com.condominios.sgc.application.usecase.usuario.SolicitarCambioCorreoUseCase;
import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class SolicitarCambioCorreoUseCaseImpl implements SolicitarCambioCorreoUseCase {
    private final UsuarioPort usuarioPort;
    private final TokenPort tokenPort;
    private final CorreoPort correoPort;

    public SolicitarCambioCorreoUseCaseImpl(UsuarioPort usuarioPort, TokenPort tokenPort, CorreoPort correoPort) {
        this.usuarioPort = usuarioPort;
        this.tokenPort = tokenPort;
        this.correoPort = correoPort;
    }

    @Override
    public void ejecutar(SolicitarCambioCorreoCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(command.idUsuario())
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.cambiarCorreo(command.nuevoCorreo());

        TokenModel token = tokenPort.generarToken(TipoToken.VERIFICACION, usuario.getId());

        correoPort.enviarEmailVerificacion(command.nuevoCorreo(), usuario.getNombres(), token.getToken());

        usuarioPort.guardar(usuario);
    }
}
