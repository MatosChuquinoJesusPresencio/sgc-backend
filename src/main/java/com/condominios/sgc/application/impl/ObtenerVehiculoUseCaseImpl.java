package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.VehiculoResponse;
import com.condominios.sgc.application.usecase.ObtenerVehiculoUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ObtenerVehiculoUseCaseImpl implements ObtenerVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public ObtenerVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse obtenerPorId(Long id) {
        VehiculoModel modelo = vehiculoPort.findById(id)
                .orElseThrow(() -> VehiculoException.vehiculoNoExistePorId(id));
        return new VehiculoResponse(
                modelo.getId(),
                modelo.getMarca(),
                modelo.getColor(),
                modelo.getModelo(),
                modelo.getPlaca(),
                modelo.getPropietarioId(),
                modelo.getInquilinoId());
    }
}
