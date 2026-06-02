package com.condominios.sgc.infrastructure.util;

import com.condominios.sgc.domain.auxiliar.Rol;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Long obtenerIdUsuario() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return Long.parseLong(jwt.getSubject());
        }
        throw new IllegalStateException("No se pudo determinar el ID del usuario autenticado");
    }

    public static Rol obtenerRolAutenticado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return Rol.valueOf(jwt.getClaimAsString("rol"));
        }
        throw new IllegalStateException("No se pudo determinar el rol del usuario autenticado");
    }
}
