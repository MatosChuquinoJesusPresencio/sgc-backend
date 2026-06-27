package com.condominios.sgc.infrastructure.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

public class CookieBearerTokenResolver implements BearerTokenResolver {

    private final String accessCookieName;
    private final DefaultBearerTokenResolver defaultResolver = new DefaultBearerTokenResolver();

    public CookieBearerTokenResolver(String accessCookieName) {
        this.accessCookieName = accessCookieName;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (accessCookieName.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (value != null && !value.isBlank()) {
                        return value;
                    }
                }
            }
        }
        return defaultResolver.resolve(request);
    }
}
