package com.condominios.sgc.application.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.condominios.sgc.application.usecase.EnviarRecuperacionContrasenaUseCase;
import com.condominios.sgc.domain.model.RestablecimientoTokenModel;
import com.condominios.sgc.domain.port.RestablecimientoTokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class EnviarRecuperacionContrasenaUseCaseImpl implements EnviarRecuperacionContrasenaUseCase {

    private final UsuarioPort usuarioPort;
    private final RestablecimientoTokenPort restablecimientoTokenPort;

    public EnviarRecuperacionContrasenaUseCaseImpl(
            UsuarioPort usuarioPort,
            RestablecimientoTokenPort restablecimientoTokenPort) {
        this.usuarioPort = usuarioPort;
        this.restablecimientoTokenPort = restablecimientoTokenPort;
    }

    @Override
    public Optional<String> ejecutar(String email, String resetToken) {
        var usuarioOpt = usuarioPort.findByCorreo(email);
        if (usuarioOpt.isEmpty()) return Optional.empty();

        var usuario = usuarioOpt.get();

        var tokenModel = new RestablecimientoTokenModel(
            UUID.randomUUID().toString(),
            usuario.getId(),
            resetToken,
            Instant.now().plusSeconds(3600)
        );
        restablecimientoTokenPort.save(tokenModel);

        return Optional.of(resetToken);
    }
}
