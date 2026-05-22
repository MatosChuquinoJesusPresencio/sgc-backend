package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.CrearVehiculoRequest;
import com.condominios.sgc.application.dto.VehiculoResponse;
import com.condominios.sgc.application.usecase.CrearVehiculoUseCase;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;

import com.condominios.sgc.domain.exception.VehiculoException;

public class CrearVehiculoUseCaseImpl implements CrearVehiculoUseCase {

    private final VehiculoPort vehiculoPort;

    public CrearVehiculoUseCaseImpl(VehiculoPort vehiculoPort) {
        this.vehiculoPort = vehiculoPort;
    }

    @Override
    public VehiculoResponse crear(CrearVehiculoRequest request) {
        boolean tienePropietario = request.propietarioId() != null && !request.propietarioId().isBlank();
        boolean tieneInquilino = request.inquilinoId() != null;
        if (tienePropietario == tieneInquilino) {
            throw VehiculoException.duenoInvalido();
        }

        VehiculoModel modelo = new VehiculoModel(
                request.marca(),
                request.color(),
                request.modelo(),
                request.placa(),
                request.tipo(),
                request.propietarioId(),
                request.inquilinoId(),
                request.estacionamientoId());

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
