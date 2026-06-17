package com.condominios.sgc.infrastructure.util;

import com.condominios.sgc.domain.auxiliar.Rol;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public record UsuarioJwt(
        Long idUsuario,
        String correo,
        String nombres,
        String apellidos,
        Rol rol,
        Long idCondominio
    ) {}

    public UsuarioJwt extraerUsuario(Jwt jwt) {
        return new UsuarioJwt(
                Long.parseLong(jwt.getSubject()),
                jwt.getClaimAsString("correo"),
                jwt.getClaimAsString("nombres"),
                jwt.getClaimAsString("apellidos"),
                Rol.valueOf(jwt.getClaimAsString("rol")),
                jwt.getClaim("idCondominio") != null
                        ? ((Number) jwt.getClaim("idCondominio")).longValue()
                        : null);
    }
}