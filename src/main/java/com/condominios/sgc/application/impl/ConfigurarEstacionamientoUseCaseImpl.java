package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.ConfigurarEstacionamientoUseCase;
import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ConfigurarEstacionamientoUseCaseImpl implements ConfigurarEstacionamientoUseCase {

    private final EstacionamientoPort estacionamientoPort;

    public ConfigurarEstacionamientoUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public EstacionamientoModel ejecutar(Long id, TipoVehiculo tipoVehiculo, Integer capacidadMaxima) {
        EstacionamientoModel estacionamiento = estacionamientoPort.findById(id)
                .orElseThrow(() -> EstacionamientoException.noEncontrado(id));

        estacionamiento.configurar(tipoVehiculo, capacidadMaxima);

        return estacionamientoPort.save(estacionamiento);
    }
}
