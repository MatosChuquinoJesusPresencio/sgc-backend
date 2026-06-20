package com.condominios.sgc.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resend")
public class ResendProperties {

    private String apiKey;
    private String from;

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
}
