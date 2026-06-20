package com.condominios.sgc.infrastructure.security;

import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.type.Rol;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServicePort jwtService;

    public JwtAuthenticationFilter(JwtServicePort jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws ServletException, IOException {
        var token = extraerToken(request);
        if (token != null) {
            try {
                var claims = jwtService.validarToken(token);
                var usuarioId = Long.parseLong(claims.getSubject());
                var correo = claims.get("correo", String.class);
                var rol = Rol.valueOf(claims.get("rol", String.class));

                var auth = new UsernamePasswordAuthenticationToken(
                        new UsuarioAuth(usuarioId, correo, rol),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }

    private String extraerToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    var valor = cookie.getValue();
                    if (!valor.isBlank()) return valor;
                }
            }
        }
        var header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
