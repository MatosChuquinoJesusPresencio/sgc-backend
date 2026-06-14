package com.condominios.sgc.infrastructure.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String rol = jwt.getClaimAsString("rol");
        String correo = jwt.getClaimAsString("correo");
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));
        return new JwtAuthenticationToken(jwt, authorities, correo);
    }
}
