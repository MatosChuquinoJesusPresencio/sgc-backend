package com.condominios.sgc.application.impl.estacionamiento;

import com.condominios.sgc.application.dto.response.EstacionamientoResponse;
import com.condominios.sgc.application.usecase.estacionamiento.ObtenerEstacionamientoPorIdUseCase;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ObtenerEstacionamientoPorIdUseCaseImpl implements ObtenerEstacionamientoPorIdUseCase {
    private final EstacionamientoPort estacionamientoPort;

    public ObtenerEstacionamientoPorIdUseCaseImpl(EstacionamientoPort estacionamientoPort) {
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public EstacionamientoResponse ejecutar(Long id) {
        return estacionamientoPort.obtenerPorId(id)
            .map(EstacionamientoResponse::desdeModelo)
            .orElseThrow(EstacionamientoException::noEncontrado);
    }
}
