package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.ActualizarVehiculoRequest;
import com.condominios.sgc.application.dto.VehiculoResponse;
import com.condominios.sgc.application.usecase.ActualizarVehiculoUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class ActualizarVehiculoUseCaseImpl implements ActualizarVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public ActualizarVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse ejecutar(Long id, ActualizarVehiculoRequest request) {
        VehiculoModel modelo = vehiculoPort.findById(id)
                .orElseThrow(() -> VehiculoException.vehiculoNoExistePorId(id));
        modelo.actualizarDatos(request.color());
        VehiculoModel guardado = vehiculoPort.save(modelo);
        return new VehiculoResponse(
                guardado.getId(),
                guardado.getMarca(),
                guardado.getColor(),
                guardado.getModelo(),
                guardado.getPlaca(),
                guardado.getTipo(),
                guardado.getPropietarioId(),
                guardado.getInquilinoId());
    }
}
