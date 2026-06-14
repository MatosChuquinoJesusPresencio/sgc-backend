package com.condominios.sgc.application.impl.autenticacion;

import com.condominios.sgc.application.usecase.autenticacion.SolicitarReseteoContrasenaUseCase;
import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class SolicitarReseteoContrasenaUseCaseImpl implements SolicitarReseteoContrasenaUseCase {
    private final UsuarioPort usuarioPort;
    private final TokenPort tokenPort;
    private final CorreoPort correoPort;

    public SolicitarReseteoContrasenaUseCaseImpl(UsuarioPort usuarioPort, TokenPort tokenPort, CorreoPort correoPort) {
        this.usuarioPort = usuarioPort;
        this.tokenPort = tokenPort;
        this.correoPort = correoPort;
    }

    @Override
    public void ejecutar(String correo) {
        UsuarioModel usuario = usuarioPort.obtenerPorCorreo(correo)
            .orElseThrow(UsuarioException::noEncontrado);

        TokenModel token = tokenPort.generarToken(TipoToken.REESTABLECIMIENTO, usuario.getId());

        correoPort.enviarReseteoContrasena(usuario.getCorreo(), usuario.getNombres(), token.getToken());
    }
}
