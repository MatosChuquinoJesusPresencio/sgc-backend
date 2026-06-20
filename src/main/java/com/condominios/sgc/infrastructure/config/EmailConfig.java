package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.infrastructure.config.properties.AppProperties;
import com.condominios.sgc.infrastructure.config.properties.ResendProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({ResendProperties.class, AppProperties.class})
public class EmailConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
