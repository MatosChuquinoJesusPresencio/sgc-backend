package com.condominios.sgc.infrastructure.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.condominios.sgc.domain.shared.exception.AutenticacionException;
import com.condominios.sgc.domain.type.Rol;

@Component
public class SecurityUtil {

    public Long obtenerIdUsuario() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return Long.parseLong(jwt.getSubject());
        }
        throw AutenticacionException.usuarioNoAutenticado();
    }

    public Rol obtenerRolAutenticado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return Rol.valueOf(jwt.getClaimAsString("rol"));
        }
        throw AutenticacionException.usuarioNoAutenticado();
    }
}
