package com.condominios.sgc.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.condominios.sgc.domain.port.CorreoPort;
import com.condominios.sgc.infrastructure.adapter.CorreoAdapter;
import com.condominios.sgc.infrastructure.client.SmtpClient;

@Configuration
public class CorreoConfig {

    @Bean
    public SmtpClient smtpClient(
            RestTemplate restTemplate,
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from}") String from) {
        return new SmtpClient(restTemplate, apiKey, from);
    }

    @Bean
    public CorreoPort correoPort(
            SmtpClient smtpClient,
            @Value("${app.base-url}") String baseUrl) {
        return new CorreoAdapter(smtpClient, baseUrl);
    }
}
