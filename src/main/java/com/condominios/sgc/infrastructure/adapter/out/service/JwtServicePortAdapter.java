package com.condominios.sgc.infrastructure.adapter.out.service;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.domain.type.TipoToken;
import com.condominios.sgc.infrastructure.adapter.out.util.JwtUtil;

@Component
public class JwtServicePortAdapter implements JwtServicePort {

    private final JwtUtil jwtUtil;

    public JwtServicePortAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String generarTokenAcceso(Long idUsuario, String correo, Rol rol) {
        return jwtUtil.generateAccessToken(idUsuario.toString(), correo, rol.name());
    }

    @Override
    public String generarTokenRefresco(Long idUsuario, Boolean recuerdame) {
        if (recuerdame != null && recuerdame) {
            return jwtUtil.generateRefreshToken(idUsuario.toString(), jwtUtil.getRememberMeRefreshExpiration());
        }
        return jwtUtil.generateRefreshToken(idUsuario.toString());
    }

    @Override
    public String generarToken(Long idUsuario, TipoToken tipo) {
        long expiration = obtenerExpiracionMs(tipo);
        return jwtUtil.generateToken(idUsuario.toString(), expiration);
    }

    @Override
    public boolean validar(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Instant obtenerExpiracion(TipoToken tipo) {
        return Instant.now().plusMillis(obtenerExpiracionMs(tipo));
    }

    private long obtenerExpiracionMs(TipoToken tipo) {
        return switch (tipo) {
            case ACCESO -> jwtUtil.getAccessTokenExpiration();
            case REFRESCO -> jwtUtil.getRefreshTokenExpiration();
            case REESTABLECIMIENTO -> jwtUtil.getResetPasswordTokenExpiration();
            case VERIFICACION -> jwtUtil.getVerifyEmailTokenExpiration();
        };
    }
}
