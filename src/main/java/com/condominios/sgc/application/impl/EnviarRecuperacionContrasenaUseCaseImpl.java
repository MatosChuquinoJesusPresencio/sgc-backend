package com.condominios.sgc.application.impl;

import java.time.Instant;
import java.util.UUID;

import com.condominios.sgc.application.usecase.EnviarRecuperacionContrasenaUseCase;
import com.condominios.sgc.domain.model.RestablecimientoTokenModel;
import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.domain.port.RestablecimientoTokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class EnviarRecuperacionContrasenaUseCaseImpl implements EnviarRecuperacionContrasenaUseCase {

    private final UsuarioPort usuarioPort;
    private final RestablecimientoTokenPort restablecimientoTokenPort;
    private final CorreoPort correoPort;

    public EnviarRecuperacionContrasenaUseCaseImpl(
            UsuarioPort usuarioPort,
            RestablecimientoTokenPort restablecimientoTokenPort,
            CorreoPort correoPort) {
        this.usuarioPort = usuarioPort;
        this.restablecimientoTokenPort = restablecimientoTokenPort;
        this.correoPort = correoPort;
    }

    @Override
    public void ejecutar(String email) {
        var usuarioOpt = usuarioPort.findByCorreo(email);
        if (usuarioOpt.isEmpty()) return;

        var usuario = usuarioOpt.get();
        String resetToken = UUID.randomUUID().toString();

        var tokenModel = new RestablecimientoTokenModel(
            UUID.randomUUID().toString(),
            usuario.getId(),
            resetToken,
            Instant.now().plusSeconds(3600)
        );
        restablecimientoTokenPort.save(tokenModel);

        correoPort.enviarRestablecimientoContrasena(email, resetToken);
    }
}
