package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ObtenerDashboardSuperAdminUseCaseImpl;
import com.condominios.sgc.application.usecase.ObtenerDashboardSuperAdminUseCase;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.UsuarioPort;

@Configuration
public class DashboardConfig {

    @Bean
    public ObtenerDashboardSuperAdminUseCase obtenerDashboardSuperAdminUseCase(
            CondominioPort condominioPort,
            UsuarioPort usuarioPort,
            TorrePort torrePort,
            LogPrestamoCarritoPort logPrestamoPort,
            LogAccesoVehicularPort logAccesoPort) {
        return new ObtenerDashboardSuperAdminUseCaseImpl(
                condominioPort, usuarioPort, torrePort, logPrestamoPort, logAccesoPort);
    }
}
