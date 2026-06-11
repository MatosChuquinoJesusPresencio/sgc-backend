package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ObtenerDashboardAdminCondominioUseCaseImpl;
import com.condominios.sgc.application.usecase.ObtenerDashboardAdminCondominioUseCase;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.EstacionamientoPort;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.UsuarioPort;

@Configuration
public class DashboardAdminCondominioConfig {

    @Bean
    public ObtenerDashboardAdminCondominioUseCase obtenerDashboardAdminCondominioUseCase(
            CondominioPort condominioPort,
            UsuarioPort usuarioPort,
            TorrePort torrePort,
            PisoPort pisoPort,
            ApartamentoPort apartamentoPort,
            EstacionamientoPort estacionamientoPort,
            ConfiguracionPort configuracionPort,
            LogPrestamoCarritoPort logPrestamoPort,
            LogAccesoVehicularPort logAccesoPort) {
        return new ObtenerDashboardAdminCondominioUseCaseImpl(
                condominioPort, usuarioPort, torrePort, pisoPort, apartamentoPort,
                estacionamientoPort, configuracionPort, logPrestamoPort, logAccesoPort);
    }
}
