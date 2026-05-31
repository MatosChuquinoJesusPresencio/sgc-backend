package com.condominios.sgc.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.condominios.sgc.domain.auxiliar.Rol;

public final class SecurityUtil {

    private SecurityUtil() {}

    public static String getAccessToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        return jwt.getTokenValue();
    }

    public static Rol obtenerRolAutenticado() {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return Rol.valueOf(authorities.stream()
            .filter(a -> a.getAuthority().startsWith("ROLE_"))
            .findFirst().orElseThrow()
            .getAuthority().substring(5));
    }
}
