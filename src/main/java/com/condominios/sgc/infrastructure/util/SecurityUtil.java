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
            try {
                return Long.parseLong(jwt.getSubject());
            } catch (NumberFormatException | NullPointerException e) {
                throw AutenticacionException.tokenInvalido();
            }
        }
        throw AutenticacionException.usuarioNoAutenticado();
    }

    public Rol obtenerRolAutenticado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            try {
                return Rol.valueOf(jwt.getClaimAsString("rol"));
            } catch (IllegalArgumentException | NullPointerException e) {
                throw AutenticacionException.tokenInvalido();
            }
        }
        throw AutenticacionException.usuarioNoAutenticado();
    }
}
