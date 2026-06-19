package com.condominios.sgc.application.port.out.service;

import com.condominios.sgc.domain.type.Rol;

public interface JwtPort {
    String generarAccessToken(Long usuarioId, Rol rol);
    String generarRefreshToken(Long usuarioId);
    Long obtenerUsuarioId(String token);
    boolean esValido(String token);
}
