package com.condominios.sgc.application.impl.autenticacion;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.condominios.sgc.application.dto.command.SolicitarReseteoContrasenaCommand;
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
    public void ejecutar(SolicitarReseteoContrasenaCommand command) {
        UsuarioModel usuario = usuarioPort.obtenerPorCorreo(command.correo())
            .orElseThrow(UsuarioException::noEncontrado);

        String tokenStr = UUID.randomUUID().toString();
        TokenModel token = new TokenModel(TipoToken.REESTABLECIMIENTO, tokenStr,
            Instant.now().plus(1, ChronoUnit.HOURS), usuario.getId());
        tokenPort.guardar(token);

        correoPort.enviarReseteoContrasena(usuario.getCorreo(), usuario.getNombres(), tokenStr);
    }
}
