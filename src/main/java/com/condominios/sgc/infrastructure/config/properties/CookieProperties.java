package com.condominios.sgc.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cookie")
public class CookieProperties {

    private String accessTokenName;
    private String refreshTokenName;
    private String path;
    private boolean secure;

    public String getAccessTokenName() { return accessTokenName; }
    public void setAccessTokenName(String accessTokenName) { this.accessTokenName = accessTokenName; }

    public String getRefreshTokenName() { return refreshTokenName; }
    public void setRefreshTokenName(String refreshTokenName) { this.refreshTokenName = refreshTokenName; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public boolean isSecure() { return secure; }
    public void setSecure(boolean secure) { this.secure = secure; }
}
