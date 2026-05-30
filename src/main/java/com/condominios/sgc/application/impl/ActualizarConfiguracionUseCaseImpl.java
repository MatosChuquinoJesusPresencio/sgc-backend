package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarConfiguracionRequest;
import com.condominios.sgc.application.usecase.ActualizarConfiguracionUseCase;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;

public class ActualizarConfiguracionUseCaseImpl implements ActualizarConfiguracionUseCase {
    
    private final ConfiguracionPort configuracionPort;

    public ActualizarConfiguracionUseCaseImpl(ConfiguracionPort configuracionPort) {
        this.configuracionPort = configuracionPort;
    }

    @Override
    public ConfiguracionModel ejecutar(Long id, ActualizarConfiguracionRequest request) {
        ConfiguracionModel configuracion = configuracionPort.findById(id)
            .orElseThrow(ConfiguracionException::noEncontrada);

        configuracion.actualizarDatos(
            request.maxAutos(), request.maxMotos(), request.penalizacionPorMin(),
            request.maxTiempoPrestamoMin(), request.maxEstacionamientosPorApartamento(),
            request.maxCarritosPorApartamento(), request.maxVehiculosPorPropietario(),
            request.maxInquilinosPorApartamento()
        );

        return configuracionPort.save(configuracion);
    } 
}
