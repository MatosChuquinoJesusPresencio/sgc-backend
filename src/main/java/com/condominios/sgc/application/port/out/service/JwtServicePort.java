package com.condominios.sgc.application.port.out.service;

import com.condominios.sgc.domain.type.Rol;
import io.jsonwebtoken.Claims;

import java.time.Instant;

public interface JwtServicePort {
    String generarTokenAcceso(Long usuarioId, String correo, Rol rol);
    String generarTokenRefresco(Long usuarioId, boolean recuerdame);
    String generarTokenVerificacion(Long usuarioId);
    String generarTokenRestablecimiento(Long usuarioId);
    Claims validarToken(String token);
    Long obtenerUsuarioId(String token);
    Instant obtenerExpiracion(String token);
}
