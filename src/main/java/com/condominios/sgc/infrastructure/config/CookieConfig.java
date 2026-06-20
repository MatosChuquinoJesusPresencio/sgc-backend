package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.infrastructure.config.properties.CookieProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CookieProperties.class)
public class CookieConfig {
}
