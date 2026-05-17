package com.condominios.sgc.application.impl;

import java.time.Instant;

import com.condominios.sgc.application.usecase.RestablecerContrasenaUseCase;
import com.condominios.sgc.domain.exception.AutenticacionException;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.RestablecimientoTokenPort;

public class RestablecerContrasenaUseCaseImpl implements RestablecerContrasenaUseCase {

    private final AutenticacionPort autenticacionPort;
    private final RestablecimientoTokenPort restablecimientoTokenPort;

    public RestablecerContrasenaUseCaseImpl(
            AutenticacionPort autenticacionPort,
            RestablecimientoTokenPort restablecimientoTokenPort) {
        this.autenticacionPort = autenticacionPort;
        this.restablecimientoTokenPort = restablecimientoTokenPort;
    }

    @Override
    public void ejecutar(String token, String nuevaContrasena) {
        var tokenModel = restablecimientoTokenPort.findByTokenAndUsadoFalse(token)
            .orElseThrow(() -> AutenticacionException.errorAutenticacion("Token inválido o expirado"));

        if (tokenModel.getExpiracion().isBefore(Instant.now())) {
            throw AutenticacionException.errorAutenticacion("Token inválido o expirado");
        }

        autenticacionPort.actualizarPasswordAdmin(tokenModel.getUsuarioId(), nuevaContrasena);

        tokenModel.setUsado(true);
        restablecimientoTokenPort.save(tokenModel);
    }
}
