package com.condominios.sgc.application.port.out.service;

import java.time.Instant;

import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.domain.type.TipoToken;

public interface JwtServicePort {
    String generarTokenAcceso(Long idUsuario, String correo, Rol rol);
    String generarTokenRefresco(Long idUsuario, Boolean recuerdame);
    String generarToken(Long idUsuario, TipoToken tipo);
    boolean validar(String token);
    Instant obtenerExpiracion(TipoToken tipo);
}
