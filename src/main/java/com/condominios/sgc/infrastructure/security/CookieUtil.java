package com.condominios.sgc.infrastructure.security;

import com.condominios.sgc.infrastructure.config.properties.CookieProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    private final CookieProperties props;

    public CookieUtil(CookieProperties props) {
        this.props = props;
    }

    public String crearCookieAccessToken(String token, long maxAgeSegundos) {
        return crearCookie(props.getAccessTokenName(), token, maxAgeSegundos);
    }

    public String crearCookieRefreshToken(String token, long maxAgeSegundos) {
        return crearCookie(props.getRefreshTokenName(), token, maxAgeSegundos);
    }

    public String eliminarCookieAccessToken() {
        return crearCookie(props.getAccessTokenName(), "", 0);
    }

    public String eliminarCookieRefreshToken() {
        return crearCookie(props.getRefreshTokenName(), "", 0);
    }

    private String crearCookie(String nombre, String valor, long maxAgeSegundos) {
        return ResponseCookie.from(nombre, valor)
                .httpOnly(true)
                .secure(props.isSecure())
                .path(props.getPath())
                .sameSite("None")
                .maxAge(maxAgeSegundos)
                .build()
                .toString();
    }
}
