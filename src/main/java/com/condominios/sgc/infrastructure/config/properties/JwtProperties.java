package com.condominios.sgc.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
    private long rememberMeRefreshExpiration;
    private long resetPasswordTokenExpiration;
    private long verifyEmailTokenExpiration;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public long getAccessTokenExpiration() { return accessTokenExpiration; }
    public void setAccessTokenExpiration(long accessTokenExpiration) { this.accessTokenExpiration = accessTokenExpiration; }

    public long getRefreshTokenExpiration() { return refreshTokenExpiration; }
    public void setRefreshTokenExpiration(long refreshTokenExpiration) { this.refreshTokenExpiration = refreshTokenExpiration; }

    public long getRememberMeRefreshExpiration() { return rememberMeRefreshExpiration; }
    public void setRememberMeRefreshExpiration(long rememberMeRefreshExpiration) { this.rememberMeRefreshExpiration = rememberMeRefreshExpiration; }

    public long getResetPasswordTokenExpiration() { return resetPasswordTokenExpiration; }
    public void setResetPasswordTokenExpiration(long resetPasswordTokenExpiration) { this.resetPasswordTokenExpiration = resetPasswordTokenExpiration; }

    public long getVerifyEmailTokenExpiration() { return verifyEmailTokenExpiration; }
    public void setVerifyEmailTokenExpiration(long verifyEmailTokenExpiration) { this.verifyEmailTokenExpiration = verifyEmailTokenExpiration; }
}
