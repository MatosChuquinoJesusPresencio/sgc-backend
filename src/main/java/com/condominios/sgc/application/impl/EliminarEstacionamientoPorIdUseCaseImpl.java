package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.usecase.EliminarEstacionamientoPorIdUseCase;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class EliminarEstacionamientoPorIdUseCaseImpl implements EliminarEstacionamientoPorIdUseCase {
    private final EstacionamientoPort estacionamientoPort;

    public EliminarEstacionamientoPorIdUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public void ejecutar(Long id) {
        estacionamientoPort.obtenerPorId(id)
            .orElseThrow(EstacionamientoException::noEncontrado);

        estacionamientoPort.eliminarPorId(id);
    }
}
