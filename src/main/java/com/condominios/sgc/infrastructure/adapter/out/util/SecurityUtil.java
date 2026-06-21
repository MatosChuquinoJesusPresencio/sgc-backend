package com.condominios.sgc.infrastructure.adapter.out.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.condominios.sgc.domain.type.Rol;

@Component
public class SecurityUtil {

    public Long obtenerIdUsuario() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return Long.parseLong(jwt.getSubject());
        }
        throw new IllegalStateException("No se pudo determinar el ID del usuario autenticado");
    }

    public Rol obtenerRolAutenticado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return Rol.valueOf(jwt.getClaimAsString("rol"));
        }
        throw new IllegalStateException("No se pudo determinar el rol del usuario autenticado");
    }
}
