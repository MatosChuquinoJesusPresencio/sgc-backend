package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.dto.VehiculoResponse;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

public class CrearVehiculoUseCaseImpl implements CrearVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public CrearVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse crear(CrearVehiculoRequest request) {
        VehiculoModel modelo = new VehiculoModel(
                request.marca(),
                request.color(),
                request.modelo(),
                request.placa(),
                request.tipo(),
                null,
                null,
                null);

        VehiculoModel guardado = vehiculoPort.save(modelo);
        return new VehiculoResponse(
                guardado.getId(),
                guardado.getMarca(),
                guardado.getColor(),
                guardado.getModelo(),
                guardado.getPlaca(),
                guardado.getPropietarioId(),
                guardado.getInquilinoId());
    }
}
